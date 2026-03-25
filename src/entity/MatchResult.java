package entity;//author: Yaw Wei Ying

import adt.ListStackQueueInterface;
import adt.ArrayList;


public class MatchResult {

    private Applicant applicant;
    private Job job;
    private double matchScore;
    private String summary;
    private ListStackQueueInterface<SkillMatch> skillMatches;

    public MatchResult(Applicant applicant, Job job, double matchScore, String summary) {
        this.applicant = applicant;
        this.job = job;
        this.matchScore = matchScore;
        this.summary = summary;
        this.skillMatches = new ArrayList<>();
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Job getJob() {
        return job;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public String getSummary() {
        return summary;
    }

    public ListStackQueueInterface<SkillMatch> getSkillMatches() {
        return skillMatches;
    }

    public void addSkillMatch(Skill requiredSkill, Skill applicantSkill, double skillScore) {
        skillMatches.add(new SkillMatch(requiredSkill, applicantSkill, skillScore));
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSkillMatches(ListStackQueueInterface<SkillMatch> skillMatches) {
        this.skillMatches = skillMatches;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Match Result: %.1f%%\n", matchScore * 100));
        sb.append("Applicant: ").append(applicant.getName()).append("\n");
        sb.append("Job: ").append(job.getTitle()).append("\n");
        sb.append("Summary: ").append(summary).append("\n\n");
        sb.append("Skill Breakdown:\n");

        for (int i = 1; i <= skillMatches.size(); i++) {
            sb.append(skillMatches.getEntry(i).toString()).append("\n");
        }

        return sb.toString();
    }

    
    public String getExplanation() {
    StringBuilder explanation = new StringBuilder();

    // 1. Basic Information
    explanation.append("Applicant: ").append(applicant.getName())
              .append(" (").append(applicant.getApplicantId()).append(")\n");
    explanation.append("Job: ").append(job.getTitle())
              .append(" (").append(job.getJobID()).append(")\n");
    explanation.append(String.format("Overall Match Score: %.1f%%\n\n", matchScore * 100));

    // 2. Location Analysis
    explanation.append("Location Analysis:\n");
    if (applicant.getLocation().equalsIgnoreCase(job.getLocation())) {
        explanation.append("P. Perfect location match (").append(job.getLocation()).append(")\n");
    } else {
        explanation.append("X. Location mismatch (Applicant: ")
                  .append(applicant.getLocation()).append(", Job: ")
                  .append(job.getLocation()).append(")\n");
    }

    // 3. Job Type Analysis
    explanation.append("\nJob Type Analysis:\n");
    if (applicant.getDesiredJobType().equalsIgnoreCase(job.getTitle())) {
        explanation.append("P. Perfect job type match (").append(job.getTitle()).append(")\n");
    } else {
        explanation.append("X. Job type mismatch (Applicant wants: ")
                  .append(applicant.getDesiredJobType()).append(", Job is: ")
                  .append(job.getTitle()).append(")\n");
    }

    // 4. Skills Analysis (without array)
    explanation.append("\nSkills Analysis:\n");
    String requirements = job.getSkillRequirements();
    int start = 0;
    int end = 0;
    int totalSkills = 0;
    int matchedSkills = 0;

    while ((end = requirements.indexOf('/', start)) != -1) {
        String skill = requirements.substring(start, end).trim();
        if (processSkill(skill, applicant, explanation)) {
            matchedSkills++;
        }
    start = end + 1;
    totalSkills++;
}
    // Process last skill
    String lastSkill = requirements.substring(start).trim();
    if (!lastSkill.isEmpty()) {
        if (processSkill(lastSkill, applicant, explanation)) {
            matchedSkills++;
        }
        totalSkills++;
}
    
    // 5. Education Level Analysis
    explanation.append("\nEducation Level Analysis:\n");
    if (applicant.getEducationLevel().equalsIgnoreCase(job.getEducationLevel())) {
        explanation.append("P. Education level match (").append(job.getEducationLevel()).append(")\n");
    } else {
        explanation.append("X. Education level mismatch (Applicant: ")
                  .append(applicant.getEducationLevel()).append(", Job: ")
                  .append(job.getEducationLevel()).append(")\n");
    }

    // 6. Salary Analysis
    explanation.append("\nSalary Analysis:\n");
    if (job.getSalary() != -1 && applicant.getExpectedSalary() != -1) {
        double lowerBound = job.getSalary() * 0.8;
        double upperBound = job.getSalary() * 1.2;
        boolean isMatch = applicant.getExpectedSalary() >= lowerBound && 
                         applicant.getExpectedSalary() <= upperBound;
        
        if (isMatch) {
            explanation.append("P. Salary expectations compatible (Job: ")
                      .append(job.getSalary()).append(", Applicant: ")
                      .append(applicant.getExpectedSalary()).append(")\n");
        } else {
            explanation.append("X. Salary expectations mismatch (Job: ")
                      .append(job.getSalary()).append(", Applicant: ")
                      .append(applicant.getExpectedSalary()).append(")\n");
        }
    } else {
        explanation.append("~ Salary information not available for comparison\n");
    }

    // 7. CGPA Analysis
    explanation.append("\nAdditional Factors:\n");
    if (applicant.getCgpa() >= 3.5) {
        explanation.append("P. Strong academic performance (CGPA: ")
                  .append(applicant.getCgpa()).append(")\n");
    } else {
        explanation.append("~ Moderate academic performance (CGPA: ")
                  .append(applicant.getCgpa()).append(")\n");
    }
    
    // 8. Summary
    explanation.append("\nSummary:\n");
    explanation.append("- ").append(matchedSkills).append("/").append(totalSkills).append(" required skills matched\n");
    explanation.append("- Location ").append(applicant.getLocation().equalsIgnoreCase(job.getLocation()) ? "match" : "mismatch").append("\n");
    explanation.append("- Job type ").append(applicant.getDesiredJobType().equalsIgnoreCase(job.getTitle()) ? "match" : "partial match").append("\n");

    return explanation.toString();
}

private boolean processSkill(String skill, Applicant applicant, 
                        StringBuilder explanation) {
    skill = skill.toLowerCase();
    boolean hasSkill = false;
    String skillName = "";

    if (skill.contains("java")) {
        hasSkill = applicant.knowsJava();
        skillName = "Java";
    } else if (skill.contains("python")) {
        hasSkill = applicant.knowsPython();
        skillName = "Python";
    } else if (skill.contains("sql")) {
        hasSkill = applicant.knowsSQL();
        skillName = "SQL";
    } else if (skill.contains("aws")) {
        hasSkill = applicant.knowsJava();
        skillName = "AWS";
    } else if (skill.contains("c++")) {
        hasSkill = applicant.knowsJava();
        skillName = "C++";
    } else {
        skillName = skill;
    }

    if (hasSkill) {
        explanation.append("P. Has required skill: ").append(skillName).append("\n");
    } else {
        explanation.append("X. Missing skill: ").append(skillName).append("\n");
    }
        return hasSkill;
} 
}
