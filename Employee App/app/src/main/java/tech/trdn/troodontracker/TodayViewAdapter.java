package tech.trdn.troodontracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static tech.trdn.troodontracker.R.layout.today_item;

public class TodayViewAdapter extends RecyclerView.Adapter<TodayViewAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<TodayInfo> userInfoList;
    private TextView date,categ,time;


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
        date.setText(todayInfo.getDate().toString());
        categ.setText(todayInfo.getCateg());
        long timediff = todayInfo.getOut_time().getTime() - todayInfo.getIn_time().getTime();
        Log.d("TAG",timediff+"");
        time.setText(msToString(timediff));


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
            date = itemView.findViewById(R.id.date_view);
            categ = itemView.findViewById(R.id.categ_view);
            time = itemView.findViewById(R.id.time_worked);
        }
    }
    public static String msToString(long ms) {
        long totalSecs = ms/1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        String minsString = (mins == 0)
                ? "00"
                : ((mins < 10)
                ? "0" + mins
                : "" + mins);
        String secsString = (secs == 0)
                ? "00"
                : ((secs < 10)
                ? "0" + secs
                : "" + secs);
        if (hours > 0)
            return hours + ":" + minsString + ":" + secsString;
        else if (mins > 0)
            return mins + ":" + secsString;
        else return ":" + secsString;
    }
}