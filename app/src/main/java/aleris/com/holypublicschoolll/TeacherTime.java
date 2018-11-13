package aleris.com.holypublicschoolll;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import aleris.com.holypublicschoolll.TeacherPOJO.TeacherBean;
import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TeacherTime extends AppCompatActivity {


    TextView text;

    ProgressBar bar;

    SharedPresencesUtility sharedPresencesUtility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_time);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(TeacherTime.this));

        sharedPresencesUtility = new SharedPresencesUtility(this);

        text = findViewById(R.id.text);

        bar = findViewById(R.id.progress);

        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://holygroup.aleriseducom.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApi all = retrofit.create(AllApi.class);

        String userId;

        if (sharedPresencesUtility != null) {

            userId = sharedPresencesUtility.getUserId(TeacherTime.this);

        } else {
            userId = "1";

        }

        String pass;
        if (sharedPresencesUtility != null) {
            pass = sharedPresencesUtility.getPassword(TeacherTime.this);
        } else {
            pass = "1";
        }


        Call<TeacherBean> call = all.techer(userId , pass);

        call.enqueue(new Callback<TeacherBean>() {
            @Override
            public void onResponse(Call<TeacherBean> call, retrofit2.Response<TeacherBean> response) {


                String ppath = response.body().getResult().get(0).getImagepath();

                text.setText(ppath);


                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TeacherBean> call, Throwable t) {


                bar.setVisibility(View.GONE);

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TeacherTime.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.show();


                ZoomageView imageView = dialog.findViewById(R.id.zoom);

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

                ImageLoader loader = ImageLoader.getInstance();

                loader.displayImage("http://holygroup.aleriseducom.com/stuimage/" + text.getText().toString(), imageView, options);

            }
        });

    }

}




