package boundary;//author: Tay Zhuang Yin

import adt.ListStackQueueInterface;
import control.JobControl;
import entity.Job;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Scanner;

public class JobBoundary {
    private final JobControl control;
    private final MainBoundary mainBoundary;
    private final java.util.Scanner sc = new Scanner(System.in);

    public JobBoundary(JobControl control, MainBoundary mainBoundary) {
        this.control = control;
        this.mainBoundary = mainBoundary;
    }
    
    public void displayMainMenu() {
        do {
            System.out.println("\n=== JOB MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Job");
            System.out.println("2. Update Job Details");
            System.out.println("3. Remove Job");
            System.out.println("4. Filter Job");
            System.out.println("5. List All Jobs");
            System.out.println("6. Sort Job");
            System.out.println("7. Search Job");
            System.out.println("8. Generate Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter selection: ");

            int selection = -1;

            try {
                selection = Integer.parseInt(sc.nextLine()); // Assuming 'sc' is the Scanner object
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }

            switch (selection) {
                case 1 -> collectJobDetails();
                case 2 -> updateJobDetails();
                case 3 -> removeJob();
                case 4 -> filterJob();
                case 5 -> displayAllJobs();
                case 6 -> displaySortingList();
                case 7 -> keywordSearch();
                case 8 -> filterReport();
                case 0 ->  {
                    return;
                }
                default -> System.out.println("Invalid selection. Please try again.");
            }
        } while (true);
    }
    
    public static class JobInputValidator {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        public static boolean isValidTitle(String title) {
            return title.length() >= 2 && title.length() <= 100;
        }

        public static boolean isValidCompany(String input) {
            String[] companies = input.split(",");
            for (String company : companies) {
                String trimmed = company.trim();
                if (trimmed.isEmpty() || !trimmed.matches("^[A-Za-z][A-Za-z0-9 .,&-]*$")) {
                    return false;
                }
            }
            return true;
        }

        public static boolean isValidLocation(String input) {
            String[] location = input.split(",");
            for (String locationJob : location) {
                String trimmed = locationJob.trim();
                if (trimmed.isEmpty() || !trimmed.matches("[A-Za-z .,&-]+")) {
                    return false;
                }
            }
            return true;
        }

