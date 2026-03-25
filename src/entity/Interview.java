package entity; // author: Yoo Xin Wei

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Interview {
    private String interviewId;
    private Applicant applicant;
    private Job job;
    private LocalDateTime scheduleTime;
    private String status;
    private int rating;
    private String feedback;
    private boolean isSuccessful;

    public Interview(String interviewId, Applicant applicant, Job job, LocalDateTime scheduleTime, 
                     String status, int rating, String feedback, boolean isSuccessful) {
        this.interviewId = interviewId;
        this.applicant = applicant;
        this.job = job;
        this.scheduleTime = scheduleTime;
        this.status = status;
        this.rating = validateRating(rating);
        this.feedback = feedback;
        this.isSuccessful = isSuccessful;
    }

    private int validateRating(int rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        return rating;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Job getJob() {
        return job;
    }

    public LocalDateTime getScheduleTime() {
        return scheduleTime;
    }

    public String getStatus() {
        return status;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setScheduleTime(LocalDateTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(int rating) {
        this.rating = validateRating(rating);
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        return String.format(
            "Interview ID: %s\n" +
            "Applicant: %s\n" +
            "Job: %s (%s)\n" +
            "Schedule Time: %s\n" +
            "Status: %s\n" +
            "Rating: %d/5\n" +
            "Successful: %s\n" +
            "Feedback: %s",
            interviewId,
            applicant.getName(),
            job.getTitle(),
            job.getCompany(),
            scheduleTime.format(formatter),
            status,
            rating,
            isSuccessful ? "Yes" : "No",
            feedback
        );
    }
}