package info.devexchanges.navvp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllConsumed extends AppCompatActivity {
    int[] IMAGES ;
    String[] DRINK_NAMES;
    String[] ALCO_LEVEL;
    String[] VOLUME;


    List<JSONObject> consumed;
    DataStorage ds;
    ListView favList;

    int alcoID_tmp=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_consumed);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        favList = (ListView) findViewById(R.id.favlist);
        AllConsumed.CustomAdapterFav customadapterfav = new AllConsumed.CustomAdapterFav();

        favList.setAdapter(customadapterfav);
    }
    @Override
    public void onResume(){
        super.onResume();
        addConsumed();
        favList.setAdapter(new AllConsumed.CustomAdapterFav());
    }


    private void init() {
        ds=new DataStorage(this);
        addConsumed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void animateBt(ImageView bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }


    /**
     Adds consumed to list
     */
    private void addConsumed(){
        consumed=ds.getConsumed();
        List<String> names=new ArrayList<String>();
        List<String> alco=new ArrayList<String>();
        List<String> volume=new ArrayList<String>();
        List<Integer> images=new ArrayList<Integer>();
        String curDate="";
        for(int i=0;i<consumed.size();i++){
            JSONObject drink=consumed.get(i);
            try {
                Date date=new Date(drink.getString("time"));
                String drink_date=DateFormat.format("dd",   date)+"."+DateFormat.format("MM",   date)+"."+DateFormat.format("yyyy", date);
                if(!curDate.equals(drink_date)){
                    curDate=drink_date;
                    names.add(curDate);
                    alco.add("");
                    volume.add("");
                    images.add(R.drawable.zz_calendar);
                }
                names.add(drink.getString("name"));

                alco.add(date.getHours()+":"+(String.valueOf(date.getMinutes()).length()==1?"0"+String.valueOf(date.getMinutes()):String.valueOf(date.getMinutes())));
                volume.add(drink.getString("quantity")+" l");
                images.add(drink.getInt("icon"));
            } catch (JSONException e) {
                Toast.makeText(this,"ERROR: Try clearing data of application",Toast.LENGTH_LONG).show();
            }
        }
        DRINK_NAMES=getArrayString(names);
        ALCO_LEVEL=getArrayString(alco);
        VOLUME=getArrayString(volume);
        IMAGES=getArrayInt(images);
    }

    private String[] getArrayString(List<String> array){
        String[] arr=new String[array.size()];
        for(int i=0;i<array.size();i++){
            arr[i]=array.get(i);
        }
        return arr;
    }
    private int[] getArrayInt(List<Integer> array){
        int[] arr=new int[array.size()];
        for(int i=0;i<array.size();i++){
            arr[i]=array.get(i);
        }
        return arr;
    }

    private void openAlert(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ds.removeConsumed(alcoID_tmp);
                        addConsumed();
                        favList.setAdapter(new AllConsumed.CustomAdapterFav());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setMessage("Delete this consumed?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    //list fovouritem
    class CustomAdapterFav extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return DRINK_NAMES.length;//size of list
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.favourite_list_item,null);
            ImageView icon =(ImageView)view.findViewById(R.id.list_item_thumbnail);
            final ImageView remove =(ImageView)view.findViewById(R.id.drink_add);
            TextView drink = (TextView)view.findViewById(R.id.list_item_drink);
            TextView alcolvl = (TextView)view.findViewById(R.id.list_item_alco);
            TextView volume = (TextView)view.findViewById(R.id.list_item_price);

            if(IMAGES[i]!= R.drawable.zz_calendar) {
                remove.setImageResource(R.drawable.delete);
                icon.setImageResource(IMAGES[i]);
                drink.setText(DRINK_NAMES[i]);
                alcolvl.setText(ALCO_LEVEL[i]);
                volume.setText(VOLUME[i]);

                remove.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        animateBt(remove);
                        try {

                            alcoID_tmp = consumed.get(i).getInt("alcoID");
                            openAlert();

                        } catch (Exception e) {
                            Toast.makeText(getBaseContext(), "ERROR: Something went wrong at deleting drink!", Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }else{
                remove.setImageResource(R.drawable.zz_empty);
                icon.setImageResource(R.drawable.zz_empty);
                icon.setLayoutParams( new LinearLayout.LayoutParams(50,50));
                drink.setText(DRINK_NAMES[i]);
                alcolvl.setText(ALCO_LEVEL[i]);
                volume.setText(VOLUME[i]);
                view.setBackgroundResource(R.drawable.rounded1);
            }
            return view;
        }
        @Override
        public void onClick(View view) {

        }

    }
}
