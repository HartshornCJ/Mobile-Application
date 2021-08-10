package android.example.hartshornscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.hartshornscheduler.Database.SchedulerRepository;
import android.os.Bundle;
import android.example.hartshornscheduler.R;
import android.view.View;

public class TermActivity extends AppCompatActivity {

    private SchedulerRepository schedulerRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //PartDetail.id2=-1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        schedulerRepository= new SchedulerRepository(getApplication());
        schedulerRepository.getAllTerms();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(schedulerRepository.getAllTerms());

    }

    public void addTerm(View view) {
        AssessmentActivity.id2 = -1;
        Intent intent=new Intent(TermActivity.this,CourseActivity.class);
        startActivity(intent);
    }

}