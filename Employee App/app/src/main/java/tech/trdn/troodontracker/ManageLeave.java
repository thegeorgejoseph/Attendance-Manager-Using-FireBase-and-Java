package tech.trdn.troodontracker;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManageLeave extends AppCompatActivity {
    RecyclerView manage;
    ResultSet resultSet;
    List<TodayInfo> userInfoList = new ArrayList<>();
    Bundle bundle;
    String empid;
    TodayViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_leave);
        manage = findViewById(R.id.manage_list);
        bundle = getIntent().getExtras();
        empid = bundle.getString("id");
        manage.setLayoutManager(new LinearLayoutManager(this));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doInBackground();

    }
    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT * FROM trackertroodon.today WHERE emp_id = "+"'"+empid+"' ORDER BY date DESC;";
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                resultSet = statement.executeQuery(SQL);
                while (resultSet.next()) {
                    TodayInfo user = new TodayInfo(resultSet.getString("emp_id"),resultSet.getTime("in_time"),resultSet.getTime("out_time"),resultSet.getDate("date"),resultSet.getString("category"));
                    userInfoList.add(user);
                    Log.d("T", String.valueOf(userInfoList));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        adapter = new TodayViewAdapter(getApplicationContext(),userInfoList);
        Log.d("T", String.valueOf(userInfoList));
        manage.setAdapter(adapter);
    }
}
