package aleris.com.holypublicschoolll;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.Calendar;


public class Splash extends Activity {

    boolean ispaused = false;

    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA
    };

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_splash);
        setContentView(R.layout.activity_splashd);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);

        } else {
            startApp();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {

            if (
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                    ) {
//

            } else {
                if (
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WAKE_LOCK)
                        ) {

                    Toast.makeText(getApplicationContext(), "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }
        }
    }

    private void startApp() {
        ispaused = false;
        final Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                    if (!ispaused) {
/*

                        Calendar calendar = Calendar.getInstance();
                        Calendar setcalendar = Calendar.getInstance();
                        setcalendar.set(Calendar.HOUR_OF_DAY, 4);
                        setcalendar.set(Calendar.MINUTE, 7);
                        setcalendar.set(Calendar.SECOND, 0);
                        // cancel already scheduled reminders
                        cancelReminder(Splash.this,AlarmReceiver.class);

                        if(setcalendar.before(calendar))
                            setcalendar.add(Calendar.DATE,1);

                        // Enable a receiver
                        ComponentName receiver = new ComponentName(Splash.this, AlarmReceiver.class);
                        PackageManager pm = Splash.this.getPackageManager();
                        pm.setComponentEnabledSetting(receiver,
                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                PackageManager.DONT_KILL_APP);

                        Intent intent1 = new Intent(Splash.this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Splash.this,
                                DAILY_REMINDER_REQUEST_CODE, intent1,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am = (AlarmManager) Splash.this.getSystemService(ALARM_SERVICE);
                        if (am != null) {
                            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(),
                                    AlarmManager.INTERVAL_DAY, pendingIntent);
                        }



*/

                        Calendar calendar = Calendar.getInstance();
                        Calendar setcalendar = Calendar.getInstance();
                        setcalendar.set(Calendar.HOUR_OF_DAY, 5);
                        setcalendar.set(Calendar.MINUTE, 0);
                        setcalendar.set(Calendar.SECOND, 0);

                        Intent ishintent = new Intent(Splash.this, HeartBeat.class);
                        PendingIntent pintent = PendingIntent.getService(Splash.this, 0, ishintent, 0);
                        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        if (alarm != null) {
                            alarm.cancel(pintent);
                        }
                        if (alarm != null) {
                            alarm.setRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pintent);
                        }


                        if (AppPrefs.getInstance(Splash.this).getData("UserType", "null").equalsIgnoreCase("2")) {
                            Intent intent = new Intent(Splash.this, Reception.class);
                            startActivity(intent);
                            finish();
                        } else if (AppPrefs.getInstance(Splash.this).getData("UserType", "null").equalsIgnoreCase("0")) {
                            Intent intent = new Intent(Splash.this, DashboardManager.class);
                            startActivity(intent);
                            finish();
                        } else if (AppPrefs.getInstance(Splash.this).getData("UserType", "null").equalsIgnoreCase("1")) {
                            Intent intent = new Intent(Splash.this, Teacher.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(Splash.this, Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timerThread.start();
    }

    static int DAILY_REMINDER_REQUEST_CODE = 1;

    public static void cancelReminder(Context context,Class<?> cls)
    {
        // Disable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

}
