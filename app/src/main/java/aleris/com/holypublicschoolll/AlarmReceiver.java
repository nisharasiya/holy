package aleris.com.holypublicschoolll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {


    String TAG = "trriggeerrr";


    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");



                return;
            }
        }

        Log.d(TAG , "triggererd");

        //Trigger the notification
    }
}
