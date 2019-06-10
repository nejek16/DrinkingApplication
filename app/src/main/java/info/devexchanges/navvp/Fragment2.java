package info.devexchanges.navvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            init(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    Timestamp reference_timestamp = new Timestamp(0);


    private void init(View view) throws JSONException {
        Graph_values();

    }

    public String Graph_values(){
        LineChart chart = (LineChart) getActivity().findViewById(R.id.chart);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);




        List<Entry> entries = new ArrayList<Entry>();

        List<Double> bac = new ArrayList<Double>();
        List<Double> bac_time = new ArrayList<Double>();
        DataStorage dataStorage = new DataStorage(getActivity());
        double widmark_factor = 0;
        double height = 0;
        double weight = 0;
        Date sober_time = new Date();
        if(dataStorage.getGender() == 'M'){
            weight = dataStorage.getWeight();
            height =  (double)dataStorage.getAge()/100;
            widmark_factor =1.0181-(0.01213*(weight/(height*height)));
        }else if(dataStorage.getGender() == 'F'){
            weight = dataStorage.getWeight();
            height =  (double)dataStorage.getAge()/100;
            widmark_factor =0.9367-(0.1240*(weight/(height*height)));
        }
        float Bac_level = 0;
        double Bac_level_time = 0;
        try {
            double mililiters = 0;
            double alco_level = 0;
            double cons = 0.789;
            Date time = new Date();
            List<JSONObject> consumed;
            consumed=dataStorage.getTimedConsumed(1440);

            Date first_drink_time = new Date(consumed.get(0).getString("time"));

            reference_timestamp = new Timestamp((new Date(consumed.get(consumed.size()-1).getString("time")).getTime()));

            for(int i = consumed.size() - 1; i >= 0 ; i--){
                for(int j = consumed.size() - 1; j >= i; j--){
                    mililiters = consumed.get(j).getDouble("quantity")*1000;
                    alco_level = consumed.get(j).getDouble("alco")/100;
                    time = new Date(consumed.get(j).getString("time"));
                    Bac_level +=((((mililiters * alco_level * cons)/(widmark_factor*dataStorage.getWeight()))/10));
                }


                Timestamp t2 = new Timestamp(time.getTime());
                Timestamp ts = new Timestamp((t2.getTime() -reference_timestamp.getTime())/1000);


                entries.add(new Entry(ts.getTime(),Bac_level));
                Bac_level = 0;
            }


            for(int j = 0; j < bac.size(); j++){
                Date currentTime = Calendar.getInstance().getTime();
                long diff = currentTime.getTime() - first_drink_time.getTime();
                long hours = diff / 1000 / 60 / 60;
                Bac_level += bac.get(j);
                Bac_level_time += bac_time.get(j);
            }
            Date currentTime = Calendar.getInstance().getTime();
            long diff = currentTime.getTime() - first_drink_time.getTime();
            long hours = diff / 1000 / 60 / 60;
            Bac_level -= hours*0.015;
            long min_sober = (long) ((Bac_level_time/0.015) * 60);
            Date targetTime;
            final long ONE_MINUTE_IN_MILLIS=60000;
            long t= currentTime.getTime();
            sober_time=new Date(t + (min_sober * ONE_MINUTE_IN_MILLIS));

            //entries.add(new Entry(sober_time.getTime(),0));
            IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(reference_timestamp.getTime());
            XAxis xAxis = chart.getXAxis();
            YAxis yAxis = chart.getAxisLeft();
            xAxis.setValueFormatter(xAxisFormatter);

            //style
            xAxis.setTextSize(14f);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            yAxis.setTextSize(14f);
            yAxis.setTextColor(Color.WHITE);



            //Timestamp tsober = new Timestamp(sober_time.getTime());
           // Timestamp tsob = new Timestamp((tsober.getTime() -reference_timestamp.getTime())/10);
           // entries.add(new Entry(tsob.getTime(),Bac_level));
            LineDataSet set1 = new LineDataSet(entries,"BAC");

            set1.setCubicIntensity(0.0009f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(2.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(R.color.lightBlue);
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setCircleRadius(7);
            chart.getAxisRight().setEnabled(false);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            LineData lineData = new LineData(set1);
            chart.setData(lineData);
            chart.invalidate();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }

        String sober = sober_time.getHours()+":"+(String.valueOf(sober_time.getMinutes()).length()==1?"0"+String.valueOf(sober_time.getMinutes()):String.valueOf(sober_time.getMinutes()));
        return sober;
        }
        //action bar
    }
