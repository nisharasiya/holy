package aleris.com.holypublicschoolll;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Teacher extends AppCompatActivity implements View.OnClickListener {

    ImageView i;

    LinearLayout uploadll, feell, profilell , attll , student , leavell , markattandenace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherdash);

        i=(ImageView)findViewById(R.id.mnu);

        i.setOnClickListener(this);

        uploadll = findViewById(R.id.uploadll);

        profilell = findViewById(R.id.profilell);

        attll = findViewById(R.id.attll);

        student = findViewById(R.id.student);

        leavell = findViewById(R.id.leavell);

        markattandenace = findViewById(R.id.markattandenace);

        feell = findViewById(R.id.feell);

        uploadll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, TeacherUpload.class);
                startActivity(intent);

            }
        });

        feell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, AboutSchool.class);
                startActivity(intent);

            }
        });

        profilell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, TeacherProfile.class);
                startActivity(intent);
            }
        });


        attll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, AttendanceTeacher.class);
                startActivity(intent);
            }
        });


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, Stu.class);
                startActivity(intent);

            }
        });


        leavell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, TeacherTime.class);
                startActivity(intent);
            }
        });

        markattandenace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, Memories.class);
                startActivity(intent);
            }
        });




       /* receptionll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, ReceptionRecord.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.mnu) {

            PopupMenu popup = new PopupMenu(Teacher.this, i);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    AppPrefs.getInstance(Teacher.this).clearAll();

                    Intent id=new Intent(Teacher.this,Login.class);

                    startActivity(id);

                    Teacher.this.finish();
                    // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

            popup.show();
        }
    }
}
