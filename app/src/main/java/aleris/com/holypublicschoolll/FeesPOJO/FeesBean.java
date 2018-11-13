package aleris.com.holypublicschoolll.FeesPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeesBean {



    @SerializedName("FRC_ID")
    @Expose
    private Integer fRCID;
    @SerializedName("FRC_DATE")
    @Expose
    private String fRCDATE;
    @SerializedName("FRC_PREV_DUE")
    @Expose
    private Integer fRCPREVDUE;
    @SerializedName("FRC_STU_ID")
    @Expose
    private Integer fRCSTUID;
    @SerializedName("FRC_FEE_AMT")
    @Expose
    private Integer fRCFEEAMT;
    @SerializedName("FRC_LATE_FINE")
    @Expose
    private Integer fRCLATEFINE;
    @SerializedName("FRC_PAID_AMT")
    @Expose
    private Integer fRCPAIDAMT;

    public Integer getFRCID() {
        return fRCID;
    }

    public void setFRCID(Integer fRCID) {
        this.fRCID = fRCID;
    }

    public String getFRCDATE() {
        return fRCDATE;
    }

    public void setFRCDATE(String fRCDATE) {
        this.fRCDATE = fRCDATE;
    }

    public Integer getFRCPREVDUE() {
        return fRCPREVDUE;
    }

    public void setFRCPREVDUE(Integer fRCPREVDUE) {
        this.fRCPREVDUE = fRCPREVDUE;
    }

    public Integer getFRCSTUID() {
        return fRCSTUID;
    }

    public void setFRCSTUID(Integer fRCSTUID) {
        this.fRCSTUID = fRCSTUID;
    }

    public Integer getFRCFEEAMT() {
        return fRCFEEAMT;
    }

    public void setFRCFEEAMT(Integer fRCFEEAMT) {
        this.fRCFEEAMT = fRCFEEAMT;
    }

    public Integer getFRCLATEFINE() {
        return fRCLATEFINE;
    }

    public void setFRCLATEFINE(Integer fRCLATEFINE) {
        this.fRCLATEFINE = fRCLATEFINE;
    }

    public Integer getFRCPAIDAMT() {
        return fRCPAIDAMT;
    }

    public void setFRCPAIDAMT(Integer fRCPAIDAMT) {
        this.fRCPAIDAMT = fRCPAIDAMT;
    }

}
