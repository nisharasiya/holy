package aleris.com.holypublicschoolll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jsibbold.zoomage.ZoomageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Scroll extends AppCompatActivity {

    ZoomageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        imageView = findViewById(R.id.zoom);

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).
                cacheOnDisk(true).resetViewBeforeLoading(false).build();

        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(getIntent().getStringExtra("url") ,imageView, options);


    }
}
