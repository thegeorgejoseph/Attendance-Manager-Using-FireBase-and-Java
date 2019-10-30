package tech.trdn.troodontracker;

import java.sql.Date;

public class LeaveInfo  {

    public String emp_id;
    public Date from_d;
    public Date to_d;
    public String reason;
    public String comments;
    public String status;

    public LeaveInfo(){}

    public LeaveInfo(String emp_id,Date from_d,Date to_d,String reason,String comments,String status){
        this.emp_id = emp_id;
        this.from_d = from_d;
        this.to_d = to_d;
        this.reason = reason;
        this.comments = comments;
        this.status = status;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public Date getFrom_d() {
        return from_d;
    }

    public Date getTo_d() {
        return to_d;
    }

    public String getReason() {
        return reason;
    }

    public String getComments() {
        return comments;
    }

    public String getStatus() {
        return status;
    }
}
