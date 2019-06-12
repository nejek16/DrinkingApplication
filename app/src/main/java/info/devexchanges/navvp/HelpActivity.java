package info.devexchanges.navvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;


public class HelpActivity extends AppCompatActivity {
    int index = 0;
    ImageView image;
    final int[] photos = {R.drawable.zz_a1,R.drawable.zz_a2,R.drawable.zz_a3,R.drawable.zz_a4,R.drawable.zz_a5,R.drawable.zz_a6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        image = (ImageView) findViewById(R.id.tutorial);

        Button next = (Button) findViewById(R.id.tutnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void changeImage(){
                index+=1;
                if(index > 5){
                    index = 0;
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else{
                    image.setImageResource(photos[index]);
                }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    private void animateBt(Button bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }
}
