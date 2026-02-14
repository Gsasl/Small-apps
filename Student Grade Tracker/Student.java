/**
 * Student class - Blueprint for student objects
 * Stores student information: name and grade
 */
public class Student {
    private String name;
    private double grade;
    
    private static final double MIN_GRADE = 0.0;
    private static final double MAX_GRADE = 100.0;
    
    /**
     * Constructor to initialize a Student with name and grade
     * @param name the student's name
     * @param grade the student's grade
     */
    public Student(String name, double grade) {
        this.name = name;
        setGrade(grade);
    }
    
    /**
     * Get the student's name
     * @return the student's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the student's grade
     * @return the student's grade
     */
    public double getGrade() {
        return grade;
    }
    
    /**
     * Set the student's grade with validation
     * @param grade the grade to set (0-100)
     */
    public void setGrade(double grade) {
        if (isValidGrade(grade)) {
            this.grade = grade;
        } else {
            throw new IllegalArgumentException(
                String.format("Grade must be between %.0f and %.0f", MIN_GRADE, MAX_GRADE)
            );
        }
    }
    
    /**
     * Validate if a grade is within acceptable range
     * @param grade the grade to validate
     * @return true if valid, false otherwise
     */
    private static boolean isValidGrade(double grade) {
        return grade >= MIN_GRADE && grade <= MAX_GRADE;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %.2f", name, grade);
    }
}
