package android.example.hartshornscheduler.Entity;


import androidx.room.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity(tableName="term_table")
public class TermEntity {
    @PrimaryKey
    private int termID;

    private String termTitle;
    private String termStartDate;
    private String termEndDate;
    //private LocalDate temp;

    @Override
    public String toString() {
        return "TermEntity{"+
                "termID="+ termID +
                ", termTitle="+ termTitle + '\'' +
                ", termStartDate="+ termStartDate + '\'' +
                ", termEndDate="+ termEndDate +
                '}';
    }

    public TermEntity(int termID, String termTitle, String termStartDate, String termEndDate) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public int getTermID() { return termID; }

    public void setTermID(int termID) { this.termID = termID; }

    public String getTermTitle() { return termTitle; }

    public void setTermTitle(String termTitle) { this.termTitle = termTitle; }

    public String getTermStartDate() { return termStartDate; }

    public void setTermStartDate(String termStartDate) { this.termStartDate = termStartDate; }

    public String getTermEndDate() { return termEndDate; }

    public void setTermEndDate(String termEndDate) { this.termEndDate = termEndDate; }

    /*
    public LocalDate getTemp() {
        return temp;
    }

    public void setTemp(LocalDate temp) {
        this.temp = temp;
    }
     */
}
