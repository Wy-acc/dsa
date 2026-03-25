package entity;//author: Tay Zhuang Yin

import adt.ListStackQueueInterface;
import java.time.LocalDate;

public class Job {
    private final String jobID;
    private String title;
    private String company;
    private String location;
    private int salary;
    private LocalDate postedDate;
    private LocalDate endDate;
    private String skillRequirements;
    private ListStackQueueInterface<Job> jobSkills;
    private String educationLevel;
    private int vacancies;
    private int duration;
    private String internshipStatus;

    public Job(String jobID, String title, String company, String location, int salary, LocalDate postedDate, LocalDate endDate, String skillRequirements, String educationLevel, int vacancies, int duration, String internshipStatus) {
        this.jobID = jobID;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.postedDate = postedDate;
        this.endDate = endDate;
        this.skillRequirements = skillRequirements;
        this.educationLevel = educationLevel;
        this.vacancies = vacancies;
        this.duration = duration;
        this.internshipStatus = internshipStatus;
    }

    // Getters
    public String getJobID() { return jobID; }
    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public int getSalary() { return salary; }
    public LocalDate getPostedDate() { return postedDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getSkillRequirements() { return skillRequirements; }
    public String getEducationLevel() { return educationLevel; }
    public int getVacancies() { return vacancies; }
    public int getDuration() { return duration; }
    public String getInternshipStatus() { return internshipStatus; }
    public ListStackQueueInterface<Job> getJobSkills() { return jobSkills;}

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setCompany(String company) { this.company = company; }
    public void setLocation(String location) { this.location = location; }
    public void setSalary(int salary) { this.salary = salary; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setSkillRequirements(String skillRequirements) { this.skillRequirements = skillRequirements; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    public void setVacancies(int vacancies) { this.vacancies = vacancies; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setInternshipStatus(String internshipStatus) { this.internshipStatus = internshipStatus; }
    public void setJobSkills(ListStackQueueInterface<Job> jobSkills) { this.jobSkills = jobSkills; }
}