package tech.trdn.troodontracker;


import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class CheckActivity extends AppCompatActivity {
    TextView date,time,status;
    double lat = LoginActivity.latitude;
    double lon = LoginActivity.longitude;
    Button checkin;
    Bundle bundle;
    String empid;
    Time sql_time;
    java.sql.Date sql_date;
    int flag,flag2=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_activity);
        date = findViewById(R.id.date_view);
        time = findViewById(R.id.time_view);
        status = findViewById(R.id.status_view);
        checkin = findViewById(R.id.checkin);
        bundle = getIntent().getExtras();
        empid = bundle.getString("id");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Date currentDate = Calendar.getInstance().getTime();
        String myFormat = "yyyy-MM-dd";
        String timeFormat = "hh:mm:ss";
        SimpleDateFormat stf = new SimpleDateFormat(timeFormat);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        date.setText(sdf.format(currentDate));
        time.setText(stf.format(currentDate));
        sql_date = java.sql.Date.valueOf(sdf.format(currentDate));
        sql_time = Time.valueOf(stf.format(currentDate));
        lat = LoginActivity.latitude;
        lon = LoginActivity.longitude;
        float[] results = new float[1];
        Location.distanceBetween(8.56244292, 76.94333903, lat, lon, results);
        float distanceInMeters = results[0];
        final boolean isWithin100m = distanceInMeters < 50;
        check();
        checkout();
        if(flag==1){
            checkin.setText("Check Out");
        }
        if(isWithin100m)
            status.setText("Inside Office");
        else
        {status.setText("Outside Office");
            checkin.setVisibility(View.INVISIBLE);}
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    doInBackground2();
                    Toast.makeText(getApplicationContext(), "Checked Out", Toast.LENGTH_SHORT).show();
                }
                else if(flag==0){
                    doInBackground();
                Toast.makeText(getApplicationContext(), "Checked In", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toast.makeText(CheckActivity.this,lat+"/"+lon, Toast.LENGTH_SHORT).show();
    }
    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "INSERT INTO trackertroodon.today (emp_id,in_time,date) VALUES (" + "\""+empid+"\"" + "," + "'" +String.valueOf(sql_time)+ "'" + "," + "'"+String.valueOf(sql_date)+"'" + ");";
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                statement.execute(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    protected void doInBackground2(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "UPDATE trackertroodon.today SET out_time = '"+sql_time+"' , category = \"P\" WHERE emp_id = "+"\""+empid+"\""+" AND date = "+"'"+String.valueOf(sql_date)+"';" ;
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                statement.execute(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    protected void check(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT COUNT(in_time) FROM trackertroodon.today WHERE emp_id = "+"\""+empid+"\""+" AND date ="+"'"+sql_date+"'"+";";
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL);
                resultSet.next();
                flag = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    protected void checkout(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT COUNT(out_time) FROM trackertroodon.today WHERE emp_id = "+"\""+empid+"\""+" AND date ="+"'"+sql_date+"'"+";";
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL);
                resultSet.next();
                flag2 = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(flag2==1)
            checkin.setVisibility(View.INVISIBLE);
    }
}
