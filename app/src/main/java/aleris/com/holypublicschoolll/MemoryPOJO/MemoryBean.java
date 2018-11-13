package aleris.com.holypublicschoolll.MemoryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemoryBean {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ImageTitle")
    @Expose
    private String imageTitle;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("ImageHeaderID")
    @Expose
    private Integer imageHeaderID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageHeaderID() {
        return imageHeaderID;
    }

    public void setImageHeaderID(Integer imageHeaderID) {
        this.imageHeaderID = imageHeaderID;
    }


}
