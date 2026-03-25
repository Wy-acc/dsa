package control; //author: Yoo Xin Wei

import adt.ListStackQueueInterface;
import adt.SortedArrayList;
import boundary.InterviewBoundary;
import dao.Dummydata;
import entity.Applicant;
import entity.Interview;
import entity.InterviewTimetable;
import entity.Job;
import entity.MatchResult;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Iterator;

public class InterviewControl {
    private ListStackQueueInterface<Interview> interviews;
    private ListStackQueueInterface<InterviewTimetable> timeSlots;
    private ListStackQueueInterface<Applicant> applicants;
    private ListStackQueueInterface<Job> jobs;
    private final InterviewBoundary boundary;
    private final MatchingControl matchingControl;
    private final Dummydata dummydata;

    public InterviewControl(
            ListStackQueueInterface<Applicant> applicants,
            ListStackQueueInterface<Job> jobs,
            MatchingControl matchingControl) {
        
        this.dummydata = new Dummydata();
        this.timeSlots = dummydata.initializeTimetable();
        this.interviews = dummydata.initializeInterviewData(applicants, jobs);
        this.applicants = applicants;
        this.jobs = jobs;
        this.matchingControl = matchingControl;
        this.boundary = new InterviewBoundary(this);
    }

    public void run() {
        int choice;
        do {
            choice = boundary.displayMainMenu();
            switch (choice) {
                case 1 -> scheduleInterview();
                case 2 -> updateInterviewStatus();
                case 3 -> viewAllInterviews();
                case 4 -> viewAvailableTimeSlots();
                case 5 -> generateReports();
                case 0 -> {
                    boundary.displayMessage("Returning to main menu");
                    return;
                }
                default -> boundary.displayMessage("Invalid choice");
            }
        } while (choice != 0);
    }

    private void scheduleInterview() {
        ListStackQueueInterface<MatchResult> matches = matchingControl.getAllMatchesAboveThreshold(0.6);
        if (matches.isEmpty()) {
            boundary.displayMessage("No suitable matches found for scheduling interviews.");
            return;
        }

        MatchResult selectedMatch = boundary.selectMatch(matches);
        if (selectedMatch == null) {
            return;
        }

        ListStackQueueInterface<InterviewTimetable> availableSlots = getAvailableTimeSlots();
        if (availableSlots.isEmpty()) {
            boundary.displayMessage("No available time slots for scheduling interviews.");
            return;
        }

        InterviewTimetable selectedSlot = boundary.selectTimeSlot(availableSlots);
        if (selectedSlot == null) {
            return;
        }

        LocalDateTime interviewDateTime = generateInterviewDateTime(selectedSlot);
        
        String interviewId = generateInterviewId();
        
        Interview newInterview = new Interview(
            interviewId,
            selectedMatch.getApplicant(),
            selectedMatch.getJob(),
            interviewDateTime,
            "Scheduled",
            0,
            "",
            false
        );
        
        interviews.add(newInterview);
        selectedSlot.setAvailable(false);
        
        boundary.displayMessage("Interview scheduled successfully!");
        boundary.displayInterviewDetails(newInterview);
    }
    
    private void updateInterviewStatus() {
        String interviewId = boundary.promptInterviewId();
        Interview interview = findInterviewById(interviewId);
        
        if (interview == null) {
            boundary.displayMessage("Interview not found with ID: " + interviewId);
            return;
        }
        
        boundary.displayInterviewDetails(interview);
        
        String newStatus = boundary.promptInterviewStatus();
        interview.setStatus(newStatus);
        
        if (newStatus.equals("Completed")) {
            int rating = boundary.promptRating();
            String feedback = boundary.promptFeedback();
            boolean isSuccessful = boundary.promptSuccessStatus();
            
            interview.setRating(rating);
            interview.setFeedback(feedback);
            interview.setSuccessful(isSuccessful);
        } else if (newStatus.equals("Cancelled") || newStatus.equals("Absent")) {
            String feedback = boundary.promptFeedback();
            interview.setFeedback(feedback);
        }
        
        boundary.displayMessage("Interview status updated successfully!");
        boundary.displayInterviewDetails(interview);
    }
    
    private void viewAllInterviews() {
        if (interviews.isEmpty()) {
            boundary.displayMessage("No interviews found in the system.");
            return;
        }
        
        boundary.displayInterviews(interviews);
    }
    
    private void viewAvailableTimeSlots() {
        ListStackQueueInterface<InterviewTimetable> availableSlots = getAvailableTimeSlots();
        
        if (availableSlots.isEmpty()) {
            boundary.displayMessage("No available time slots found.");
            return;
        }
        
        boundary.displayTimeSlots(availableSlots);
    }
    
    private void generateReports() {
        int reportType = boundary.promptReportType();
        switch (reportType) {
            case 1 -> generateInterviewScheduleReport();
            case 2 -> generateSuccessfulCandidatesReport();
            case 3 -> generateInterviewStatusSummaryReport();
            case 4 -> generateTopCandidatesReport();
            case 0 -> boundary.displayMessage("Returning to interview menu");
            default -> boundary.displayMessage("Invalid report type");
        }
    }

