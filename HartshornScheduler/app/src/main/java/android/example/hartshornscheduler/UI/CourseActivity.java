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

public class CourseActivity extends AppCompatActivity {
    
    private SchedulerRepository schedulerRepository;

    static int id2;

    int Id;
    String title;
    String startDate;
    String endDate;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    TermEntity currentTerm;


    public static int numCourses;

    Calendar startCalendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePick;

    Calendar endCalendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePick;
    Long tempDate;
    String myFormat = "yyyy-MM-dd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Id=getIntent().getIntExtra("termID",-1);
        id2=Id;
        Log.d("CourseActivity", "at onCreate termID: "+Id );

        if (Id==-1)Id=AssessmentActivity.id2;
        schedulerRepository= new SchedulerRepository(getApplication());
        List<TermEntity> allTerms=schedulerRepository.getAllTerms();
        currentTerm=null;
        for(TermEntity p:allTerms){
            if(p.getTermID()==Id)currentTerm=p;
        }
//        name=getIntent().getStringExtra("termTitle");
//        price=getIntent().getStringExtra("termPrice");
        editTitle=findViewById(R.id.termTitle);
        editStartDate=findViewById(R.id.termStartDate);
        editEndDate=findViewById(R.id.termEndDate);
        if(currentTerm!=null){
            title=currentTerm.getTermTitle();
            startDate=currentTerm.getTermStartDate();
            endDate=currentTerm.getTermEndDate();
            Log.d("CourseActivity", "at onCreate termID: "+Id );
            Log.d("CourseActivity", "at on Create not Null");
        }
        if(Id!=-1){
            Log.d("CourseActivity", "at onCreate termID: "+Id );
            Log.d("CourseActivity", "at on Create not -1");
            editTitle.setText(title);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            if(startDate!=null){
                try {
                    startCalendar.setTime(sdf.parse(startDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(endDate!=null){
                try {
                    endCalendar.setTime(sdf.parse(endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        schedulerRepository= new SchedulerRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.associated_courses);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseEntity> filteredCourses=new ArrayList<>();
        for(CourseEntity p:schedulerRepository.getAllCourse()){
            if(p.getTermID()==Id)filteredCourses.add(p);
        }
        numCourses=filteredCourses.size();
        adapter.setWords(filteredCourses);


        startDatePick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseActivity.this, startDatePick, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        endDatePick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseActivity.this, endDatePick, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        //String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startDate = sdf.format(startCalendar.getTime());
        editStartDate.setText(startDate);
        endDate = sdf.format(endCalendar.getTime());
        editEndDate.setText(endDate);
    }

    public void addCourse(View view) {
        NoteActivity.id2 = -1;
        AssessmentDetail.id2 = -1;
        Intent intent=new Intent(CourseActivity.this,AssessmentActivity.class);
        intent.putExtra("termID", Id);
        intent.putExtra("courseId", -1);
        startActivity(intent);
    }

    public void addTermFromScreen(View view) {
        TermEntity p;

        if(Id!=-1)p= new TermEntity(Id,editTitle.getText().toString(),editStartDate.getText().toString(), editEndDate.getText().toString());
        else{
            List<TermEntity> allTerms=schedulerRepository.getAllTerms();
            if(allTerms.size()>0)Id=allTerms.get(allTerms.size()-1).getTermID();
            else Id = 0;
            p= new TermEntity(++Id,editTitle.getText().toString(),editStartDate.getText().toString(), editEndDate.getText().toString());
        }
        schedulerRepository.insert(p);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.deleteCourse) {

            if(numCourses==0) {
                schedulerRepository.delete(currentTerm);
            }
            else{
                Toast.makeText(getApplicationContext(),"Can't delete a term with courses",Toast.LENGTH_LONG).show();// make another toast
            }

        }

        return super.onOptionsItemSelected(item);
    }

}