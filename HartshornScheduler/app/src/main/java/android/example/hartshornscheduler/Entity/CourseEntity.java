package android.example.hartshornscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName="course_table")
public class CourseEntity {

    @PrimaryKey
    private int courseID;

    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseStatus;
    private String courseMentor;
    private String courseMentorNumber;
    private String courseMentorEMail;

    private int termID;




    @Override
    public String toString() {
        return "TermEntity{"+
                "courseID="+ courseID +
                ", courseTitle="+ courseTitle + '\'' +
                ", courseStartDate="+ courseStartDate + '\'' +
                ", courseEndDate="+ courseEndDate + '\'' +
                ", courseStatus="+ courseStatus + '\'' +
                ", courseMentor="+ courseMentor + '\'' +
                ", courseMentorNumber="+ courseMentorNumber + '\'' +
                ", courseMentorEMail="+ courseMentorEMail + '\'' +
                ", termID=" + termID +
                '}';
    }



    public CourseEntity(int courseID, String courseTitle, String courseStartDate, String courseEndDate,String courseStatus,
                        String courseMentor, String courseMentorNumber, String courseMentorEMail, int termID)
    {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentor = courseMentor;
        this.courseMentorNumber = courseMentorNumber;
        this.courseMentorEMail = courseMentorEMail;
        this.termID = termID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseMentor() {
        return courseMentor;
    }

    public void setCourseMentor(String courseMentor) {
        this.courseMentor = courseMentor;
    }

    public String getCourseMentorEMail() {
        return courseMentorEMail;
    }

    public void setCourseMentorEMail(String courseMentorEMail) {
        this.courseMentorEMail = courseMentorEMail;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseMentorNumber() {
        return courseMentorNumber;
    }

    public void setCourseMentorNumber(String courseMentorNumber) {
        this.courseMentorNumber = courseMentorNumber;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

}
