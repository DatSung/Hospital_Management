package obj_data;

import java.util.*;

public class Patient extends Person {

    //Properties
    private String diagnosis;
    private String admissionDate;
    private String dischargeDate;
    private ArrayList<String> nurseAssigned = new ArrayList<String>();

    //Constructor
    public Patient() {

    }

    public Patient(String diagnosis, String admissionDate, String dischargeDate, ArrayList<String> nurseAssigned) {
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.nurseAssigned = nurseAssigned;
    }

    public Patient(String personId, String personName, int personAge, String personGender, String personAddress, String personPhone, String diagnosis, String admissionDate, String dischargeDate, ArrayList<String> nurseAssigned) {
        super(personId, personName, personAge, personGender, personAddress, personPhone);
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.nurseAssigned = nurseAssigned;
    }

    //Getter Setter

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public ArrayList<String> getNurseAssigned() {
        return nurseAssigned;
    }

    public void setNurseAssigned(ArrayList<String> nurseAssigned) {
        this.nurseAssigned = nurseAssigned;
    }

    //Methods

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + super.getPersonId() + '\'' +
                ", personName='" + super.getPersonName() + '\'' +
                ", personAge=" + super.getPersonAge() +
                ", personGender='" + super.getPersonGender() + '\'' +
                ", personAddress='" + super.getPersonAddress() + '\'' +
                ", personPhone=" + super.getPersonPhone() + '\'' +
                "Patient{" +
                "diagnosis='" + diagnosis + '\'' +
                ", admissionDate='" + admissionDate + '\'' +
                ", dischargeDate='" + dischargeDate + '\'' +
                ", NurseAssigned=" + nurseAssigned +
                '}';
    }

    public String displayPatient() {
        return String.format("| %11s | %18s | %23s | %13s | %13s |", super.getPersonId(), getAdmissionDate(), super.getPersonName(), super.getPersonPhone(), getDiagnosis());
    }
}
