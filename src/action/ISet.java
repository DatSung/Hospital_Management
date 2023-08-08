package action;

import obj_data.*;
import sys_data.NurseSet;
import sys_data.PatientSet;

import java.io.File;
import java.util.ArrayList;

public interface ISet {
    public boolean saveFile(File fileName);

    public boolean readFile(File fileName);

    public boolean add(Person person);

    public boolean add(Person person, NurseSet nurse);

    public void printSet();

    public void displaySet();

    public void showAvailable(PatientSet patientSet);

    public ArrayList<Patient> findPatientBeAssigned(String nurseStaffID, PatientSet patientSet);

    public Nurse findById(String staffID);

    public ArrayList<Nurse> findByName(String name);

    public void remove(String staffID, PatientSet patientSet);

    public void updateByName(ArrayList<Nurse> nurse);

    public void updateById(String staffID);

    public boolean checkMaxAssigned(String staffID, PatientSet patientSet);

    public ArrayList<String> getCanAssigned(PatientSet patientSet);

    public void displayByDate(String startDate, String endDate);

    public void sortByPersonID(String type);

    public void sortByName(String type);
    
    public void sortByAdmissiondate(String type);

    public void count();
}
