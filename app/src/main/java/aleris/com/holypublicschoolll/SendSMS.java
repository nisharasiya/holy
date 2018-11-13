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
import aleris.com.holypublicschoolll.TCalssPOJO.TCalssBean;
import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendSMS extends AppCompatActivity {

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

    GrAdapter adapter;

    SharedPresencesUtility sharedPresencesUtility;

    ProgressBar bar;

    List<SendSmsBean> list;

    List<String> clasIds;

    List<beaner> ll;


    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);


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
        progressDialog = new ProgressDialog(SendSMS.this);

        sendsms = (Button) findViewById(R.id.sendsms);

        remarks = (EditText) findViewById(R.id.remarks);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        list = new ArrayList<>();

        adapter = new GrAdapter(this, list, ll);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        bar = findViewById(R.id.progress);

        new MenuFetch().execute("http://holygroup.aleriseducom.com/API/menudetail.aspx?id=1");

        new ClassFetch().execute("http://holygroup.aleriseducom.com/API/menudetail.aspx?id=2");

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


        class_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String cid;

                if (position > 0)
                {
                    cid = clasIds.get(position - 1);
                }
                else
                {
                    cid = "0";
                }

                bar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://holygroup.aleriseducom.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApi all = retrofit.create(AllApi.class);

                String userId;

                if (sharedPresencesUtility != null) {

                    userId = sharedPresencesUtility.getUserId(SendSMS.this);

                } else {
                    userId = "1";

                }

                String pass;
                if (sharedPresencesUtility != null) {
                    pass = sharedPresencesUtility.getPassword(SendSMS.this);
                } else {
                    pass = "1";
                }


                Call<List<SendSmsBean>> call = all.bean(cid);

                Log.d("userid", userId);
                Log.d("pass", pass);


                call.enqueue(new Callback<List<SendSmsBean>>() {
                    @Override
                    public void onResponse(Call<List<SendSmsBean>> call, retrofit2.Response<List<SendSmsBean>> response) {
                        Log.d("response", String.valueOf(response.body().size()));

                        for (int i = 0; i < response.body().size(); i++) {
                            beaner beaner = new beaner();
                            beaner.setName(response.body().get(i).getStudentName());
                            beaner.setPhone(response.body().get(i).getPhone());
                            beaner.setStatus("1");
                            ll.add(beaner);
                        }

                        adapter.setgrid(response.body(), ll);

                        bar.setVisibility(View.GONE);


                    }

                    @Override
                    public void onFailure(Call<List<SendSmsBean>> call, Throwable t) {


                        Log.d("failure", t.toString());
                        bar.setVisibility(View.GONE);

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String msg = remarks.getText().toString();

               if (msg.length()>0){

                   List<beaner> l2 = adapter.getLl();

                   for (int i = 0 ; i < l2.size() ; i++)
                   {
                       if (l2.get(i).getStatus().equals("1"))
                       {
                           bar.setVisibility(View.VISIBLE);

                           Retrofit retrofit = new Retrofit.Builder()
                                   .baseUrl("http://holygroup.aleriseducom.com/")
                                   .addConverterFactory(ScalarsConverterFactory.create())
                                   .addConverterFactory(GsonConverterFactory.create())
                                   .build();

                           AllApi all = retrofit.create(AllApi.class);

                           Call<List<SendBean2>> call = all.bean2(l2.get(i).getName() , l2.get(i).getPhone() , msg);

                           call.enqueue(new Callback<List<SendBean2>>() {
                               @Override
                               public void onResponse(Call<List<SendBean2>> call, retrofit2.Response<List<SendBean2>> response) {


                                   Toast.makeText(SendSMS.this, response.body().get(0).getErrorMessage(), Toast.LENGTH_SHORT).show();

                                   bar.setVisibility(View.GONE);

                               }

                               @Override
                               public void onFailure(Call<List<SendBean2>> call, Throwable t) {

                                   bar.setVisibility(View.GONE);
                               }
                           });


                       }
                   }


               }else {

                   Toast.makeText(SendSMS.this, "Please enter a Remark", Toast.LENGTH_SHORT).show();
               }



            }
        });


    }


    public class GrAdapter extends RecyclerView.Adapter<GrAdapter.MyViewholder> {

        Context context;

        List<SendSmsBean> list = new ArrayList<>();

        List<beaner> ll = new ArrayList<>();

        public GrAdapter(Context context, List<SendSmsBean> list, List<beaner> ll) {

            this.context = context;

            this.list = list;

            this.ll = ll;
        }

        @NonNull
        @Override
        public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.g_list_model, parent, false);

            return new MyViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {

            final SendSmsBean item = list.get(position);

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
                        beaner1.setPhone(item.getPhone());
                        beaner1.setName(item.getStudentName());


                        ll.set(position, beaner1);


                    } else {
                        beaner beaner1 = new beaner();
                        beaner1.setStatus("0");
                        beaner1.setPhone(item.getPhone());
                        beaner1.setName(item.getStudentName());

                        ll.set(position, beaner1);


                    }


                }
            });

            holder.id.setText(item.getAdmno());
            holder.clas.setText(item.getCls());
            holder.ph.setText(item.getPhone());
            holder.name.setText(item.getStudentName());

        }

        public List<beaner> getLl() {
            return ll;
        }

        public void setgrid(List<SendSmsBean> list, List<beaner> ll) {

            this.list = list;
            this.ll = ll;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewholder extends RecyclerView.ViewHolder {

            TextView clas, ph, id , name;

            CheckBox checkBox;

            public MyViewholder(View itemView) {
                super(itemView);

                clas = itemView.findViewById(R.id.clas);
                id = itemView.findViewById(R.id.id);
                ph = itemView.findViewById(R.id.ph);
                checkBox = itemView.findViewById(R.id.checkbox);
                name = itemView.findViewById(R.id.name);
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

    private class MenuFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(SendSMS.this);
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
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

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

                JSONArray array1 = new JSONArray(result.toString());

                worldlist = new ArrayList<String>();
                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);

                    String classmateName = obj1.getString("ID");

                    String parentsMobile = obj1.getString("MENU");

                    worldlist.add(obj1.optString("MENU"));

                    menu_id.setAdapter(new ArrayAdapter<String>(SendSMS.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist));

                    String strName = menu_id.getSelectedItem().toString();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClassFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

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
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

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

            try {
                JSONArray array1 = new JSONArray(result.toString());
                worldlist2 = new ArrayList<String>();
                worldlist2.add("All");
                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);
                    String classmateName = obj1.getString("SEC_ID");
                    String parentsMobile = obj1.getString("class");

                    worldlist2.add(obj1.optString("class"));

                    clasIds.add(obj1.getString("SEC_ID"));

                    class_id.setAdapter(new ArrayAdapter<String>(SendSMS.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist2));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
