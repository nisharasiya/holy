package aleris.com.holypublicschoolll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import javax.xml.transform.Templates;

public class Admin extends AppCompatActivity implements View.OnClickListener {

    ImageView i;

    LinearLayout uploadll,  employee ,  about, sms  , smsmobile   , attendance , fees , teachersms , gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        i=(ImageView)findViewById(R.id.mnu);

        i.setOnClickListener(this);

        uploadll = findViewById(R.id.uploadll);

        sms = findViewById(R.id.sms);

      //  attll = findViewById(R.id.attll);

        smsmobile = findViewById(R.id.student);

   //     leavell = findViewById(R.id.leavell);

        attendance = findViewById(R.id.attandance);

        //about = findViewById(R.id.about);
        employee = findViewById(R.id.about);
        fees = findViewById(R.id.fees);
        teachersms = findViewById(R.id.teachersms);
        gallery = findViewById(R.id.gallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, Gallery.class);
                startActivity(intent);


            }
        });


        teachersms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, TeacherSms.class);
                startActivity(intent);



            }
        });



        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, Fees.class);
                startActivity(intent);



            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, AttendanceReport.class);
                startActivity(intent);


            }
        });

        uploadll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, TeacherUpload.class);
                startActivity(intent);

            }
        });

      /*  about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, AboutSchool.class);
                startActivity(intent);

            }
        });*/


        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, Emlpoyee.class);
                startActivity(intent);

            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, SendSMS.class);
                startActivity(intent);
            }
        });



        smsmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this, SmsMobile.class);
                startActivity(intent);

            }
        });




       /* markattandenace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Teacher.this, Student.class);
                startActivity(intent);
            }
        });
*/



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

            PopupMenu popup = new PopupMenu(Admin.this, i);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    AppPrefs.getInstance(Admin.this).clearAll();

                    Intent id=new Intent(Admin.this,Login.class);

                    startActivity(id);

                    Admin.this.finish();
                    // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

            popup.show();
        }
    }
}
