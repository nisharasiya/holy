package aleris.com.holypublicschoolll;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HeartBeat extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("trriggeerrr" , "created");



        Log.d("trriggeerrr" , "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://holygroup.aleriseducom.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApi all = retrofit.create(AllApi.class);


        Call<String> call = all.trigger();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("trriggeerrr" , "success");

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("trriggeerrr" , "failure");
            }
        });



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {



        return null;



    }
}
