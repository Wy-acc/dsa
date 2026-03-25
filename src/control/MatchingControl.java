package control;//author: Yaw Wei Ying

import adt.ArrayList;
import adt.ListStackQueueInterface;
import boundary.MatchingBoundary;
import entity.Applicant;
import entity.Job;
import entity.MatchResult;
import java.util.Iterator;
import adt.SortedArrayList;
import java.util.Comparator;

public class MatchingControl {

    private final ListStackQueueInterface<Job> jobs;
    private final ListStackQueueInterface<Applicant> applicants;
    private final ListStackQueueInterface<MatchResult> matches = new ArrayList<>();
    private final ListStackQueueInterface<MatchResult> allMatches = new ArrayList<>();
    private final MatchingBoundary matchingUI = new MatchingBoundary();

    public MatchingControl(ListStackQueueInterface<Job> jobs, ListStackQueueInterface<Applicant> applicants) {
        this.jobs = jobs;
        this.applicants = applicants;
    }

    public void run() {
        int choice;
        do {
            matchingUI.displayMenu();
            choice = matchingUI.getIntInput();
            switch (choice) {
                case 1 -> {
                        matchingUI.ApplicantSubMenu();
                        int searchType = matchingUI.getIntInput();
                        if (searchType == 1) {
                            matchApplicantToJobs();
                        } else if (searchType == 2) {
                            String name = matchingUI.promptApplicantName();
                            matchApplicantToJobsByName(name);
                        }
                    }
                case 2 -> {
                        matchingUI.JobSubMenu();
                        int searchType = matchingUI.getIntInput();
                        if (searchType == 1) {
                            matchJobToApplicants();
                        } else if (searchType == 2) {
                            String keywords = matchingUI.promptJobKeywords();
                            matchJobToApplicantsByName(keywords);
                        }
                    }
                case 3 -> showAllMatches();
                case 4 -> matchingUI.generateReport(matches);
                case 0 -> {
                    return;
                }
                default -> matchingUI.showMessage("Invalid choice");
            }
        } while (choice != 0);
    }

    private void matchApplicantToJobs() {
        matches.clear();
        
        String applicantId = matchingUI.promptApplicantId();
        Applicant applicant = findApplicantById(applicantId);

        if (applicant == null) {
            matchingUI.showMessage("Applicant not found");
            return;
        }

        Iterator<Job> jobIterator = jobs.getIterator();
        while (jobIterator.hasNext()) {
            Job job = jobIterator.next();
            if (job.getInternshipStatus().equalsIgnoreCase("Open")) {
                MatchResult result = calculateMatch(applicant, job);
                if (result.getMatchScore() >= 0.3 && !isDuplicateMatch(matches, applicant, job)) {
                    matches.add(result);
                }
            }
        }
        sortMatchesByScore((ArrayList<MatchResult>) matches);
        matchingUI.displayMatches(matches, "Jobs matching applicant " + applicant.getName());
    }
    
    private void matchJobToApplicants() {
        matches.clear();
        
        String jobId = matchingUI.promptJobId();
        Job job = findJobById(jobId);

        if (job == null) {
            matchingUI.showMessage("Job not found");
            return;
        }
        
        if (!job.getInternshipStatus().equalsIgnoreCase("Open")) {
            matchingUI.showMessage("This job is not currently open for applications");
            return;
        }

        Iterator<Applicant> applicantIterator = applicants.getIterator();
        while (applicantIterator.hasNext()) {
            Applicant applicant = applicantIterator.next();
            MatchResult result = calculateMatch(applicant, job);
            if (result.getMatchScore() >= 0.5) {
                matches.add(result);
            }
        }
        sortMatchesByScore((ArrayList<MatchResult>) matches);
        matchingUI.displayMatches(matches, "Applicants matching job " + job.getTitle());
    }
    
