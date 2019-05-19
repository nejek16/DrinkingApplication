package info.devexchanges.navvp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }
    int[] IMAGES ;
    String[] DRINK_NAMES;
    String[] ALCO_LEVEL;
    String[] VOLUME;

    int[] IMAGES_c;
    String[] DRINK_NAMES_c;
    String[] ALCO_LEVEL_c;
    String[] VOLUME_c;

    List<JSONObject> drinks;
    List<JSONObject> consumed;
    DataStorage ds;

    ListView conList;
    ListView favList;

    int alcoID_tmp=-1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        favList = (ListView) view.findViewById(R.id.favlist);
        conList = (ListView) view.findViewById(R.id.conlist);
        CustomAdapterFav customadapterfav = new CustomAdapterFav();
        CustomAdapterCon customadaptercon = new CustomAdapterCon();

        favList.setAdapter(customadapterfav);
        conList.setAdapter(customadaptercon);


        //Hides view of other tab (fragment_content)
        // LinearLayout contentFrag=(LinearLayout) view.findViewById(R.id.viewAddDrink);
        // contentFrag.setVisibility(View.GONE);
    }
    @Override
    public void onResume(){
        super.onResume();
        addFavDrinks();
        addConsumed();
        favList.setAdapter(new Fragment1.CustomAdapterFav());
        conList.setAdapter(new Fragment1.CustomAdapterCon());
    }

    TextView txtFavorites;
    TextView txtConsumed;
    private void init(View view) {
        ds=new DataStorage(getActivity());
        addFavDrinks();
        addConsumed();



        txtFavorites = (TextView) view.findViewById(R.id.txtFavorites);
        txtFavorites.setText("Favorites");
        txtConsumed = (TextView) view.findViewById(R.id.txtConsumed);
        txtConsumed.setText("Consumed");
    }

    /**
     Adds favorite drinks to list
     */
    private void addFavDrinks(){
        drinks=ds.getFavDrinks();
        DRINK_NAMES=new String[drinks.size()];
        ALCO_LEVEL=new String[drinks.size()];
        VOLUME=new String[drinks.size()];
        IMAGES=new int[drinks.size()];
        for(int i=0;i<drinks.size();i++){
            JSONObject drink=drinks.get(i);
            try {
                DRINK_NAMES[i]=drink.getString("name");
                ALCO_LEVEL[i]=drink.getString("alco")+" %";
                VOLUME[i]=drink.getString("quantity")+" l";
                IMAGES[i]=drink.getInt("icon");
            } catch (JSONException e) {
                Toast.makeText(getActivity(),"ERROR: Try clearing data of application",Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     Adds consumed to list
     */
    private void addConsumed(){
        consumed=ds.getTimedConsumed(1440);
        DRINK_NAMES_c=new String[consumed.size()];
        ALCO_LEVEL_c=new String[consumed.size()];
        VOLUME_c=new String[consumed.size()];
        IMAGES_c=new int[consumed.size()];
        for(int i=0;i<consumed.size();i++){
            JSONObject drink=consumed.get(i);
            try {
                DRINK_NAMES_c[i]=drink.getString("name");
                Date time=new Date(drink.getString("time"));
                ALCO_LEVEL_c[i]=time.getHours()+":"+(String.valueOf(time.getMinutes()).length()==1?"0"+String.valueOf(time.getMinutes()):String.valueOf(time.getMinutes()));
                VOLUME_c[i]=drink.getString("quantity")+" l";
                IMAGES_c[i]=drink.getInt("icon");
            } catch (JSONException e) {
                Toast.makeText(getActivity(),"ERROR: Try clearing data of application",Toast.LENGTH_LONG).show();
            }
        }
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
            view = getActivity().getLayoutInflater().inflate(R.layout.favourite_list_item,null);
            ImageView icon =(ImageView)view.findViewById(R.id.list_item_thumbnail);
            final ImageView add =(ImageView)view.findViewById(R.id.drink_add);
            TextView drink = (TextView)view.findViewById(R.id.list_item_drink);
            TextView alcolvl = (TextView)view.findViewById(R.id.list_item_alco);
            TextView volume = (TextView)view.findViewById(R.id.list_item_price);

            add.setImageResource(R.drawable.add);
            icon.setImageResource(IMAGES[i]);
            drink.setText(DRINK_NAMES[i]);
            alcolvl.setText(ALCO_LEVEL[i]);
            volume.setText(VOLUME[i]);


            add.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateBt(add);
                    try {
                        Date cur=Calendar.getInstance().getTime();
                        String time=cur.toString();
                        int drinkID=drinks.get(i).getInt("drinkID");
                        String name=drinks.get(i).getString("name");
                        double alco=drinks.get(i).getDouble("alco");
                        int icon=drinks.get(i).getInt("icon");
                        Boolean favorite=drinks.get(i).getBoolean("favorite");
                        double quantity=drinks.get(i).getDouble("quantity");
                        double cost=drinks.get(i).getDouble("cost");
                        double kcal=drinks.get(i).getDouble("kcal");
                        //Toast.makeText(getActivity(),time,Toast.LENGTH_LONG).show();
                        ds.addConsumed(time,drinkID,name,alco,icon,favorite,quantity,cost,kcal);
                        addConsumed();
                        conList.setAdapter(new CustomAdapterCon());
                        Bac_level();

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(),"ERROR: Something went wrong at adding consumed!",Toast.LENGTH_LONG).show();
                    }
                }

            });
            return view;
        }
        @Override
        public void onClick(View view) {

        }
    }
    //consumed
    class CustomAdapterCon extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return DRINK_NAMES_c.length;//size of list
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
            view = getActivity().getLayoutInflater().inflate(R.layout.favourite_list_item_consumed,null);
            ImageView icon =(ImageView)view.findViewById(R.id.list_item_thumbnail_con);
            final ImageView delete =(ImageView)view.findViewById(R.id.drink_delete);
            TextView drink = (TextView)view.findViewById(R.id.list_item_drink_con);
            TextView alcolvl = (TextView)view.findViewById(R.id.list_item_alco_con);
            TextView volume = (TextView)view.findViewById(R.id.list_item_price_con);

            delete.setImageResource(R.drawable.delete);
            icon.setImageResource(IMAGES_c[i]);
            drink.setText(DRINK_NAMES_c[i]);
            alcolvl.setText(ALCO_LEVEL_c[i]);
            volume.setText(VOLUME_c[i]);


            delete.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateBt(delete);
                    try {
                        alcoID_tmp=consumed.get(i).getInt("alcoID");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog);
                        builder.setMessage("Delete this consumed drink")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();



                    } catch (JSONException e) {
                        Toast.makeText(getActivity(),"ERROR: Failed at deleting consumed!",Toast.LENGTH_LONG).show();
                    }
                }

            });
            return view;
        }
        @Override
        public void onClick(View view) {

        }
    }
    private void animateBt(ImageView bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int choice) {
            switch (choice) {
                case DialogInterface.BUTTON_POSITIVE:
                    ds.removeConsumed(alcoID_tmp);
                    addConsumed();
                    conList.setAdapter(new CustomAdapterCon());
                    Bac_level();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void Bac_level(){
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
        double Bac_level = 0;
        double Bac_level_time = 0;
        try {
            double mililiters = 0;
            double alco_level = 0;
            double cons = 0.789;
            Date time;
            List<JSONObject> consumed;
            consumed=dataStorage.getTimedConsumed(1440);
            Date first_drink_time = new Date(consumed.get(0).getString("time"));
            for(int i = 0; i < consumed.size(); i++){
                mililiters = consumed.get(i).getDouble("quantity")*1000;
                alco_level = consumed.get(i).getDouble("alco")/100;
                time = new Date(consumed.get(i).getString("time"));
                if(first_drink_time.after(time)){
                    first_drink_time = time;
                }
                first_drink_time = time;
                Date currentTime = Calendar.getInstance().getTime();
                long diff = currentTime.getTime() - time.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;

                if(minutes >= 25){
                    minutes = 20;
                }
                if(minutes == 0){
                    minutes = 1;
                }
                double min = minutes *0.05; //20 minutes to drink to take effect
                bac.add((((mililiters * alco_level * cons)/(widmark_factor*dataStorage.getWeight()))/10)*min);//minus time
                bac_time.add((((mililiters * alco_level * cons)/(widmark_factor*dataStorage.getWeight()))/10));
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
        } catch (Exception e) {
            Toast.makeText(getActivity(),"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }
        String sober = sober_time.getHours()+":"+(String.valueOf(sober_time.getMinutes()).length()==1?"0"+String.valueOf(sober_time.getMinutes()):String.valueOf(sober_time.getMinutes()));
        if(Bac_level <= 0){
            Bac_level = 0;
            sober = "you are";
            //end of drinking sesion
            //stop drinking button
        }
        //action bar
        TextView toolbarTxPro=(TextView)getActivity().findViewById(R.id.promili);
        toolbarTxPro.setText(String.format("%.3f", Bac_level)+ " ‰");
        if(Bac_level == 0){
            toolbarTxPro.setTextColor(getResources().getColor(R.color.green));
        }if(Bac_level > 0.08){
            toolbarTxPro.setTextColor(getResources().getColor(R.color.less_green));
        }if(Bac_level > 0.16){
            toolbarTxPro.setTextColor(getResources().getColor(R.color.yellow));
        }if(Bac_level > 0.24){
            toolbarTxPro.setTextColor(getResources().getColor(R.color.orange));
        }if(Bac_level > 0.32){
            toolbarTxPro.setTextColor(getResources().getColor(R.color.red));
        }

        TextView toolbarTxSob=(TextView)getActivity().findViewById(R.id.sober);
        toolbarTxSob.setText("Sober: "+ sober);
    }
}
