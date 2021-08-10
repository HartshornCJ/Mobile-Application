package android.example.hartshornscheduler.Database;

import android.content.Context;
import android.example.hartshornscheduler.DAO.AssessmentDOA;
import android.example.hartshornscheduler.DAO.CourseDAO;
import android.example.hartshornscheduler.DAO.NoteDOA;
import android.example.hartshornscheduler.DAO.TermDAO;
import android.example.hartshornscheduler.Entity.AssessmentEntity;
import android.example.hartshornscheduler.Entity.CourseEntity;
import android.example.hartshornscheduler.Entity.NoteEntity;
import android.example.hartshornscheduler.Entity.TermEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class, NoteEntity.class}, version = 20, exportSchema = false)
public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDOA assessmentDAO();
    public abstract NoteDOA noteDOA();
    private static final int NUMBER_OF_THREADS = 4;


    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile SchedulerDatabase INSTANCE;

    static SchedulerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabase.class, "scheduler_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                /**
                 * Populate the database in the background.
                 * If you want to start with more words, just add them.
                 */

                TermDAO mTermDAO = INSTANCE.termDAO();
                CourseDAO mCourseDAO = INSTANCE.courseDAO();
                AssessmentDOA mAssessmentDAO = INSTANCE.assessmentDAO();
                NoteDOA mNoteDAO = INSTANCE.noteDOA();

                // Start the app with a clean database every time.
                // Not needed if you only populate on creation.
                //mTermDAO.deleteAllTerms();
                //mCourseDAO.deleteAllCourses();
                //mAssessmentDAO.deleteAllAssessments();
                //mNoteDAO.deleteAllNotes();

                /*
                AssessmentEntity assessment = new AssessmentEntity(1, "Test 1", ("2020-12-30"), "Notes", 1);
                mAssessmentDAO.insert(assessment);
                assessment = new AssessmentEntity(2, "Test 2", ("2020-12-31"), "Notes", 1);
                mAssessmentDAO.insert(assessment);
                assessment = new AssessmentEntity(3, "Test 3", ("2021-1-1"), "Notes", 2);
                mAssessmentDAO.insert(assessment);

                CourseEntity course = new CourseEntity(1, "Course 1", ("2020-12-1"), ("2020-12-31"), "In Progress", "temp", "123-234-1234", "email", 1);
                mCourseDAO.insert(course);
                course = new CourseEntity(2, "Course 2", ("2021-1-1"), ("2021-1-20"), "Scheduled", "temp", "123-234-1234", "email", 2);
                mCourseDAO.insert(course);
                course = new CourseEntity(3, "Course 3", ("2020-2-1"), ("2020-2-15"), "Scheduled", "temp", "123-234-1234", "email", 2);
                mCourseDAO.insert(course);

                TermEntity term = new TermEntity(1, "Term 1", ("2020-12-1"), ("2020-12-31"));
                mTermDAO.insert(term);
                term = new TermEntity(2, "Term 2", ("2021-1-1"), ("2021-2-16"));
                mTermDAO.insert(term);
                term = new TermEntity(3, "Term 3", ("2021-1-1"), ("2021-2-16"));
                mTermDAO.insert(term);
                term = new TermEntity(4, "Term 4", ("2021-1-1"), ("2021-2-16"));
                mTermDAO.insert(term);
                term = new TermEntity(5, "Term 5", ("2021-1-1"), ("2021-2-16"));
                mTermDAO.insert(term);
                term = new TermEntity(6, "Term 6", ("2021-1-1"), ("2021-2-16"));
                mTermDAO.insert(term);

                NoteEntity note = new NoteEntity(1,"test", "details", 1);
                mNoteDAO.insert(note);
                note = new NoteEntity(2,"test", "details", 2);
                mNoteDAO.insert(note);
                note = new NoteEntity(3,"test", "details", 3);
                mNoteDAO.insert(note);*/
            });
        }
    };
}