    private void matchApplicantToJobsByName(String name) {
    matches.clear();
    ListStackQueueInterface<Applicant> foundApplicants = new ArrayList<>();
    
    Iterator<Applicant> applicantIterator = applicants.getIterator();
    while (applicantIterator.hasNext()) {
        Applicant applicant = applicantIterator.next();
        if (calculateFuzzyMatch(applicant.getName(), name) > 0.7) {
            foundApplicants.add(applicant);
        }
    }
    
    if (foundApplicants.isEmpty()) {
        matchingUI.showMessage("No applicants found with that name");
        return;
    }
    
    // For each found applicant, find matching jobs
    Iterator<Applicant> foundIterator = foundApplicants.getIterator();
    while (foundIterator.hasNext()) {
        Applicant applicant = foundIterator.next();
        Iterator<Job> jobIterator = jobs.getIterator();
        while (jobIterator.hasNext()) {
            Job job = jobIterator.next();
            if (job.getInternshipStatus().equalsIgnoreCase("Open")) {
                MatchResult result = calculateMatch(applicant, job);
                if (result.getMatchScore() >= 0.5) {
                    matches.add(result);
                }
            }
        }
    }
    
    matchingUI.displayMatches(matches, "Jobs matching applicants named '" + name + "'");
}

    private void matchJobToApplicantsByName(String name) {
    matches.clear();
    ListStackQueueInterface<Job> foundJobs = new ArrayList<>();

    // Find jobs that match the name (fuzzy match) and have "Open" internship status
    Iterator<Job> jobIterator = jobs.getIterator();
    while (jobIterator.hasNext()) {
        Job job = jobIterator.next();
        if (job.getInternshipStatus().equalsIgnoreCase("Open") &&
            calculateFuzzyMatch(job.getTitle(), name) > 0.7) {
            foundJobs.add(job);
        }
    }

    if (foundJobs.isEmpty()) {
        matchingUI.showMessage("No jobs found matching those keywords");
        return;
    }

    // For each found job, find matching applicants
    Iterator<Job> foundIterator = foundJobs.getIterator();
    while (foundIterator.hasNext()) {
        Job job = foundIterator.next();
        Iterator<Applicant> applicantIterator = applicants.getIterator();
        while (applicantIterator.hasNext()) {
            Applicant applicant = applicantIterator.next();
            MatchResult result = calculateMatch(applicant, job);
            if (result.getMatchScore() >= 0.5) {
                matches.add(result);
            }
        }
    }

    matchingUI.displayMatches(matches, "Applicants matching jobs with keywords '" + name + "'");
    }

    
    private void showAllMatches() {
        allMatches.clear();
        double minScore = matchingUI.promptMinSkillScore();
        
        Iterator<Applicant> applicantIterator = applicants.getIterator();
        while (applicantIterator.hasNext()) {
            Applicant applicant = applicantIterator.next();
            
            Iterator<Job> jobIterator = jobs.getIterator();
            while (jobIterator.hasNext()) {
                Job job = jobIterator.next();
                // Only include open jobs
                if (job.getInternshipStatus().equalsIgnoreCase("Open")) {
                    MatchResult result = calculateMatch(applicant, job);
                    if (result.getMatchScore() >= (minScore/100) && !isDuplicateMatch(allMatches, applicant, job)) {
                        allMatches.add(result);
                    }
                }
            }
        }
        sortMatchesByScore((ArrayList<MatchResult>) matches);
        matchingUI.displayMatches(allMatches, "Filtered Matches with Skill Score >= " + minScore + "% (Only Open Jobs)");
    }
    
    private void sortMatchesByScore(ArrayList<MatchResult> matchList) {
    for (int i = 0; i < matchList.getNumberOfEntries(); i++) {
        for (int j = 0; j < matchList.getNumberOfEntries() - i - 1; j++) {
            MatchResult current = matchList.getEntry(j);
            MatchResult next = matchList.getEntry(j + 1);

            // Skip if either is null
            if (current == null || next == null) {
                continue;
            }

            if (current.getMatchScore() < next.getMatchScore()) {
                // Swap
                matchList.replace(j, next);
                matchList.replace(j + 1, current);
                }
            }
        }
    }

