package aleris.com.holypublicschoolll.SendSMSPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendSmsBean {


    @SerializedName("admno")
    @Expose
    private String admno;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("cls")
    @Expose
    private String cls;
    @SerializedName("phone")
    @Expose
    private String phone;

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

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
