package tech.trdn.trootrackeradmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static tech.trdn.trootrackeradmin.R.layout.today_item;

public class TodayViewAdapter extends RecyclerView.Adapter<TodayViewAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<TodayInfo> userInfoList;
    private TextView emp_id,intime,outtime;
    public RadioGroup radioGroup;
    private RadioButton lastCheckedRB = null;




    TodayViewAdapter(Context context, List<TodayInfo> TempList) {

        this.userInfoList = TempList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View = LayoutInflater.from(parent.getContext()).inflate(today_item , parent, false);
        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TodayInfo todayInfo = userInfoList.get(position);
        emp_id.setText(todayInfo.getEmp_id());
        intime.setText(String.valueOf(todayInfo.getIn_time()));
        outtime.setText(String.valueOf(todayInfo.getOut_time()));
        if(todayInfo.getCateg().equals("P"))
            radioGroup.check(R.id.p);
        else if(todayInfo.getCateg().equals("H"))
            radioGroup.check(R.id.h);
        else if(todayInfo.getCateg().equals("C"))
            radioGroup.check(R.id.c);
        else if(todayInfo.getCateg().equals("M"))
            radioGroup.check(R.id.m);
        else if(todayInfo.getCateg().equals("L"))
            radioGroup.check(R.id.l);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
                lastCheckedRB = checked_rb;
                String cat = lastCheckedRB.getText().toString().trim();
                todayInfo.setCateg(cat);
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
            emp_id = itemView.findViewById(R.id.employee_id);
            intime = itemView.findViewById(R.id.in_time);
            outtime = itemView.findViewById(R.id.out_time);
            radioGroup = itemView.findViewById(R.id.leave_type);
        }
    }
}