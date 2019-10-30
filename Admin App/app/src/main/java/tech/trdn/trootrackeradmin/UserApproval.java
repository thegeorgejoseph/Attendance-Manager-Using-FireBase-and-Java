package tech.trdn.trootrackeradmin;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserApproval extends AppCompatActivity {
    Bundle bundle;
    EditText emp_id, f_name, m_name, l_name, email, phno, dob_t, doj_t;
    Button approve,decline;
    public String empid, fname, mname, lname, mail, phn, dob_f, doj_f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_approval);
        bundle = getIntent().getExtras();
        approve = findViewById(R.id.user_approve_b);
        decline = findViewById(R.id.user_decline_b);
        emp_id = findViewById(R.id.employee_text);
        f_name = findViewById(R.id.f_name_text);
        m_name = findViewById(R.id.m_name_text);
        l_name = findViewById(R.id.l_name_text);
        email = findViewById(R.id.email_text);
        phno = findViewById(R.id.phno_text);
        dob_t = findViewById(R.id.dob_text);
        doj_t = findViewById(R.id.doj_text);
        empid = bundle.getString("empid");
        fname = bundle.getString("fname");
        mname = bundle.getString("mname");
        lname = bundle.getString("lname");
        mail = bundle.getString("email");
        phn = bundle.getString("phno");
        dob_f = bundle.getString("dob");
        doj_f = bundle.getString("doj");
        emp_id.setText(empid);
        f_name.setText(fname);
        m_name.setText(mname);
        l_name.setText(lname);
        email.setText(mail);
        phno.setText(phn);
        dob_t.setText(dob_f);
        doj_t.setText(doj_f);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection conn1;
                MysqlConnect connect = new MysqlConnect();
                conn1 = connect.connect();
                if (conn1 != null) {
                    CallableStatement cstmt;
                    try {
                        String SQL = "{call approve_user(?)}";
                        cstmt = conn1.prepareCall(SQL);
                        cstmt.setString(1,empid);
                        cstmt.execute();


                    } catch (SQLException e) {
                        System.out.print(e+"");
                    }
                }
                finish();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doInBackground();
                finish();
            }
        });
    }
    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "DELETE FROM trackertroodon.registered_employees WHERE emp_id = '"+empid+"';";
            try {
                Statement statement = conn1.createStatement();
                statement.execute(SQL);
                } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
