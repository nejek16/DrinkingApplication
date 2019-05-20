package info.devexchanges.navvp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditDrink extends AppCompatActivity {
    int drinkID = -1;
    DataStorage ds =new DataStorage(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drink);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        
        if(b != null)
            drinkID = b.getInt("ID");

        init();

        setData(ds.getDrinkById(drinkID));
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


    private void animateBt(Button bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }

    Button bt_addDrink;
    Button bt_liter005;
    Button bt_liter01;
    Button bt_liter033;
    Button bt_liter05;
    Button bt_liter1;
    EditText tx_quant;
    EditText tx_alcoLvl;
    EditText tx_price;
    EditText tx_cal;
    EditText tx_drinkName;
    ImageView not_favourite;
    ImageView favourite;
    ImageView icon_beer;
    ImageView icon_white_vine;
    ImageView icon_red_vine;
    ImageView icon_cocktail;
    ImageView icon_viski;
    ImageView icon_shot;
    int icon;
    private void init(){
        icon_beer = (ImageView) findViewById(R.id.icon_beer);
        icon_beer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                icon_beer.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
                icon = R.drawable.beer;
            }
        });

        icon_white_vine = (ImageView) findViewById(R.id.icon_white_vine);
        icon_white_vine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                icon_white_vine.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
                icon = R.drawable.white_wine;
            }
        });

        icon_red_vine = (ImageView) findViewById(R.id.icon_red_vine);
        icon_red_vine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                icon_red_vine.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
                icon = R.drawable.red_wine;
            }
        });

        icon_cocktail = (ImageView) findViewById(R.id.icon_cocktail);
        icon_cocktail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                icon_cocktail.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
                icon = R.drawable.martini;
            }
        });

        icon_viski = (ImageView) findViewById(R.id.icon_viski);
        icon_viski.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                icon_viski.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
                icon = R.drawable.viski;
            }
        });

        icon_shot = (ImageView) findViewById(R.id.icon_shot);
        icon_shot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                icon_shot.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon = R.drawable.shot;
            }
        });



        not_favourite = (ImageView) findViewById(R.id.not_favourite);
        not_favourite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                not_favourite.setVisibility(View.GONE);
                favourite.setVisibility(View.VISIBLE);
            }
        });

        favourite = (ImageView) findViewById(R.id.favourite);
        favourite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourite.setVisibility(View.GONE);
                not_favourite.setVisibility(View.VISIBLE);
            }
        });

        bt_addDrink=(Button) findViewById(R.id.bt_addDrink);
        bt_addDrink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_addDrink);
                if(storeData()){
                    Toast.makeText(getBaseContext(),"Drink changed!",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(),"Failed to change drink!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_liter005=(Button)findViewById(R.id.bt_liters1);
        bt_liter005.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter005);
                changeQuantityVal(0.05);
            }
        });

        bt_liter01=(Button)findViewById(R.id.bt_liters2);
        bt_liter01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter01);
                changeQuantityVal(0.1);
            }
        });

        bt_liter033=(Button)findViewById(R.id.bt_liters3);
        bt_liter033.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter033);
                changeQuantityVal(0.33);
            }
        });

        bt_liter05=(Button)findViewById(R.id.bt_liters4);
        bt_liter05.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter05);
                changeQuantityVal(0.5);
            }
        });

        bt_liter1=(Button)findViewById(R.id.bt_liters5);
        bt_liter1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter1);
                changeQuantityVal(1);
            }
        });


        tx_quant=(EditText) findViewById(R.id.edTx_quant);
        tx_alcoLvl=(EditText) findViewById(R.id.edTx_alcoLvl);
        tx_price=(EditText) findViewById(R.id.edTx_price);
        tx_cal=(EditText) findViewById(R.id.edTx_kcal);
        tx_drinkName=(EditText) findViewById(R.id.edTx_drinkName);
    }

    private void setData(JSONObject jsn){
        try {
            //Set icon
            icon = jsn.getInt("icon");
            if(icon== R.drawable.beer){
                icon_beer.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
            }else if(icon== R.drawable.white_wine){
                icon_white_vine.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
            }else if(icon== R.drawable.red_wine){
                icon_red_vine.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
            }else if(icon== R.drawable.martini){
                icon_cocktail.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
            }else if(icon== R.drawable.viski){
                icon_viski.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
                icon_shot.setBackgroundColor(Color.TRANSPARENT);
            }else if(icon== R.drawable.shot){
                icon_shot.setBackground(getResources().getDrawable(R.drawable.rounded1));
                icon_white_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_red_vine.setBackgroundColor(Color.TRANSPARENT);
                icon_cocktail.setBackgroundColor(Color.TRANSPARENT);
                icon_viski.setBackgroundColor(Color.TRANSPARENT);
                icon_beer.setBackgroundColor(Color.TRANSPARENT);
            }



            //Favorite
            if(jsn.getBoolean("favorite")){
                favourite.setVisibility(View.VISIBLE);
                not_favourite.setVisibility(View.GONE);
            }else{
                favourite.setVisibility(View.GONE);
                not_favourite.setVisibility(View.VISIBLE);
            }

            //Drink name
            tx_drinkName.setText(jsn.getString("name"));

            //Quantity
            tx_quant.setText(jsn.getString("quantity"));

            //Alcohol level
            tx_alcoLvl.setText(jsn.getString("alco"));

            //Price
            tx_price.setText(jsn.getString("cost"));

            //Calories
            tx_cal.setText(jsn.getString("kcal"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeQuantityVal(double value){
        tx_quant.setText(String.valueOf(value));
    }

    /**
     Returns true if store successful
     */
    private boolean storeData(){
        try {
            String name=tx_drinkName.getText().toString().trim();
            boolean favorite=favourite.getVisibility()==View.VISIBLE;
            double quantity=Double.parseDouble(tx_quant.getText().toString());
            double alco=Double.parseDouble(tx_alcoLvl.getText().toString());
            double cost=tx_price.getText().length()==0?0.:Double.parseDouble(tx_price.getText().toString());
            double kcal=tx_cal.getText().length()==0?0.:Double.parseDouble(tx_cal.getText().toString());

            if(name.length()>15){
                Toast.makeText(this,"Name of drink too long!",Toast.LENGTH_LONG).show();
                return false;
            }

            if(ds.editbyDrinkId(drinkID,name,alco,icon,favorite,quantity,cost,kcal)){
                return true;
            }else{
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

}
