package dao;//author: Yaw Wei Ying, Tay Mian Yin, Tay Zhuang Yin, Yoo Xin Wei

import adt.*;
import entity.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dummydata {
    
    public static final String[] DAYS_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final String[] TIME_SLOTS = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
    public static final String[] INTERVIEW_STATUS_TYPES = {"Scheduled", "Completed", "Cancelled", "No-Show"};
    
    public ListStackQueueInterface<Applicant> initializeSampleData() {
        ListStackQueueInterface<Applicant> data = new ArrayList<>();
        
        data.add(new Applicant("A001", "Karthik Raj", "M", "karthikraj@example.com", 3.97, "Kuala Lumpur", "Software Engineer", true, false, true, false, true, false, true, false, true, 960, "Degree"));
        data.add(new Applicant("A002", "Daniel Teh", "M", "danielteh@example.com", 3.94, "Kuala Lumpur", "Software Engineer", false, true, true, true, false, true, false, true, false, 780, "Diploma"));
        data.add(new Applicant("A003", "Bryan Lim", "M", "bryanlim@example.com", 3.86, "Kuala Lumpur", "Data Analyst", true, true, true, true, true, false, false, false, false, 900, "Degree"));
        data.add(new Applicant("A004", "Ahmad Zain", "M", "ahmadzain@example.com", 3.51, "Kuala Lumpur", "Data Analyst", false, false, true, false, true, false, false, false, false, 840, "Degree"));
        data.add(new Applicant("A005", "Siti Aishah", "F", "sitiaishah@example.com", 3.71, "Kuala Lumpur", "Data Analyst", true, true, false, true, true, false, true, false, false, 990, "Diploma"));
        data.add(new Applicant("A006", "Teo Mei Mei", "F", "meimei@example.com", 3.99, "Kuala Lumpur", "Backend Developer", false, false, true, false, true, true, false, true, true, 690, "Degree"));
        data.add(new Applicant("A007", "Tan Mei Ling", "F", "meiling@example.com", 3.72, "Kuala Lumpur", "Backend Developer", true, false, true, true, false, true, false, false, true, 1000, "Diploma"));
        data.add(new Applicant("A008", "Linda Tan", "F", "lindatan@example.com", 3.99, "Kuala Lumpur", "Java Developer", true, true, false, false, false, false, true, true, false, 710, "Degree"));
        data.add(new Applicant("A009", "Raj Kumar", "M", "rajkumar@example.com", 3.73, "Kuala Lumpur", "Java Developer", false, true, true, true, false, false, true, false, true, 830, "Diploma"));
        data.add(new Applicant("A010", "David Lee", "M", "davidlee@example.com", 3.94, "Kuala Lumpur", "Java Developer", true, false, false, true, true, true, false, true, false, 800, "Degree"));
    
        data.add(new Applicant("A011", "Aisyah Yusof", "F", "aisyahyusof@example.com", 3.7, "Pulau Penang", "Software Engineer", false, true, false, false, true, false, true, true, true, 750, "Diploma"));
        data.add(new Applicant("A012", "Sofia Ho", "F", "sofiaho@example.com", 3.6, "Pulau Penang", "Data Analyst", true, true, true, false, false, true, false, false, true, 660, "Degree"));
        data.add(new Applicant("A013", "Yasmin Lee", "F", "yasminlee@example.com", 3.51, "Pulau Penang", "Data Analyst", false, false, true, true, true, true, true, false, false, 940, "Degree"));
        data.add(new Applicant("A014", "Lim Wei Jie", "M", "weijie@example.com", 3.99, "Pulau Penang", "Data Analyst", true, false, false, false, true, true, false, true, true, 870, "Diploma"));
        data.add(new Applicant("A015", "Tan Cheng Huat", "M", "chenghuattan@example.com", 3.58, "Pulau Penang", "Backend Developer", true, true, false, true, false, false, true, false, true, 600, "Degree"));
        data.add(new Applicant("A016", "Amina binti Abdullah", "F", "amina@example.com", 3.53, "Pulau Penang", "Backend Developer", false, true, true, false, true, false, false, true, false, 650, "Degree"));
        data.add(new Applicant("A017", "John Lim", "M", "johnlim@example.com", 3.83, "Pulau Penang", "Backend Developer", true, false, true, true, true, false, true, true, false, 980, "Diploma"));
        data.add(new Applicant("A018", "Kevin Tan", "M", "kevintan@example.com", 3.69, "Pulau Penang", "Java Developer", false, false, false, true, false, true, false, false, true, 720, "Degree"));
        data.add(new Applicant("A019", "Anita Raj", "F", "anitaraj@example.com", 3.76, "Pulau Penang", "Java Developer", true, true, true, false, true, true, true, false, false, 890, "Degree"));
        data.add(new Applicant("A020", "Mohamed Faisal", "M", "mohamedfaisal@example.com", 3.95, "Pulau Penang", "Java Developer", false, true, false, true, true, false, false, true, true, 770, "Diploma"));
      
        data.add(new Applicant("A021", "Aidan Lee", "M", "aidanlee@example.com", 3.68, "Kampar", "Software Engineer", true, false, false, false, false, true, true, true, true, 820, "Degree"));
        data.add(new Applicant("A022", "Farhan Ali", "M", "farhanali@example.com", 3.58, "Kampar", "Data Analyst", true, true, false, false, true, false, true, false, false, 970, "Degree"));
        data.add(new Applicant("A023", "Nurul Huda", "F", "nurulhuda@example.com", 3.86, "Kampar", "Data Analyst", false, true, true, true, true, true, false, false, true, 760, "Diploma"));
        data.add(new Applicant("A024", "Lee Xiao Ping", "F", "leexiaoping@example.com", 3.59, "Kampar", "Backend Developer", true, false, true, false, false, true, false, true, true, 880, "Degree"));
        data.add(new Applicant("A025", "Chia Mei Ling", "F", "chiameiling@example.com", 3.92, "Kampar", "Backend Developer", false, false, false, true, true, false, true, true, false, 910, "Degree"));
        data.add(new Applicant("A026", "Nurul Iman", "F", "nuruliman@example.com", 3.53, "Kampar", "Backend Developer", true, true, true, true, false, false, false, false, true, 940, "Diploma"));
        data.add(new Applicant("A027", "Dinesh Kumar", "M", "dineshkumar@example.com", 3.92, "Kampar", "Java Developer", false, true, false, false, true, true, true, true, false, 850, "Degree"));
        data.add(new Applicant("A028", "Adam Idris", "M", "adamidris@example.com", 3.87, "Kampar", "Java Developer", true, false, true, true, false, false, true, false, true, 680, "Diploma"));
        data.add(new Applicant("A029", "Wong Mei Yan", "F", "meiyan@example.com", 3.75, "Kampar", "Java Developer", true, true, false, true, true, true, false, true, false, 700, "Diploma"));
        data.add(new Applicant("A030", "Anisah binti Salleh", "F", "anisah@example.com", 3.88, "Kampar", "Python Developer", false, true, true, false, false, false, false, false, true, 620, "Degree"));
       
        data.add(new Applicant("A031", "Ramesh Pillai", "M", "rameshpillai@example.com", 3.92, "Kuantan", "Data Analyst", true, false, false, false, true, true, true, false, true, 930, "Degree"));
        data.add(new Applicant("A032", "Hana Rahman", "F", "hanarahman@example.com", 3.69, "Kuantan", "Data Analyst", false, false, true, true, false, true, true, true, false, 890, "Diploma"));
        data.add(new Applicant("A033", "Vani Raj", "F", "vaniraj@example.com", 3.66, "Kuantan", "Data Analyst", true, true, true, false, false, false, true, true, true, 960, "Degree"));
        data.add(new Applicant("A034", "Tan Siew Ling", "F", "tansiewling@example.com", 3.93, "Kuantan", "Backend Developer", false, false, false, true, true, true, false, false, true, 610, "Degree"));
        data.add(new Applicant("A035", "Nurul Syafiqah", "F", "nurulSyafiqah@example.com", 3.78, "Kuantan", "Backend Developer", true, true, false, false, false, true, true, false, false, 810, "Diploma"));
        data.add(new Applicant("A036", "Chan Kok Keong", "M", "chankokkeong@example.com", 3.63, "Kuantan", "Backend Developer", false, true, true, true, true, false, false, true, true, 950, "Degree"));
        data.add(new Applicant("A037", "Arif Bin Ahmad", "M", "arif@example.com", 3.62, "Kuantan", "Java Developer", true, false, true, true, false, false, true, false, false, 880, "Diploma"));
        data.add(new Applicant("A038", "Liyana Azman", "F", "liyanaazman@example.com", 3.98, "Kuantan", "Java Developer", false, true, false, false, false, true, true, true, true, 640, "Degree"));
        data.add(new Applicant("A039", "Jason Tan", "M", "jasontan@example.com", 3.83, "Kuantan", "Python Developer", true, true, true, false, true, true, false, true, false, 630, "Degree"));
        data.add(new Applicant("A040", "Arvind Kumar", "M", "arvindkumar@example.com", 3.67, "Kuantan", "Python Developer", false, false, false, true, false, true, true, false, true, 900, "Diploma"));
     
        data.add(new Applicant("A041", "Low Wei Chen", "M", "weichen@example.com", 3.72, "Segamat", "Data Analyst", true, true, false, true, true, false, false, true, true, 850, "Diploma"));
        data.add(new Applicant("A042", "Farisah binti Ismail", "F", "farisah@example.com", 3.71, "Segamat", "Data Analyst", false, true, true, false, false, true, false, true, false, 820, "Degree"));
        data.add(new Applicant("A043", "Azlan Shah", "M", "azlanshah@example.com", 3.96, "Segamat", "Data Analyst", true, false, false, true, true, true, true, false, false, 990, "Diploma"));
        data.add(new Applicant("A044", "Khoo Siew Ying", "F", "siewying@example.com", 3.8, "Segamat", "Backend Developer", true, true, true, false, false, false, false, false, true, 740, "Degree"));
        data.add(new Applicant("A045", "Afiqah binti Zulkifli", "F", "afiqah@example.com", 3.65, "Segamat", "Backend Developer", false, true, false, true, true, true, false, false, true, 930, "Degree"));
        data.add(new Applicant("A046", "Kiran Devi", "F", "kirandevi@example.com", 3.92, "Segamat", "Backend Developer", true, false, true, false, true, false, true, true, false, 790, "Diploma"));
        data.add(new Applicant("A047", "Emily Ooi", "F", "emilyooi@example.com", 3.51, "Segamat", "Java Developer", false, false, false, false, true, true, true, true, false, 710, "Degree"));
        data.add(new Applicant("A048", "Haziq Fahmi", "M", "haziqfahmi@example.com", 3.91, "Segamat", "Java Developer", true, true, false, true, false, true, false, false, true, 870, "Diploma"));
        data.add(new Applicant("A049", "Loo Ee Lin", "F", "eelin@example.com", 3.76, "Segamat", "Java Developer", false, true, true, false, true, false, true, true, false, 800, "Degree"));
        data.add(new Applicant("A050", "Brian Lee", "M", "brianlee@example.com", 3.94, "Segamat", "Python Developer", true, false, false, true, true, false, false, true, true, 970, "Degree"));
      
        data.add(new Applicant("A051", "Zareen binti Mohd Ali", "F", "zareen@example.com", 3.64, "Kota Kinabalu", "Software Engineer", false, false, true, true, false, true, false, false, true, 780, "Diploma"));
        data.add(new Applicant("A052", "Nadira binti Zainal", "F", "nadira@example.com", 3.87, "Kota Kinabalu", "Software Engineer", true, true, true, false, true, false, true, true, false, 660, "Degree"));
        data.add(new Applicant("A053", "Siti Sarah", "F", "sitisarah@example.com", 3.96, "Kota Kinabalu", "Data Analyst", false, true, false, true, false, true, true, false, true, 960, "Degree"));
        data.add(new Applicant("A054", "Michelle Yap", "F", "michelle@example.com", 3.89, "Kota Kinabalu", "Data Analyst", true, false, true, true, false, true, false, true, false, 920, "Degree"));
        data.add(new Applicant("A055", "Yvonne Tan", "F", "yvonnetan@example.com", 3.81, "Kota Kinabalu", "Data Analyst", false, false, false, false, true, true, false, false, true, 650, "Diploma"));
        data.add(new Applicant("A056", "Ravi Shankar", "M", "ravishankar@example.com", 3.87, "Kota Kinabalu", "Backend Developer", false, false, false, true, true, true, false, false, false, 720, "Degree"));
        data.add(new Applicant("A057", "Alex Ng", "M", "alexng@example.com", 3.87, "Kota Kinabalu", "Backend Developer", true, true, true, false, false, false, true, true, true, 840, "Degree"));
        data.add(new Applicant("A058", "Mohd Izzat", "M", "mohdizzat@example.com", 3.82, "Kota Kinabalu", "Backend Developer", true, true, false, false, false, false, true, true, false, 980, "Diploma"));
        data.add(new Applicant("A059", "Calvin Khoo", "M", "calvinkhoo@example.com", 3.65, "Kota Kinabalu", "Java Developer", false, false, true, true, false, true, false, false, true, 890, "Diploma"));
        data.add(new Applicant("A060", "Amanda Lee", "F", "amandalee@example.com", 3.95, "Kota Kinabalu", "Java Developer", true, true, true, true, true, false, false, true, false, 690, "Degree"));
 
        System.out.println("Sample applicants data initialized");
        return data;
    }

    public ListStackQueueInterface<Job> JobData() {
        ListStackQueueInterface<Job> jobs = new CircularArrayQueue<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        jobs.enqueue(new Job("J001", "Data Analyst", "Intel", "Kuala Lumpur", 1050, LocalDate.parse("02/03/2025", formatter), LocalDate.parse("31/03/2025", formatter), "Python/SQL", "Degree", 3, 4, "Open"));
        jobs.enqueue(new Job("J002", "Software Engineer", "Google", "Kuala Lumpur", 950, LocalDate.parse("03/03/2025", formatter), LocalDate.parse("30/03/2025", formatter), "Java/C++", "Degree", 4, 2, "Closed"));
        jobs.enqueue(new Job("J003", "Data Scientist", "Amazon", "Cyberjaya", 900, LocalDate.parse("06/03/2025", formatter), LocalDate.parse("29/03/2025", formatter), "Python/R/SQL", "Diploma", 5, 4, "Filled"));
        jobs.enqueue(new Job("J004", "Web Developer", "Shopee", "Kuala Lumpur", 700, LocalDate.parse("07/03/2025", formatter), LocalDate.parse("02/04/2025", formatter), "HTML/CSS/JavaScript", "Diploma", 5, 4, "Open"));
        jobs.enqueue(new Job("J005", "Business Analyst", "IBM", "Selangor", 850, LocalDate.parse("12/03/2025", formatter), LocalDate.parse("01/04/2025", formatter), "SQL", "Degree", 4, 2, "Closed"));
        jobs.enqueue(new Job("J006", "Software Developer", "Shopee", "Kuala Lumpur", 950, LocalDate.parse("15/03/2025", formatter), LocalDate.parse("05/04/2025", formatter), "Java/C++", "Degree", 3, 4, "Open"));
        jobs.enqueue(new Job("J007", "Mobile App Developer", "Grab", "Johor Bahru", 900, LocalDate.parse("17/03/2025", formatter), LocalDate.parse("12/04/2025", formatter), "Java/Kotlin", "Diploma", 4, 4, "Filled"));
        jobs.enqueue(new Job("J008", "Data Scientist", "DHL", "Penang", 820, LocalDate.parse("18/03/2025", formatter), LocalDate.parse("18/04/2025", formatter), "SQL/Python", "Degree", 6, 4, "Open"));
        jobs.enqueue(new Job("J009", "Software Engineer", "Petronas", "Kuala Lumpur", 870, LocalDate.parse("18/03/2025", formatter), LocalDate.parse("10/04/2025", formatter), "C++/JavaScript", "Diploma", 7, 2, "Closed"));
        jobs.enqueue(new Job("J010", "Cybersecurity Analyst", "Huawei", "Cyberjaya", 850, LocalDate.parse("20/03/2025", formatter), LocalDate.parse("15/04/2025", formatter), "Python", "Degree", 5, 4, "Open"));
        jobs.enqueue(new Job("J011", "Data Scientist", "Samsung", "Selangor", 1000, LocalDate.parse("20/03/2025", formatter), LocalDate.parse("03/04/2025", formatter), "SQL/R/Python, ", "Diploma", 3, 2, "Filled"));
        jobs.enqueue(new Job("J012", "AI Engineer", "NVIDIA", "Kuala Lumpur", 750, LocalDate.parse("21/03/2025", formatter), LocalDate.parse("08/04/2025", formatter), "Python", "Degree", 5, 4, "Open"));
        jobs.enqueue(new Job("J013", "Machine Learning Engineer", "Intel", "Penang", 950, LocalDate.parse("22/03/2025", formatter), LocalDate.parse("06/04/2025", formatter), "Python", "Degree", 4, 2, "Closed"));
        jobs.enqueue(new Job("J014", "Security Analyst", "Microsoft", "Johor Bahru", 900, LocalDate.parse("27/03/2025", formatter), LocalDate.parse("09/04/2025", formatter), "Python/C++/JavaScript", "Degree", 5, 4, "Open"));
        jobs.enqueue(new Job("J015", "Frontend Developer", "TikTok", "Cyberjaya", 850, LocalDate.parse("28/03/2025", formatter), LocalDate.parse("11/04/2025", formatter), "JavaScript", "Diploma", 4, 4, "Open"));
        jobs.enqueue(new Job("J016", "Data Scientist", "Alibaba", "Kuala Lumpur", 900, LocalDate.parse("03/04/2025", formatter), LocalDate.parse("18/04/2025", formatter), "Python/R/SQL", "Degree", 2, 4, "Filled"));
        jobs.enqueue(new Job("J017", "Data Analyst", "Intel", "Penang", 950, LocalDate.parse("07/04/2025", formatter), LocalDate.parse("20/04/2025", formatter), "SQL/Python/Java", "Degree", 5, 2, "Closed"));
        jobs.enqueue(new Job("J018", "Software Engineer", "Apple", "Selangor", 800, LocalDate.parse("09/04/2025", formatter), LocalDate.parse("26/04/2025", formatter), "Python/C++/HTML/CSS", "Diploma", 6, 4, "Open"));
        jobs.enqueue(new Job("J019", "Security Analyst", "Sony", "Kuala Lumpur", 750, LocalDate.parse("10/04/2025", formatter), LocalDate.parse("20/04/2025", formatter), "Python/C++/JavaScript", "Diploma", 5, 2, "Filled"));
        jobs.enqueue(new Job("J020", "IT Support", "Dell", "Cyberjaya", 880, LocalDate.parse("12/04/2025", formatter), LocalDate.parse("22/04/2025", formatter), "Python/SQL", "Diploma", 4, 4, "Open"));

        System.out.println("Sample jobs initialized successfully!");
        return jobs;
    }
    
    public ListStackQueueInterface<InterviewTimetable> initializeTimetable() {
        ListStackQueueInterface<InterviewTimetable> slots = new SortedArrayList<>(
            (slot1, slot2) -> {
                int dayCompare = getDayValue(slot1.getDay()) - getDayValue(slot2.getDay());
                if (dayCompare != 0) {
                    return dayCompare;
                }
                return slot1.getTime().compareTo(slot2.getTime());
            }
        );
        
        int slotIdCounter = 1;
        for (String day : DAYS_OF_WEEK) {
            for (String time : TIME_SLOTS) {
                String slotId = String.format("TS%03d", slotIdCounter++);
                slots.add(new InterviewTimetable(slotId, day, time, true));
            }
        }
        
        System.out.println("Interview time slots initialized successfully!");
        return slots;
    }
    
    private int getDayValue(String day) {
        switch (day) {
            case "Monday": return 1;
            case "Tuesday": return 2;
            case "Wednesday": return 3;
            case "Thursday": return 4;
            case "Friday": return 5;
            default: return 0;
        }
    }
    
    public String validateInterviewStatus(String status) {
        for (String validStatus : INTERVIEW_STATUS_TYPES) {
            if (validStatus.equalsIgnoreCase(status)) {
                return validStatus;
            }
        }
        throw new IllegalArgumentException("Invalid interview status: " + status);
    }
    
    public ListStackQueueInterface<Interview> initializeInterviewData(
            ListStackQueueInterface<Applicant> applicants,
            ListStackQueueInterface<Job> jobs) {
        
        ListStackQueueInterface<Interview> interviews = new SortedArrayList<>(
            (interview1, interview2) -> interview1.getScheduleTime().compareTo(interview2.getScheduleTime())
        );
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        Applicant[] sampleApplicants = new Applicant[5];
        for (int i = 0; i < 5; i++) {
            sampleApplicants[i] = applicants.getEntry(i + 1);
        }
        
        Job[] sampleJobs = new Job[3];
        for (int i = 0; i < 3; i++) {
            sampleJobs[i] = jobs.getEntry(i + 1);
        }
        
        interviews.add(new Interview(
            "I001",
            sampleApplicants[0],
            sampleJobs[0],
            LocalDateTime.parse("15/04/2025 09:00", formatter),
            "Completed",
            5,
            "Excellent.",
            true
        ));
        
        interviews.add(new Interview(
            "I002",
            sampleApplicants[1],
            sampleJobs[0],
            LocalDateTime.parse("15/04/2025 11:00", formatter),
            "Completed",
            4,
            "Good.",
            true
        ));
        
        interviews.add(new Interview(
            "I003",
            sampleApplicants[2],
            sampleJobs[1],
            LocalDateTime.parse("16/04/2025 10:00", formatter),
            "Completed",
            3,
            "Average",
            false
        ));
        
        interviews.add(new Interview(
            "I004",
            sampleApplicants[3],
            sampleJobs[1],
            LocalDateTime.parse("21/04/2025 14:00", formatter),
            "Scheduled",
            0,
            "",
            false
        ));
        
        interviews.add(new Interview(
            "I005",
            sampleApplicants[4],
            sampleJobs[2],
            LocalDateTime.parse("22/04/2025 09:00", formatter),
            "Scheduled",
            0,
            "",
            false
        ));
        
        if (applicants.size() >= 7) {
            interviews.add(new Interview(
                "I006",
                applicants.getEntry(6),
                sampleJobs[2],
                LocalDateTime.parse("18/04/2025 15:00", formatter),
                "Cancelled",
                0,
                "Candidate withdrew application",
                false
            ));
            
            interviews.add(new Interview(
                "I007",
                applicants.getEntry(7),
                sampleJobs[0],
                LocalDateTime.parse("17/04/2025 16:00", formatter),
                "Absent",
                0,
                "Candidate did not attend without notice",
                false
            ));
        }
        
        System.out.println("Sample interview data initialized successfully!");
        return interviews;
    }
}