package tech.trdn.trootrackeradmin;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LeaveApproval extends AppCompatActivity {
    RecyclerView recyclerView;
    ResultSet resultSet;
    List<LeaveInfo> leaveInfos = new ArrayList<>();
    LeaveAdapter adapter;
    TextView empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_approval);
        recyclerView = findViewById(R.id.leave_list);
        empty = findViewById(R.id.empty_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doInBackground();
    }
    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "SELECT * FROM trackertroodon.leave_request WHERE status = '0'";
            try {
                Statement statement = conn1.createStatement();
                resultSet = statement.executeQuery(SQL);
                while(resultSet.next()){
                    LeaveInfo info = new LeaveInfo(resultSet.getString("emp_id"),resultSet.getDate("from_date"),resultSet.getDate("to_date"),resultSet.getString("reason"),resultSet.getString("comment"),resultSet.getString("status"));
                    leaveInfos.add(info);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        adapter = new LeaveAdapter(getApplicationContext(),leaveInfos);
        Log.d("T", String.valueOf(leaveInfos));
        recyclerView.setAdapter(adapter);
        if(adapter.getItemCount()==0){
            empty.setVisibility(View.VISIBLE);
        }
        else
            empty.setVisibility(View.INVISIBLE);
    }
}
