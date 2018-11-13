package aleris.com.holypublicschoolll.TCalssPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TCalssBean {

    @SerializedName("STU_ID")
    @Expose
    private Integer sTUID;
    @SerializedName("admno")
    @Expose
    private String admno;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Roll")
    @Expose
    private Integer roll;

    public Integer getSTUID() {
        return sTUID;
    }

    public void setSTUID(Integer sTUID) {
        this.sTUID = sTUID;
    }

    public String getAdmno() {
        return admno;
    }

    public void setAdmno(String admno) {
        this.admno = admno;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }


}
