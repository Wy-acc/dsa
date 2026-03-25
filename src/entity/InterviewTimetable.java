package entity; // author: Yoo Xin Wei

public class InterviewTimetable {
    private String slotId;
    private String day;
    private String time;
    private boolean isAvailable;
    
    public InterviewTimetable(String slotId, String day, String time, boolean isAvailable) {
        this.slotId = slotId;
        this.day = day;
        this.time = time;
        this.isAvailable = isAvailable;
    }
    
    public String getSlotId() {
        return slotId;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    @Override
    public String toString() {
        return String.format("%s at %s (%s)", day, time, isAvailable ? "Available" : "Booked");
    }
}