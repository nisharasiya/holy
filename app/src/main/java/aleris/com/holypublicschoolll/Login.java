package aleris.com.holypublicschoolll;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;


public class Login extends AppCompatActivity {

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

    private EditText mobileTxt, passTxt;

    private TextView login, signup, forgot;

    ProgressDialog pbar;

    ProgressDialog pd;

    SharedPresencesUtility sharedPresencesUtility;

    SessionMan session;

    TextView forgot_pass;

    CheckBox checkBox;

    SharedPreferences fcmpref;

    SharedPreferences.Editor fcmedit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseApp.initializeApp(this);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(Login.this));

        fcmpref = getSharedPreferences("fcm", Context.MODE_PRIVATE);

        fcmedit = fcmpref.edit();

        try {

            String tok = FirebaseInstanceId.getInstance().getToken();

            Log.d("token", tok);

            fcmedit.putString("token", tok);

            fcmedit.apply();

        } catch (Exception e) {

            new Thread() {

                public void run() {

                    new MyFirebaseInstanceIDService().onTokenRefresh();
                }

            }.start();

            e.printStackTrace();
        }

        session = new SessionMan(Login.this);

        sharedPresencesUtility = new SharedPresencesUtility(Login.this);

        mobileTxt = (EditText) findViewById(R.id.login_mobile);

        passTxt = (EditText) findViewById(R.id.login_password);

        checkBox = (CheckBox) findViewById(R.id.checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    // show password
                    passTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    passTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        login = findViewById(R.id.loginButton);

        forgot_pass = findViewById(R.id.forgot_pass);

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Forgotpass.class);

                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkEmpty(mobileTxt)) {

                    if (!checkEmpty(passTxt)) {

                        String userId = mobileTxt.getText().toString();

                        String pass = passTxt.getText().toString();

                        // new JsonTask().execute("http://holygroup.aleriseducom.com/API/loginPage.aspx?user_name=" + userId + "&password=" + pass + "&deviceid=" + fcmpref.getString("token" , "") , "android");

                        Log.d("firebase check", "http://holygroup.aleriseducom.com/API/loginPage.aspx?User_name=" + userId + "&password=" + pass + "&deviceid=" + fcmpref.getString("token", ""));

                        new JsonTask().execute("http://holygroup.aleriseducom.com/API/loginPage.aspx?User_name=" + userId + "&password=" + pass + "&deviceid=" + fcmpref.getString("token", ""), "android");
                        // +  fcmpref.getString("token" , "") , "android"
                    } else {
                        Toast.makeText(Login.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Please enter User Id!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(Login.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;

            BufferedReader reader = null;

            try {

                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);

                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (pd.isShowing()) {
                pd.dismiss();
            }

            try {

                JSONObject jsonObject = new JSONObject(result);

                String status = jsonObject.getString("usertype");


                Log.d("status" , result);
                Log.d("status" , status);

                AppPrefs.getInstance(Login.this).putData("UserType", status);

                sharedPresencesUtility.setUserLoginId(Login.this, mobileTxt.getText().toString());

                sharedPresencesUtility.setUserPassword(Login.this, passTxt.getText().toString());

                 Calendar calendar = Calendar.getInstance();
                Calendar setcalendar = Calendar.getInstance();
                setcalendar.set(Calendar.HOUR_OF_DAY, 5);
                setcalendar.set(Calendar.MINUTE, 0);
                setcalendar.set(Calendar.SECOND, 0);

                Intent ishintent = new Intent(Login.this, HeartBeat.class);

                PendingIntent pintent = PendingIntent.getService(Login.this, 0, ishintent, 0);
                AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                if (alarm != null) {
                    alarm.cancel(pintent);
                }
                if (alarm != null) {
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pintent);
                }


                if (status.equals("0")) {

                    Intent intent = new Intent(Login.this, DashboardManager.class);

                    startActivity(intent);

                    finish();
                } else if (status.equals("1")) {

                    Intent intent = new Intent(Login.this, Teacher.class);

                    startActivity(intent);

                    finish();

                } else if (status.equals("2")) {

                    Intent intent = new Intent(Login.this, Reception.class);

                    startActivity(intent);

                    finish();

                } else if (status.equals("3")) {

                    Intent intent = new Intent(Login.this, Admin.class);

                    startActivity(intent);

                    finish();


                } else {

                    Toast.makeText(Login.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() <= 0)
            return true;

        else
            return false;
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

        if (!ispaused) {

            if (AppPrefs.getInstance(Login.this).getData("UserType", "null").equalsIgnoreCase("2")) {
                Intent intent = new Intent(Login.this, Reception.class);
                startActivity(intent);
                finish();


            } else if (AppPrefs.getInstance(Login.this).getData("UserType", "null").equalsIgnoreCase("0")) {
                Intent intent = new Intent(Login.this, DashboardManager.class);
                startActivity(intent);
                finish();


            } else if (AppPrefs.getInstance(Login.this).getData("UserType", "null").equalsIgnoreCase("1")) {
                Intent intent = new Intent(Login.this, Teacher.class);
                startActivity(intent);
                finish();


            } else if (AppPrefs.getInstance(Login.this).getData("UserType", "null").equalsIgnoreCase("3")) {
                Intent intent = new Intent(Login.this, Admin.class);
                startActivity(intent);
                finish();
            }
        }

    }
}














