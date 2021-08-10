package android.example.hartshornscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.example.hartshornscheduler.Database.SchedulerRepository;
import android.example.hartshornscheduler.Entity.AssessmentEntity;
import android.os.Bundle;
import android.example.hartshornscheduler.R;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {

    private SchedulerRepository schedulerRepository;

    public static int numAlert;

    static int id2;

    int assessmentID;

    String assessmentTitle;
    String assessmentDueDate;
    String assessmentNotes;
    String assessmentType;
    
    int courseID;
    
    EditText editTitle;
    EditText editDueDate;
    EditText editType;

    Calendar assessmentCalendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener assDate;
    Long tempAssDate;
    String myFormat = "yyyy-MM-dd";

    AssessmentEntity currentAsessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        assessmentID=getIntent().getIntExtra("assessmentID",-1);
        Log.d("Assessment Detail", "at onCreate assessmentID: "+assessmentID );
        assessmentTitle=getIntent().getStringExtra("assessmentTitle");
        assessmentDueDate=getIntent().getStringExtra("assessmentDueDate");
        assessmentNotes=getIntent().getStringExtra("assessmentNotes");
        assessmentType=getIntent().getStringExtra("assessmentType");
        courseID=getIntent().getIntExtra("courseID", -1);
        id2=courseID;
        numAlert=AssessmentActivity.numAlert;

        editTitle=findViewById(R.id.assessmentTitle);
        editDueDate=findViewById(R.id.assessmentDueDate);
        //editNotes=findViewById(R.id.assessmentNotes);
        editType=findViewById(R.id.assessmentType);
        if(assessmentID!=-1){
            editTitle.setText(assessmentTitle);
            editDueDate.setText(assessmentDueDate);
            editType.setText(assessmentType);
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                assessmentCalendar.setTime(sdf.parse(assessmentDueDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        schedulerRepository= new SchedulerRepository(getApplication());


        assDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                assessmentCalendar.set(Calendar.YEAR, year);
                assessmentCalendar.set(Calendar.MONTH, monthOfYear);
                assessmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        editDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AssessmentDetail.this, assDate, assessmentCalendar
                        .get(Calendar.YEAR), assessmentCalendar.get(Calendar.MONTH),
                        assessmentCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    private void updateLabel() {
        //String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        assessmentDueDate = sdf.format(assessmentCalendar.getTime());
        editDueDate.setText(assessmentDueDate);

    }


    public void addAssessmentFromScreen(View view) {
        AssessmentEntity p;

        Log.d("Assessment Detail", "at addAssessmentFromScreen assessmentID: "+assessmentID );

        if(assessmentID!=-1)p= new AssessmentEntity(assessmentID,editTitle.getText().toString(),editDueDate.getText().toString(),null, editType.getText().toString(), courseID);
        else{
            List<AssessmentEntity> allAssessments=schedulerRepository.getAllAssessments();
            Log.d("AssessmentDetails", "at addAssessmentFromScreen size: "+allAssessments.size() );
            if(allAssessments.size()>0)assessmentID=allAssessments.get(allAssessments.size()-1).getAssessmentID();
            else assessmentID =0;
            p= new AssessmentEntity(++assessmentID,editTitle.getText().toString(),assessmentDueDate,null,editType.getText().toString(), courseID);
        }

        schedulerRepository.insert(p);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "The Assessment " +editTitle.getText().toString()+" is planned to be completed on "+ editDueDate.getText().toString());
            // (Optional) Here we're setting the title of the content
            sendIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString() + " Information");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (id == R.id.notifications) {
            Intent intent=new Intent(AssessmentDetail.this,MyReceiver.class);
            intent.putExtra("key","Today is the Goal Completion Date for Assessment "+editTitle.getText().toString() + "!!!!");
            PendingIntent sender= PendingIntent.getBroadcast(AssessmentDetail.this,++numAlert,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            tempAssDate=assessmentCalendar.getTimeInMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP, tempAssDate, sender);
            return true;
        }

        if (id == R.id.deleteAssessment) {

            List<AssessmentEntity> allAssessments=schedulerRepository.getAllAssessments();

            for(AssessmentEntity p:allAssessments){
                if(p.getAssessmentID()==assessmentID)currentAsessment=p;
            }

            schedulerRepository.delete(currentAsessment);

        }

        return super.onOptionsItemSelected(item);
    }

}