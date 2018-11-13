package aleris.com.holypublicschoolll.TeacherSMSPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherSMSBean {



    @SerializedName("emp_name")
    @Expose
    private String empName;
    @SerializedName("emp_mobile")
    @Expose
    private String empMobile;
    @SerializedName("dept_desc")
    @Expose
    private String deptDesc;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }
}
