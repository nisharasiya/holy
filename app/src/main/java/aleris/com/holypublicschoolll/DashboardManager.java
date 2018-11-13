package aleris.com.holypublicschoolll;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import aleris.com.holypublicschoolll.Utils.SharedPresencesUtility;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardManager extends AppCompatActivity {

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    ImageView i;

    CircleImageView a;

    String strPic;

    SharedPresencesUtility sharedPresencesUtility;

    LinearLayout rcard, talk_princ, leave, cmll, aboutll, feell, homework, att, fees, transport;

    RelativeLayout memories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash);

        sharedPresencesUtility = new SharedPresencesUtility(DashboardManager.this);

        i = (ImageView) findViewById(R.id.mnu);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        a = (CircleImageView) findViewById(R.id.a);

        rcard = (LinearLayout) findViewById(R.id.reportll);

        talk_princ = (LinearLayout) findViewById(R.id.talkll);

        leave = (LinearLayout) findViewById(R.id.leavell);

        feell = (LinearLayout) findViewById(R.id.file);

        cmll = (LinearLayout) findViewById(R.id.cmll);

        aboutll = (LinearLayout) findViewById(R.id.aboutll);

        homework = findViewById(R.id.homeworkll);

        att = findViewById(R.id.attll);

        fees = findViewById(R.id.feell);

        transport = findViewById(R.id.transportll);

        memories = findViewById(R.id.memories);

        memories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardManager.this, Memories.class);
                startActivity(intent);

            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardManager.this, Homework.class);
                startActivity(intent);

            }
        });

        att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardManager.this, Attendance.class);
                startActivity(intent);
            }
        });


        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardManager.this, FeesStruct.class);
                startActivity(intent);

            }
        });

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardManager.this, Bus.class);
                startActivity(intent);

            }
        });


        rcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardManager.this, Report_card.class);
                startActivity(intent);
            }
        });


        cmll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardManager.this, Classmate.class);
                startActivity(intent);
            }
        });

        talk_princ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardManager.this, Notice.class);
                startActivity(intent);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardManager.this, TimeTable.class);
                startActivity(intent);
            }
        });


        aboutll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardManager.this, AboutSchool.class);
                startActivity(intent);
            }
        });

        feell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardManager.this, FileDownload.class);

                Toast.makeText(DashboardManager.this, "Please click to item on open a Image", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        /*i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DashboardManager.this, i);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    AppPrefs.getInstance(DashboardManager.this).clearAll();
                    Intent id=new Intent(DashboardManager.this,Login.class);
                    startActivity(id);
                    DashboardManager.this.finish();

                    return true;
                }
            });

            popup.show();
            }
        });
*/
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardManager.this, My_info.class);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPresencesUtility != null) {
            strPic = sharedPresencesUtility.getRegistration(DashboardManager.this);
        }

        if (strPic.equals("")) {
            Toast.makeText(DashboardManager.this, "Change profile photo", Toast.LENGTH_LONG).show();
        } else {
            Picasso.with(getApplicationContext()).load("http://holygroup.aleriseducom.com/stuimage/" + strPic).into(a);
            Log.d("pic", "http://holygroup.aleriseducom.com/stuimage/" + strPic);
        }

    }

    private void setupViewPager(ViewPager viewPager) {

        // b.putString("sname","sd");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainActivity(), "Student");
        adapter.addFragment(new Academics(), "Academics");
        viewPager.setAdapter(adapter);
    }

    public ArrayList<String> getval() {
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        ArrayList<String> a = new ArrayList<>();
        a.add(b.getString("sid"));
        a.add(b.getString("name"));
        a.add(b.getString("fname"));
        return a;

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.profile:

                Intent intent = new Intent(DashboardManager.this, My_info.class);
                startActivity(intent);

                return true;

            case R.id.one:

                Intent id = new Intent(DashboardManager.this, Login.class);
                startActivity(id);
                DashboardManager.this.finish();

                AppPrefs.getInstance(DashboardManager.this).putData("UserType", "-1");
                //  DashboardManager.this.finish();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
}




