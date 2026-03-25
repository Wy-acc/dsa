package boundary; //author: Yaw Wei Ying

import adt.ListStackQueueInterface;
import entity.MatchResult;
import java.util.Scanner;


public class MatchingBoundary {
    private final Scanner scanner = new Scanner(System.in);
    // Table formatting constants
    private static final String FORMAT_HEADER = "| %-15s | %-25s | %-10s |\n";
    private static final String FORMAT_ROW = "| %-15s | %-25s | %-10s |\n";

    
    public void displayMenu() {
        System.out.println("\n=== MATCHING SYSTEM MENU ===");
        System.out.println("1. Match Applicant for Jobs");
        System.out.println("2. Match Job for Applicants");
        System.out.println("3. Show Matches by Score");
        System.out.println("4. Generate Report for Applicant and Job");
        System.out.println("0. Return to Main Menu");
        System.out.print("Enter your choice: ");
    }

    public void ApplicantSubMenu(){
        System.out.println("\nMatching by:");
        System.out.println("1. Applicant ID");
        System.out.println("2. Applicant Name");
        System.out.print("Enter your choice: ");
    }
    
    public void JobSubMenu(){
        System.out.println("\nMatching by:");
        System.out.println("1. Job ID");
        System.out.println("2. Job Title/Keywords");
        System.out.print("Enter your choice: ");
    }
    
    
    public String promptApplicantId() {
        System.out.print("\nEnter Applicant ID: ");
        return scanner.nextLine();
    }

    public String promptJobId() {
        System.out.print("\nEnter Job ID: ");
        return scanner.nextLine();
    }
    
    public String promptApplicantName() {
    System.out.print("\nEnter applicant name to match: ");
    return scanner.nextLine();
}

    public String promptJobKeywords() {
        System.out.print("\nEnter job title to match: ");
        return scanner.nextLine();
    }

    public void displayMatches(ListStackQueueInterface<MatchResult> matches, String title) {
        System.out.println("\n+===============================================================+");
        System.out.printf("| %-61s |\n", title.toUpperCase());
        System.out.println("+===============================================================+");
        
        if (matches.isEmpty()) {
            System.out.println("| No matches found                                 |");
            System.out.println("+-----------------------------------------------------+");
            return;
        }

        for (int i = 1; i <= matches.size(); i++) {
            MatchResult result = matches.getEntry(i);
            String scoreWithPercent = String.format("%.0f%%", result.getMatchScore() * 100);
            System.out.println("\n+----------------------------------------- Match " + i + " ------------------------------------------+");
            System.out.printf("| %-15s: %-73s |\n", "Applicant", result.getApplicant().getName());
            System.out.printf("| %-15s: %-73s |\n", "Job Title", result.getJob().getTitle());
            System.out.printf("| %-15s: %-68s |\n", "Roundoff Match Score", scoreWithPercent);
            System.out.println("+------------------------------------- Matching Details -------------------------------------+");            
            try (Scanner explanationScanner = new Scanner(result.getExplanation())) {
                while (explanationScanner.hasNextLine()) {
                    String line = explanationScanner.nextLine()
                        .replace("(X)", "[MISMATCH]")
                        .replace("->", "[MATCH]");
                    System.out.printf("| %-90s |\n", line);
                }
            }
            System.out.println("+--------------------------------------------------------------------------------------------+");
        }
    }

    public void showMessage(String message) {
        System.out.println("\n+-----------------------------------------------+");
        System.out.printf("| %45s |\n", message);
        System.out.println("+-----------------------------------------------+");
    }

    public void displaySavedMatches(ListStackQueueInterface<MatchResult> matches) {
        System.out.println("\n+=================================================+");
        System.out.println("|                   SAVED MATCHES                 |");
        System.out.println("+-----------------+---------------------------+----+");
        System.out.printf(FORMAT_HEADER, "Applicant", "Job Title", "Score");
        System.out.println("+-----------------+---------------------------+----+");
        
        if (matches.isEmpty()) {
            System.out.printf("| %-47s |\n", "No matches to save");
            System.out.println("+--------------------------------------------------------------+");
            return;
        }

        for (int i = 1; i <= matches.size(); i++) {
            MatchResult match = matches.getEntry(i);
            System.out.printf(FORMAT_ROW, 
                match.getApplicant().getName(),
                match.getJob().getTitle(),
                String.format("%.2f", match.getMatchScore()));
        }
        System.out.println("+---------------------+-------------------------------+----------+");
    }

    public String getStringInput() {
        return scanner.nextLine();
    }

    public int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void generateReport(ListStackQueueInterface<MatchResult> matches) {
        if (matches.isEmpty()) {
                System.out.println("\nNo match data available to generate report. Please match data before generate report.");
                return;
            }

            System.out.println("\n===============================================================================================");
            System.out.println("                          APPLICANTS AND JOBS MATCHING REPORT");
            System.out.println("===============================================================================================");
            System.out.println("+---------------+---------------------------+------------+--------------------------------+");      
            System.out.printf("| %-13s | %-25s | %-10s | %-30s |\n",
                    "Applicant ID", "Applicant Name", "Score", "Matched Job");
            System.out.println("|---------------|---------------------------|------------|--------------------------------|");

            int total = 0;
            int ApplicantCount = 0;
            int JobCount = 0;

            adt.ListStackQueueInterface<MatchResult> seenApplicants = new adt.ArrayList<>();
            adt.ListStackQueueInterface<MatchResult> seenJobs = new adt.ArrayList<>();

            for (int i = 1; i <= matches.size(); i++) {
                MatchResult result = matches.getEntry(i);
                String applicantId = result.getApplicant().getApplicantId();
                String applicantName = result.getApplicant().getName();
                String jobId = result.getJob().getJobID();
                String jobTitle = result.getJob().getTitle();
                double score = result.getMatchScore() * 100;

                System.out.printf("| %-13s | %-25s | %-9.2f%% | %-30s |\n",
                        applicantId, applicantName, score, jobTitle + " (" + jobId + ")");

                total++;

                if (!containsApplicant(seenApplicants, applicantId)) {
                    seenApplicants.add(result);
                    ApplicantCount++;
                }

                if (!containsJob(seenJobs, jobId)) {
                    seenJobs.add(result);
                    JobCount++;
                }
            }

            System.out.println("+---------------+---------------------------+------------+--------------------------------+");
            System.out.printf("Total applicants matched: %d\n", ApplicantCount);
            System.out.printf("Total jobs involved: %d\n", JobCount);
            System.out.printf("Total match records: %d\n", total);
            System.out.println("===============================================================================================");
        }

        private boolean containsApplicant(ListStackQueueInterface<MatchResult> list, String applicantId) {
            for (int i = 1; i <= list.size(); i++) {
                if (list.getEntry(i).getApplicant().getApplicantId().equalsIgnoreCase(applicantId)) {
                    return true;
                }
            }
            return false;
        }

        private boolean containsJob(ListStackQueueInterface<MatchResult> list, String jobId) {
            for (int i = 1; i <= list.size(); i++) {
                if (list.getEntry(i).getJob().getJobID().equalsIgnoreCase(jobId)) {
                    return true;
                }
            }
            return false;
        }
        
        public double promptMinSkillScore() {
            System.out.print("\nEnter minimum skill match score (%): ");
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

}

    