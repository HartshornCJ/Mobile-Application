package android.example.hartshornscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.example.hartshornscheduler.Database.SchedulerRepository;
import android.example.hartshornscheduler.Entity.AssessmentEntity;
import android.example.hartshornscheduler.Entity.NoteEntity;
import android.example.hartshornscheduler.R;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class NoteDetail extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    static int id2;

    int courseId;

    String noteTitle;
    String noteDetails;

    int noteId;

    EditText editTitle;
    EditText editDetails;

    NoteEntity currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        courseId=getIntent().getIntExtra("courseID", -1);
        noteTitle=getIntent().getStringExtra("noteTitle");
        noteDetails=getIntent().getStringExtra("noteDetails");
        noteId=getIntent().getIntExtra("noteID", -1);
        Log.d("Note Detail", "at addNoteFromScreen NoteID: "+noteId );
        id2=courseId;

        editTitle=findViewById(R.id.noteTitle);
        editDetails=findViewById(R.id.noteDetails);

        if(noteId!=-1){
            editTitle.setText(noteTitle);
            editDetails.setText(noteDetails);
        }
        schedulerRepository= new SchedulerRepository(getApplication());
    }

    public void addNoteFromScreen(View view) {
        Log.d("Note Detail", "at addNoteFromScreen NoteID: "+noteId );
        NoteEntity p;

        if(noteId!=-1)p= new NoteEntity(noteId,editTitle.getText().toString(),editDetails.getText().toString(),courseId);
        else{
            List<NoteEntity> allNotes=schedulerRepository.getAllNotes();
            if(allNotes.size()>0)noteId=allNotes.get(allNotes.size()-1).getNoteID();
            else noteId =0;
            p= new NoteEntity(++noteId,editTitle.getText().toString(),editDetails.getText().toString(),courseId);
        }
        Log.d("Note Detail", "at addNoteFromScreen P course ID: "+p.getCourseID() );
        Log.d("Note Detail", "at addNoteFromScreen P note ID: "+p.getNoteID() );
        schedulerRepository.insert(p);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "The Note " +editTitle.getText().toString()+":  "+ editDetails.getText().toString());
            // (Optional) Here we're setting the title of the content
            sendIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString() + " Note");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (id == R.id.deleteAssessment) {

            List<NoteEntity> allEntity=schedulerRepository.getAllNotes();

            for(NoteEntity p:allEntity){
                if(p.getNoteID()==noteId)currentNote=p;
            }

            schedulerRepository.delete(currentNote);

        }

        return super.onOptionsItemSelected(item);
    }

}