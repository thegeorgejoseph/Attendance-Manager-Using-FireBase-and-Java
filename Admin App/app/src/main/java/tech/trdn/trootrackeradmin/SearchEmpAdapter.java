package tech.trdn.trootrackeradmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static tech.trdn.trootrackeradmin.R.layout.search_item;

public class SearchEmpAdapter extends RecyclerView.Adapter<SearchEmpAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<EmpInfo> userInfoList;
    private TextView emp_id,dp,dl,dh,dm,dc;



    SearchEmpAdapter(Context context, List<EmpInfo> TempList) {

        this.userInfoList = TempList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View = LayoutInflater.from(parent.getContext()).inflate(search_item , parent, false);
        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmpInfo userInfo = userInfoList.get(position);
        emp_id.setText(userInfo.getEmp_id());
        dp.setText((String.valueOf(userInfo.getDaysPresent())));
        dl.setText((String.valueOf(userInfo.getDaysLeave())));
        dh.setText((String.valueOf(userInfo.getDaysHalf())));
        dm.setText((String.valueOf(userInfo.getDaysMedical())));
        dc.setText((String.valueOf(userInfo.getDaysCasual())));
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
            emp_id = itemView.findViewById(R.id.id_view);
            dp = itemView.findViewById(R.id.dp_view);
            dl = itemView.findViewById(R.id.dl_view);
            dh = itemView.findViewById(R.id.dh_view);
            dm = itemView.findViewById(R.id.dm_view);
            dc = itemView.findViewById(R.id.dc_view);


        }
    }
}