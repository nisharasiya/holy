package aleris.com.holypublicschoolll;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aleris.com.holypublicschoolll.FeesPOJO.FeesBean;
import aleris.com.holypublicschoolll.SendSMSPOJO.SendSmsBean;
import aleris.com.holypublicschoolll.Sendsms2POJO.SendBean2;
import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;
import aleris.com.holypublicschoolll.attendancePOJO.attendanceBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Fees extends AppCompatActivity {

    TextView fromdate, todate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    Spinner class_id;

    ProgressBar bar;

    List<String> clasIds;

    List<beaner> ll;

    SharedPresencesUtility sharedPresencesUtility;

    RadioButton p, c;

    RadioGroup group;

    RecyclerView grid;

    GridLayoutManager manager;

    FeesAdapter adapter;

    List<FeesBean> list;

    ArrayList<String> worldlist;

    ArrayList<String> worldlist2;

    Button searchView;

    String fd, td, cls, status = "0", adm;

    TextView totalamount;

    EditText admission;

    Toolbar toolbar;

    // int count = 0;

    TextView can, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        clasIds = new ArrayList<>();

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


        fromdate = findViewById(R.id.fromdate);

        todate = findViewById(R.id.todate);

        class_id = findViewById(R.id.class_id);

        bar = findViewById(R.id.progress);

        group = findViewById(R.id.group);

        p = findViewById(R.id.paid);

        c = findViewById(R.id.cancel);

        grid = findViewById(R.id.grid);

        admission = findViewById(R.id.admission);

        totalamount = findViewById(R.id.amount);
        amount = findViewById(R.id.amount);
        // can = findViewById(R.id.can);


        searchView = findViewById(R.id.serach);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        list = new ArrayList<>();

        new ClassFetch().execute("http://holygroup.aleriseducom.com/API/menudetail.aspx?id=2");

        adapter = new FeesAdapter(this, list);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Fees.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();*/


                final Dialog dialog = new Dialog(Fees.this);
                dialog.setContentView(R.layout.dialogg);
                dialog.setCancelable(false);
                dialog.show();

                final DatePicker picker = (DatePicker) dialog.findViewById(R.id.picker);
                Button ok = (Button) dialog.findViewById(R.id.ok);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String day = String.valueOf(picker.getDayOfMonth());
                        String month = String.valueOf(picker.getMonth() + 1);
                        String year = String.valueOf(picker.getYear());

                        String f = year + "-" + month + "-" + day;

                        //fromdate.setText(year + "-" + month + "-" + day);
                        fromdate.setText(f);

                        fd = f;

                        dialog.dismiss();


                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();


                    }
                });


            }
        });

      /*  mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("gvfjh", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year + "-" + month + "-" + day;
                fromdate.setText(date);
                fd = date;
            }
        };*/


        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Fees.this);
                dialog.setContentView(R.layout.dialogg);
                dialog.setCancelable(false);
                dialog.show();

                final DatePicker picker = (DatePicker) dialog.findViewById(R.id.picker);
                Button ok = (Button) dialog.findViewById(R.id.ok);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String day = String.valueOf(picker.getDayOfMonth());
                        String month = String.valueOf(picker.getMonth() + 1);
                        String year = String.valueOf(picker.getYear());

                        String d = year + "-" + month + "-" + day;

                        todate.setText(year + "-" + month + "-" + day);
                        todate.setText(d);


                        td = d;
                        dialog.dismiss();


                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();


                    }
                });
            }
        });

       /* mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("gvfjh", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String dat = year + "-" + month + "-" + day;
                todate.setText(dat);
                td = dat;
            }
        };
*/

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adm = admission.getText().toString();

                //  bar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://holygroup.aleriseducom.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApi all = retrofit.create(AllApi.class);

                Log.d("asdasd", fd);
                Log.d("asdasd", td);
                Log.d("asdasd", adm);
                Log.d("asdasd", cls);
                Log.d("asdasd", status);

                Call<List<FeesBean>> call = all.feess(fd, td, adm, cls, status);

                call.enqueue(new Callback<List<FeesBean>>() {
                    @Override
                    public void onResponse(final Call<List<FeesBean>> call, retrofit2.Response<List<FeesBean>> response) {

                        int count = 0;

                        if (status.equals("0")) {
                            for (int i = 0; i < response.body().size(); i++) {
                                count = count + response.body().get(i).getFRCPAIDAMT();

                                totalamount.setText("Total Amount : " + String.valueOf(count));

                            }
                        } else {
                            totalamount.setText("Total Amount : 0");
                        }

                        adapter.setgrid(response.body());


                        //  bar.setVisibility(View.GONE);

                        Log.d("asdasd", "response");

                    }


                    @Override
                    public void onFailure(Call<List<FeesBean>> call, Throwable t) {


                        Log.d("asdasd", t.toString());
                        // bar.setVisibility(View.GONE);

                    }
                });


            }
        });


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.paid) {

                    status = "0";


                }
                if (checkedId == R.id.cancel) {

                    status = "1";


                }


            }


        });


       /* p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                can.setVisibility(View.VISIBLE);
                totalamount.setVisibility(View.GONE);

            }

        });

        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                totalamount.setVisibility(View.VISIBLE);
                can.setVisibility(View.GONE);



            }
        });
*/

        class_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //String cid;

                if (position > 0) {

                    cls = clasIds.get(position);

                } else {

                    cls = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.MyViewHolder> {

        Context context;

        int count = 0;

        List<FeesBean> list = new ArrayList<>();


        public FeesAdapter(Context context, List<FeesBean> list) {

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public FeesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.fees_list_model, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeesAdapter.MyViewHolder holder, int position) {


            FeesBean item = list.get(position);

         /*   holder.previousdues.setText("Previous Dues : " +item.getFRCPREVDUE());
            holder.totalfee.setText("Total Fee : " +item.getFRCFEEAMT());
            holder.latefine.setText("Late Fine : " +item.getFRCLATEFINE());
            holder.paidamount.setText("PaidAmount : " +item.getFRCPAIDAMT());
            holder.date.setText("Date : " +item.getFRCDATE());
*/


            holder.previousdues.setText(String.valueOf(item.getFRCPREVDUE()));
            holder.totalfee.setText("" + item.getFRCFEEAMT());
            holder.latefine.setText("" + item.getFRCLATEFINE());
            holder.paidamount.setText("" + item.getFRCPAIDAMT());

            //count = count + item.getFRCPAIDAMT();

            //totalamount.setText("Total Amount : " + String.valueOf(count));


            //  holder.date.setText(item.getFRCDATE());


        }


        public void setgrid(List<FeesBean> list) {

            this.list = list;

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView previousdues, totalfee, latefine, paidamount, date;


            public MyViewHolder(View itemView) {
                super(itemView);

                previousdues = itemView.findViewById(R.id.dues);

                totalfee = itemView.findViewById(R.id.fee);

                latefine = itemView.findViewById(R.id.fine);

                paidamount = itemView.findViewById(R.id.paid);

                //   date = itemView.findViewById(R.id.date);

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

                    class_id.setAdapter(new ArrayAdapter<String>(Fees.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}

