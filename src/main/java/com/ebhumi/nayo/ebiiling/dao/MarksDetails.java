package com.ebhumi.nayo.ebiiling.dao;

import javax.persistence.*;

@Entity
@Table(name = "marks_details")
public class MarksDetails {

    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private long testId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "max_marks")
    private String maxMarks;

    @Column(name = "marks_obtained")
    private String marksObtained;

    @Column(name = "result")
    private String result;

    public MarksDetails() { }

    public MarksDetails(String subject, String maxMarks, String marksObtained, String result) {
        this.subject = subject;
        this.maxMarks = maxMarks;
        this.marksObtained = marksObtained;
        this.result = result;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
