package tech.trdn.troodontracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeaveMainActivity extends AppCompatActivity {
    Button newRequest;
    Bundle bundle;
    RecyclerView recyclerView;
    LeaveAdapter adapter;
    ResultSet resultSet;
    String empid;
    List<LeaveInfo> userInfoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_main_activity);
        newRequest = findViewById(R.id.new_request_b);
        recyclerView = findViewById(R.id.leave_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bundle = getIntent().getExtras();
        empid = bundle.getString("id");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doInBackground();
        newRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LeaveMainActivity.this,NewLeaveActivity.class);
                i.putExtra("id",bundle.getString("id"));
                startActivity(i);
            }
        });
    }

    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT * FROM trackertroodon.leave_request WHERE emp_id = "+"'"+empid+"' ORDER BY from_date DESC;";
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                resultSet = statement.executeQuery(SQL);
                while (resultSet.next()) {
                    LeaveInfo user = new LeaveInfo(resultSet.getString("emp_id"),resultSet.getDate("from_date"),resultSet.getDate("to_date"),resultSet.getString("reason"),resultSet.getString("comment"),resultSet.getString("status"));
                    userInfoList.add(user);
                    Log.d("T", String.valueOf(userInfoList));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        adapter = new LeaveAdapter(getApplicationContext(),userInfoList);
        Log.d("T", String.valueOf(userInfoList));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
