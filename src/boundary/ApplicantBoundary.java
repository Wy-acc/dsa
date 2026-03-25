package boundary; //author: Tay Mian Yin

import adt.ListStackQueueInterface;
import control.ApplicantControl;
import entity.Applicant;

public class ApplicantBoundary {
    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);
    private final ApplicantControl control;
    
    public ApplicantBoundary(ApplicantControl control) {
        this.control = control;
    }
    
    public void start(){
        int choice;
        do{
            System.out.println("\n=== Applicant Management Menu ===");
            System.out.println("1. Add Applicant");
            System.out.println("2. Update Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Filter Applicants");
            System.out.println("5. List All Applicants");
            System.out.println("6. Sort Applicants");
            System.out.println("7. Search Applicants");
            System.out.println("8. Applicants Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
           
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayMessage("Invalid input! Please enter a number between 1 and 8.");
                continue;
            }
            
            switch (choice) {
                case 1 -> addApplicant();
                case 2 -> updateApplicant();
                case 3 -> removeApplicant();
                case 4 -> filterApplicants(false);
                case 5 -> listApplicants();
                case 6 -> sortMenu();
                case 7 -> searchMenu();
                case 8 -> generateApplicantReport();
                case 0 -> { return; }
                default -> boundary.ApplicantBoundary.displayMessage("Invalid choice! Please enter 1-8");
            }
        }while (true);
    }

    private void addApplicant() {
        System.out.println("\n--- Add New Applicant ---");
        String applicationId = control.generateApplicationId();
        
        String name;
        while(true) {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            if(!name.isEmpty()) 
                break;
            System.out.println("Name cannot be empty!");
        }
        
        String gender;
        while (true) {
            System.out.print("Gender (M/F): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.matches("M|F")) {
                gender = input;
                break;
            }
            System.out.println("Invalid input! Please enter 'M' or 'F'.");
        }
        
        String email = validateEmail();
        
        double cgpa;
        while (true) {
            System.out.print("CGPA (0.00-4.00): ");
            String input = scanner.nextLine();
            if (input.matches("^[0-4](\\.\\d{1,2})?$")) {
                cgpa = Double.parseDouble(input);
                break;
            }
            System.out.println("Invalid CGPA! Must be 0.00-4.00");
        }
        
        String location = selectLocation();
        String desiredJobType = selectJobType();
        
        System.out.print("\n");
        boolean java = askSkill("Java");
        boolean python = askSkill("Python");
        boolean sql = askSkill("SQL");
        boolean js = askSkill("JavaScript");
        boolean cpp = askSkill("C++");
        boolean r = askSkill("R");
        boolean css = askSkill("CSS");
        boolean html = askSkill("HTML");
        boolean kotlin = askSkill("Kotlin");
        if (!java && !python && !sql && !js && !cpp && !r && !css && !html && !kotlin) {
            displayMessage("At least one skill is required! Add applicant failed!");
            return;
        }
        
        int expectedSalary;
        while (true) {
            System.out.print("Expected Salary (in integer): ");
            String input = scanner.nextLine();
            try {
                expectedSalary = Integer.parseInt(input);
                if (expectedSalary < 0) {
                    System.out.println("Salary cannot be negative!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary! Please enter a valid integer.");
            }
        }
        
        String educationLevel = selectEducationLevel();
        
        Applicant applicant = new Applicant(applicationId, name, gender, email, cgpa, location, desiredJobType, java, python, sql, js, cpp, r, css, html, kotlin, expectedSalary, educationLevel);
        control.addApplicant(applicant);
    }
    
    private static String validateEmail() {
        while (true) {
            System.out.print("Email: ");
            String input = scanner.nextLine();
            if (input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return input;
            }
            System.out.println("Invalid email format! Example: user@example.com");
        }
    }
    
    public static String selectLocation() {
        while (true) {
            System.out.println("\nDesired Locations:");
            for (int i = 0; i < entity.Applicant.LOCATIONS.length; i++) {
                System.out.println((i + 1) + ". " + entity.Applicant.LOCATIONS[i]);
            }
            System.out.print("Select location (1-6): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 6) {
                    return entity.Applicant.LOCATIONS[choice - 1];
                }
                System.out.println("Invalid choice! Please enter 1-6");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number");
            }
        }
    }
    
    private static String selectJobType() {
        while (true) {
            System.out.println("\nDesired Job Types:");
            for (int i = 0; i < entity.Applicant.JOBTYPES.length; i++) {
                System.out.println((i + 1) + ". " + entity.Applicant.JOBTYPES[i]);
            }
            System.out.print("Select job type (1-6): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 6) {
                    return entity.Applicant.JOBTYPES[choice - 1];
                }
                System.out.println("Invalid choice! Please enter 1-6");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number");
            }
        }
    }

    private String selectEducationLevel() {
        while (true) {
            System.out.println("\nEducation Levels:");
            for (int i = 0; i < entity.Applicant.EDUCATIONLEVELS.length; i++) {
                System.out.println((i + 1) + ". " + entity.Applicant.EDUCATIONLEVELS[i]);
            }
            System.out.print("Select education level (1 or 2): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1 || choice == 2) {
                    return entity.Applicant.EDUCATIONLEVELS[choice - 1];
                }
                System.out.println("Invalid choice! Please enter 1 or 2");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number");
            }
        }
    }
    
    private boolean askSkill(String skillName){
        while (true) {
            System.out.printf("Do you know %s? (Y/N): ", skillName);
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input! Please enter 'Y' or 'N'.");
            }
        }
    }
    
    public static void displayApplicantDetails(entity.Applicant a) {
        System.out.println("\n--- Applicant Details ---");
        System.out.println("Applicant ID       : " + a.getApplicantId());
        System.out.println("Name               : " + a.getName());
        System.out.println("Gender             : " + a.getGender());
        System.out.println("Email              : " + a.getEmail());
        System.out.println("CGPA               : " + String.format("%.2f", a.getCgpa()));
        System.out.println("Location           : " + a.getLocation());
        System.out.println("Desired Job Type   : " + a.getDesiredJobType());
        System.out.println("Skills             : " + a.getSkillsString());
        System.out.println("Expected Salary(RM): " + a.getExpectedSalary());
        System.out.println("Education Level    : " + a.getEducationLevel());
    }

    public static void displayMessage(String message) {
        System.out.println("\n" + message);
    }

    private void updateApplicant(){
        System.out.print("Enter applicant ID to update (or type '0' to cancel): ");
        String id = scanner.nextLine();
        
        if (id.equals("0")) {
            System.out.println("Cancelled updating.");
            return;
        }
        
        int position = control.findApplicant(id);
        if (position == -1) {
            return;
        }
        
        int choice = 0;
        do {
            System.out.println("\n--- Update Applicant Menu ---");
            System.out.println("1. Update Name");
            System.out.println("2. Update Gender");
            System.out.println("3. Update Email");
            System.out.println("4. Update CGPA");
            System.out.println("5. Update Location");
            System.out.println("6. Update Job Type");
            System.out.println("7. Update Skills");
            System.out.println("8. Update Expected Salary");
            System.out.println("9. Update Education Level");
            System.out.println("0. Finish Updating");
            System.out.print("Select an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayMessage("Invalid input!");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    while(true) {
                        System.out.print("New name: ");
                        String name = scanner.nextLine().trim();
                        if(!name.isEmpty()) {
                            control.updateApplicant(position, id, "name", name);
                            displayMessage("Applicant information updated successfully.");
                            break;
                        }
                        System.out.println("Name cannot be empty!");
                    }
                }
                case 2 -> {
                    String gender;
                    while (true) {
                        System.out.print("New gender (M/F): ");
                        String input = scanner.nextLine().trim().toUpperCase();
                        if (input.matches("M|F")) {
                            gender = input;
                            control.updateApplicant(position, id, "gender", gender);
                            displayMessage("Applicant information updated successfully.");
                            break;
                        }
                        System.out.println("Invalid input! Please enter M/F.");
                    }
                }
                case 3 -> {
                    String email;
                    while (true) {
                        System.out.print("New email: ");
                        String input = scanner.nextLine();
                        if (input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                            email = input;
                            control.updateApplicant(position, id, "email", email);
                            displayMessage("Applicant information updated successfully.");
                            break;
                        }
                        System.out.println("Invalid email format! Example: user@example.com");
                    }
                }
                case 4 -> {
                    double cgpa;
                    while (true) {
                        System.out.print("New CGPA (0.00-4.00): ");
                        String input = scanner.nextLine();
                        if (input.matches("^[0-4](\\.\\d{1,2})?$")) {
                            cgpa = Double.parseDouble(input);
                            control.updateApplicant(position, id, "cgpa", cgpa);
                            displayMessage("Applicant information updated successfully.");
                            break;
                        }
                        System.out.println("Invalid CGPA! Must be 0.00-4.00");
                    }
                }
                case 5 -> {
                    String location = selectLocation();
                    control.updateApplicant(position, id, "location", location);
                    displayMessage("Applicant information updated successfully.");
                }
                case 6 -> {
                    String desiredJobType = selectJobType();
                    control.updateApplicant(position, id, "desiredJobType", desiredJobType);
                    displayMessage("Applicant information updated successfully.");
                }
                case 7 -> {
                    boolean java = askSkill("Java");
                    boolean python = askSkill("Python");
                    boolean sql = askSkill("SQL");
                    boolean js = askSkill("JavaScript");
                    boolean cpp = askSkill("C++");
                    boolean r = askSkill("R");
                    boolean css = askSkill("CSS");
                    boolean html = askSkill("HTML");
                    boolean kotlin = askSkill("Kotlin");
                    if (!java && !python && !sql && !js && !cpp && !r && !css && !html && !kotlin) {
                        displayMessage("At least one skill is required! Add applicant failed!");
                        return;
                    }
                    control.updateApplicant(position, id, "java", String.valueOf(java));
                    control.updateApplicant(position, id, "python", String.valueOf(python));
                    control.updateApplicant(position, id, "sql", String.valueOf(sql));
                    control.updateApplicant(position, id, "js", String.valueOf(js));
                    control.updateApplicant(position, id, "cpp", String.valueOf(cpp));
                    control.updateApplicant(position, id, "r", String.valueOf(r));
                    control.updateApplicant(position, id, "css", String.valueOf(css));
                    control.updateApplicant(position, id, "html", String.valueOf(html));
                    control.updateApplicant(position, id, "kotlin", String.valueOf(kotlin));
                    displayMessage("Applicant information updated successfully.");
                }
                case 8 -> {
                    int expectedSalary;
                    while (true) {
                        System.out.print("New expected salary: ");
                        String input = scanner.nextLine();
                        try {
                            expectedSalary = Integer.parseInt(input);
                            if (expectedSalary < 0) {
                                System.out.println("Salary cannot be negative!");
                                continue;
                            }
                            control.updateApplicant(position, id, "expectedSalary", expectedSalary);
                            displayMessage("Applicant information updated successfully.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid salary input! Please enter a valid integer.");
                        }
                    }
                }
                case 9 -> {
                    String educationLevel = selectEducationLevel();
                    control.updateApplicant(position, id, "educationLevel", educationLevel);
                    displayMessage("Applicant information updated successfully.");
                }
                case 0 -> displayMessage("Finished updating.");
                default -> displayMessage("Invalid choice.");
            }
        } while (choice != 0);
    }
    
    private void removeApplicant(){
        System.out.print("Enter applicant ID to remove (or type '0' to cancel): ");
        String id = scanner.nextLine();
        
        if (id.equals("0")) {
            System.out.println("Cancelled removing.");
            return;
        }
        
        control.removeApplicant(id);
    }
    
    private void filterApplicants(boolean isReport){
        do {
            System.out.println("\n=== Filter Applicants ===");
            System.out.println("Choose filters (comma-separated):");
            System.out.println("1. Gender");
            System.out.println("2. CGPA Range");
            System.out.println("3. Location");
            System.out.println("4. Job Type");
            System.out.println("5. Skills");
            System.out.println("6. Expected Salary");
            System.out.println("7. Education Level");
            System.out.println("0. Return");
            System.out.print("Select your choices (e.g., '1,3,5'): ");
            String input = scanner.nextLine().trim();
            
            String[] choices = input.split(",");
            ListStackQueueInterface<Applicant> filteredList = null;

            for (String ch : choices) {
                try {
                    int currentChoice = Integer.parseInt(ch.trim());
                    switch (currentChoice) {
                        case 1 -> {
                            String gender;
                            while (true) {
                                System.out.print("Enter gender (M/F): ");
                                input = scanner.nextLine().trim().toUpperCase();

                                if (input.equals("M")) {
                                    gender = "Male";
                                    break;
                                } else if (input.equals("F")) {
                                    gender = "Female";
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter 'M' or 'F'");
                                }
                            }
                            if (filteredList == null) {
                                filteredList = control.filterByGender(gender);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterByGender(gender));
                            }
                        }
                        case 2 -> {
                            System.out.print("Enter minimum CGPA: ");
                            double minCGPA = Double.parseDouble(scanner.nextLine());
                            System.out.print("Enter maximum CGPA: ");
                            double maxCGPA = Double.parseDouble(scanner.nextLine());
                            if (filteredList == null) {
                                filteredList = control.filterByCGPA(minCGPA, maxCGPA);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterByCGPA(minCGPA, maxCGPA));
                            }
                        }
                        case 3 -> {
                            System.out.print("Enter location: ");
                            String location = scanner.nextLine();
                            if (filteredList == null) {
                                filteredList = control.filterByLocation(location);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterByLocation(location));
                            }
                        }
                        case 4 -> {
                            System.out.print("Enter job type: ");
                            String jobType = scanner.nextLine();
                            if (filteredList == null) {
                                filteredList = control.filterByJobType(jobType);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterByJobType(jobType));
                            }
                        }
                        case 5 -> { 
                            System.out.print("Filter by skills (Y/N):\nJava: ");
                            boolean java = getValidSkillInput();
                            System.out.print("Python: ");
                            boolean python = getValidSkillInput();
                            System.out.print("SQL: ");
                            boolean sql = getValidSkillInput();
                            System.out.print("JavaScript: ");
                            boolean js = getValidSkillInput();
                            System.out.print("C++: ");
                            boolean cpp = getValidSkillInput();
                            System.out.print("R: ");
                            boolean r = getValidSkillInput();
                            System.out.print("CSS: ");
                            boolean css = getValidSkillInput();
                            System.out.print("HTML: ");
                            boolean html = getValidSkillInput();
                            System.out.print("Kotlin: ");
                            boolean kotlin = getValidSkillInput();
                            if (filteredList == null) {
                                filteredList = control.filterBySkills(java, python, sql, js, cpp, r, css, html, kotlin);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterBySkills(java, python, sql, js, cpp, r, css, html, kotlin));
                            }
                        }
                        case 6 -> {
                            System.out.print("Enter minimum expected salary: ");
                            int minSalary = Integer.parseInt(scanner.nextLine());
                            System.out.print("Enter maximum expected salary: ");
                            int maxSalary = Integer.parseInt(scanner.nextLine());
                            if (filteredList == null) {
                                filteredList = control.filterByExpectedSalary(minSalary, maxSalary);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterByExpectedSalary(minSalary, maxSalary));
                            }
                        }
                        case 7 -> {
                            String educationLevel = selectEducationLevel();
                            if (filteredList == null) {
                                filteredList = control.filterByEducationLevel(educationLevel);
                            } else {
                                filteredList = control.combineFilters(filteredList, control.filterByEducationLevel(educationLevel));
                            }
                        }
                        case 0 -> {
                            return;
                        }
                        default -> System.out.println("Skipped invalid choice: " + currentChoice);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid choice format: " + ch);
                }
            }

            if (filteredList != null) {
                System.out.println();
                if (isReport) {
                    displayApplicantsAsTable(filteredList);
                } else {
                    displayFilteredApplicants(filteredList);
                }
            } else {
                System.out.println("No filters applied.");
            }

            System.out.print("Apply another filter? (Y/N): ");
        } while (scanner.nextLine().equalsIgnoreCase("Y"));
    }
    
    private boolean getValidSkillInput() {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }
    
    public void displayFilteredApplicants(ListStackQueueInterface<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants found for the given criteria.");
        } else {
            System.out.println("\n=== List of Filtered Applicants ===");
            for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
                Applicant applicant = applicants.getEntry(i);
                System.out.println(applicant);
            }
        }
    }
    
    public void displayApplicantsAsTable(ListStackQueueInterface<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants found for the given criteria.");
        } else {
            System.out.println("                                                                               === Filtered Applicant Report ===                                                                           ");
            System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-25s %-6s %-25s %-4s %-20s %-20s %-56s %-8s %-15s\n", 
                              "ID", "Name", "Gender", "Email", "CGPA", "Location", "Job Type", "Skills", "Salary", "Education");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 1; i <= applicants.size(); i++) {
                Applicant a = applicants.getEntry(i);
                System.out.printf("%-5s %-25s %-6s %-25s %-4.2f %-20s %-20s %-56s %-8s %-15s\n", 
                                  a.getApplicantId(), a.getName(), a.getGender(), a.getEmail(), a.getCgpa(), 
                                  a.getLocation(), a.getDesiredJobType(), a.getSkillsString(), a.getExpectedSalary(), a.getEducationLevel());
            }

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.println("                                                                            === End of Filtered Applicant Report ===                                                                       ");
        }
    }
    
    private void listApplicants(){
        System.out.println("\n--- All Applicants ---");
        control.displayApplicants();
    }
    
    private void sortMenu() {
        int choice = -1;
        ListStackQueueInterface<Applicant> sortedList;
        do{
            sortedList = null;
            
            System.out.println("\n--- Sort Applicants ---");
            System.out.println("1. By Name (Ascending)");
            System.out.println("2. By CGPA (Descending)");
            System.out.println("3. By Location");
            System.out.println("4. By Desired Job Type");
            System.out.println("5. By Expected Salary (Ascending)");
            System.out.println("6. By Education Level");
            System.out.println("0. Return");
            System.out.print("Select an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayMessage("Invalid input!");
                continue;
            }

            switch (choice) {
                case 1 -> sortedList = control.insertionSort("name");
                case 2 -> sortedList = control.insertionSort("cgpa");
                case 3 -> sortedList = control.insertionSort("location");
                case 4 -> sortedList = control.insertionSort("jobtype");
                case 5 -> sortedList = control.insertionSort("salary");
                case 6 -> sortedList = control.insertionSort("education");
                case 0 -> {return;}
                default -> displayMessage("Invalid sort option.");
            }
            if (sortedList != null) {
                displayMessage("Applicants sorted.");
                for (int i = 1; i <= sortedList.size(); i++) {
                    System.out.println(sortedList.getEntry(i));
                }
            }
        } while(choice != 0);
    }
    
    private void searchMenu() {
        int choice = -1;
        boolean found = false;
        String field = "", id = "", name = "", email = "", location = "", jobType = "", level = "";
        do{
            System.out.println("\n--- Search Applicants ---");
            System.out.println("1. By Applicant ID");
            System.out.println("2. By Name");
            System.out.println("3. By Email");
            System.out.println("4. By Location");
            System.out.println("5. By Desired Job Type");
            System.out.println("6. By Education Level");
            System.out.println("0. Return");
            System.out.print("Select an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayMessage("Invalid input!");
                continue;
            }
            
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter applicant ID: ");
                    id = scanner.nextLine();
                    System.out.print("\n");
                    field = "id";
                    found = control.search(field, id);
                }
                case 2 -> {
                    System.out.print("Enter name keyword: ");
                    name = scanner.nextLine();
                    System.out.print("\n");
                    field = "name";
                    found = control.search(field, name);
                }
                case 3 -> {
                    System.out.print("Enter email: ");
                    email = scanner.nextLine();
                    System.out.print("\n");
                    field = "email";
                    found = control.search(field, email);
                }
                case 4 -> {
                    System.out.print("Enter location keyword: ");
                    location = scanner.nextLine();
                    System.out.print("\n");
                    field = "location";
                    found = control.search(field, location);
                }
                case 5 -> {
                    System.out.print("Enter desired job type keyword: ");
                    jobType = scanner.nextLine();
                    System.out.print("\n");
                    field = "jobtype";
                    found = control.search(field, jobType);
                }
                case 6 -> {
                    System.out.print("Enter education level: ");
                    level = scanner.nextLine();
                    System.out.print("\n");
                    field = "education";
                    found = control.search(field, level);
                }
                case 0 -> {return;}
                default -> displayMessage("Invalid search option.");
                
            }
            if (!found && choice >= 1 && choice <= 6) {
                String message = switch (field.toLowerCase()) {
                    case "id" -> "Applicant not found with ID: " + id + "\n";
                    case "name" -> "No applicants found with name containing \"" + name + "\".\n";
                    case "email" -> "No applicant found with email: " + email;
                    case "location" -> "No applicants found with location containing \"" + location + "\".\n";
                    case "jobtype" -> "No applicants found with job type containing \"" + jobType + "\".\n";
                    case "education" -> "No applicants found with education level \"" + level + "\".\n";
                    default -> "Invalid search field.";
                };
                ApplicantBoundary.displayMessage(message);
            }
        } while(choice != 0);
    }
    
    private void generateApplicantReport() {
        int choice = 0;
        do{
            System.out.println("\n------- Report Menu ------");
            System.out.println("1. Total Applicants Report");
            System.out.println("2. Skill Popularity Report");
            System.out.println("3. Filtered Report");
            System.out.println("0. Return");
            System.out.print("Select an option: ");

            try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    displayMessage("Invalid input!");
                    continue;
                }
            
            switch (choice) {
                case 1 -> {
                    generateTotalApplicantsReport();
                    break;
                }
                case 2 -> {
                    generateSkillPopularityReport();
                    break;
                }
                case 3 -> {
                    filterApplicants(true);
                    break;
                }
                case 0 -> {return;}
                default -> displayMessage("Invalid search option.");
            }
        } while(choice != 0);
    }
    
    public void generateTotalApplicantsReport(){
        System.out.println("\n=== Total Applicants Report ===");
        ListStackQueueInterface<Applicant> applicants = control.getApplicantList();

        int totalApplicants = applicants.size();

        if (totalApplicants == 0) {
            displayMessage("No applicants found to generate report.");
            return;
        }
        
        System.out.printf("%-15s : %d\n", "\nTotal Applicants", totalApplicants);

        String[] locations = Applicant.LOCATIONS;
        int[] locationCounts = new int[locations.length];

        String[] jobTypes = Applicant.JOBTYPES;
        int[] jobTypeCounts = new int[jobTypes.length];

        String[] educationLevels = Applicant.EDUCATIONLEVELS;
        int[] educationCounts = new int[educationLevels.length];
        
        for (int i = 1; i <= totalApplicants; i++) {
            Applicant a = applicants.getEntry(i);

            // Location count
            for (int j = 0; j < locations.length; j++) {
                if (a.getLocation().equals(locations[j])) {
                    locationCounts[j]++;
                    break;
                }
            }

            for (int j = 0; j < jobTypes.length; j++) {
                if (a.getDesiredJobType().equals(jobTypes[j])) {
                    jobTypeCounts[j]++;
                    break;
                }
            }

            for (int j = 0; j < educationLevels.length; j++) {
                if (a.getEducationLevel().equalsIgnoreCase(educationLevels[j])) {
                    educationCounts[j]++;
                    break;
                }
            }
        }
        
        System.out.println("\n------ Applicants by Location ------");
        System.out.println("------------------------------");
        System.out.printf("%-20s | %-10s\n", "Location", "Count");
        System.out.println("------------------------------");
        for (int i = 0; i < locations.length; i++) {
            System.out.printf("%-20s | %-10d\n", locations[i], locationCounts[i]);
        }
        System.out.println("------------------------------");

        System.out.println("\n--- Applicants by Desired Job Type ---");
        System.out.println("--------------------------------------");
        System.out.printf("%-25s | %-10s\n", "Job Type", "Count");
        System.out.println("--------------------------------------");
        for (int i = 0; i < jobTypes.length; i++) {
            System.out.printf("%-25s | %-10d\n", jobTypes[i], jobTypeCounts[i]);
        }
        System.out.println("--------------------------------------");

        System.out.println("\n--- Applicants by Education Level ---");
        System.out.println("-------------------------------------");
        System.out.printf("%-25s | %-10s\n", "Education Level", "Count");
        System.out.println("-------------------------------------");
        for (int i = 0; i < educationLevels.length; i++) {
            System.out.printf("%-25s | %-10d\n", educationLevels[i], educationCounts[i]);
        }
        System.out.println("-------------------------------------");
        
        System.out.println("\n=== End of Total Applicants Report ===");
    }
    
    public void generateSkillPopularityReport(){
        ListStackQueueInterface<Applicant> applicants = control.getApplicantList();
        
        String[] skills = {"Java", "Python", "SQL", "JavaScript", "C++", "R", "CSS", "HTML", "Kotlin"};
        int[] skillCounts = new int[skills.length];

        for (int i = 1; i <= applicants.size(); i++) {
            Applicant a = applicants.getEntry(i);
            
            if (a.knowsJava()) skillCounts[0]++;
            if (a.knowsPython()) skillCounts[1]++;
            if (a.knowsSQL()) skillCounts[2]++;
            if (a.knowsJavaScript()) skillCounts[3]++;
            if (a.knowsCpp()) skillCounts[4]++;
            if (a.knowsR()) skillCounts[5]++;
            if (a.knowsCSS()) skillCounts[6]++;
            if (a.knowsHTML()) skillCounts[7]++;
            if (a.knowsKotlin()) skillCounts[8]++;
        }
        
        System.out.println("\n======= Skill Popularity Report =======");
        System.out.println("\n------------------------");
        System.out.printf("%-15s | %-10s\n", "Skill", "Count");
        System.out.println("------------------------");
        for (int i = 0; i < skills.length; i++) {
            System.out.printf("%-15s | %-10d\n", skills[i], skillCounts[i]);
        }
        System.out.println("------------------------");
        
        System.out.println("\n=== End of Skill Popularity Report ====");
    }
}

