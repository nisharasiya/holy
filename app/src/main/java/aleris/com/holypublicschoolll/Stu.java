package aleris.com.holypublicschoolll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aleris.com.holypublicschoolll.TCalssPOJO.TCalssBean;
import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Stu extends AppCompatActivity {

    RecyclerView grid;

    GridLayoutManager manager;

    GridAdapter adapter;

    List<TCalssBean> list;

    ProgressBar bar;

    ImageView back;

    SharedPresencesUtility sharedPresencesUtility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu);

        sharedPresencesUtility=new SharedPresencesUtility( this );

        back = findViewById(R.id.back);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        list = new ArrayList<>();

        adapter = new GridAdapter(getApplicationContext(), list);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        bar = findViewById(R.id.progress);

        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://holygroup.aleriseducom.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApi all = retrofit.create(AllApi.class);

        String userId;

        if(sharedPresencesUtility!=null){

            userId=sharedPresencesUtility.getUserId(Stu.this);

        }else{
            userId="1";

        }

        String pass;
        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(Stu.this);
        }else{
            pass="1";
        }


        Call<List<TCalssBean>> call = all.tech(userId , pass);

        Log.d("userid" , userId);
        Log.d("pass" , pass);

        call.enqueue(new Callback<List<TCalssBean>>() {
            @Override
            public void onResponse(Call<List<TCalssBean>> call, Response<List<TCalssBean>> response) {

                Log.d("response", String.valueOf(response.body().size()));
                adapter.setgrid(response.body());

                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<TCalssBean>> call, Throwable t) {

                Log.d("failure", t.toString());
                bar.setVisibility(View.GONE);

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }


    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

        Context context;

        List<TCalssBean> list = new ArrayList<>();

        public GridAdapter(Context context, List<TCalssBean> list) {


            this.context = context;

            this.list = list;

        }


        @NonNull
        @Override
        public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.stu_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GridAdapter.MyViewHolder holder, int position) {

            TCalssBean item = list.get(position);

            holder.name.setText(item.getStudentName());

            holder.roll.setText(String.valueOf(item.getRoll()));

        }

        public void setgrid(List<TCalssBean> list) {

            this.list = list;

            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name, roll;

            public MyViewHolder(View itemView) {
                super(itemView);

                // imageView = itemView.findViewById(R.id.image);

                roll = itemView.findViewById(R.id.roll);

                name = itemView.findViewById(R.id.name);


            }
        }
    }

}
