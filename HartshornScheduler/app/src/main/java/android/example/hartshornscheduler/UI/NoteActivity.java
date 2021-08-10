package android.example.hartshornscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.hartshornscheduler.Database.SchedulerRepository;
import android.example.hartshornscheduler.Entity.NoteEntity;
import android.example.hartshornscheduler.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private SchedulerRepository schedulerRepository;

    int courseID;

    static boolean forID;
    static int id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //PartDetail.id2=-1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        courseID=getIntent().getIntExtra("courseID", -1);
        if(courseID==-1)courseID=NoteDetail.id2;
        forID = true;
        id2=courseID;

        schedulerRepository= new SchedulerRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteAdapter adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<NoteEntity> filteredNotes=new ArrayList<>();
        Log.d("Note Activity", "at onCreate list size: "+filteredNotes.size() );
        for(NoteEntity p:schedulerRepository.getAllNotes()){
            if(p.getCourseID()==courseID)filteredNotes.add(p);
        }
        adapter.setWords(filteredNotes);
        //Log.d("Note Activity", "at onCreate Size: "+schedulerRepository.getAllNotes().size() );
        //adapter.setWords(schedulerRepository.getAllNotes());



    }

    public void addNote(android.view.View view){
        Intent intent = new Intent(NoteActivity.this, NoteDetail.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
}