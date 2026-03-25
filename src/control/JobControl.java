package control;//author: Tay Zhuang Yin

import entity.Job;
import adt.CircularArrayQueue;
import boundary.JobBoundary;
import adt.ListStackQueueInterface;
import boundary.MainBoundary;
import java.util.Iterator;

public class JobControl {
    private final MainBoundary mainBoundary;
    private ListStackQueueInterface<Job> jobs = new CircularArrayQueue<>();
    
    public JobControl(ListStackQueueInterface<Job> jobs) {
        this.jobs = jobs;
        mainBoundary = new MainBoundary();
    }

    public void addNewJob(Job j) {
        jobs.enqueue(j);
        JobBoundary.displayMessage("Job added successfully. ID: " + j.getJobID());
    }
    
    public void updateJob(Job jobToUpdate) {
        int size = jobs.size();
        boolean found = false;

        for (int i = 0; i < size; i++) {
            Job currentJob = jobs.dequeue();

            if (currentJob.getJobID().equals(jobToUpdate.getJobID())) {
                // Update only changed fields
                if (jobToUpdate.getTitle() != null) currentJob.setTitle(jobToUpdate.getTitle());
                if (jobToUpdate.getCompany() != null) currentJob.setCompany(jobToUpdate.getCompany());
                if (jobToUpdate.getLocation() != null) currentJob.setLocation(jobToUpdate.getLocation());
                if (jobToUpdate.getSalary() != -1) currentJob.setSalary(jobToUpdate.getSalary());
                if (jobToUpdate.getPostedDate() != null) currentJob.setPostedDate(jobToUpdate.getPostedDate());
                if (jobToUpdate.getEndDate() != null) currentJob.setEndDate(jobToUpdate.getEndDate());
                if (jobToUpdate.getSkillRequirements() != null) currentJob.setSkillRequirements(jobToUpdate.getSkillRequirements());
                if (jobToUpdate.getEducationLevel() != null) currentJob.setEducationLevel(jobToUpdate.getEducationLevel());
                if (jobToUpdate.getVacancies() != -1) currentJob.setVacancies(jobToUpdate.getVacancies());
                if (jobToUpdate.getDuration() != -1) currentJob.setDuration(jobToUpdate.getDuration());
                if (jobToUpdate.getInternshipStatus() != null) currentJob.setInternshipStatus(jobToUpdate.getInternshipStatus());

                found = true;
                JobBoundary.displayMessage("Job updated successfully! ID: " + currentJob.getJobID());
            }

            jobs.enqueue(currentJob); 
        }

        if (!found) {
            JobBoundary.displayMessage("Job not found. Update failed.");
        }
    }
    
    public boolean removeJobById(String jobId) {
        return jobs.removeById(jobId);
    }
    
    public ListStackQueueInterface<Job> filterJobs(String title, String company, String location, int minSalary, String skill, String level, int duration, String availability) {
        ListStackQueueInterface<Job> filtered = new CircularArrayQueue<>();
        ListStackQueueInterface<Job> jobsCopy = new CircularArrayQueue<>();
        ListStackQueueInterface<Job> tempJobs = new CircularArrayQueue<>();
        while (!jobs.isEmpty()) {
            Job job = jobs.dequeue();
            jobsCopy.enqueue(job);  
            tempJobs.enqueue(job);
        }

        while (!tempJobs.isEmpty()) {
            jobs.enqueue(tempJobs.dequeue());
        }
    
        String[] companies = company.isEmpty() ? new String[0] : company.split(",");
        String[] locations = location.isEmpty() ? new String[0] : location.split(",");
        String[] skills = skill.isEmpty() ? new String[0] : skill.split(",");
        
        while (!jobsCopy.isEmpty()) {
            Job job = jobsCopy.dequeue(); // remove from original
            boolean match = false;
            
            if (!title.isEmpty() && job.getTitle().equalsIgnoreCase(title)) {
                match = true;
            }

            if (companies.length > 0 && matchesAny(job.getCompany(), companies)) {
                match = true;
            }

            if (locations.length > 0 && matchesAny(job.getLocation(), locations)) {
                match = true;
            }

            if (minSalary > 0 && job.getSalary() >= minSalary) {
                match = true;
            }

            if (skills.length > 0 && containsAny(job.getSkillRequirements(), skills)) {
                match = true;
            }

            if (!level.isEmpty() && job.getEducationLevel().equalsIgnoreCase(level)) {
                match = true;
            }

            if (duration > 0 && job.getDuration() == duration) {
                match = true;
            }

            if (!availability.isEmpty() && job.getInternshipStatus().equalsIgnoreCase(availability)) {
                match = true;
            }

            if (match) {
                filtered.enqueue(job);
            }
        }
        return filtered;
    }
    
