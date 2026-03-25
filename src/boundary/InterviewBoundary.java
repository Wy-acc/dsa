package boundary; //author: Yoo Xin Wei

import adt.ListStackQueueInterface;
import control.InterviewControl;
import entity.Interview;
import entity.InterviewTimetable;
import entity.MatchResult;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

public class InterviewBoundary {
    private final Scanner scanner = new Scanner(System.in);
    private final InterviewControl control;
    
    public InterviewBoundary(InterviewControl control) {
        this.control = control;
    }
    
    public int displayMainMenu() {
        System.out.println("\n=== INTERVIEW MANAGEMENT SYSTEM ===");
        System.out.println("1. Schedule New Interview");
        System.out.println("2. Update Interview Status");
        System.out.println("3. View All Interviews");
        System.out.println("4. View Available Time Slots");
        System.out.println("5. Reports");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }
    
    public MatchResult selectMatch(ListStackQueueInterface<MatchResult> matches) {
        System.out.println("\n=== SELECT CANDIDATE FOR INTERVIEW ===");
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-20s | %-25s | %-25s | %-10s |\n", "No.", "Applicant", "Job", "Company", "Match Score");
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        
        for (int i = 1; i <= matches.size(); i++) {
            MatchResult match = matches.getEntry(i);
            System.out.printf("| %-5d | %-20s | %-25s | %-25s | %-10.2f%% |\n", 
                    i, 
                    match.getApplicant().getName(),
                    match.getJob().getTitle(),
                    match.getJob().getCompany(),
                    match.getMatchScore() * 100);
        }
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        
        System.out.print("Enter selection number (0 to cancel): ");
        try {
            int selection = Integer.parseInt(scanner.nextLine());
            if (selection == 0) {
                return null;
            }
            if (selection >= 1 && selection <= matches.size()) {
                return matches.getEntry(selection);
            }
            displayMessage("Invalid selection.");
            return null;
        } catch (NumberFormatException e) {
            displayMessage("Invalid input.");
            return null;
        }
    }
    
    public InterviewTimetable selectTimeSlot(ListStackQueueInterface<InterviewTimetable> slots) {
        System.out.println("\n=== AVAILABLE TIME SLOTS ===");
        System.out.println("+-----------------------------------------------------+");
        System.out.printf("| %-5s | %-12s | %-10s | %-15s |\n", "No.", "Day", "Time", "Status");
        System.out.println("+-----------------------------------------------------+");
        
        for (int i = 1; i <= slots.size(); i++) {
            InterviewTimetable slot = slots.getEntry(i);
            System.out.printf("| %-5d | %-12s | %-10s | %-15s |\n", 
                    i, 
                    slot.getDay(),
                    slot.getTime(),
                    slot.isAvailable() ? "Available" : "Booked");
        }
        System.out.println("+-----------------------------------------------------+");
        
        System.out.print("Enter slot number to select (0 to cancel): ");
        try {
            int selection = Integer.parseInt(scanner.nextLine());
            if (selection == 0) {
                return null;
            }
            if (selection >= 1 && selection <= slots.size()) {
                return slots.getEntry(selection);
            }
            displayMessage("Invalid selection.");
            return null;
        } catch (NumberFormatException e) {
            displayMessage("Invalid input.");
            return null;
        }
    }
    
    public String promptInterviewId() {
        System.out.print("Enter Interview ID: ");
        return scanner.nextLine().trim();
    }
    
