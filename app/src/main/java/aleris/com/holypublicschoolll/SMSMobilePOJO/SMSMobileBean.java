package aleris.com.holypublicschoolll.SMSMobilePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SMSMobileBean {




    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
