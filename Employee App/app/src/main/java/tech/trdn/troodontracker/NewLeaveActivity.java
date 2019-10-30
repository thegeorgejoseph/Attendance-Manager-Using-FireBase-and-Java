package tech.trdn.troodontracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewLeaveActivity extends AppCompatActivity {
    Button from,to,req;
    EditText from_v,to_v,rea,com;
    DatePickerDialog datePickerDialog;
    String fromd,tod;
    java.util.Date from_date,to_date;
    String reason,comment,empid;
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_leave_activity);
        from = findViewById(R.id.from_p);
        to = findViewById(R.id.to_p);
        from_v = findViewById(R.id.from_d);
        to_v = findViewById(R.id.to_d);
        req = findViewById(R.id.leave_req_b);
        rea = findViewById(R.id.reason);
        com = findViewById(R.id.comment);
        bundle = getIntent().getExtras();
        empid = bundle.getString("id");
        final Calendar myCalendar = Calendar.getInstance();

        from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(NewLeaveActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(from_v,myCalendar);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(NewLeaveActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(to_v,myCalendar);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fromd = from_v.getText().toString();
                tod = to_v.getText().toString();
                if(fromd.equals("")||tod.equals("")||rea.getText().toString().equals(""))
                    Toast.makeText(NewLeaveActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
                else{
                try {
                    from_date = sdf.parse(fromd);
                    to_date = sdf.parse(tod);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Insert insert = new Insert();
                insert.execute("");
                Toast.makeText(NewLeaveActivity.this, "Requested", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewLeaveActivity.this,LeaveMainActivity.class);
                i.putExtra("id",bundle.getString("id"));
                startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void updateLabel(EditText ed, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        ed.setText(sdf.format(myCalendar.getTime()));
    }

    public class Insert extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Connection conn1;
            String result = null;
            java.sql.Date from_d = new java.sql.Date(from_date.getTime());
            java.sql.Date to_d = new java.sql.Date(to_date.getTime());
            reason = rea.getText().toString();
            comment = com.getText().toString();
            try {
                MysqlConnect connect = new MysqlConnect();
                conn1 = connect.connect();
                if (conn1 != null) {
                    CallableStatement cstmt;
                    try {
                        String SQL = "{call leave_request(?, ?, ?, ?, ?, ?)}";
                        cstmt = conn1.prepareCall(SQL);
                        cstmt.setString(1, empid);
                        cstmt.setDate(2,from_d);
                        cstmt.setDate(3,to_d);
                        cstmt.setString(4,reason);
                        cstmt.setString(5,comment);
                        cstmt.setString(6,"0");
                        cstmt.execute();


                    } catch (SQLException e) {

                    }
                    return null;
                }
            } catch (Exception e) {
            }
            return result;
        }
    }
}
