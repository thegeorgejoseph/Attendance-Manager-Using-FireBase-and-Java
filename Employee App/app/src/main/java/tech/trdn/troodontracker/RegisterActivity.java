package tech.trdn.troodontracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText emp_id, f_name, m_name, l_name, email, phno, dob_t, doj_t, pass, pass_c;
    Button reg,dob_b,doj_b;
    public String empid, fname, mname, lname, mail, phn, dob_f, doj_f, pass_f, device_id;
    java.util.Date dob_d, doj_d;
    DatePickerDialog datePickerDialog;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        emp_id = findViewById(R.id.employee_text);
        f_name = findViewById(R.id.f_name_text);
        m_name = findViewById(R.id.m_name_text);
        l_name = findViewById(R.id.l_name_text);
        email = findViewById(R.id.email_text);
        phno = findViewById(R.id.phno_text);
        dob_t = findViewById(R.id.dob_text);
        doj_t = findViewById(R.id.doj_text);
        pass = findViewById(R.id.pass_text);
        pass_c = findViewById(R.id.pass_confirm_text);
        reg = findViewById(R.id.final_reg_b);
        dob_b = findViewById(R.id.dobb);
        doj_b = findViewById(R.id.dojb);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            device_id = telephonyManager.getDeviceId();
        }
        else
            Toast.makeText(this, "Device cannot be verified", Toast.LENGTH_SHORT).show();
        final Calendar myCalendar = Calendar.getInstance();

        dob_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(dob_t,myCalendar);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        doj_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(doj_t,myCalendar);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (pass.getText().toString().equals(pass_c.getText().toString())) {
                    empid = emp_id.getText().toString();
                    fname = f_name.getText().toString();
                    mname = m_name.getText().toString();
                    lname = l_name.getText().toString();
                    mail = email.getText().toString();
                    phn = phno.getText().toString();
                    dob_f = dob_t.getText().toString();
                    doj_f = doj_t.getText().toString();
                    pass_f = pass.getText().toString();
                    try {
                        dob_d = sdf.parse(dob_f);
                        doj_d = sdf.parse(doj_f);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(empid.equals("")||fname.equals("")||lname.equals("")||mname.equals("")||mail.equals("")||phn.equals("")||dob_f.equals("")||doj_f.equals("")||pass_f.equals("")){
                        Toast.makeText(RegisterActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Insert insert = new Insert();
                        insert.execute("");
                        Toast.makeText(getApplicationContext(), "Request Sent!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            java.sql.Date dob = new java.sql.Date(dob_d.getTime());
            java.sql.Date doj = new java.sql.Date(doj_d.getTime());
            try {
                MysqlConnect connect = new MysqlConnect();
                conn1 = connect.connect();
                if (conn1 != null) {
                    CallableStatement cstmt;
                    try {
                        String SQL = "{call usp_insert_employee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                        cstmt = conn1.prepareCall(SQL);
                        cstmt.setString(1, empid);
                        cstmt.setString(2, fname);
                        cstmt.setString(3, mname);
                        cstmt.setString(4, lname);
                        cstmt.setString(5, mail);
                        cstmt.setString(6, phn);
                        cstmt.setDate(7, dob);
                        cstmt.setDate(8, doj);
                        cstmt.setString(9, pass_f);
                        cstmt.setDate(10, sqlDate);
                        cstmt.setDate(11, sqlDate);
                        cstmt.setString(12, device_id);
                        cstmt.setInt(13,0);
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
