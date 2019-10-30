package tech.trdn.trootrackeradmin;

public class UserInfo {

    private String employeeid;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String email;
    private String phno;
    private String dob;
    private String doj;
    private String pass;
    private String status;

    public UserInfo(){}

    public UserInfo(String employeeid,String firstName,String middleName,String lastName,String email,String phno,String dob,String doj,String pass,String status){

        this.employeeid = employeeid;
        this.FirstName = firstName;
        this.MiddleName = middleName;
        this.LastName = lastName;
        this.email = email;
        this.phno = phno;
        this.dob = dob;
        this.doj = doj;
        this.pass = pass;
        this.status = status;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhno() {
        return phno;
    }

    public String getDob() {
        return dob;
    }

    public String getDoj() {
        return doj;
    }

    public String getPass() {
        return pass;
    }

    public String getStatus() {
        return status;
    }

}
