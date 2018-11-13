package aleris.com.holypublicschoolll;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import aleris.com.holypublicschoolll.SendSMSPOJO.SendSmsBean;
import aleris.com.holypublicschoolll.Sendsms2POJO.SendBean2;
import aleris.com.holypublicschoolll.TeacherSMSPOJO.TeacherSMSBean;
import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TeacherSms extends AppCompatActivity {

    private ProgressDialog pDialog;

    private JSONObject json;

    private int success = 0;

    String imageString;

    private static int RESULT_LOAD_IMAGE = 1;

    ImageView imageView;

    Button sendsms;

    Spinner menu_id, class_id;

    ProgressDialog pd;

    ArrayList<String> worldlist;

    ArrayList<String> worldlist2;

    String file_name;

    ProgressDialog progressDialog;

    String UPLOAD_URL;

    Bitmap bitmap;

    EditText remarks;

    RelativeLayout send;

    RecyclerView grid;

    GridLayoutManager manager;

    GrrrAdapter adapter;

    SharedPresencesUtility sharedPresencesUtility;

    ProgressBar bar;

    List<TeacherSMSBean> list;

    List<String> clasIds;

    List<beaner> ll;


    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sms);
        sharedPresencesUtility = new SharedPresencesUtility(this);

        clasIds = new ArrayList<>();
        ll = new ArrayList<>();
        imageView = (ImageView) findViewById(R.id.imageView);

        menu_id = findViewById(R.id.menu_id);

        class_id = findViewById(R.id.class_id);


        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
        progressDialog = new ProgressDialog(TeacherSms.this);

        sendsms = (Button) findViewById(R.id.sendsms);

        remarks = (EditText) findViewById(R.id.remarks);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        list = new ArrayList<>();

        adapter = new GrrrAdapter(this, list, ll);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        bar = findViewById(R.id.progress);

        //new MenuFetch().execute("http://holygroup.aleriseducom.com/API/menudetail.aspx?id=1");

        //new ClassFetch().execute("http://holygroup.aleriseducom.com/API/menudetail.aspx?id=2");

    /*   buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadBitmap(bitmap);

            }
        });*/


        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://holygroup.aleriseducom.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApi all = retrofit.create(AllApi.class);

        String userId;

        if (sharedPresencesUtility != null) {

            userId = sharedPresencesUtility.getUserId(TeacherSms.this);

        } else {
            userId = "1";

        }

        String pass;
        if (sharedPresencesUtility != null) {
            pass = sharedPresencesUtility.getPassword(TeacherSms.this);
        } else {
            pass = "1";
        }


        Call<List<TeacherSMSBean>> call = all.teachersms();

        Log.d("userid", userId);
        Log.d("pass", pass);


        call.enqueue(new Callback<List<TeacherSMSBean>>() {
            @Override
            public void onResponse(Call<List<TeacherSMSBean>> call, retrofit2.Response<List<TeacherSMSBean>> response) {
                Log.d("response", String.valueOf(response.body().size()));

                for (int i = 0; i < response.body().size(); i++) {
                    beaner beaner = new beaner();
                    beaner.setName(response.body().get(i).getEmpName());
                    beaner.setPhone(response.body().get(i).getEmpMobile());
                    beaner.setStatus("1");
                    ll.add(beaner);
                }

                adapter.setgrid(response.body(), ll);

                bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<List<TeacherSMSBean>> call, Throwable t) {


                Log.d("failure", t.toString());
                bar.setVisibility(View.GONE);

            }
        });



