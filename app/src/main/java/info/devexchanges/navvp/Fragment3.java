package info.devexchanges.navvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

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
    private void init(View view){
        bt_addDrink=(Button) view.findViewById(R.id.bt_addDrink);
        bt_addDrink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_addDrink);
            }
        });

        bt_liter005=(Button)view.findViewById(R.id.bt_liters1);
        bt_liter005.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter005);
            }
        });

        bt_liter01=(Button)view.findViewById(R.id.bt_liters2);
        bt_liter01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter01);
            }
        });

        bt_liter033=(Button)view.findViewById(R.id.bt_liters3);
        bt_liter033.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter033);
            }
        });

        bt_liter05=(Button)view.findViewById(R.id.bt_liters4);
        bt_liter05.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter05);
            }
        });

        bt_liter1=(Button)view.findViewById(R.id.bt_liters5);
        bt_liter1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                animateBt(bt_liter1);
            }
        });

    }

}
