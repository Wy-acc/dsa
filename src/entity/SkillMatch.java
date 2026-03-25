package entity;//author: Yaw Wei Ying

public class SkillMatch {

    private Skill requiredSkill;
    private Skill applicantSkill;
    private double matchScore;

    public SkillMatch(Skill requiredSkill, Skill applicantSkill, double matchScore) {
        this.requiredSkill = requiredSkill;
        this.applicantSkill = applicantSkill;
        this.matchScore = matchScore;
    }

    public Skill getRequiredSkill() {
        return requiredSkill;
    }

    public Skill getApplicantSkill() {
        return applicantSkill;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setRequiredSkill(Skill requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public void setApplicantSkill(Skill applicantSkill) {
        this.applicantSkill = applicantSkill;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    @Override
    public String toString() {
        return String.format("%s (Req: %d, Applicant: %d) - Score: %.1f",
                requiredSkill.getName(),
                requiredSkill.getProficiency(),
                applicantSkill.getProficiency(),
                matchScore);
    }
}
