package tech.trdn.trootrackeradmin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static tech.trdn.trootrackeradmin.R.layout.leave_item;

public class LeaveAdapter extends RecyclerView.Adapter<tech.trdn.trootrackeradmin.LeaveAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<LeaveInfo> userInfoList;
    private TextView from,to,status,reason,comment,emp;
    Button button;
    String empid;



    LeaveAdapter(Context context, List<LeaveInfo> TempList) {

        this.userInfoList = TempList;

    }

    @NonNull
    @Override
    public tech.trdn.trootrackeradmin.LeaveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View = LayoutInflater.from(parent.getContext()).inflate(leave_item , parent, false);
        return new tech.trdn.trootrackeradmin.LeaveAdapter.ViewHolder(View);

    }

    @Override
    public void onBindViewHolder(@NonNull tech.trdn.trootrackeradmin.LeaveAdapter.ViewHolder holder, int position) {
        final LeaveInfo leaveInfo = userInfoList.get(position);
        empid = leaveInfo.getEmp_id();
        emp.setText(empid);
        from.setText(leaveInfo.getFrom_d().toString());
        to.setText(leaveInfo.getTo_d().toString());
        reason.setText(leaveInfo.getReason());
        comment.setText(leaveInfo.getComments());
        if(leaveInfo.getStatus().equals("0")){
            status.setText("Pending");
        }
        else
            status.setText("Approved");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doInBackground();
                Toast.makeText(from.getContext(), "Approved", Toast.LENGTH_SHORT).show();
                ((Activity)from.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView);
            emp = itemView.findViewById(R.id.emp_id_text);
            from = itemView.findViewById(R.id.from_text);
            to = itemView.findViewById(R.id.to_text);
            status = itemView.findViewById(R.id.status_text);
            reason = itemView.findViewById(R.id.reason_text);
            comment = itemView.findViewById(R.id.comment_text);
            button = itemView.findViewById(R.id.leave_approve_b);
        }
    }

    protected void doInBackground(String... strings) {
        Connection conn1;
        MysqlConnect connect = new MysqlConnect();
        conn1 = connect.connect();
        if (conn1 != null) {
            String SQL = "UPDATE trackertroodon.leave_request SET status = '1' WHERE emp_id ='"+empid+"';";
            try {
                Statement statement = conn1.createStatement();
                statement.execute(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}