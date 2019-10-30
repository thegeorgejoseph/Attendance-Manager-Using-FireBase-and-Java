package tech.trdn.trootrackeradmin;

import java.sql.Date;
import java.sql.Time;

public class TodayInfo {
    private String emp_id;
    private Time in_time;
    private Time out_time;
    private Date date;
    private String categ;

    public TodayInfo(){}

    public TodayInfo(String emp_id,Time in_time,Time out_time,Date date,String categ){
        this.emp_id = emp_id;
        this.in_time = in_time;
        this.out_time = out_time;
        this.date = date;
        this.categ = categ;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public Time getIn_time() {
        return in_time;
    }

    public Time getOut_time() {
        return out_time;
    }

    public Date getDate() {
        return date;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }
}