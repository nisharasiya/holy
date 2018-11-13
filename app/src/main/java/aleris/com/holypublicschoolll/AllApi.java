package aleris.com.holypublicschoolll;

import java.util.List;

import aleris.com.holypublicschoolll.EmployeePOJO.EmpBean;
import aleris.com.holypublicschoolll.FeesPOJO.FeesBean;
import aleris.com.holypublicschoolll.MemoryPOJO.MemoryBean;
import aleris.com.holypublicschoolll.SMSMobilePOJO.SMSMobileBean;
import aleris.com.holypublicschoolll.SendSMSPOJO.SendSmsBean;
import aleris.com.holypublicschoolll.Sendsms2POJO.SendBean2;
import aleris.com.holypublicschoolll.TCalssPOJO.TCalssBean;
import aleris.com.holypublicschoolll.TeacherPOJO.TeacherBean;
import aleris.com.holypublicschoolll.TeacherSMSPOJO.TeacherSMSBean;
import aleris.com.holypublicschoolll.attendancePOJO.attendanceBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AllApi {

    @GET("API/Gallery.aspx")
    Call<List<MemoryBean>> mem();


    @GET("api/TCLASSDETAIL.aspx")
    Call<List<TCalssBean>> tech(
            @Query("user_name") String username,
            @Query("password") String password
    );


    @GET("api/empdetails.aspx")
    Call<TeacherBean> techer(
            @Query("user_name") String username,
            @Query("password") String password

    );



    @GET("api/getsturecord.aspx")
    Call<List<SendSmsBean>> bean(
            @Query("clsid") String cid
    );



    @GET("/api/stusendsms.aspx")
    Call<List<SendBean2>> bean2(
            @Query("name") String username,
            @Query("mob") String gvfdsg,
            @Query("msg") String sgs
    );



    @GET("api/totalattreport.aspx")
    Call<List<attendanceBean>> att(
            @Query("atttype") String username,
            @Query("date") String gvfds
    );




    @GET("api/totalattreport.aspx")
    Call<List<EmpBean>> emp(
            @Query("atttype") String username,
            @Query("date") String date,
            @Query("type") String type
    );



    @GET("api/feedetailsUMMERY.aspx")
    Call<List<FeesBean>> feess(
            @Query("from") String from,
            @Query("to") String to,
            @Query("admno") String adm ,
            @Query("class") String clas ,
            @Query("status") String status
    );



    @GET("API/SENDSMSTOMOB.aspx")
    Call<SMSMobileBean> smsmobile(
            @Query("NAME") String name,
            @Query("MOB") String mob,
            @Query("MSG") String msg
    );



    @GET("api/Allteacher.aspx")
    Call<List<TeacherSMSBean>> teachersms();

    @GET("api/holidaynotification.aspx")
    Call<String> trigger();



}