        public static boolean isValidSalary(String input) {
            try {
                int value = Integer.parseInt(input);
                return value > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isValidDate(String input) {
            try {
                LocalDate.parse(input, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }

        public static LocalDate parseDate(String input) {
            return LocalDate.parse(input, formatter);
        }

        public static boolean isValidEndDate(LocalDate start, LocalDate end) {
            return end.isAfter(start);
        }

        public static boolean isValidSkill(String input) {
            String[] skill = input.split(",");
            for (String skills : skill) {
                String trimmed = skills.trim();
                if (trimmed.isEmpty() || !trimmed.matches("^[A-Za-z][A-Za-z0-9 ./,&-]*$")) {
                    return false;
                }
            }
            return true;
        }

        public static boolean isValidEducation(String education) {
            return "Diploma".equals(education) || "Degree".equals(education);
        }

        public static boolean isValidVacancy(String input) {
            try {
                int value = Integer.parseInt(input);
                return value > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        public static boolean isValidDuration(String input) {
            try {
                int value = Integer.parseInt(input);
                return value > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isValidStatus(String status) {
            return status.equalsIgnoreCase("Open") || status.equalsIgnoreCase("Closed") || status.equalsIgnoreCase("Filled");
        }
    }
    
    public void collectJobDetails() {
        String title, company, location, skillRequirements, educationLevel, internshipStatus;
        String salaryInput, postedInput, endInput, vacanciesInput, durationInput;
        int salary, vacancies, duration;
        LocalDate postedDate, endDate;
        
        System.out.println("Please enter the job details.");
        System.out.println("###Type '0' to quit");
        String jobId = control.generateJobId();
        do {
            System.out.print("Job Title: ");
            title = sc.nextLine().trim();
            if (title.equals("0")) return;
            if (JobInputValidator.isValidTitle(title)) {
                break;
            } else {
                System.out.println("Invalid job title. Please enter a valid job title.");
            }
        } while (true);
        do {
            System.out.print("Company: ");
            company = sc.nextLine().trim();
            if (company.equals("0")) return;
            if (JobInputValidator.isValidCompany(company)) {
                break;
            } else {
                System.out.println("Invalid company name. Please enter a valid company name.");
            }
        } while (true);
        do {
            System.out.print("Location: ");
            location = sc.nextLine().trim();
            if (location.equals("0")) return;
            if (JobInputValidator.isValidLocation(location)) {
                break;
            } else {
                System.out.println("Invalid location. Please enter a valid location.");
            }
        } while (true);
        do {
            System.out.print("Salary: ");
            salaryInput = sc.nextLine().trim();
            if (salaryInput.equals("0")) return;
            if (JobInputValidator.isValidSalary(salaryInput)) {
                salary = Integer.parseInt(salaryInput);
                break;
            } else {
                System.out.println("Invalid salary. Please enter a valid salary.");
            }
        } while (true);
        do {
            System.out.print("Posted Date (DD/MM/YYYY): ");
            postedInput = sc.nextLine().trim();
            if (postedInput.equals("0")) return;
            if (JobInputValidator.isValidDate(postedInput)) {
                postedDate = JobInputValidator.parseDate(postedInput);
                break;
            } else {
                System.out.println("Invalid posted date. Please enter a valid date.");
            }
        } while (true);
        do {
            System.out.print("End Date (DD/MM/YYYY): ");
            endInput = sc.nextLine().trim();
            if (endInput.equals("0")) return;
            if (endInput.isEmpty()) {
                endDate = null;
                break;
            } else if (JobInputValidator.isValidDate(endInput)) {
                endDate = JobInputValidator.parseDate(endInput);
                if (JobInputValidator.isValidEndDate(postedDate, endDate)) {
                    break;
                } else {
                    System.out.println("End date must be after the posted date.");
                }
            } else {
                System.out.println("Invalid end date. Please enter a valid date.");
            }
        } while (true);
        do {
            System.out.print("Skill Requirements (Use slash for multiple input): ");
            skillRequirements = sc.nextLine().trim();
            if (skillRequirements.equals("0")) return;
            if (JobInputValidator.isValidSkill(skillRequirements)) {
                break;
            } else {
                System.out.println("Invalid skill requirements. Please enter valid skills.");
            }
        } while (true);
        do {
            System.out.print("Education Level (Diploma/Degree): ");
            educationLevel = sc.nextLine().trim();
            if (educationLevel.equals("0")) return;
            if (JobInputValidator.isValidEducation(educationLevel)) {
                break;
            } else {
                System.out.println("Invalid education level. Please enter a valid education level (Diploma/Degree).");
            }
        } while (true);
        do {
            System.out.print("Vacancies: ");
            vacanciesInput = sc.nextLine().trim();
            if (vacanciesInput.equals("0")) return;
            if (JobInputValidator.isValidVacancy(vacanciesInput)) {
                vacancies = Integer.parseInt(vacanciesInput);
                break;
            } else {
                System.out.println("Invalid number of vacancies. Please enter a valid number.");
            }
        } while (true);
        do {
            System.out.print("Duration (in months), 2 or 4: ");
            durationInput = sc.nextLine().trim();
            if (durationInput.equals("0")) return;
            if (JobInputValidator.isValidDuration(durationInput)) {
                duration = Integer.parseInt(durationInput);
                break;
            } else {
                System.out.println("Invalid duration. Please enter a valid number.");
            }
        } while (true);
        do {
            System.out.print("Job Availability (Open/Closed/Filled): ");
            internshipStatus = sc.nextLine().trim();
            if (internshipStatus.equals("0")) return;
            if (JobInputValidator.isValidStatus(internshipStatus)) {
                break;
            } else {
                System.out.println("Invalid job availability. Please enter a valid status (Open/Close/Filled).");
            }
        } while (true);
        
        Job newJob = new Job(jobId, title, company, location, salary, postedDate, endDate, skillRequirements, educationLevel, vacancies, duration, internshipStatus);
        control.addNewJob(newJob);
        displayJobDetails(newJob);
    }
    
    public void updateJobDetails() {
        String title, company, location, skillRequirements, educationLevel, internshipStatus;
        int salary, vacancies, duration;
        LocalDate postedDate, endDate;
        
        Job selectedJob = null;
        while (selectedJob == null) {
            System.out.println("###Type '0' to quit");
            System.out.print("Enter the Job ID to update: ");
            String jobId = sc.nextLine().trim();
            if (jobId.equals("0")) return;
            selectedJob = control.getJobById(jobId);

            if (selectedJob == null) {
                System.out.println("Invalid Job ID. Please try again.");
            }
        }
        
        while (true) {
            System.out.print("New Job Title (press enter to skip): ");
            String titleInput = sc.nextLine().trim();
            if (titleInput.equals("0")) return;
            if (titleInput.isEmpty()) {
                title = selectedJob.getTitle();
                break;
            } else if (JobInputValidator.isValidTitle(titleInput)) {
                title = titleInput;
                break;
            } else {
                System.out.println("Invalid job title. Please enter a valid job title.");
            }
        }
            
        while (true) {
            System.out.print("New Company (press enter to skip): ");
            String companyInput = sc.nextLine().trim();
            if (companyInput.equals("0")) return;
            if (companyInput.isEmpty()) {
                company = selectedJob.getCompany();
                break;  
            } else if (JobInputValidator.isValidCompany(companyInput)) {
                company = companyInput;
                break; 
            } else {
                System.out.println("Invalid company name. Please enter a valid company name.");
            }
        }

        while (true) {
            System.out.print("New Location (press enter to skip): ");
            String locationInput = sc.nextLine().trim();
            if (locationInput.equals("0")) return;
            if (locationInput.isEmpty()) {
                location = selectedJob.getLocation();
                break;
            } else if (JobInputValidator.isValidLocation(locationInput)) {
                location = locationInput;
                break;
            } else {
                System.out.println("Invalid location. Please enter a valid location.");
            }
        }

        while (true) {
            System.out.print("New Salary (press enter to skip): ");
            String salaryInput = sc.nextLine().trim();
            if (salaryInput.equals("0")) return;
            if (salaryInput.isEmpty()) {
                salary = selectedJob.getSalary();
                break;
            } else if (JobInputValidator.isValidSalary(salaryInput)) {
                salary = Integer.parseInt(salaryInput);
                break;
            } else {
                System.out.println("Invalid salary. Please enter a valid salary.");
            }
        }

        while (true) {
            System.out.print("New Posted Date (DD/MM/YYYY), press enter to skip): ");
            String postedDateInput = sc.nextLine().trim();
            if (postedDateInput.equals("0")) return;
            if (postedDateInput.isEmpty()) {
                postedDate = selectedJob.getPostedDate();
                break;  
            } else if (JobInputValidator.isValidDate(postedDateInput)) {
                postedDate = JobInputValidator.parseDate(postedDateInput);
                break;  
            } else {
                System.out.println("Invalid posted date. Please enter a valid date.");
            }
        }

        while (true) {
            System.out.print("New End Date (DD/MM/YYYY), press enter to skip): ");
            String endDateInput = sc.nextLine().trim();
            if (endDateInput.equals("0")) return;
            if (endDateInput.isEmpty()) {
                endDate = selectedJob.getEndDate();
                break;  
            } else if (JobInputValidator.isValidDate(endDateInput)) {
                LocalDate tempEndDate = JobInputValidator.parseDate(endDateInput);
                if (JobInputValidator.isValidEndDate(postedDate, tempEndDate)) {
                    endDate = tempEndDate;
                    break;
                } else {
                    System.out.println("End date must be after the posted date.");
                }
            } else {
                System.out.println("Invalid due date. Please enter a valid date.");
            }
        }

        while (true) {
            System.out.print("New Skill Requirements (press enter to skip): ");
            String skillRequirementsInput = sc.nextLine().trim();
            if (skillRequirementsInput.equals("0")) return;
            if (skillRequirementsInput.isEmpty()) {
                skillRequirements = selectedJob.getSkillRequirements();
                break;
            } else if (JobInputValidator.isValidSkill(skillRequirementsInput)) {
                skillRequirements = skillRequirementsInput;
                break; 
            } else {
                System.out.println("Invalid skill requirements. Please enter valid skills.");
            }
        }

        while (true) {
            System.out.print("New Education Level (Diploma/Degree), press enter to skip): ");
            String educationLevelInput = sc.nextLine().trim();
            if (educationLevelInput.equals("0")) return;
            if (educationLevelInput.isEmpty()) {
                educationLevel = selectedJob.getEducationLevel();
                break; 
            } else if (JobInputValidator.isValidEducation(educationLevelInput)) {
                educationLevel = educationLevelInput;
                break; 
            } else {
                System.out.println("Invalid education level. Please enter a valid education level (Diploma/Degree).");
            }
        }

        while (true) {
            System.out.print("New Vacancies (press enter to skip): ");
            String vacanciesInput = sc.nextLine().trim();
            if (vacanciesInput.equals("0")) return;
            if (vacanciesInput.isEmpty()) {
                vacancies = selectedJob.getVacancies();
                break; 
            } else if (JobInputValidator.isValidVacancy(vacanciesInput)) {
                vacancies = Integer.parseInt(vacanciesInput);
                break;  
            } else {
                System.out.println("Invalid number of vacancies. Please enter a valid number.");
            }
        }
        
        while (true) {
            System.out.print("New Duration (in months), 2 or 4, press enter to skip): ");
            String durationInput = sc.nextLine().trim();
            if (durationInput.equals("0")) return;
            if (durationInput.isEmpty()) {
                duration = selectedJob.getDuration();
                break; 
            } else if (JobInputValidator.isValidDuration(durationInput)) {
                duration = Integer.parseInt(durationInput);
                break;  
            } else {
                System.out.println("Invalid duration. Please enter a valid number.");
            }
        }

        while (true) {
            System.out.print("New Job Availability (Open/Closed/Filled), press enter to skip): ");
            String internshipStatusInput = sc.nextLine().trim();
            if (internshipStatusInput.equals("0")) return;
            if (internshipStatusInput.isEmpty()) {
                internshipStatus = selectedJob.getInternshipStatus();
                break;  
            } else if (JobInputValidator.isValidStatus(internshipStatusInput)) {
                internshipStatus = internshipStatusInput;
                break;  
            } else {
                System.out.println("Invalid job availability. Please enter a valid status (Open/Close/Filled).");
            }
        }
        
        selectedJob.setTitle(title);
        selectedJob.setCompany(company);
        selectedJob.setLocation(location);
        selectedJob.setSalary(salary == -1 ? selectedJob.getSalary() : salary);
        selectedJob.setPostedDate(postedDate == null ? selectedJob.getPostedDate() : postedDate);
        selectedJob.setEndDate(endDate == null ? selectedJob.getEndDate() : endDate);
        selectedJob.setSkillRequirements(skillRequirements);
        selectedJob.setEducationLevel(educationLevel);
        selectedJob.setVacancies(vacancies == -1 ? selectedJob.getVacancies() : vacancies);
        selectedJob.setDuration(duration == -1 ? selectedJob.getDuration() : duration);
        selectedJob.setInternshipStatus(internshipStatus);
        
        control.updateJob(selectedJob);
        displayJobDetails(selectedJob);
    }
    
    public void removeJob() {
        Scanner scanner = new Scanner(System.in);
        boolean jobFound = false;

        while (!jobFound) {
            System.out.print("Enter the Job ID to remove (or type '0' to quit): ");
            String jobId = scanner.nextLine();
            if (jobId.equals("0")) return;

            jobFound = control.removeJobById(jobId);

            if (jobFound) {
                System.out.println("Job with " + jobId + " has been removed successfully.");
            } else {
                System.out.println("No job found with " + jobId + ". Please try again.");
            }
        }
    }
    
    public ListStackQueueInterface<Job> filterInput() {
        System.out.println("###Press enter to skip");
        System.out.println("###Type '0' to quit");
        
        String jobTitle = "";
        boolean validJobTitle = false;
        while (!validJobTitle) {
            System.out.print("Job Title: ");
            jobTitle = sc.nextLine().trim();
            if (jobTitle.equals("0")) return null;
            if (jobTitle.isEmpty() || JobInputValidator.isValidTitle(jobTitle)) {
                validJobTitle = true;
            } else {
                System.out.println("Invalid job title. Please enter a valid job title.");
            }
        }
        
        String companyName = "";
        boolean validCompany = false;
        while (!validCompany) {
            System.out.print("Company (Use comma for multiple input): ");
            companyName = sc.nextLine().trim();
            if (companyName.equals("0")) return null;
            if (companyName.isEmpty() || JobInputValidator.isValidCompany(companyName)) {
                validCompany = true;
            } else {
                System.out.println("Invalid company name. Please enter a valid company name.");
            }
        }
        
        String location = "";
        boolean validLocation = false;
        while (!validLocation) {
            System.out.print("Location (Use comma for multiple input): ");
            location = sc.nextLine().trim();
            if (location.equals("0")) return null;
            if (location.isEmpty() || JobInputValidator.isValidLocation(location)) {
                validLocation = true;
            } else {
                System.out.println("Invalid location. Please enter a valid location.");
            }
        }
        
        int salary = 0;
        boolean validSalary = false;
        while (!validSalary) {
            System.out.print("Salary: ");
            String input = sc.nextLine().trim();
            if (input.equals("0")) return null;
            if (input.isEmpty()) {
                break;
            }

            if (JobInputValidator.isValidSalary(input)) {
                salary = Integer.parseInt(input);
                validSalary = true;
            } else {
                System.out.println("Invalid salary. Please enter a positive number.");
            }
        }
        
        String skill = "";
        boolean validSkill = false;
        while (!validSkill) {
            System.out.print("Skill Requirements (Use comma for multiple input): ");
            skill = sc.nextLine().trim();
            if (skill.equals("0")) return null;
            if (skill.isEmpty() || JobInputValidator.isValidSkill(skill)) {
                validSkill = true;
            } else {
                System.out.println("Invalid skill requirements. Please enter valid skills.");
            }
        }
        
        String level = "";
        boolean validEducation = false;
        while (!validEducation) {
            System.out.print("Education Level (Diploma/Degree): ");
            level = sc.nextLine().trim();
            if (level.equals("0")) return null;
            if (level.isEmpty() || JobInputValidator.isValidEducation(level)) {
                validEducation = true;
            } else {
                System.out.println("Invalid education level. Please enter a valid education level (Diploma/Degree).");
            }
        }
        
        int duration = 0;
        boolean validDuration = false;
        while (!validDuration) {
            System.out.print("Duration (in months), 2 or 4: ");
            String input = sc.nextLine().trim();
            if (input.equals("0")) return null;
            if (input.isEmpty()) {
                break;
            }

            if (JobInputValidator.isValidDuration(input)) {
                duration = Integer.parseInt(input);
                validDuration = true;
            } else {
                System.out.println("Invalid duration. Please enter a positive number.");
            }
        }

        String availability = "";
        boolean validAvailability = false;
        while (!validAvailability) {
            System.out.print("Job Availability (Open/Closed/Filled): ");
            availability = sc.nextLine().trim();
            if (availability.equals("0")) return null;
            if (availability.isEmpty() || JobInputValidator.isValidStatus(availability)) {
                validAvailability = true;
            } else {
                System.out.println("Invalid job availability. Please enter a valid status (Open/Close/Filled).");
            }
        }
        return control.filterJobs(jobTitle, companyName, location, salary, skill, level, duration, availability);
    }
    
    public void filterJob() {
        System.out.println("\n--- Filter Jobs ---");
        ListStackQueueInterface<Job> results = filterInput();
        if (results == null) return;
        if (results.isEmpty()) {
            System.out.println("No jobs matched your criteria.");
        } else {
            System.out.println("\n--- Filtered Job Results ---");
            System.out.println("Total matched: " + results.size());

            Iterator<Job> iterator = results.getIterator();
            while (iterator.hasNext()) {
                Job job = iterator.next();
                displayJobDetails(job);
            }
        }
    }
    
    public void displayAllJobs() {
        System.out.println("\n=== ALL JOBS ===");
        control.listAllJobs();
    }
    
    public void displaySortingList() {
        int choice;
        ListStackQueueInterface<Job> sorted = null;

        do {
            System.out.println("\n==== JOB SORTING MENU ====");
            System.out.println("1. Sort by Job Title");
            System.out.println("2. Sort by Company");
            System.out.println("3. Sort by Location");
            System.out.println("4. Sort by Salary");
            System.out.println("5. Sort by Education Level");
            System.out.println("6. Sort by Job Availability");
            System.out.println("0. Back to Job Menu");
            System.out.print("Enter choice: ");
            
            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> sorted = control.insertionSort("title");
                    case 2 -> sorted = control.insertionSort("company");
                    case 3 -> sorted = control.insertionSort("location");
                    case 4 -> sorted = control.insertionSort("salary");
                    case 5 -> sorted = control.insertionSort("educationLevel");
                    case 6 -> sorted = control.insertionSort("internshipStatus");
                    case 0 -> {
                        return;
                    }
                default -> System.out.println("Invalid choice. Please try again.");
                }

                if (choice >= 1 && choice <= 6 && sorted != null) {
                    System.out.println("\nSorted Job Listings:");
                    control.displayJobs(sorted);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (sorted == null);
    }
    
    public void keywordSearch() {
        System.out.print("Enter a keyword to search (or enter '0' to exit): ");
        String keyword = sc.nextLine();
        if (keyword.equals("0")) return;
        ListStackQueueInterface<Job> result = control.linearSearchByKeyword(keyword);
        if (!result.isEmpty()) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                Job results = result.dequeue();  
                result.enqueue(results);         
                displayJobDetails(results);
            }
        } else {
            System.out.println("No job found matching the search keyword.");
        }
    }
    
    public void filterReport() {
        System.out.println("\n--- Filter By Criteria ---");
        ListStackQueueInterface<Job> results = filterInput();

        if (results == null) return;
        if (results.isEmpty()) {
            System.out.println("No job postings matched your criteria.");
            System.out.println("No report will be displayed due to invalid filter input.");
        } else {
            System.out.println("\nTotal Job Posting Matches: " + results.size());
            generateReport(results);
        }
    }
    
    private void generateReport(ListStackQueueInterface<Job> jobs) {
        int length = 202;
        String title = " Job Posting Report ";
        int padding = (length - title.length()) / 2;
        System.out.println("\n" + "-".repeat(padding) + title + "-".repeat(padding));

        // Header
        System.out.printf("%-6s | %-25s | %-15s | %-15s | %-10s | %-12s | %-12s | %-23s | %-15s | %-10s | %-8s | %-20s\n", "JobID", "Title", "Company", "Location", "Salary", "Posted Date", "End Date", "Skill Requirements", "Education Level", "Vacancies", "Duration", "Job Availability");
        System.out.println("-".repeat(202));
        Iterator<Job> iterator = jobs.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();

            System.out.printf("%-6s | %-25s | %-15s | %-15s | %-10s | %-12s | %-12s | %-23s | %-15s | %-10s | %-8s | %-20s\n",
                    job.getJobID(),
                    job.getTitle(),
                    job.getCompany(),
                    job.getLocation(),
                    job.getSalary(),
                    job.getPostedDate(),
                    job.getEndDate(),
                    job.getSkillRequirements(),
                    job.getEducationLevel(),
                    job.getVacancies(),
                    job.getDuration(),
                    job.getInternshipStatus()); 
        }

        System.out.println("-".repeat(202) + "\n");
    }

    public static void displayMessage(String message) {
        System.out.println("\n========= SYSTEM MESSAGE =========");
        System.out.println(message);
        System.out.println("==================================");
    }

    public void displayJobDetails(Job job) {
        System.out.println("\n------------- JOB DETAILS -----------------");
        System.out.println("Job ID          : " + job.getJobID());
        System.out.println("Title           : " + job.getTitle());
        System.out.println("Company         : " + job.getCompany());
        System.out.println("Location        : " + job.getLocation());
        System.out.println("Salary          : $" + job.getSalary());
        System.out.println("Posted Date     : " + job.getPostedDate());
        System.out.println("End Date        : " + job.getEndDate());
        System.out.println("Requirements    : " + job.getSkillRequirements());
        System.out.println("Education Level : " + job.getEducationLevel());
        System.out.println("Vacancies       : " + job.getVacancies());
        System.out.println("Duration(months): " + job.getDuration());
        System.out.println("Internship      : " + job.getInternshipStatus());
        System.out.println("-------------------------------------------");
    }
}