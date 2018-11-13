package aleris.com.holypublicschoolll;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aleris.com.holypublicschoolll.EmployeePOJO.EmpBean;
import aleris.com.holypublicschoolll.attendancePOJO.attendanceBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Emlpoyee extends AppCompatActivity {


    Toolbar toolbar;

    RadioButton p, a;

    RadioGroup group;

    RecyclerView grid;

    GridLayoutManager manager;

    GridAdapter1 adapter;

    ProgressBar bar;

    List<EmpBean> list;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView date;

    String fd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emlpoyee);

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


        p = findViewById(R.id.present);

        a = findViewById(R.id.absent);

        group = findViewById(R.id.group);

        bar = findViewById(R.id.progress);

        grid = findViewById(R.id.grid);

        date = findViewById(R.id.date);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        list = new ArrayList<>();

        adapter = new GridAdapter1(this, list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);


       /* date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Emlpoyee.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("jsfklhdslg", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String d = year + "/" + month + "/" + day;
                date.setText(d);
            }
        };*/





        date.setOnClickListener(new View.OnClickListener() {
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


                final Dialog dialog = new Dialog(Emlpoyee.this);
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
                        date.setText(f);

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

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.present) {

                    bar.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://holygroup.aleriseducom.com/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApi all = retrofit.create(AllApi.class);

                   // Calendar c = Calendar.getInstance();
                   // System.out.println("Current time => "+c.getTime());

                   // SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                  //  String formattedDate = df.format(c.getTime());

                    Call<List<EmpBean>> call = all.emp("P", fd , "1");

                    call.enqueue(new Callback<List<EmpBean>>() {
                        @Override
                        public void onResponse(Call<List<EmpBean>> call, Response<List<EmpBean>> response) {


                            Log.d("response2" , "response");
                            // date.setText(response.body().get(0).getDate());
                            adapter.setgrid(response.body());

                            bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<List<EmpBean>> call, Throwable t) {
                            Log.d("response2" , t.toString());
                            bar.setVisibility(View.GONE);

                        }
                    });




                } else {

                    if (checkedId == R.id.absent) {

                        bar.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://holygroup.aleriseducom.com/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApi all = retrofit.create(AllApi.class);


                        /*Calendar c = Calendar.getInstance();
                        System.out.println("Current time => "+c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = df.format(c.getTime());
*/
                        Call<List<EmpBean>> call = all.emp("A", fd , "1");

                        Log.d("response1" , fd);

                        call.enqueue(new Callback<List<EmpBean>>() {
                            @Override
                            public void onResponse(Call<List<EmpBean>> call, Response<List<EmpBean>> response) {

                                // date.setText(response.body().get(0).getDate());
                                adapter.setgrid(response.body());
                                bar.setVisibility(View.GONE);

                                Log.d("response1" , "rtesponse");
                            }

                            @Override
                            public void onFailure(Call<List<EmpBean>> call, Throwable t) {

                                Log.d("response1" , t.toString());

                                bar.setVisibility(View.GONE);

                            }
                        });


                    }
                }


            }
        });


    }


    public class GridAdapter1 extends RecyclerView.Adapter<GridAdapter1.MyViewHolder> {

        Context context;

        List<EmpBean> list = new ArrayList<>();


        public GridAdapter1(Context context, List<EmpBean> list) {

            this.context = context;

            this.list = list;

        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.emp_list_model, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            EmpBean item = list.get(position);
            holder.name.setText(item.getName());
            holder.atttype.setText(item.getATTTYPE());
            holder.desg.setText(item.getDesg());
            holder.depart.setText(item.getDept());


        }

        public void setgrid(List<EmpBean> list) {


            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {

            return list.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView name, desg , depart , atttype;

            public MyViewHolder(View itemView) {
                super(itemView);

                desg = itemView.findViewById(R.id.desg);
                name = itemView.findViewById(R.id.name);
                depart = itemView.findViewById(R.id.department);
                atttype = itemView.findViewById(R.id.atttype);


            }
        }
    }

}