    private boolean matchesAny(String value, String[] options) {
        if (options.length == 0) return true;
        for (String option : options) {
            if (value != null && value.trim().equalsIgnoreCase(option.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAny(String source, String[] keywords) {
        if (keywords.length == 0) return true; 
        if (source != null) {
            source = source.trim().toLowerCase(); 
        }
        for (String keyword : keywords) {
            if (source != null && source.contains(keyword.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    public void listAllJobs() {
        JobBoundary boundary = new JobBoundary(this, mainBoundary);
        
        if (jobs.isEmpty()) {
            System.out.println("No jobs available.");
            return;
        }

        int size = jobs.size();
        System.out.println("Total jobs: " + size);

        for (int i = 0; i < size; i++) {
            Job job = jobs.getEntry(i); 
            boundary.displayJobDetails(job);
        }
    }
    
    public ListStackQueueInterface<Job> getJobsQueue() {
        return jobs;
    }
    
    private ListStackQueueInterface<Job> copyJobsQueue() {
        ListStackQueueInterface<Job> copy = new CircularArrayQueue<>();
        ListStackQueueInterface<Job> temp = new CircularArrayQueue<>();

        while (!jobs.isEmpty()) {
            Job job = jobs.dequeue();
            copy.enqueue(job);
            temp.enqueue(job);  
        }

        while (!temp.isEmpty()) {
            jobs.enqueue(temp.dequeue()); 
        }

        return copy;
    }
    
    public ListStackQueueInterface<Job> insertionSort(String attribute) {
        ListStackQueueInterface<Job> source = copyJobsQueue();
        ListStackQueueInterface<Job> sortedQueue = new CircularArrayQueue<>();

        while (!source.isEmpty()) {
            Job current = source.dequeue();
            ListStackQueueInterface<Job> tempQueue = new CircularArrayQueue<>();
            boolean inserted = false;

            while (!sortedQueue.isEmpty()) {
                Job temp = sortedQueue.dequeue();

                if (!inserted && compare(current, temp, attribute) < 0) {
                    tempQueue.enqueue(current);
                    inserted = true;
                }
                tempQueue.enqueue(temp);
            }

            if (!inserted) {
                tempQueue.enqueue(current);
            }

            while (!tempQueue.isEmpty()) {
                sortedQueue.enqueue(tempQueue.dequeue());
            }
        }
        return sortedQueue;
    }

    private int compare(Job a, Job b, String attribute) {
        return switch (attribute.toLowerCase()) {
            case "title" -> a.getTitle().compareToIgnoreCase(b.getTitle());
            case "company" -> a.getCompany().compareToIgnoreCase(b.getCompany());
            case "location" -> a.getLocation().compareToIgnoreCase(b.getLocation());
            case "salary" -> Double.compare(a.getSalary(), b.getSalary());
            case "educationlevel" -> a.getEducationLevel().compareToIgnoreCase(b.getEducationLevel());
            case "internshipstatus" -> a.getInternshipStatus().compareToIgnoreCase(b.getInternshipStatus());
            default -> 0;
        };
    }
    
    public void displayJobs(ListStackQueueInterface<Job> queue) {
        System.out.println("Total jobs displayed: " + queue.size());

        ListStackQueueInterface<Job> copyQueue = new CircularArrayQueue<>();
        JobBoundary boundary = new JobBoundary(this, mainBoundary);

        while (!queue.isEmpty()) {
            Job job = queue.dequeue();
            copyQueue.enqueue(job);
        }

        while (!copyQueue.isEmpty()) {
            Job job = copyQueue.dequeue();
            boundary.displayJobDetails(job);
        }
    }
    
    public ListStackQueueInterface<Job> linearSearchByKeyword(String keyword) {
        ListStackQueueInterface<Job> resultQueue = new CircularArrayQueue<>();  // Queue to store matching jobs
        int size = jobs.size();

        for (int i = 0; i < size; i++) {
            Job job = jobs.dequeue();       
            jobs.enqueue(job);           

            if (job.getTitle().toLowerCase().contains(keyword.toLowerCase()) || 
                job.getCompany().toLowerCase().contains(keyword.toLowerCase()) || 
                job.getLocation().toLowerCase().contains(keyword.toLowerCase()) || 
                job.getEducationLevel().toLowerCase().contains(keyword.toLowerCase()) ||
                job.getInternshipStatus().toLowerCase().contains(keyword.toLowerCase()) ||
                job.getSkillRequirements().toLowerCase().contains(keyword.toLowerCase())) {
                resultQueue.enqueue(job);   
            }
        }
        return resultQueue;
    }
    
    public Job getJobById(String jobId) {
        Iterator<Job> iterator = jobs.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getJobID().equals(jobId)) {
                return job;
            }
        }
        return null;
    }

    public String generateJobId() {
        if (jobs.isEmpty()) return "J001";

        int maxId = 0;
        int size = jobs.size();

        for (int i = 0; i < size; i++) {
            Job job = (Job) jobs.getEntry(i);

            String idStr = job.getJobID().substring(1); 
            try {
                int id = Integer.parseInt(idStr);  
                if (id > maxId) {
                    maxId = id; 
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid job ID format: " + job.getJobID());
            }
        }
        return String.format("J%03d", maxId + 1);
    }
}