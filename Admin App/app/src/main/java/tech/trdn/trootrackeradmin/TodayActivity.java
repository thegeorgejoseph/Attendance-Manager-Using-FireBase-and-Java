package tech.trdn.trootrackeradmin;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TodayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    RecyclerView.Adapter adapter;
    List<TodayInfo> userInfoList = new ArrayList<>();
    ResultSet resultSet;
    java.sql.Date sql_date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_layout);
        recyclerView = findViewById(R.id.today_list);
        button = findViewById(R.id.today_button);
        Date currentDate = Calendar.getInstance().getTime();
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        sql_date = java.sql.Date.valueOf(sdf.format(currentDate));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doInBackground();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int len = adapter.getItemCount();
                for(int i=0;i<len;i++){
                    TodayInfo user = userInfoList.get(i);
                    String categ = user.getCateg();
                    String empid = user.getEmp_id();
                    doInBackground2(categ,empid);
                    finish();
                }
            }
        });
    }
    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT * FROM trackertroodon.today WHERE date = "+"'"+sql_date+"';";
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
        recyclerView.setAdapter(adapter);
    }
        protected void doInBackground2(String categ,String empid) {
            Connection conn1;
            MysqlConnect connect = new MysqlConnect();
            conn1 = connect.connect();
            if (conn1 != null) {
                String SQL = "UPDATE trackertroodon.today SET category ="+"'"+categ+"'"+" WHERE date = "+"'"+sql_date+"' AND emp_id ='"+empid+"';";
                Log.d("TAG",SQL);
                try {
                    Statement statement = conn1.createStatement();
                    statement.execute(SQL);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }
}