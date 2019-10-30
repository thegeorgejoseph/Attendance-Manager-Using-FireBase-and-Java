package tech.trdn.troodontracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static tech.trdn.troodontracker.R.layout.leave_item;

    public class LeaveAdapter extends RecyclerView.Adapter<tech.trdn.troodontracker.LeaveAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

        private List<LeaveInfo> userInfoList;
        private TextView from,to,status;


        LeaveAdapter(Context context, List<LeaveInfo> TempList) {

            this.userInfoList = TempList;

        }

        @NonNull
        @Override
        public tech.trdn.troodontracker.LeaveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View View = LayoutInflater.from(parent.getContext()).inflate(leave_item , parent, false);
            return new tech.trdn.troodontracker.LeaveAdapter.ViewHolder(View);
        }

        @Override
        public void onBindViewHolder(@NonNull tech.trdn.troodontracker.LeaveAdapter.ViewHolder holder, int position) {
            final LeaveInfo leaveInfo = userInfoList.get(position);
            from.setText(leaveInfo.getFrom_d().toString());
            to.setText(leaveInfo.getTo_d().toString());
            if(leaveInfo.getStatus().equals("0")){
                status.setText("Pending");
            }
            else
                status.setText("Approved");

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
                from = itemView.findViewById(R.id.from_text);
                to = itemView.findViewById(R.id.to_text);
                status = itemView.findViewById(R.id.status_text);

            }
        }
    }