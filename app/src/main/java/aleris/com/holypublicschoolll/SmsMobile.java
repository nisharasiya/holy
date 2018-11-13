package aleris.com.holypublicschoolll;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import aleris.com.holypublicschoolll.SMSMobilePOJO.SMSMobileBean;
import aleris.com.holypublicschoolll.Sendsms2POJO.SendBean2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SmsMobile extends AppCompatActivity {


    EditText mobile, remarks;

    Button sms;

    Toolbar toolbar;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_mobile);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        mobile = findViewById(R.id.mobile);

        remarks = findViewById(R.id.remarks);

        bar = findViewById(R.id.progress);

        sms = findViewById(R.id.sms);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String m = mobile.getText().toString();
                String r = remarks.getText().toString();

                if (m.length() > 0) {

                    if (r.length() > 0) {

                        bar.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://holygroup.aleriseducom.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApi all = retrofit.create(AllApi.class);

                        Call<SMSMobileBean> call = all.smsmobile("ADMIN", m, r);

                        call.enqueue(new Callback<SMSMobileBean>() {
                            @Override
                            public void onResponse(Call<SMSMobileBean> call, retrofit2.Response<SMSMobileBean> response) {

                                Toast.makeText(SmsMobile.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();



                                bar.setVisibility(View.GONE);


                                mobile.setText("");
                                remarks.setText("");




                            }

                            @Override
                            public void onFailure(Call<SMSMobileBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);
                            }
                        });


                    } else {

                        Toast.makeText(SmsMobile.this, "Please enter a remarks", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(SmsMobile.this, "Please enter a Mobile no.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
