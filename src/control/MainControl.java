package control; //author: Yaw Wei Ying, Tay Mian Yin, Tay Zhuang Yin, Yoo Xin Wei

import adt.*;
import boundary.ApplicantBoundary;
import boundary.MainBoundary;
import boundary.JobBoundary;
import entity.Job;
import entity.Applicant;
import dao.Dummydata;

public class MainControl {
    private ListStackQueueInterface<Job> jobs;
    private ListStackQueueInterface<Applicant> applicants;
    private MainBoundary mainBoundary;
    private JobControl jobControl;
    private JobBoundary jobBoundary;
    private ApplicantControl applicantControl;
    private MatchingControl matchingControl;
    private ApplicantBoundary applicantBoundary;
    private InterviewControl interviewControl;

    public MainControl() {
        // Initialize data from DAO
        Dummydata dao = new Dummydata();
        jobs = dao.JobData();
        applicants = dao.initializeSampleData();
        
        mainBoundary = new MainBoundary();
        jobControl = new JobControl(jobs);
        jobBoundary = new JobBoundary(jobControl, mainBoundary);
        applicantControl = new ApplicantControl(applicants);
        matchingControl = new MatchingControl(jobs, applicants);
        applicantBoundary = new ApplicantBoundary(applicantControl);
        interviewControl = new InterviewControl(applicants, jobs, matchingControl);
        
    }

    public void run() {
        while (true) {
            int choice = mainBoundary.showMainMenu();
            switch (choice) {
                case 1 -> jobBoundary.displayMainMenu();
                case 2 -> applicantBoundary.start();
                case 3 -> matchingControl.run();
                case 4 -> interviewControl.run();
                case 0 -> { 
                    mainBoundary.showMessage("Exiting system...");
                    return; 
                }
                default -> mainBoundary.showMessage("Invalid choice");
            }
        }
    }
    
    public static class InternshipSystem {
        public static void main(String[] args) {
            MainControl controller = new MainControl();
            controller.run();
        }
    }
}