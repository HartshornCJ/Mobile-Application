package android.example.hartshornscheduler.Database;

import android.app.Application;
import android.example.hartshornscheduler.DAO.AssessmentDOA;
import android.example.hartshornscheduler.DAO.CourseDAO;
import android.example.hartshornscheduler.DAO.NoteDOA;
import android.example.hartshornscheduler.DAO.TermDAO;
import android.example.hartshornscheduler.Entity.AssessmentEntity;
import android.example.hartshornscheduler.Entity.CourseEntity;
import android.example.hartshornscheduler.Entity.NoteEntity;
import android.example.hartshornscheduler.Entity.TermEntity;
import android.util.Log;

import java.util.List;

public class SchedulerRepository {
    private AssessmentDOA mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private NoteDOA mNoteDAO;
    private List<AssessmentEntity> mAllAssessments;
    private List<CourseEntity> mAllCourses;
    private List<TermEntity> mAllTerms;
    private List<NoteEntity> mAllNotes;
    private int termID;

    public SchedulerRepository(Application application){
        SchedulerDatabase db = SchedulerDatabase.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO=db.termDAO();
        mNoteDAO=db.noteDOA();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<NoteEntity> getAllNotes(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllNotes=mNoteDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllNotes;
    }


    public List<AssessmentEntity> getAllAssessments(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<CourseEntity> getAllCourse(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllCourses=mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
/*
    public List<TermEntity> getAllTerm(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        Log.d("SchedulerRepository", "at getAllTerm " + mAllTerms);
        return mAllTerms;
    }
*/
    public List<TermEntity> getAllTerms(){
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }




    public void insert (AssessmentEntity assessmentEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.insert(assessmentEntity);
        });
    }
    public void insert (CourseEntity courseEntity) {
        Log.d("SchedulerRepository", "at insert courseID: "+courseEntity.getCourseID() );
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mCourseDAO.insert(courseEntity);
        });
    }
    public void insert (TermEntity termEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mTermDAO.insert(termEntity);
        });
    }
    public void insert (NoteEntity noteEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mNoteDAO.insert(noteEntity);
        });
    }

    public void delete (CourseEntity courseEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mCourseDAO.delete(courseEntity);
        });
    }
    public void delete (TermEntity termEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mTermDAO.delete(termEntity);
        });
    }
    public void delete (AssessmentEntity assessmentEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.delete(assessmentEntity);
        });
    }

    public void delete (NoteEntity noteEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mNoteDAO.delete(noteEntity);
        });
    }
}
