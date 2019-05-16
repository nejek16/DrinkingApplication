package info.devexchanges.navvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import info.devexchanges.navvp.DataStorage;

public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
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
    private void init(View view){
        not_favourite = (ImageView) view.findViewById(R.id.not_favourite);
        not_favourite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                not_favourite.setVisibility(View.GONE);
                favourite.setVisibility(View.VISIBLE);
            }
        });

        favourite = (ImageView) view.findViewById(R.id.favourite);
        favourite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourite.setVisibility(View.GONE);
                not_favourite.setVisibility(View.VISIBLE);
            }
        });

        bt_addDrink=(Button) view.findViewById(R.id.bt_addDrink);
        bt_addDrink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_addDrink);
                Toast.makeText(getActivity(),tx_cal.getText().length()==0?"0":String.valueOf(tx_cal.getText().toString()),Toast.LENGTH_SHORT).show();
                if(storeData()){
                    Toast.makeText(getActivity(),"Drink added!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Failed to add drink!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_liter005=(Button)view.findViewById(R.id.bt_liters1);
        bt_liter005.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter005);
                changeQuantityVal(0.05);
            }
        });

        bt_liter01=(Button)view.findViewById(R.id.bt_liters2);
        bt_liter01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter01);
                changeQuantityVal(0.1);
            }
        });

        bt_liter033=(Button)view.findViewById(R.id.bt_liters3);
        bt_liter033.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter033);
                changeQuantityVal(0.33);
            }
        });

        bt_liter05=(Button)view.findViewById(R.id.bt_liters4);
        bt_liter05.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter05);
                changeQuantityVal(0.5);
            }
        });

        bt_liter1=(Button)view.findViewById(R.id.bt_liters5);
        bt_liter1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter1);
                changeQuantityVal(1);
            }
        });


        tx_quant=(EditText) view.findViewById(R.id.edTx_quant);
        tx_alcoLvl=(EditText) view.findViewById(R.id.edTx_alcoLvl);
        tx_price=(EditText) view.findViewById(R.id.edTx_price);
        tx_cal=(EditText) view.findViewById(R.id.edTx_kcal);
        tx_drinkName=(EditText) view.findViewById(R.id.edTx_drinkName);
    }

    private void changeQuantityVal(double value){
        tx_quant.setText(String.valueOf(value));
    }

    /**
     Returns true if store successful
     */
    private boolean storeData(){
        try {
            String name=tx_drinkName.getText().toString();
            boolean favorite=favourite.getVisibility()==View.VISIBLE;
            double quantity=Double.parseDouble(tx_quant.getText().toString());
            double alco=Double.parseDouble(tx_alcoLvl.getText().toString());
            double cost=tx_price.getText().length()==0?0.:Double.parseDouble(tx_price.getText().toString());
            double kcal=tx_cal.getText().length()==0?0.:Double.parseDouble(tx_cal.getText().toString());

            if(name.length()>12){
                Toast.makeText(getActivity(),"Name of drink too long!",Toast.LENGTH_LONG).show();
                return false;
            }
            DataStorage ds=new DataStorage(getActivity());

            ds.addDrink(name,alco,null,favorite,quantity,cost,kcal);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

}
