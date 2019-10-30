package tech.trdn.trootrackeradmin;

public class EmpInfo {

    public String emp_id;
    public int DaysPresent;
    public int DaysLeave;
    public int DaysHalf;
    public int DaysMedical;
    public int DaysCasual;

    public EmpInfo(){}

    public EmpInfo(String emp_id,int daysPresent,int daysLeave,int daysHalf,int daysMedical,int daysCasual){

        this.emp_id = emp_id;
        this.DaysPresent = daysPresent;
        this.DaysLeave = daysLeave;
        this.DaysHalf = daysHalf;
        this.DaysMedical = daysMedical;
        this.DaysCasual = daysCasual;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public int getDaysPresent() {
        return DaysPresent;
    }

    public int getDaysLeave() {
        return DaysLeave;
    }

    public int getDaysHalf() {
        return DaysHalf;
    }

    public int getDaysMedical() {
        return DaysMedical;
    }

    public int getDaysCasual() {
        return DaysCasual;
    }
}
