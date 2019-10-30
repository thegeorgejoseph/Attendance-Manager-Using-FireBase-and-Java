package tech.trdn.trootrackeradmin;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import static tech.trdn.trootrackeradmin.R.layout.user_grid_item;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<UserInfo> userInfoList;
    private TextView emp_id;
    private ImageView statusimg;


    RecyclerViewAdapter(Context context, List<UserInfo> TempList) {

        this.userInfoList = TempList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View = LayoutInflater.from(parent.getContext()).inflate(user_grid_item , parent, false);
        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfo userInfo = userInfoList.get(position);
        emp_id.setText(userInfo.getEmployeeid());
        if(userInfo.getStatus().equals("0"))
            statusimg.setBackgroundColor(Color.RED);
        else if(userInfo.getStatus().equals("1"))
            statusimg.setBackgroundColor(Color.GREEN);
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
            emp_id = itemView.findViewById(R.id.emp_id);
            statusimg = itemView.findViewById(R.id.stat_img);


        }
    }
}
