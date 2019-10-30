package tech.trdn.troodontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button checkin,manage,request;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        checkin = findViewById(R.id.checkin_b);
        manage = findViewById(R.id.manage_b);
        request = findViewById(R.id.request_b);
        bundle = getIntent().getExtras();

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CheckActivity.class);
                i.putExtra("id",bundle.getString("id"));
                startActivity(i);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(MainActivity.this,LeaveMainActivity.class);
                j.putExtra("id",bundle.getString("id"));
                startActivity(j);
            }
        });

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(MainActivity.this,ManageLeave.class);
                k.putExtra("id",bundle.getString("id"));
                startActivity(k);
            }
        });
    }
}
