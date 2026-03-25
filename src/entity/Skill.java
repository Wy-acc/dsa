package entity;//author: Yaw Wei Ying

public class Skill {

    private String name;
    private int proficiency;
    private boolean isRequired;

    public Skill(String name, int proficiency, boolean isRequired) {
        this.name = name;
        this.proficiency = proficiency;
        this.isRequired = isRequired;
    }

    public String getName() {
        return name;
    }

    public int getProficiency() {
        return proficiency;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = Math.max(1, Math.min(5, proficiency));
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Skill)) {
            return false;
        }
        Skill other = (Skill) obj;
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return String.format("%s (Proficiency: %d, %s)",
                name,
                proficiency,
                isRequired ? "Required" : "Optional");
    }
}
