package aleris.com.holypublicschoolll.TeacherPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("imagepath")
    @Expose
    private String imagepath;
    @SerializedName("EMP_MOBILE")
    @Expose
    private String eMPMOBILE;
    @SerializedName("Desg")
    @Expose
    private String desg;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("EMP_RELATIVE_NAME")
    @Expose
    private String eMPRELATIVENAME;
    @SerializedName("EMP_NAME")
    @Expose
    private String eMPNAME;
    @SerializedName("EMP_CODE")
    @Expose
    private String eMPCODE;
    @SerializedName("EMP_DOL")
    @Expose
    private String eMPDOL;
    @SerializedName("EMP_DOB")
    @Expose
    private String eMPDOB;
    @SerializedName("EMP_DOJ")
    @Expose
    private String eMPDOJ;
    @SerializedName("EMP_BASIC_SALARY")
    @Expose
    private String eMPBASICSALARY;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getEMPMOBILE() {
        return eMPMOBILE;
    }

    public void setEMPMOBILE(String eMPMOBILE) {
        this.eMPMOBILE = eMPMOBILE;
    }

    public String getDesg() {
        return desg;
    }

    public void setDesg(String desg) {
        this.desg = desg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEMPRELATIVENAME() {
        return eMPRELATIVENAME;
    }

    public void setEMPRELATIVENAME(String eMPRELATIVENAME) {
        this.eMPRELATIVENAME = eMPRELATIVENAME;
    }

    public String getEMPNAME() {
        return eMPNAME;
    }

    public void setEMPNAME(String eMPNAME) {
        this.eMPNAME = eMPNAME;
    }

    public String getEMPCODE() {
        return eMPCODE;
    }

    public void setEMPCODE(String eMPCODE) {
        this.eMPCODE = eMPCODE;
    }

    public String getEMPDOL() {
        return eMPDOL;
    }

    public void setEMPDOL(String eMPDOL) {
        this.eMPDOL = eMPDOL;
    }

    public String getEMPDOB() {
        return eMPDOB;
    }

    public void setEMPDOB(String eMPDOB) {
        this.eMPDOB = eMPDOB;
    }

    public String getEMPDOJ() {
        return eMPDOJ;
    }

    public void setEMPDOJ(String eMPDOJ) {
        this.eMPDOJ = eMPDOJ;
    }

    public String getEMPBASICSALARY() {
        return eMPBASICSALARY;
    }

    public void setEMPBASICSALARY(String eMPBASICSALARY) {
        this.eMPBASICSALARY = eMPBASICSALARY;
    }


}
