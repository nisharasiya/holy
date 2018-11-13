package aleris.com.holypublicschoolll.EmployeePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpBean {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dept")
    @Expose
    private String dept;
    @SerializedName("desg")
    @Expose
    private String desg;
    @SerializedName("ATT_TYPE")
    @Expose
    private String aTTTYPE;
    @SerializedName("SAT_ID")
    @Expose
    private Integer sATID;
    @SerializedName("DAY")
    @Expose
    private Integer dAY;
    @SerializedName("MONTH")
    @Expose
    private Integer mONTH;
    @SerializedName("YEAR")
    @Expose
    private Integer yEAR;
    @SerializedName("USERID")
    @Expose
    private String uSERID;
    @SerializedName("SAT_ID1")
    @Expose
    private Integer sATID1;
    @SerializedName("date")
    @Expose
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDesg() {
        return desg;
    }

    public void setDesg(String desg) {
        this.desg = desg;
    }

    public String getATTTYPE() {
        return aTTTYPE;
    }

    public void setATTTYPE(String aTTTYPE) {
        this.aTTTYPE = aTTTYPE;
    }

    public Integer getSATID() {
        return sATID;
    }

    public void setSATID(Integer sATID) {
        this.sATID = sATID;
    }

    public Integer getDAY() {
        return dAY;
    }

    public void setDAY(Integer dAY) {
        this.dAY = dAY;
    }

    public Integer getMONTH() {
        return mONTH;
    }

    public void setMONTH(Integer mONTH) {
        this.mONTH = mONTH;
    }

    public Integer getYEAR() {
        return yEAR;
    }

    public void setYEAR(Integer yEAR) {
        this.yEAR = yEAR;
    }

    public String getUSERID() {
        return uSERID;
    }

    public void setUSERID(String uSERID) {
        this.uSERID = uSERID;
    }

    public Integer getSATID1() {
        return sATID1;
    }

    public void setSATID1(Integer sATID1) {
        this.sATID1 = sATID1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
