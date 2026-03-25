package entity; //author: Tay Mian Yin

public class Applicant {
    public static final String[] LOCATIONS = {"Kuala Lumpur", "Pulau Penang", "Kampar", "Kuantan", "Segamat", "Kota Kinabalu"};
    public static final String[] JOBTYPES = {"Software Engineer", "Data Analyst", "Backend Developer", "Java Developer", "Python Developer"};
    public static final String[] EDUCATIONLEVELS = {"Diploma", "Degree"};
    
    private String applicantId;
    private String name;
    private String gender;
    private String email;
    private double cgpa;
    private String location;
    private String desiredJobType;
    private int expectedSalary;
    private String educationLevel;
    
    private boolean knowsJava;
    private boolean knowsPython;
    private boolean knowsSQL;
    private boolean knowsJavaScript;
    private boolean knowsCpp;
    private boolean knowsR;
    private boolean knowsCSS;
    private boolean knowsHTML;
    private boolean knowsKotlin;

    public Applicant(String applicantId, String name, String gender, String email, double cgpa, String location, String desiredJobType, boolean java, boolean python, boolean sql, boolean js, boolean cpp, boolean r, boolean css, boolean html, boolean kotlin, int expectedSalary, String educationLevel) {
        if (!isValidLocation(location)) {
            throw new IllegalArgumentException("Invalid location: " + location);
        }
        if (!isValidJobType(desiredJobType)) {
            throw new IllegalArgumentException("Invalid job type: " + desiredJobType);
        }
        if (!isValidEducationLevel(educationLevel)) {
            throw new IllegalArgumentException("Invalid education level: " + educationLevel);
        }
        this.applicantId = applicantId;
        this.name = name;
        this.gender = formatGender(gender);
        this.email = email;
        this.cgpa = cgpa;
        this.location = location;
        this.desiredJobType = desiredJobType;
        
        this.knowsJava = java;
        this.knowsPython = python;
        this.knowsSQL = sql;
        this.knowsJavaScript = js;
        this.knowsCpp = cpp;
        this.knowsR = r;
        this.knowsCSS = css;
        this.knowsHTML = html;
        this.knowsKotlin = kotlin;
        
        this.expectedSalary = expectedSalary;
        this.educationLevel = educationLevel;
    }

    private String formatGender(String input) {
        String normalized = input.trim().toUpperCase();
        if (normalized.startsWith("M")) return "Male";
        if (normalized.startsWith("F")) return "Female";
        throw new IllegalArgumentException("Invalid gender format");
    }
    
    private boolean isValidLocation(String location) {
        for (String validLoc : LOCATIONS) {
            if (validLoc.equalsIgnoreCase(location)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidJobType(String jobType) {
        for (String validJob : JOBTYPES) {
            if (validJob.equalsIgnoreCase(jobType)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidEducationLevel(String educationLevel) {
        for (String validEducation : EDUCATIONLEVELS) {
            if (validEducation.equalsIgnoreCase(educationLevel)) {
                return true;
            }
        }
        return false;
    }

    public String getApplicantId() { return applicantId; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public double getCgpa() { return cgpa; }
    public String getLocation() { return location; }
    public String getDesiredJobType() { return desiredJobType; }
    public boolean knowsJava() { return knowsJava; }
    public boolean knowsPython() { return knowsPython; }
    public boolean knowsSQL() { return knowsSQL; }
    public boolean knowsJavaScript() { return knowsJavaScript; }
    public boolean knowsCpp() { return knowsCpp; }
    public boolean knowsR() { return knowsR; }
    public boolean knowsCSS() { return knowsCSS; }
    public boolean knowsHTML() { return knowsHTML; }
    public boolean knowsKotlin() { return knowsKotlin; }
    public int getExpectedSalary() { return expectedSalary; }
    public String getEducationLevel() { return educationLevel; }
    
    public void setName(String name) {this.name = name;}
    public void setGender(String gender) {this.gender = formatGender(gender);}
    public void setEmail(String email) {this.email = email;}
    public void setCgpa(double cgpa) {this.cgpa = cgpa;}
    public void setLocation(String location) {
        if (!isValidLocation(location)) {
            throw new IllegalArgumentException("Invalid location: " + location);
        }
        this.location = location;
    }
    public void setDesiredJobType(String desiredJobType) {
        if (!isValidJobType(desiredJobType)) {
            throw new IllegalArgumentException("Invalid job type: " + desiredJobType);
        }
        this.desiredJobType = desiredJobType;
    }
    public void setJava(boolean knowsJava) {this.knowsJava = knowsJava;}
    public void setPython(boolean knowsPython) {this.knowsPython = knowsPython;}
    public void setSql(boolean knowsSQL) {this.knowsSQL = knowsSQL;}
    public void setJavaScript(boolean knowsJavaScript) { this.knowsJavaScript = knowsJavaScript; }
    public void setCpp(boolean knowsCpp) { this.knowsCpp = knowsCpp; }
    public void setR(boolean knowsR) { this.knowsR = knowsR; }
    public void setCSS(boolean knowsCSS) { this.knowsCSS = knowsCSS; }
    public void setHTML(boolean knowsHTML) { this.knowsHTML = knowsHTML; }
    public void setKotlin(boolean knowsKotlin) { this.knowsKotlin = knowsKotlin; }
    public void setExpectedSalary(int expectedSalary) {this.expectedSalary = expectedSalary;}
    public void setEducationLevel(String educationLevel) {
        if (!isValidEducationLevel(educationLevel)) {
            throw new IllegalArgumentException("Invalid education level: " + educationLevel);
        }
        this.educationLevel = educationLevel;
    }
    
    @Override
    public String toString() {
        return String.format(
            "--- Applicant Details ---\n" +
            "Applicant ID       : %s\n" +
            "Name               : %s\n" +
            "Gender             : %s\n" +
            "Email              : %s\n" +
            "CGPA               : %.2f\n" +
            "Location           : %s\n" +
            "Desired Job Type   : %s\n" +
            "Skills             : %s\n" +
            "Expected Salary(RM): %d\n" +
            "Education Level    : %s\n",
            applicantId,
            name,
            gender,
            email,
            cgpa,
            location,
            desiredJobType,
            getSkillsString(),
            expectedSalary,
            educationLevel
        );
    }
    
    public String getSkillsString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (knowsJava) sb.append("Java, ");
        if (knowsPython) sb.append("Python, ");
        if (knowsSQL) sb.append("SQL, ");
        if (knowsJavaScript) sb.append("JavaScript, ");
        if (knowsCpp) sb.append("C++, ");
        if (knowsR) sb.append("R, ");
        if (knowsCSS) sb.append("CSS, ");
        if (knowsHTML) sb.append("HTML, ");
        if (knowsKotlin) sb.append("Kotlin, ");
        return sb.length() > 0 ? sb.substring(0, sb.length()-2) : "";
    }
}