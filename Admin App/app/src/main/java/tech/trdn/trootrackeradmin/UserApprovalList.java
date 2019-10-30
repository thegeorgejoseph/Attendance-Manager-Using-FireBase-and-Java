package tech.trdn.trootrackeradmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserApprovalList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<UserInfo> userInfoList = new ArrayList<>();
    ResultSet resultSet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_approval_list);
        recyclerView = findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doInBackground();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnTouchActionListener() {
            @Override
            public void onClick(View view, int position) {
                UserInfo UploadInfo = userInfoList.get(position);
                Intent i = new Intent(UserApprovalList.this, UserApproval.class);
                i.putExtra("empid",UploadInfo.getEmployeeid());
                i.putExtra("fname",UploadInfo.getFirstName());
                i.putExtra("mname",UploadInfo.getMiddleName());
                i.putExtra("lname",UploadInfo.getLastName());
                i.putExtra("email",UploadInfo.getEmail());
                i.putExtra("phno",UploadInfo.getPhno());
                i.putExtra("dob",UploadInfo.getDob());
                i.putExtra("doj",UploadInfo.getDoj());
                startActivity(i);
            }

            @Override
            public void onRightSwipe(View view, int position) {
            }

            @Override
            public void onLeftSwipe(View view, int position) {
            }
        }));

    }

        protected void doInBackground(String... strings) {
            Connection conn1;
            MysqlConnect connect = new MysqlConnect();
            conn1 = connect.connect();
            if (conn1 != null) {
                String SQL = "SELECT * FROM trackertroodon.registered_employees;";
                try {
                    Statement statement = conn1.createStatement();
                    resultSet = statement.executeQuery(SQL);
                    while (resultSet.next()) {
                        Log.d("TAG", "CONNECTED" + resultSet.getString(2));
                        UserInfo user = new UserInfo(resultSet.getString("emp_id"), resultSet.getString("f_name"), resultSet.getString("m_name"), resultSet.getString("l_name"), resultSet.getString("email"), resultSet.getString("phno"), resultSet.getString("dob"), resultSet.getString("doj"), resultSet.getString("password"), resultSet.getString("approval_status"));
                        Log.d("TAG", "CONNECTED" + user.getStatus());
                        userInfoList.add(user);
                        Log.d("T", String.valueOf(userInfoList));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            adapter = new RecyclerViewAdapter(getApplicationContext(),userInfoList);
            Log.d("T", String.valueOf(userInfoList));
            recyclerView.setAdapter(adapter);
        }

}

