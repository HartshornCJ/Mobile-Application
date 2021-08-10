package android.example.hartshornscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.example.hartshornscheduler.Database.SchedulerRepository;
import android.example.hartshornscheduler.Entity.AssessmentEntity;
import android.example.hartshornscheduler.Entity.CourseEntity;
import android.example.hartshornscheduler.Entity.NoteEntity;
import android.example.hartshornscheduler.Entity.TermEntity;
import android.os.Bundle;
import android.example.hartshornscheduler.R;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentActivity extends AppCompatActivity {

    private SchedulerRepository schedulerRepository;

    public static int numAlert;

    static int id2;

    int courseID;

    String courseTitle;
    String courseStartDate;
    String courseEndDate;
    String courseStatus;
    String courseMentor;
    String courseMentorNumber;
    String courseMentorEMail;

    int termID;


    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editMentor;
    EditText editMentorNumber;
    EditText editMentorEMail;

    CourseEntity currentCourse;

    public static int numAssessments;

    Calendar courseStartCalendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener courseStarDate;

    Calendar courseEndCalendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener courseEnDate;
    Long tempDate;
    String myFormat = "yyyy-MM-dd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("AsseessmentActivity", "at onCreate" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        //int id = getIntent().getIntExtra("", -1)
        courseID=getIntent().getIntExtra("courseID",-1);
        termID=getIntent().getIntExtra("termID",-1);
        id2=termID;

        Log.d("AsseessmentActivity", "at onCreate courseID: "+courseID );
        Log.d("AsseessmentActivity", "at onCreate termID: "+termID );
        if (courseID==-1&&NoteActivity.forID) {
            courseID=NoteActivity.id2;
            NoteActivity.forID=false;
        }
        else if(courseID==-1){
            courseID=AssessmentDetail.id2;
            numAlert=AssessmentDetail.numAlert;
        }
        schedulerRepository = new SchedulerRepository(getApplication());
        List<CourseEntity> allCourses=schedulerRepository.getAllCourse();

        for(CourseEntity p:allCourses){
            if(p.getCourseID()==courseID)currentCourse=p;
        }

        editTitle=findViewById(R.id.courseTitle);
        editStartDate=findViewById(R.id.courseStartDate);
        editEndDate=findViewById(R.id.courseEndDate);
        editStatus=findViewById(R.id.courseStatus);
        editMentor=findViewById(R.id.courseMentor);
        editMentorNumber=findViewById(R.id.courseMNum);
        editMentorEMail=findViewById(R.id.courseMEMail);
        //editNote=findViewById(R.id.courseStartDate);

        if(currentCourse!=null){
            courseTitle=currentCourse.getCourseTitle();
            courseStartDate = currentCourse.getCourseStartDate();
            courseEndDate= currentCourse.getCourseEndDate();
            courseStatus = currentCourse.getCourseStatus();
            courseMentor = currentCourse.getCourseMentor();
            courseMentorNumber = currentCourse.getCourseMentorNumber();
            courseMentorEMail = currentCourse.getCourseMentorEMail();
            if (termID==-1)id2=currentCourse.getTermID();
        }
        if(courseID!=-1){
            editTitle.setText(courseTitle);
            editStartDate.setText(courseStartDate);
            editEndDate.setText(courseEndDate);
            editStatus.setText(courseStatus);
            editMentor.setText(courseMentor);
            editMentorNumber.setText(courseMentorNumber);
            editMentorEMail.setText(courseMentorEMail);

            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                if(courseStartDate!=null)
                {
                    courseStartCalendar.setTime(sdf.parse(courseStartDate));
                }
                //courseStartCalendar.setTime(sdf.parse(courseStartDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                if(courseEndDate!=null) {
                    courseEndCalendar.setTime(sdf.parse(courseEndDate));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        schedulerRepository= new SchedulerRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.associated_assessments);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments=new ArrayList<>();
        for(AssessmentEntity p:schedulerRepository.getAllAssessments()){
            if(p.getCourseID()==courseID)filteredAssessments.add(p);
        }
        numAssessments=filteredAssessments.size();
        adapter.setWords(filteredAssessments);


        courseStarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                courseStartCalendar.set(Calendar.YEAR, year);
                courseStartCalendar.set(Calendar.MONTH, monthOfYear);
                courseStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }

        };

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AssessmentActivity.this, courseStarDate, courseStartCalendar
                        .get(Calendar.YEAR), courseStartCalendar.get(Calendar.MONTH),
                        courseStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        courseEnDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                courseEndCalendar.set(Calendar.YEAR, year);
                courseEndCalendar.set(Calendar.MONTH, monthOfYear);
                courseEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }

        };

        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AssessmentActivity.this, courseEnDate, courseEndCalendar
                        .get(Calendar.YEAR), courseStartCalendar.get(Calendar.MONTH),
                        courseStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabelStart() {
        //String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        courseStartDate = sdf.format(courseStartCalendar.getTime());
        editStartDate.setText(courseStartDate);
    }

    private void updateLabelEnd() {
        //String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        courseEndDate = sdf.format(courseEndCalendar.getTime());
        editEndDate.setText(courseEndDate);
    }

    public void addAssessment(View view) {

        Intent intent=new Intent(AssessmentActivity.this,AssessmentDetail.class);
        intent.putExtra("termID", termID);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    public void viewNotes(View view) {

        Intent intent=new Intent(AssessmentActivity.this, NoteActivity.class);
        intent.putExtra("termID", termID);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    public void addCourseFromScreen(View view) {
        CourseEntity p;

        if(courseID!=-1)p= new CourseEntity(courseID, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),
                editStatus.getText().toString(), editMentor.getText().toString(),editMentorNumber.getText().toString(), editMentorEMail.getText().toString(),termID);
                //Log.d("AsseessmentActivity", "at addCourseFromScreen in IF" );
        else{
            List<CourseEntity> allCourses=schedulerRepository.getAllCourse();
            //Log.d("AsseessmentActivity", "at addCourseFromScreen in ELSE courseID: "+ allCourses.size() );
            //if(allCourses.size()>0)courseID=allCourses.get(allCourses.size()-1).getTermID();
            if(allCourses.size()>0){
                courseID = allCourses.size();
            }
            else courseID = 0;
            p= new CourseEntity(++courseID,editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),
                    editStatus.getText().toString(), editMentor.getText().toString(),editMentorNumber.getText().toString(), editMentorEMail.getText().toString(),termID);
            Log.d("AsseessmentActivity", "at addCourseFromScreen in ELSE" );
        }
        schedulerRepository.insert(p);
        //Log.d("AsseessmentActivity", "at addCourseFromScreen courseID: "+courseID );
        //Log.d("AsseessmentActivity", "at addCourseFromScreen termID: "+termID );
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "The Term " +editTitle.getText().toString()+" is planned to be start on "+editStartDate+", and to end on "+editEndDate+
                    ". The course mentor is"+editMentor+". The Status is "+editStatus+".");
            // (Optional) Here we're setting the title of the content
            sendIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString() + " Information");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (id == R.id.notificationsStart) {
            Intent intent=new Intent(AssessmentActivity.this,MyReceiver.class);
            intent.putExtra("key","Today is the Start Date for Course "+editTitle.getText().toString() + "!!!!");
            PendingIntent sender= PendingIntent.getBroadcast(AssessmentActivity.this,++numAlert,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            tempDate=courseStartCalendar.getTimeInMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP, tempDate, sender);
            return true;
        }

        if (id == R.id.notificationsEnd) {
            Intent intent=new Intent(AssessmentActivity.this,MyReceiver.class);
            intent.putExtra("key","Today is the End Date for Course "+editTitle.getText().toString() + "!!!!");
            PendingIntent sender= PendingIntent.getBroadcast(AssessmentActivity.this,++numAlert,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            tempDate=courseEndCalendar.getTimeInMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP, tempDate, sender);
            return true;
        }

        if (id == R.id.deleteCourse) {

            if(numAssessments==0) {
                schedulerRepository.delete(currentCourse);
            }
            else{
                Toast.makeText(getApplicationContext(),"Can't delete a course with assessments",Toast.LENGTH_LONG).show();// make another toast
            }

        }

        return super.onOptionsItemSelected(item);
    }


}