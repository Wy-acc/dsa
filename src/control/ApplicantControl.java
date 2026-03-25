package control; //author: Tay Mian Yin

import entity.Applicant;
import adt.ArrayList;
import adt.ListStackQueueInterface;
import boundary.ApplicantBoundary;

public class ApplicantControl {
    private ListStackQueueInterface<Applicant> applicants = new ArrayList();

    public ApplicantControl(ListStackQueueInterface<Applicant> applicants) {
        this.applicants = applicants;
    }

    public void addApplicant(Applicant a) {
        if (applicants.add(a)) {
            ApplicantBoundary.displayMessage("Applicant added successfully!");
            ApplicantBoundary.displayApplicantDetails(a);
        } else {
            ApplicantBoundary.displayMessage("Failed to add applicant.");
        }
    }
    
    public String generateApplicationId(){
        int maxId = 0;
        for (int i = 1; i <= applicants.size(); i++) {
            String idStr = applicants.getEntry(i).getApplicantId().substring(1);
            maxId = Math.max(maxId, Integer.parseInt(idStr));
        }
        String newId = "A" + String.format("%03d", maxId + 1);
        return newId;
    }
    
    public int findApplicant(String applicantId) {
        for (int i = 1; i <= applicants.size(); i++) {
            Applicant applicant = applicants.getEntry(i);

            if (applicant != null && applicant.getApplicantId().equals(applicantId)) {
                return i;
            }
        }

        ApplicantBoundary.displayMessage("Applicant with ID " + applicantId + " not found.");
        return -1;
    }
    
    public boolean updateApplicant(int position, String id, String field, Object value){
        Applicant applicant = applicants.getEntry(position);
        if (applicant == null) {
            return false; 
        }
        
        switch (field) {
            case "name" -> applicant.setName((String)value);
            case "gender" -> applicant.setGender((String)value);
            case "email" -> applicant.setEmail((String)value);
            case "cgpa" -> applicant.setCgpa((Double)value);
            case "location" -> applicant.setLocation((String)value);
            case "desiredJobType" -> applicant.setDesiredJobType((String)value);
            case "java" -> applicant.setJava(Boolean.parseBoolean((String)value));
            case "python" -> applicant.setPython(Boolean.parseBoolean((String)value));
            case "sql" -> applicant.setSql(Boolean.parseBoolean((String)value));
            case "js" -> applicant.setJavaScript(Boolean.parseBoolean((String)value));
            case "cpp" -> applicant.setCpp(Boolean.parseBoolean((String)value));
            case "r" -> applicant.setR(Boolean.parseBoolean((String)value));
            case "css" -> applicant.setCSS(Boolean.parseBoolean((String)value));
            case "html" -> applicant.setHTML(Boolean.parseBoolean((String)value));
            case "kotlin" -> applicant.setKotlin(Boolean.parseBoolean((String)value));
            case "expectedSalary" -> applicant.setExpectedSalary((Integer)value);
            case "educationLevel" -> applicant.setEducationLevel((String)value);
            default -> {
                return false;
            }
        }
        return applicants.replace(position, applicant);
    }
    
    public boolean removeApplicant(String applicantId){
        for (int i = 1; i <= applicants.size(); i++) {
            Applicant applicant = applicants.getEntry(i);

            if (applicant != null && applicant.getApplicantId().equals(applicantId)) {
                applicants.remove(i);
                ApplicantBoundary.displayMessage("Applicant with ID " + applicantId + " removed successfully.");
                return true;
            }
        }

        ApplicantBoundary.displayMessage("Applicant with ID " + applicantId + " not found.");
        return false;
    }
    
    public void displayApplicants() {
        for (int i = 1; i <= applicants.size(); i++) {
            Applicant current = applicants.getEntry(i);
            System.out.println(current);
        }
    }
    
