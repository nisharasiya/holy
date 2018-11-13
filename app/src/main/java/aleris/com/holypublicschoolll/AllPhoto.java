package aleris.com.holypublicschoolll;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import aleris.com.holypublicschoolll.MemoryPOJO.MemoryBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AllPhoto extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    GridAdapter adapter;

    List<MemoryBean> list;

    ProgressBar bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.allphoto, container, false);

        //  ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(AllPhoto.this));

        grid = vi.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext(), 3);

        list = new ArrayList<>();

        adapter = new GridAdapter(getContext(), list);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        bar = vi.findViewById(R.id.progress);

        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://holygroup.aleriseducom.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApi all = retrofit.create(AllApi.class);

        Call<List<MemoryBean>> call = all.mem();

        call.enqueue(new Callback<List<MemoryBean>>() {
            @Override
            public void onResponse(Call<List<MemoryBean>> call, Response<List<MemoryBean>> response) {

                adapter.setgrid(response.body());

                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<MemoryBean>> call, Throwable t) {


                bar.setVisibility(View.GONE);

            }
        });


        return vi;

    }

    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

        Context context;

        List<MemoryBean> list = new ArrayList<>();

        public GridAdapter(Context context, List<MemoryBean> list) {

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.grid_list_model, parent, false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull final GridAdapter.MyViewHolder holder, int position) {

            final MemoryBean item = list.get(position);
            holder.count.setText(item.getImageTitle());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).
                    cacheOnDisk(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("http://holygroup.aleriseducom.com/" + item.getImageUrl(), holder.imageView, options);

            holder.likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.likes.setVisibility(View.GONE);
                    holder.like.setVisibility(View.VISIBLE);


                }
            });


            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.likes.setVisibility(View.VISIBLE);
                    holder.like.setVisibility(View.GONE);


                }
            });


            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* Dialog dialog = new Dialog(context);

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setContentView(R.layout.dialog);

                    dialog.setCancelable(true);

                    dialog.show();

                    ZoomageView imageView = dialog.findViewById(R.id.zoom);

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).
                            cacheOnDisk(true).resetViewBeforeLoading(false).build();

                    ImageLoader loader = ImageLoader.getInstance();

                    loader.displayImage("http://holygroup.aleriseducom.com/" + item.getImageUrl(), imageView, options);

                    final ImageView arrow = dialog.findViewById(R.id.arrow);

                    final TextView dot = dialog.findViewById(R.id.dot);

                    arrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            arrow.setVisibility(View.GONE);
                            dot.setVisibility(View.VISIBLE);

                        }
                    });*/


                    Intent i = new Intent(getContext(), Scroll.class);
                    i.putExtra("url", "http://holygroup.aleriseducom.com/" + item.getImageUrl());
                    startActivity(i);


                }
            });


        }

        public void setgrid(List<MemoryBean> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView, likes, like;

            TextView count;

            public MyViewHolder(View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image);

                likes = itemView.findViewById(R.id.likes);

                like = itemView.findViewById(R.id.like);

                count = itemView.findViewById(R.id.count);

            }
        }
    }


}
