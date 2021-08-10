package android.example.hartshornscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName="note_table")
public class NoteEntity {
    @PrimaryKey
    private int noteID;

    private String noteTitle;
    private String noteDetails;

    private int courseID;


    @Override
    public String toString() {
        return "TermEntity{"+
                "noteID="+ noteID +
                ", noteTitle="+ noteTitle + '\'' +
                ", noteDetails="+ noteDetails + '\'' +
                ", courseID=" + courseID +
                '}';
    }

    public NoteEntity(int noteID, String noteTitle, String noteDetails, int courseID) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteDetails = noteDetails;
        this.courseID = courseID;
    }

    public int getNoteID() { return noteID; }

    public void setNoteID(int noteID) { this.noteID = noteID; }

    public String getNoteTitle() { return noteTitle; }

    public void setNoteTitle(String noteTitle) { this.noteTitle = noteTitle; }

    public String getNoteDetails() { return noteDetails; }

    public void setNoteDetails(String noteDetails) { this.noteDetails = noteDetails; }

    public int getCourseID() { return courseID;}

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