    public ListStackQueueInterface<Applicant> filterByGender(String gender) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant.getGender().equalsIgnoreCase(gender)) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }
    
    public ListStackQueueInterface<Applicant> filterByCGPA(double min, double max) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            double cgpa = applicant.getCgpa();
            if (cgpa >= min && cgpa <= max) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }
    
    public ListStackQueueInterface<Applicant> filterByLocation(String location) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant.getLocation().equalsIgnoreCase(location)) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }

    public ListStackQueueInterface<Applicant> filterByJobType(String jobType) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant.getDesiredJobType().equalsIgnoreCase(jobType)) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }

    public ListStackQueueInterface<Applicant> filterBySkills(boolean java, boolean python, boolean sql, boolean js, boolean cpp, boolean r, boolean css, boolean html, boolean kotlin) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            boolean matchesJava = (java == applicant.knowsJava());
            boolean matchesPython = (python == applicant.knowsPython());
            boolean matchesSQL = (sql == applicant.knowsSQL());
            boolean matchesJavaScript = (js == applicant.knowsJavaScript());
            boolean matchesCpp = (cpp == applicant.knowsCpp());
            boolean matchesR = (r == applicant.knowsR());
            boolean matchesCSS = (css == applicant.knowsCSS());
            boolean matchesHTML = (html == applicant.knowsHTML());
            boolean matchesKotlin = (kotlin == applicant.knowsKotlin());

            if (matchesJava && matchesPython && matchesSQL && matchesJavaScript && matchesCpp && matchesR && matchesCSS && matchesHTML && matchesKotlin) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }
    
    public ListStackQueueInterface<Applicant> filterByExpectedSalary(int minSalary, int maxSalary) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant.getExpectedSalary() >= minSalary && applicant.getExpectedSalary() <= maxSalary) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }

    public ListStackQueueInterface<Applicant> filterByEducationLevel(String educationLevel) {
        ListStackQueueInterface<Applicant> filteredList = new ArrayList<>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant.getEducationLevel().equalsIgnoreCase(educationLevel)) {
                filteredList.add(applicant);
            }
        }
        return filteredList;
    }
    
    public ListStackQueueInterface<Applicant> combineFilters(
        ListStackQueueInterface<Applicant> listA,
        ListStackQueueInterface<Applicant> listB) {
        ListStackQueueInterface<Applicant> result = new ArrayList<>();
        for (int i = 1; i <= listA.getNumberOfEntries(); i++) {
            Applicant a = listA.getEntry(i);
            if (listB.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }
    
    private ListStackQueueInterface<Applicant> applicantList() {
        ListStackQueueInterface<Applicant> copy = new ArrayList<>();
        for (int i = 1; i <= applicants.size(); i++) {
            copy.add(applicants.getEntry(i));
        }
        return copy;
    }
    
    public ListStackQueueInterface<Applicant> insertionSort(String sortType) {
        ListStackQueueInterface<Applicant> sortedList = applicantList();

        for (int i = 2; i <= sortedList.size(); i++) {
            Applicant key = sortedList.getEntry(i);
            int j = i - 1;

            while (j > 0 && compare(sortedList.getEntry(j), key, sortType) > 0) {
                sortedList.replace(j + 1, sortedList.getEntry(j));
                j--;
            }

            sortedList.replace(j + 1, key);
        }

        return sortedList;
    }
    
    private int compare(Applicant a1, Applicant a2, String sortType) {
        switch (sortType.toLowerCase()) {
            case "name":
                return a1.getName().compareToIgnoreCase(a2.getName());
            case "cgpa":
                return Double.compare(a2.getCgpa(), a1.getCgpa());
            case "location":
                return a1.getLocation().compareToIgnoreCase(a2.getLocation());
            case "salary":
                return Integer.compare(a1.getExpectedSalary(), a2.getExpectedSalary());
            case "education":
                return a1.getEducationLevel().compareToIgnoreCase(a2.getEducationLevel());
            case "jobtype":
                return a1.getDesiredJobType().compareToIgnoreCase(a2.getDesiredJobType());
            default:
                return 0;
        }
    }
    
    public boolean search(String field, String keyword) {
        boolean found = false;
        keyword = keyword.toLowerCase();

        for (int i = 1; i <= applicants.size(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant == null) continue;

            boolean match = switch (field.toLowerCase()) {
                case "id" -> applicant.getApplicantId().equalsIgnoreCase(keyword);
                case "name" -> applicant.getName().toLowerCase().contains(keyword);
                case "email" -> applicant.getEmail().equalsIgnoreCase(keyword);
                case "location" -> applicant.getLocation().toLowerCase().contains(keyword);
                case "jobtype" -> applicant.getDesiredJobType().toLowerCase().contains(keyword);
                case "education" -> applicant.getEducationLevel().equalsIgnoreCase(keyword);
                default -> false;
            };

            if (match) {
                System.out.println(applicant);
                found = true;
                if (field.equalsIgnoreCase("id") || field.equalsIgnoreCase("email")) break;
            }
        }
        return found;
    }

    public ListStackQueueInterface<Applicant> getApplicantList() {
        return applicants;
    }

}            