/*
        class_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String cid;

                if (position > 0)
                {
                    cid = clasIds.get(position);
                }
                else
                {
                    cid = "0";
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/


        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String msg = remarks.getText().toString();

                if (msg.length() > 0) {

                    List<beaner> l2 = adapter.getLl();

                    for (int i = 0; i < l2.size(); i++) {
                        if (l2.get(i).getStatus().equals("1")) {
                            bar.setVisibility(View.VISIBLE);

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://holygroup.aleriseducom.com/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApi all = retrofit.create(AllApi.class);

                            Call<List<SendBean2>> call = all.bean2(l2.get(i).getName(), l2.get(i).getPhone(), msg);


                            call.enqueue(new Callback<List<SendBean2>>() {
                                @Override
                                public void onResponse(Call<List<SendBean2>> call, retrofit2.Response<List<SendBean2>> response) {

                                    Toast.makeText(TeacherSms.this, response.body().get(0).getErrorMessage(), Toast.LENGTH_SHORT).show();

                                    bar.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<List<SendBean2>> call, Throwable t) {

                                    bar.setVisibility(View.GONE);
                                }
                            });


                        }
                    }


                } else {

                    Toast.makeText(TeacherSms.this, "Please enter a Remark", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    public class GrrrAdapter extends RecyclerView.Adapter<GrrrAdapter.MyViewholder> {


        Context context;

        List<TeacherSMSBean> list = new ArrayList<>();

        List<beaner> ll = new ArrayList<>();

        public GrrrAdapter(Context context, List<TeacherSMSBean> list, List<beaner> ll) {

            this.context = context;

            this.list = list;
            this.ll = ll;
        }

        @NonNull
        @Override
        public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.techer_list_model, parent, false);

            return new MyViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {

            final TeacherSMSBean item = list.get(position);

            final beaner beaner = ll.get(position);

            if (beaner.getStatus().equals("1")) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }


            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        beaner beaner1 = new beaner();
                        beaner1.setStatus("1");
                        beaner1.setPhone(item.getEmpMobile());
                        beaner1.setName(item.getEmpName());

                        ll.set(position, beaner1);


                    } else {
                        beaner beaner1 = new beaner();
                        beaner1.setStatus("0");
                        beaner1.setPhone(item.getEmpMobile());
                        beaner1.setName(item.getEmpName());

                        ll.set(position, beaner1);


                    }


                }
            });

            holder.depart.setText(item.getDeptDesc());
            holder.ph.setText(item.getEmpMobile());
            holder.name.setText(item.getEmpName());


        }


        public List<beaner> getLl() {
            return ll;
        }

        public void setgrid(List<TeacherSMSBean> list, List<beaner> ll) {

            this.list = list;
            this.ll = ll;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewholder extends RecyclerView.ViewHolder {


            TextView depart, ph, name;

            CheckBox checkBox;


            public MyViewholder(View itemView) {
                super(itemView);


                depart = itemView.findViewById(R.id.department);
                name = itemView.findViewById(R.id.name);
                ph = itemView.findViewById(R.id.ph);
                checkBox = itemView.findViewById(R.id.checkbox);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                imageView.setImageBitmap(bitmap);
                Log.i("Upload", imageView.toString());

                //calling the method uploadBitmap to upload image

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        final String tags = "something";

        long imagename = System.currentTimeMillis();

        String strName = imagename + ".jpg";

        String strMenu = menu_id.getSelectedItem().toString().trim();

        String strClass = class_id.getSelectedItem().toString().trim();

        strMenu = strMenu.replaceAll(" ", "%20");

        strClass = strClass.replaceAll(" ", "%20");


        Toast.makeText(getApplicationContext(), "Uploading...Please wait!!", Toast.LENGTH_SHORT).show();

        UPLOAD_URL = "http://holygroup.aleriseducom.com/API/document.aspx?MID=" + strMenu + "&CID=" + strClass + "&TXTMSG=" + remarks.getText().toString() + "&subject=0" + "&filename=";

        Log.e("check", UPLOAD_URL);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, UPLOAD_URL + strName,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {
                            Toast.makeText(getApplicationContext(), "Uploaded!!", Toast.LENGTH_SHORT).show();

                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                            Log.i("tagconvertstr", "[" + response + "]");

                            Log.i("Respo", json);

                            JSONObject obj = new JSONObject(new String(response.data));

                            Toast.makeText(getApplicationContext(), obj.getString("errorMessage"), Toast.LENGTH_SHORT).show();

                            Log.i("JsonRespo", obj.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("tags", tags);

                Log.i("Tags",params.toString());

                return params;
            }
*/
            /*
             * Here we are passing image by renaming it with a unique name
             * */
            /*@Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                Log.i("PIcdata",params.toString());
                return params;
            }*/
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


}