    private boolean isDuplicateMatch(ListStackQueueInterface<MatchResult> matchList, Applicant applicant, Job job) {
        Iterator<MatchResult> matchIterator = matchList.getIterator();
        while (matchIterator.hasNext()) {
            MatchResult existing = matchIterator.next();
            if (existing.getApplicant().getApplicantId().equals(applicant.getApplicantId())
                    && existing.getJob().getJobID().equals(job.getJobID())) {
                return true;
            }
        }
        return false;
    }

    private MatchResult calculateMatch(Applicant applicant, Job job) {
        double score = 0;
        StringBuilder explanation = new StringBuilder();

        // Location match (25%)
        boolean locationMatch = isLocationMatch(applicant.getLocation(), job.getLocation());
        if (locationMatch) {
            score += 0.25;
            explanation.append("-> Location match (").append(job.getLocation()).append(")\n");
        } else {
            explanation.append("(X) Location mismatch (Applicant: ").append(applicant.getLocation())
                    .append(" vs Job: ").append(job.getLocation()).append(")\n");
        }

        // Job type match (20%)
        boolean jobTypeMatch = isJobTypeMatch(applicant.getDesiredJobType(), job.getTitle());
        if (jobTypeMatch) {
            score += 0.2;
            explanation.append("-> Job title match (").append(job.getTitle()).append(")\n");
        } else {
            explanation.append("(X) Job title mismatch (Applicant wants: ").append(applicant.getDesiredJobType())
                    .append(" vs Job: ").append(job.getTitle()).append(")\n");
        }

        // Skills match (35%)
        double skillsScore = calculateSkillsMatch(applicant, job);
        score += skillsScore * 0.35;
        explanation.append(String.format("-> Skills match (%.0f%%)\n", skillsScore * 100));
        explanation.append("\nSkill Breakdown:\n");
        explanation.append(generateSkillsBreakdown(applicant, job));

        // Education level match (10%)
        boolean educationMatch = isEducationMatch(applicant.getEducationLevel(), job.getEducationLevel());
        if (educationMatch) {
            score += 0.1;
            explanation.append("-> Education level match (").append(job.getEducationLevel()).append(")\n");
        } else {
            explanation.append("(X) Education level mismatch (Applicant: ").append(applicant.getEducationLevel())
                    .append(" vs Job: ").append(job.getEducationLevel()).append(")\n");
        }

        // Salary match (10%)
        boolean salaryMatch = isSalaryMatch(applicant.getExpectedSalary(), job.getSalary());
        if (salaryMatch) {
            score += 0.1;
            explanation.append("-> Salary expectation match (Job: ").append(job.getSalary())
                    .append(", Applicant: ").append(applicant.getExpectedSalary()).append(")\n");
        } else {
            explanation.append("(X) Salary expectation mismatch (Job: ").append(job.getSalary())
                    .append(", Applicant: ").append(applicant.getExpectedSalary()).append(")\n");
        }

        return new MatchResult(applicant, job, score, explanation.toString());
    }

    
    private boolean isLocationMatch(String applicantLocation, String jobLocation) {
        if (applicantLocation.equalsIgnoreCase("Kuala Lumpur")) {
            return jobLocation.equalsIgnoreCase("Kuala Lumpur") || jobLocation.equalsIgnoreCase("Cyberjaya");
        }
        return applicantLocation.equalsIgnoreCase(jobLocation);
    }

    private boolean isJobTypeMatch(String desiredJob, String jobTitle) {
        desiredJob = desiredJob.trim().toLowerCase();
        jobTitle = jobTitle.trim().toLowerCase();

        if (desiredJob.contains("software") && jobTitle.contains("software")) {
            return true;
        }
        if (desiredJob.contains("data") && jobTitle.contains("data")) {
            return true;
        }
        if (desiredJob.contains("backend") && jobTitle.contains("backend")) {
            return true;
        }
        if (desiredJob.contains("java") && jobTitle.contains("java")) {
            return true;
        }
        if (desiredJob.contains("python") && jobTitle.contains("python")) {
            return true;
        }
        if (desiredJob.contains("database") || desiredJob.contains("db admin")) {
            return jobTitle.contains("database") || jobTitle.contains("db admin");
        }

        return desiredJob.equals(jobTitle);
    }
    
