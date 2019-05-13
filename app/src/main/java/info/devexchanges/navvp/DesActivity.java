package info.devexchanges.navvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DesActivity extends AppCompatActivity implements View.OnClickListener {


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
        txtInputAge.setText("Enter your age");

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
        ImageView male = (ImageView) findViewById(R.id.male);
        ImageView female = (ImageView) findViewById(R.id.female);
        if(view.getId() == R.id.male){
            male.setBackground(getResources().getDrawable(R.drawable.rounded1));
            female.setBackgroundColor(Color.TRANSPARENT);
        }else{
            female.setBackground(getResources().getDrawable(R.drawable.rounded1));
            male.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
