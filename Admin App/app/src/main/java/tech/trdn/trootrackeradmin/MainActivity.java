package tech.trdn.trootrackeradmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button user_approval,leave_approval,today,anal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_approval = findViewById(R.id.new_user_b);
        leave_approval = findViewById(R.id.leave_app_b);
        today = findViewById(R.id.today_b);
        anal = findViewById(R.id.anal_b);
        user_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,UserApprovalList.class);
                startActivity(i);
            }
        });
        leave_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(MainActivity.this,LeaveApproval.class);
                startActivity(j);
            }
        });
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(MainActivity.this,TodayActivity.class);
                startActivity(k);
            }
        });
        anal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(l);
            }
        });
    }
}
