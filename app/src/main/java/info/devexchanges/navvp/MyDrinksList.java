package info.devexchanges.navvp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MyDrinksList extends AppCompatActivity implements View.OnClickListener {
    int[] IMAGES ;
    String[] DRINK_NAMES;
    String[] ALCO_LEVEL;
    String[] VOLUME;


    List<JSONObject> drinks;
    DataStorage ds;
    ListView favList;

    int alcoID_tmp=-1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drinks_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        favList = (ListView) findViewById(R.id.favlist);
        MyDrinksList.CustomAdapterFav customadapterfav = new MyDrinksList.CustomAdapterFav();

        favList.setAdapter(customadapterfav);


    }

    @Override
    public void onResume(){
        super.onResume();
        addDrinks();
        favList.setAdapter(new MyDrinksList.CustomAdapterFav());
    }


    private void init() {
        ds=new DataStorage(this);
        addDrinks();

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

    @Override
    public void onClick(View view) {

    }
    private void animateBt(ImageView bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }
    private void animateView(View bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }

    /**
     Adds favorite drinks to list
     */
    private void addDrinks(){
        drinks=ds.getDrinks();
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
                Toast.makeText(this,"ERROR: Try clearing data of application",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void openAlert(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ds.removeDrink(alcoID_tmp);
                        addDrinks();
                        favList.setAdapter(new MyDrinksList.CustomAdapterFav());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setMessage("Delete this drink?")
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

            remove.setImageResource(R.drawable.delete);
            icon.setImageResource(IMAGES[i]);
            drink.setText(DRINK_NAMES[i]);
            alcolvl.setText(ALCO_LEVEL[i]);
            volume.setText(VOLUME[i]);

            view.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View view) {
                    animateView(view);
                    Intent intent = new Intent(getBaseContext(), EditDrink.class);
                    try {
                        Bundle b = new Bundle();
                        b.putInt("ID", drinks.get(i).getInt("drinkID"));
                        intent.putExtras(b);
                    } catch (JSONException e) {
                        Toast.makeText(getBaseContext(),"ERROR: Something went wrong when finding drink ID!",Toast.LENGTH_LONG).show();
                    }
                    startActivity(intent);

                }

            });
            remove.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateBt(remove);
                    try {

                        alcoID_tmp=drinks.get(i).getInt("drinkID");
                        openAlert();

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(),"ERROR: Something went wrong at deleting drink!",Toast.LENGTH_LONG).show();
                    }
                }


            });
            return view;
        }
        @Override
        public void onClick(View view) {

        }

    }
}
