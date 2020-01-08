package info.devexchanges.navvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private String[] pageTitle = {"Home", "Statistics", "Add new drink"};
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataStorage = new DataStorage(this);
        getApiData();

        setContentView(R.layout.activity_main);


        if(dataStorage.isFirstOpen()){
            Intent intent = new Intent(this, DesActivity.class);
            startActivity(intent);
        }

        //BAC calculation
        Bac_level();

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        //create default navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //setting Tab layout (number of Tabs = number of ViewPager pages)
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < pageTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }
        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //handling navigation view item event
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //Gtes data form Api and inserts it into file
    protected void getApiData()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://isalcotrackerapi.azurewebsites.net/api/Drinks";
        JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ApiKey","SecretKey");
                return params;
            }
        };
        requestQueue.add(request);


    }
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            JSONArray r = new JSONArray();
            for(int i=0;i < response.length();i++){
                JSONObject o = null;
                try {
                    o = response.getJSONObject(i);
                    o.put("icon",Integer.parseInt(o.getString("icon")));
                    o.put("alco",Double.parseDouble(o.getString("alcoholLevel")));
                    r.put(o);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            dataStorage.writeFile(r.toString(),"ATdrinks.json");
        }
    };
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.fr1) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.fr2) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.fr3) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.go) {
            Intent intent = new Intent(this, DesActivity.class);
            intent.putExtra("string", "Go to other Activity by NavigationView item cliked!");
            startActivity(intent);
        } else if (id == R.id.clear) {
            DataStorage ds=new DataStorage(this);
            ds.clearData();
        }
        else if (id == R.id.MyDrinks) {
            Intent intent = new Intent(this, MyDrinksList.class);
            intent.putExtra("string", "Go to other Activity by NavigationView item cliked!");
            startActivity(intent);
        }else if (id == R.id.AllConsumed) {
            Intent intent = new Intent(this, AllConsumed.class);
            intent.putExtra("string", "Go to other Activity by NavigationView item cliked!");
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void Bac_level(){
        List<Double> bac = new ArrayList<Double>();
        List<Double> bac_time = new ArrayList<Double>();
        DataStorage dataStorage = new DataStorage(this);
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
            //Toast.makeText(this,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }
        String sober = sober_time.getHours()+":"+(String.valueOf(sober_time.getMinutes()).length()==1?"0"+String.valueOf(sober_time.getMinutes()):String.valueOf(sober_time.getMinutes()));
        if(Bac_level <= 0){
            Bac_level = 0;
            sober = "you are";
            //end of drinking sesion
            //stop drinking button
        }
        //action bar
        TextView toolbarTxPro=(TextView)findViewById(R.id.promili);
        toolbarTxPro.setText(String.format("%.3f", Bac_level)+ " ‰");

        if(Bac_level > 0){
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
        TextView toolbarTxSob=(TextView)findViewById(R.id.sober);
        toolbarTxSob.setText("Sober: "+ sober);
    }
}