    public String promptInterviewStatus() {
        System.out.println("Select new status:");
        System.out.println("1. Scheduled");
        System.out.println("2. Completed");
        System.out.println("3. Cancelled");
        System.out.println("4. Absent");
        
        while (true) {
            System.out.print("Enter choice (1-4): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: return "Scheduled";
                    case 2: return "Completed";
                    case 3: return "Cancelled";
                    case 4: return "Absent";
                    default: System.out.println("Invalid choice. Please enter 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    public int promptRating() {
        while (true) {
            System.out.print("Enter rating (0-5): ");
            try {
                int rating = Integer.parseInt(scanner.nextLine());
                if (rating >= 0 && rating <= 5) {
                    return rating;
                }
                System.out.println("Rating must be between 0 and 5.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    public String promptFeedback() {
        System.out.print("Enter feedback: ");
        return scanner.nextLine();
    }
    
    public boolean promptSuccessStatus() {
        while (true) {
            System.out.print("Is this candidate successful? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }
    
    public void displayInterviewDetails(Interview interview) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        System.out.println("\n+----------------------------------------+");
        System.out.println("|            INTERVIEW DETAILS           |");
        System.out.println("+----------------------------------------+");
        System.out.printf("| Interview ID: %-24s |\n", interview.getInterviewId());
        System.out.printf("| Applicant: %-27s |\n", interview.getApplicant().getName());
        System.out.printf("| Job: %-33s |\n", interview.getJob().getTitle());
        System.out.printf("| Company: %-29s |\n", interview.getJob().getCompany());
        System.out.printf("| Schedule: %-28s |\n", interview.getScheduleTime().format(formatter));
        System.out.printf("| Status: %-30s |\n", interview.getStatus());
        if (interview.getStatus().equals("Completed")) {
            System.out.printf("| Rating: %-30s |\n", interview.getRating() + "/5");
            System.out.printf("| Successful: %-26s |\n", interview.isSuccessful() ? "Yes" : "No");
            System.out.printf("| Feedback: %-28s |\n", interview.getFeedback());
        }
        System.out.println("+----------------------------------------+");
    }
    
    public void displayInterviews(ListStackQueueInterface<Interview> interviews) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        System.out.println("\n=== ALL INTERVIEWS ===");
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-8s | %-20s | %-20s | %-18s | %-10s | %-6s |\n", 
                "ID", "Applicant", "Job", "Schedule", "Status", "Rating");
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            Interview interview = iterator.next();
            System.out.printf("| %-8s | %-20s | %-20s | %-18s | %-10s | %-6s |\n", 
                    interview.getInterviewId(),
                    interview.getApplicant().getName(),
                    interview.getJob().getTitle(),
                    interview.getScheduleTime().format(formatter),
                    interview.getStatus(),
                    interview.getStatus().equals("Completed") ? interview.getRating() + "/5" : "-");
        }
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.println("Total interviews: " + interviews.size());
    }
    
    public void displayTimeSlots(ListStackQueueInterface<InterviewTimetable> slots) {
        System.out.println("\n=== AVAILABLE TIME SLOTS ===");
        System.out.println("+-----------------------------------------------------+");
        System.out.printf("| %-5s | %-12s | %-10s | %-15s |\n", "ID", "Day", "Time", "Status");
        System.out.println("+-----------------------------------------------------+");
        
        Iterator<InterviewTimetable> iterator = slots.getIterator();
        while (iterator.hasNext()) {
            InterviewTimetable slot = iterator.next();
            System.out.printf("| %-5s | %-12s | %-10s | %-15s |\n", 
                    slot.getSlotId(),
                    slot.getDay(),
                    slot.getTime(),
                    slot.isAvailable() ? "Available" : "Booked");
        }
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Total available slots: " + slots.size());
    }

    public int promptReportType() {
        System.out.println("\n=== INTERVIEW REPORTS ===");
        System.out.println("1. Interview Schedule Report");
        System.out.println("2. Successful Candidates Report");
        System.out.println("3. Interview Status Summary Report");
        System.out.println("4. Top Candidates Report");
        System.out.println("0. Back to Interview Menu");
        System.out.print("Select report type: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public int promptMinimumRating() {
        while (true) {
            System.out.print("Enter minimum rating threshold (1-5): ");
            try {
                int rating = Integer.parseInt(scanner.nextLine());
                if (rating >= 1 && rating <= 5) {
                    return rating;
                }
                System.out.println("Rating must be between 1 and 5.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    public void displayInterviewScheduleReport(ListStackQueueInterface<Interview> scheduledInterviews) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        System.out.println("\n=== INTERVIEW SCHEDULE REPORT ===");
        System.out.println("Date: " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("\nUpcoming Interviews:");
        System.out.println("+--------------------------------------------------------------------------------------------+");
        System.out.printf("| %-8s | %-20s | %-20s | %-18s | %-12s |\n", 
                "ID", "Applicant", "Job", "Schedule", "Company");
        System.out.println("+--------------------------------------------------------------------------------------------+");
        
        if (scheduledInterviews.isEmpty()) {
            System.out.println("| No scheduled interviews found                                                                     |");
        } else {
            Iterator<Interview> iterator = scheduledInterviews.getIterator();
            while (iterator.hasNext()) {
                Interview interview = iterator.next();
                System.out.printf("| %-8s | %-20s | %-20s | %-18s | %-12s |\n", 
                        interview.getInterviewId(),
                        interview.getApplicant().getName(),
                        interview.getJob().getTitle(),
                        interview.getScheduleTime().format(formatter),
                        interview.getJob().getCompany());
            }
        }
        
        System.out.println("+--------------------------------------------------------------------------------------------+");
        System.out.println("Total scheduled interviews: " + scheduledInterviews.size());
    }
    
    public void displaySuccessfulCandidatesReport(ListStackQueueInterface<Interview> successfulInterviews) {
        System.out.println("\n=== SUCCESSFUL CANDIDATES REPORT ===");
        System.out.println("Date: " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("\nSuccessful Candidates:");
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-8s | %-20s | %-20s | %-12s | %-6s | %-15s |\n", 
                "ID", "Applicant", "Job", "Company", "Rating", "Feedback");
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        
        if (successfulInterviews.isEmpty()) {
            System.out.println("| No successful candidates found                                                                    |");
        } else {
            Iterator<Interview> iterator = successfulInterviews.getIterator();
            while (iterator.hasNext()) {
                Interview interview = iterator.next();
                System.out.printf("| %-8s | %-20s | %-20s | %-12s | %-6s | %-15s |\n", 
                        interview.getInterviewId(),
                        interview.getApplicant().getName(),
                        interview.getJob().getTitle(),
                        interview.getJob().getCompany(),
                        interview.getRating() + "/5",
                        interview.getFeedback().length() > 15 ? 
                            interview.getFeedback().substring(0, 12) + "..." : 
                            interview.getFeedback());
            }
        }
        
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.println("Total successful candidates: " + successfulInterviews.size());
    }
    
    public void displayInterviewStatusSummaryReport(int total, int scheduled, int completed, int cancelled, int noShow) {
        System.out.println("\n=== INTERVIEW STATUS SUMMARY REPORT ===");
        System.out.println("Date: " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("\nInterview Status Distribution:");
        System.out.println("+--------------------------------------+");
        System.out.printf("| %-15s | %-8s | %-8s |\n", "Status", "Count", "Percent");
        System.out.println("+--------------------------------------+");
        
        if (total > 0) {
            System.out.printf("| %-15s | %-8d | %-7.2f%% |\n", "Scheduled", scheduled, (scheduled * 100.0) / total);
            System.out.printf("| %-15s | %-8d | %-7.2f%% |\n", "Completed", completed, (completed * 100.0) / total);
            System.out.printf("| %-15s | %-8d | %-7.2f%% |\n", "Cancelled", cancelled, (cancelled * 100.0) / total);
            System.out.printf("| %-15s | %-8d | %-7.2f%% |\n", "No-Show", noShow, (noShow * 100.0) / total);
        } else {
            System.out.println("| No interview data available           |");
        }
        
        System.out.println("+--------------------------------------+");
        System.out.println("Total interviews: " + total);
    }
    
    public void displayTopCandidatesReport(ListStackQueueInterface<Interview> topCandidates, int minRating) {
        System.out.println("\n=== TOP CANDIDATES REPORT ===");
        System.out.println("Date: " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Minimum Rating: " + minRating + "/5");
        System.out.println("\nTop-Rated Candidates:");
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-8s | %-20s | %-20s | %-12s | %-6s | %-15s |\n", 
                "ID", "Applicant", "Job", "Company", "Rating", "Status");
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        
        if (topCandidates.isEmpty()) {
            System.out.println("| No candidates found with rating >= " + minRating + "                                                            |");
        } else {
            Iterator<Interview> iterator = topCandidates.getIterator();
            while (iterator.hasNext()) {
                Interview interview = iterator.next();
                System.out.printf("| %-8s | %-20s | %-20s | %-12s | %-6s | %-15s |\n", 
                        interview.getInterviewId(),
                        interview.getApplicant().getName(),
                        interview.getJob().getTitle(),
                        interview.getJob().getCompany(),
                        interview.getRating() + "/5",
                        interview.isSuccessful() ? "Successful" : "Not Successful");
            }
        }
        
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.println("Total candidates with rating >= " + minRating + ": " + topCandidates.size());
    }
}