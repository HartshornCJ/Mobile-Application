package android.example.hartshornscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName="assessment_table")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentID;

    private String assessmentTitle;
    private String assessmentDueDate;
    private String assessmentNotes;
    private String assessmentType;

    private int courseID;


    @Override
    public String toString() {
        return "TermEntity{"+
                "assessmentID="+ assessmentID +
                ", assessmentTitle="+ assessmentTitle + '\'' +
                ", assessmentDueDate="+ assessmentDueDate + '\'' +
                ", assessmentNotes="+ assessmentNotes + '\'' +
                ", assessmentType="+ assessmentType + '\'' +
                ", courseID=" + courseID +
                '}';
    }

    public AssessmentEntity(int assessmentID, String assessmentTitle, String assessmentDueDate, String assessmentNotes, String assessmentType, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentNotes = assessmentNotes;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }

    public int getAssessmentID() { return assessmentID; }

    public void setAssessmentID(int assessmentID) { this.assessmentID = assessmentID; }

    public String getAssessmentTitle() { return assessmentTitle; }

    public void setAssessmentTitle(String assessmentTitle) { this.assessmentTitle = assessmentTitle; }

    public String getAssessmentDueDate() { return assessmentDueDate; }

    public void setAssessmentDueDate(String assessmentDueDate) { this.assessmentDueDate = assessmentDueDate; }

    public String getAssessmentNotes() {
        return assessmentNotes;
    }

    public void setAssessmentNotes(String assessmentNotes) {
        this.assessmentNotes = assessmentNotes;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseID() { return courseID;}

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
