package info.devexchanges.navvp;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;
import java.util.Random;

public class AmIDrunk extends AppCompatActivity implements View.OnClickListener {

    private Button but_StartTest;
    private Button but_FinishTest;
    private TextView tx_TimeDrunk;
    private long startTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_idrunk);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void init()
    {
        //Time: 10s , You are drunk
        but_StartTest = (Button) findViewById(R.id.but_StartTest) ;
        but_FinishTest = (Button) findViewById(R.id.but_butFinishTest);
        tx_TimeDrunk = (TextView) findViewById(R.id.timeDrunk);

        but_FinishTest.setVisibility(View.INVISIBLE);
    }

    public void onStartClick(View view) throws InterruptedException {
        animateBt(but_StartTest);
        Random r = new Random();

        final Handler hanlder = new Handler();
        hanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                but_FinishTest.setVisibility(View.VISIBLE);
                startTime = System.currentTimeMillis();
            }
        },r.nextInt(5)+2 * 1000);



    }

    public void onFinishClick(View view)
    {
        long responseTime = System.currentTimeMillis() - startTime;
        String msg = "Time: "+Math.round(responseTime) +"ms";

        if(responseTime < 500)
        {
            msg += ", You are SOBER";
        }
        else if(responseTime < 800)
        {
            msg += ", You are SLIGHTLY DRUNK";
        }
        else if(responseTime < 1000)
        {
            msg += ", You are DRUNK";
        }
        else
        {
            msg += ", You are VERY DRUNK";
        }

        tx_TimeDrunk.setText(msg);

        but_FinishTest.setVisibility(View.INVISIBLE);
    }

    private void animateBt(Button bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }
}
