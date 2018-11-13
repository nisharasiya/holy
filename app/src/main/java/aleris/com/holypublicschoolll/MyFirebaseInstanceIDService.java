package aleris.com.holypublicschoolll;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    SharedPreferences pref;
    SharedPreferences.Editor edit;


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();


            Log.d("refresh", refreshedToken);

            pref = getSharedPreferences("fcm", Context.MODE_PRIVATE);
            edit = pref.edit();


            edit.putString("token", refreshedToken);
            edit.apply();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