    private boolean isEducationMatch(String applicantEducation, String jobEducation) {
        if (applicantEducation == null || jobEducation == null) return false;

        // Simple exact match for now - could be enhanced with level comparisons
        return applicantEducation.equalsIgnoreCase(jobEducation);
    }

    public boolean isSalaryMatch(int applicantExpected, int jobSalary) {
        if (jobSalary == -1) return true; // Job salary not specified
        if (applicantExpected == -1) return true; // Applicant salary not specified

        // Consider it a match if applicant's expectation is within 20% of job salary
        double lowerBound = jobSalary * 0.8;
        double upperBound = jobSalary * 1.2;
        return applicantExpected >= lowerBound && applicantExpected <= upperBound;
    }

    private double calculateSkillsMatch(Applicant applicant, Job job) {
        ListStackQueueInterface<String> requiredSkills = getSkillsForJob(job.getJobID());

        if (requiredSkills.isEmpty()) {
            return 0;
        }
        
        int matchedSkills = 0;
        double proficiencyScore = 0;

        Iterator<String> skillIterator = requiredSkills.getIterator();
        while (skillIterator.hasNext()) {
            String skill = skillIterator.next().toLowerCase();

            if ((skill.contains("java") && applicant.knowsJava()) ||
                (skill.contains("python") && applicant.knowsPython()) ||
                (skill.contains("sql") && applicant.knowsSQL()) ||
                (skill.contains("javascript") && applicant.knowsJavaScript()) ||
                (skill.contains("cpp") && applicant.knowsCpp()) ||
                (skill.contains("r") && applicant.knowsR()) ||
                (skill.contains("css") && applicant.knowsCSS()) ||
                (skill.contains("html") && applicant.knowsHTML()) || 
                (skill.contains("kotlin") && applicant.knowsKotlin())    
                    ) {

                matchedSkills++;
                if (skill.equals("java") || skill.equals("python") || skill.equals("sql")
                    || skill.equals("javascript") || skill.equals("cpp") || skill.equals("r") 
                    || skill.equals("css") || skill.equals("html") || skill.equals("kotlin")    
                        ) {
                    proficiencyScore += 0.8;
                } else {
                    proficiencyScore += 0.6;
                }
            }
        }
       
        double coverage = (double) matchedSkills / requiredSkills.getNumberOfEntries();
        double proficiency = matchedSkills > 0 ? proficiencyScore / matchedSkills : 0;

        return (coverage * 0.7) + (proficiency * 0.3);
    }

