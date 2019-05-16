package info.devexchanges.navvp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DesActivity extends AppCompatActivity implements View.OnClickListener {

    DataStorage dataStorage = new DataStorage(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView male = (ImageView) findViewById(R.id.male);
        ImageView female = (ImageView) findViewById(R.id.female);

        TextView maletxt = (TextView) findViewById(R.id.maletxt);
        TextView femaletxt = (TextView) findViewById(R.id.femaletxt);
        maletxt.setText("  Male");
        femaletxt.setText("  Female");
        TextView txtInputKg = (TextView) findViewById(R.id.txtInputKg);
        TextView txtInputAge = (TextView) findViewById(R.id.txtInputAge);
        txtInputKg.setText("Enter your weight");
        txtInputAge.setText("Enter your height");

        Button new_user = (Button)findViewById(R.id.bt_newuser);
        new_user.setOnClickListener(this);

        male.setOnClickListener(this);
        female.setOnClickListener(this);
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
        Character gender = 'M';
        EditText edTx_weight = (EditText) findViewById(R.id.edTx_weight);
        EditText edTx_age = (EditText) findViewById(R.id.edTx_age);
        ImageView male = (ImageView) findViewById(R.id.male);
        ImageView female = (ImageView) findViewById(R.id.female);
        Button new_user = (Button)findViewById(R.id.bt_newuser);
        if(view.getId() == R.id.male){
            male.setBackground(getResources().getDrawable(R.drawable.rounded1));
            female.setBackgroundColor(Color.TRANSPARENT);
            gender = 'M';
        }else if(view.getId() == R.id.female){
            female.setBackground(getResources().getDrawable(R.drawable.rounded1));
            male.setBackgroundColor(Color.TRANSPARENT);
            gender = 'F';
        }
        if(view.getId() == R.id.bt_newuser){
            int age,weight;
            try {
                age = Integer.parseInt(edTx_age.getText().toString());
                weight = Integer.parseInt(edTx_weight.getText().toString());
                animateBt(new_user);
                dataStorage.setAge(age);
                dataStorage.setGender(gender);
                dataStorage.setWeight(weight);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Please input your information", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void animateBt(Button bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }
}
