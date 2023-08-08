package obj_data;

public class Nurse extends Person {
    //Properties

    private String staffID;
    private String department;
    private String shift;
    private double salary;

    //Constructor
    public Nurse() {

    }

    public Nurse(String staffID, String department, String shift, double salary) {
        this.staffID = staffID;
        this.department = department;
        this.shift = shift;
        this.salary = salary;
    }

    public Nurse(String personId, String personName, int age, String personGender, String personAddress, String personPhone, String staffID, String department, String shift, double salary) {
        super(personId, personName, age, personGender, personAddress, personPhone);
        this.staffID = staffID;
        this.department = department;
        this.shift = shift;
        this.salary = salary;
    }

    //Getter Setter

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    //Methods
    public void inputPerson() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + super.getPersonId() + '\'' +
                ", personName='" + super.getPersonName() + '\'' +
                ", personAge=" + super.getPersonAge() +
                ", personGender='" + super.getPersonGender() + '\'' +
                ", personAddress='" + super.getPersonAddress() + '\'' +
                ", personPhone=" + super.getPersonPhone() + '\'' +
                "Nurse{" +
                "staffID='" + staffID + '\'' +
                ", department='" + department + '\'' +
                ", shift='" + shift + '\'' +
                ", salary=" + salary +
                '}';
    }

    public String displayNurse() {
        return String.format("| %23s | %5s | %10s | %30s | %13s | %25s | %13s | %20f |", super.getPersonName(), super.getPersonAge(), super.getPersonGender(), super.getPersonAddress(), super.getPersonPhone(), getDepartment(), getShift(), getSalary());
    }

}