    private void generateInterviewScheduleReport() {
        ListStackQueueInterface<Interview> scheduledInterviews = new SortedArrayList<>(
            (interview1, interview2) -> interview1.getScheduleTime().compareTo(interview2.getScheduleTime())
        );
        
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            Interview interview = iterator.next();
            if (interview.getStatus().equals("Scheduled")) {
                scheduledInterviews.add(interview);
            }
        }
        
        boundary.displayInterviewScheduleReport(scheduledInterviews);
    }
    
    private void generateSuccessfulCandidatesReport() {
        ListStackQueueInterface<Interview> successfulInterviews = new SortedArrayList<>(
            (interview1, interview2) -> Integer.compare(interview2.getRating(), interview1.getRating()) // Descending by rating
        );
        
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            Interview interview = iterator.next();
            if (interview.isSuccessful() && interview.getStatus().equals("Completed")) {
                successfulInterviews.add(interview);
            }
        }
        
        boundary.displaySuccessfulCandidatesReport(successfulInterviews);
    }
    
    private void generateInterviewStatusSummaryReport() {
        int totalInterviews = interviews.getNumberOfEntries();
        int scheduled = 0;
        int completed = 0;
        int cancelled = 0;
        int absent = 0;
        
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            String status = iterator.next().getStatus();
            switch (status) {
                case "Scheduled" -> scheduled++;
                case "Completed" -> completed++;
                case "Cancelled" -> cancelled++;
                case "Absent" -> absent++;
            }
        }
        
        boundary.displayInterviewStatusSummaryReport(totalInterviews, scheduled, completed, cancelled, absent);
    }
    
    private void generateTopCandidatesReport() {
        int minRating = boundary.promptMinimumRating();
        
        ListStackQueueInterface<Interview> topCandidates = new SortedArrayList<>(
            (interview1, interview2) -> Integer.compare(interview2.getRating(), interview1.getRating()) // Descending by rating
        );
        
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            Interview interview = iterator.next();
            if (interview.getStatus().equals("Completed") && interview.getRating() >= minRating) {
                topCandidates.add(interview);
            }
        }
        
        boundary.displayTopCandidatesReport(topCandidates, minRating);
    }
    
    private ListStackQueueInterface<InterviewTimetable> getAvailableTimeSlots() {
        ListStackQueueInterface<InterviewTimetable> availableSlots = new SortedArrayList<>(
            (slot1, slot2) -> {
                int dayCompare = getDayValue(slot1.getDay()) - getDayValue(slot2.getDay());
                if (dayCompare != 0) {
                    return dayCompare;
                }
                return slot1.getTime().compareTo(slot2.getTime());
            }
        );
        
        Iterator<InterviewTimetable> iterator = timeSlots.getIterator();
        while (iterator.hasNext()) {
            InterviewTimetable slot = iterator.next();
            if (slot.isAvailable()) {
                availableSlots.add(slot);
            }
        }
        
        return availableSlots;
    }
    
    private Interview findInterviewById(String interviewId) {
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            Interview interview = iterator.next();
            if (interview.getInterviewId().equals(interviewId)) {
                return interview;
            }
        }
        return null;
    }
    
    private LocalDateTime generateInterviewDateTime(InterviewTimetable slot) {
        LocalDate today = LocalDate.now();
        
        int dayOffset = getDayOffset(slot.getDay(), today.getDayOfWeek().getValue());
        
        LocalDate interviewDate = today.plusDays(dayOffset);
        
        String[] timeParts = slot.getTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        
        return LocalDateTime.of(interviewDate.getYear(), 
                              interviewDate.getMonth(), 
                              interviewDate.getDayOfMonth(), 
                              hour, 
                              minute);
    }
    
    private int getDayOffset(String targetDay, int currentDayOfWeek) {
        int targetDayValue = getDayValue(targetDay);
        if (targetDayValue < currentDayOfWeek) {
            return 7 - currentDayOfWeek + targetDayValue;
        } else {
            return targetDayValue - currentDayOfWeek;
        }
    }
    
    private int getDayValue(String day) {
        return switch (day) {
            case "Monday" -> 1;
            case "Tuesday" -> 2;
            case "Wednesday" -> 3;
            case "Thursday" -> 4;
            case "Friday" -> 5;
            default -> 0;
        };
    }
    
    private String generateInterviewId() {
        int highestId = 0;
        
        Iterator<Interview> iterator = interviews.getIterator();
        while (iterator.hasNext()) {
            String currentId = iterator.next().getInterviewId();
            try {
                int idNumber = Integer.parseInt(currentId.substring(1));
                if (idNumber > highestId) {
                    highestId = idNumber;
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
            }
        }
        
        return String.format("I%03d", highestId + 1);
    }
    
    public ListStackQueueInterface<Interview> getAllInterviews() {
        return interviews;
    }
}