    private String generateSkillsBreakdown(Applicant applicant, Job job) {
        StringBuilder sb = new StringBuilder();
        ListStackQueueInterface<String> requiredSkills = getSkillsForJob(job.getJobID());
        
        if (requiredSkills.isEmpty()) {
            return "No specific skills required for this job\n";
        }

        Iterator<String> skillIterator = requiredSkills.getIterator();
        while (skillIterator.hasNext()) {
            String skill = skillIterator.next().toLowerCase();
            boolean hasSkill = false;
            String skillName;

            if (skill.contains("java")) {
                hasSkill = applicant.knowsJava();
                skillName = "Java";
            } else if (skill.contains("python")) {
                hasSkill = applicant.knowsPython();
                skillName = "Python";
            } else if (skill.contains("sql")) {
                hasSkill = applicant.knowsSQL();
                skillName = "SQL";
            } else if (skill.contains("cpp")) {
                hasSkill = applicant.knowsCpp();
                skillName = "C++";
            } else if (skill.contains("r")) {
                hasSkill = applicant.knowsR();
                skillName = "R";
            } else if (skill.contains("html")) {
                hasSkill = applicant.knowsHTML();
                skillName = "HTML";
            } else if (skill.contains("css")) {
                hasSkill = applicant.knowsCSS();
                skillName = "CSS";
            } else if (skill.contains("javascript")) {
                hasSkill = applicant.knowsJavaScript();
                skillName = "JavaScript";
            } else if (skill.contains("kotlin")) {
                hasSkill = applicant.knowsKotlin();
                skillName = "Kotlin";  
            } else {
                skillName = skill;
            }            
            
            sb.append("- ").append(skillName).append(": ").append(hasSkill ? "->" : "(X)");
            if (!skill.equals(skillName.toLowerCase())) {
                sb.append(" (Matched as related skill)");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
        
    private ListStackQueueInterface<String> getSkillsForJob(String jobId) {
    Iterator<Job> iterator = jobs.getIterator();
    while (iterator.hasNext()) {
        Job job = iterator.next();
        if (job.getJobID().equals(jobId)) {
            String skills = job.getSkillRequirements();
            if (skills == null || skills.isEmpty()) {
                return new ArrayList<>();
            }
            
            // Parse the skills string (assuming comma-separated)
            ListStackQueueInterface<String> skillList = new ArrayList<>();
            String[] skillArray = skills.split(",");
            for (String skill : skillArray) {
                skillList.add(skill.trim());
            }
            return skillList;
        }
    }
    return new ArrayList<>();
    } 
        
    private Applicant findApplicantById(String id) {
        Iterator<Applicant> iterator = applicants.getIterator();
        while (iterator.hasNext()) {
            Applicant applicant = iterator.next();
            if (applicant.getApplicantId().equals(id)) {
                return applicant;
            }
        }
        return null;
    }

    private Job findJobById(String id) {
        Iterator<Job> iterator = jobs.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getJobID().equals(id)) {
                return job;
            }
        }
        return null;
    }

    private double calculateFuzzyMatch(String text, String searchTerm) {
    if (text == null || searchTerm == null) return 0.0;
    
    text = text.toLowerCase();
    searchTerm = searchTerm.toLowerCase();

    // Exact match
    if (text.contains(searchTerm)) return 1.0;
    
    // Check for partial matches
    if (searchTerm.length() > 3) {
        // Try matching beginning of words
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (word.startsWith(searchTerm)) return 0.9;
        }
        
        // Try matching 80% of the search term
        int partialLength = (int) (searchTerm.length() * 0.8);
        if (partialLength >= 3) {
            String partial = searchTerm.substring(0, partialLength);
            if (text.contains(partial)) return 0.7;
        }
    }
    
    // Try acronym matching (e.g., "se" matches "software engineer")
    if (searchTerm.length() <= 3) {
        String[] words = text.split("\\s+");
        StringBuilder acronym = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                acronym.append(word.charAt(0));
            }
        }
        if (acronym.toString().contains(searchTerm)) return 0.6;
    }
    
    return 0.0;
    }
    
    public ListStackQueueInterface<MatchResult> getAllMatchesAboveThreshold(double threshold) {
        ListStackQueueInterface<MatchResult> filteredMatches = new SortedArrayList<>(
            Comparator.comparing(MatchResult::getMatchScore).reversed()
        );
        
        Iterator<Applicant> applicantIterator = applicants.getIterator();
        while (applicantIterator.hasNext()) {
            Applicant applicant = applicantIterator.next();
            
            Iterator<Job> jobIterator = jobs.getIterator();
            while (jobIterator.hasNext()) {
                Job job = jobIterator.next();
                if (job.getInternshipStatus().equalsIgnoreCase("Open")) {
                    MatchResult result = calculateMatch(applicant, job);
                    if (result.getMatchScore() >= threshold) {
                        filteredMatches.add(result);
                    }
                }
            }
        }
        
        return filteredMatches;
    }
}