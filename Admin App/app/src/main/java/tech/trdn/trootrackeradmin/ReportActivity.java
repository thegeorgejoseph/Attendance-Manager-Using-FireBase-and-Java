package tech.trdn.trootrackeradmin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    EditText from_d,to_d,user_t;
    Button from_b,to_b,search_b;
    RecyclerView recyclerView;
    DatePickerDialog datePickerDialog;
    SearchEmpAdapter adapter;
    ResultSet resultSet,resultSet2;
    String fromd,tod,user_id;
    Date from_date,to_date;
    List<EmpInfo> userInfoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);
        from_d = findViewById(R.id.fromText);
        to_d = findViewById(R.id.toText);
        user_t = findViewById(R.id.userText);
        from_b = findViewById(R.id.from_pick_b);
        to_b = findViewById(R.id.to_pick_b);
        search_b = findViewById(R.id.search_b);
        recyclerView = findViewById(R.id.search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Calendar myCalendar = Calendar.getInstance();
        from_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(from_d,myCalendar);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        to_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(to_d,myCalendar);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        search_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //date search
                    user_id = user_t.getText().toString();
                    fromd = from_d.getText().toString();
                    tod = to_d.getText().toString();
                    if(user_id.equals("")&&(!(fromd.equals("")||(tod.equals(""))))) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            from_date = sdf.parse(fromd);
                            to_date = sdf.parse(tod);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        java.sql.Date from_d = new java.sql.Date(from_date.getTime());
                        java.sql.Date to_d = new java.sql.Date(to_date.getTime());
                        doInBackground2(from_d, to_d, user_id);
                    }
                    else if(!(fromd.equals("")||(tod.equals("")))){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            from_date = sdf.parse(fromd);
                            to_date = sdf.parse(tod);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        java.sql.Date from_d = new java.sql.Date(from_date.getTime());
                        java.sql.Date to_d = new java.sql.Date(to_date.getTime());
                        doInBackground(from_d, to_d, user_id);
                    }
                    else Toast.makeText(ReportActivity.this, "Enter dates or userID", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateLabel(EditText ed, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        ed.setText(sdf.format(myCalendar.getTime()));
    }
    protected void doInBackground(java.sql.Date from_date, java.sql.Date to_date, String empid) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT (category) FROM trackertroodon.today WHERE (date between "+"'"+from_date+"' and '"+to_date+"') and emp_id = '"+empid+"';";
            Log.d("TAG",SQL);
            try {
                userInfoList.clear();
                Statement statement = conn1.createStatement();
                resultSet = statement.executeQuery(SQL);
                int dp=0,dl=0,dh=0,dm=0,dc=0;
                while (resultSet.next()) {
                    Log.d("TAG",resultSet.getString("category"));
                    switch (resultSet.getString("category")){
                        case "P":dp++;
                        break;
                        case "L":dl++;
                        break;
                        case "H":dh++;
                        break;
                        case "M":dm++;
                        break;
                        case "C":dc++;
                        break;
                    }
                }
                EmpInfo user = new EmpInfo(empid,dp,dl,dh,dm,dc);
                userInfoList.add(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        adapter = new SearchEmpAdapter(getApplicationContext(),userInfoList);
        Log.d("T", String.valueOf(userInfoList));
        recyclerView.setAdapter(adapter);
    }
    protected void doInBackground2(java.sql.Date from_date, java.sql.Date to_date, String empid) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT emp_id FROM trackertroodon.registered_employees WHERE approval_status = '1';";
            try {userInfoList.clear();
                Statement statement = conn1.createStatement();
                resultSet2 = statement.executeQuery(SQL);
                while (resultSet2.next()) {
                    String emp = resultSet2.getString("emp_id");
                    doInBackground3(from_date,to_date,emp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            adapter = new SearchEmpAdapter(getApplicationContext(),userInfoList);
            Log.d("T", String.valueOf(userInfoList));
            recyclerView.setAdapter(adapter);
        }
    }
    protected void doInBackground3(java.sql.Date from_date, java.sql.Date to_date, String empid) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT (category) FROM trackertroodon.today WHERE (date between "+"'"+from_date+"' and '"+to_date+"') and emp_id = '"+empid+"';";
            Log.d("TAG",SQL);
            try {
                Statement statement = conn1.createStatement();
                resultSet = statement.executeQuery(SQL);
                int dp=0,dl=0,dh=0,dm=0,dc=0;
                while (resultSet.next()) {
                    Log.d("TAG",resultSet.getString("category"));
                    switch (resultSet.getString("category")){
                        case "P":dp++;
                            break;
                        case "L":dl++;
                            break;
                        case "H":dh++;
                            break;
                        case "M":dm++;
                            break;
                        case "C":dc++;
                            break;
                    }
                }
                EmpInfo user = new EmpInfo(empid,dp,dl,dh,dm,dc);
                userInfoList.add(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
