package sys_data;

import action.ISet;
import obj_data.Nurse;
import obj_data.Patient;
import obj_data.Person;
import ult.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NurseSet extends HashMap<String, Nurse> implements ISet, Serializable {
    private static final long serialVersionUID = -6140363508500781797L;

    public NurseSet() {
        super();
    }


    public void count() {
        System.out.println(this.size());
    }

    public boolean saveFile(File fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public boolean readFile(File fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            fis = new FileInputStream(fileName);

            if (fis.available() > 0) {
                ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof HashMap<?, ?>) {
                    HashMap<?, ?> map = (HashMap<?, ?>) obj;
                    map.forEach((key, value) -> {
                        if (key instanceof String && value instanceof Nurse) {
                            put((String) key, (Nurse) value);
                        }
                    });
                }
                return true;
            } else {
                return false;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean add(Person person) {

        if (person instanceof Nurse) {
            Nurse nurse = (Nurse) person;
            Input input = new Input();


            if (nurse.getStaffID() == null) {
                while (true) {
                    String staffID;
                    try {
                        while (true) {
                            try {
                                staffID = input.getString("Enter staffID [N****]:", "Do not enter empty").toUpperCase().trim();
                                if (!input.checkStaffID(staffID)) {
                                    throw new Exception();
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid staffID!");
                            }
                        }
                        for (Map.Entry<String, Nurse> entry : this.entrySet()) {
                            String key = entry.getKey();
                            Nurse nurseTmp = entry.getValue();
                            if (nurseTmp.getStaffID().equals(staffID)) {
                                throw new Exception();
                            }
                        }
                        nurse.setStaffID(staffID);
                        break;
                    } catch (Exception e) {
                        System.out.println("Duplicate StaffID, please Enter again!");
                    }
                }
            }

            while (true) {
                try {
                    String personName = input.getString("Enter person name:", "Do not enter empty!").trim();
                    if (personName.matches(".*[0-9].*")) {
                        throw new Exception();
                    }
                    nurse.setPersonName(personName);
                    break;
                } catch (Exception e) {
                    System.out.println("The name do not contains a number!");
                }
            }

            while (true) {
                try {
                    int age = input.getInt("Enter person age", "Do not enter none number!");
                    if (!input.checkAge(age, "Nurse")) {
                        throw new Exception();
                    }
                    nurse.setPersonAge(age);
                    break;
                } catch (Exception e) {
                    System.out.println("Nurse's age must be under 19 year old!");
                }
            }


            while (true) {
                try {
                    String gender = input.getString("Enter person gender [Male/Female/Other]:", "Do not enter empty!").trim();
                    if (!input.checkGender(gender)) {
                        throw new Exception();
                    }
                    nurse.setPersonGender(gender);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid option!");
                }
            }


            nurse.setPersonAddress(input.getString("Enter person address:", "Do not enter empty!").trim());


            while (true) {
                try {
                    String phone = input.getString("Enter phone:", "Do not enter empty").trim();
                    if (!phone.matches("^(\\+?\\d{1,3}[- ]?)?\\d{10}$")) {
                        throw new Exception();
                    }
                    nurse.setPersonPhone(phone);
                    break;
                } catch (Exception e) {
                    System.out.println("Phone number invalid!");
                }
            }


            while (true) {
                try {
                    String department = input.getString("Enter Department:", "Do not enter empty!").trim();
                    if (!department.matches("^\\s*.{3,50}\\s*$")) {
                        throw new Exception();
                    }
                    nurse.setDepartment(department);
                    break;
                } catch (Exception e) {
                    System.out.println("Deparment must be from 3 to 50 characters!");
                }
            }


            nurse.setShift(input.getString("Enter shift:", "Do not enter empty!"));


            while (true) {
                try {
                    Double salary = input.getDouble("Enter salary:", "Enter a positive number!");
                    if (!input.checkSalary(salary)) {
                        throw new Exception();
                    }
                    nurse.setSalary(salary);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid salary!");
                }
            }

            if (this.put(nurse.getStaffID(), nurse) == null) {
                return true;
            }
        }
        return false;
    }

    public boolean add(Person person, NurseSet nurse) {
        return false;
    }


    public void printSet() {
        for (Map.Entry<String, Nurse> entry : this.entrySet()) {
            String key = entry.getKey();
            Nurse nurse = entry.getValue();
            System.out.println(key + ": " + nurse.toString());
        }
    }

    public void displaySet() {
        if (!this.isEmpty()) {
            for (Map.Entry<String, Nurse> entry : this.entrySet()) {
                String key = entry.getKey();
                Nurse nurse = entry.getValue();
                System.out.println(key + ": " + nurse.displayNurse());
            }
        } else {
            System.out.println("There are no nurse to display!");
        }
    }

    public void showAvailable(PatientSet patientSet) {
        for (Map.Entry<String, Nurse> entry : this.entrySet()) {
            String key = entry.getKey();
            Nurse nurse = entry.getValue();
            if (!checkMaxAssigned(key, patientSet)) {

            } else {
                System.out.println(key + ": " + nurse.displayNurse());
            }

        }
    }

    public ArrayList<Patient> findPatientBeAssigned(String nurseStaffID, PatientSet patientSet) {

        ArrayList<Patient> patientTmp = new ArrayList<Patient>();

        for (Map.Entry<String, Patient> entry : patientSet.entrySet()) {
            String key = entry.getKey();
            Patient patient = entry.getValue();
            for (String s : patient.getNurseAssigned()) {
                if (s.equals(nurseStaffID)) {
                    patientTmp.add(patient);
                }
            }
        }
        return patientTmp;
    }

    public Nurse findById(String staffID) {

        for (Map.Entry<String, Nurse> entry : this.entrySet()) {
            String key = entry.getKey();
            Nurse nurse = entry.getValue();
            if (nurse.getStaffID().toUpperCase().equals(staffID.toUpperCase())) {

                return nurse;
            }
        }
        return null;
    }

    public ArrayList<Nurse> findByName(String name) {

        ArrayList<Nurse> nurseTmp = new ArrayList<Nurse>();

        for (Map.Entry<String, Nurse> entry : this.entrySet()) {
            String key = entry.getKey();
            Nurse nurse = entry.getValue();
            if (nurse.getPersonName().toUpperCase().contains(name.toUpperCase())) {
                nurseTmp.add(nurse);
            }
        }
        if (nurseTmp.size() == 0) {
            return null;
        }
        return nurseTmp;
    }

    public void remove(String staffID, PatientSet patientSet) {

        Input input = new Input();

        if (!findPatientBeAssigned(staffID, patientSet).isEmpty()) {
            System.out.println("Can not remove!, this nurse is already assign for these patients: ");
            for (Patient patient : findPatientBeAssigned(staffID, patientSet)) {
                System.out.println(patient.displayPatient());
            }
            return;
        }

        System.out.println("This nurse is not assigning anyone, removed!");
        this.remove(staffID);


//        if (choiceYN.equals("Y")) {
//            this.remove(staffID);
//            for (Patient patient : findPatientBeAssigned(staffID, patientSet)) {
//                for (String s : patient.getNurseAssigned()) {
//                    if (s.equals(staffID)) {
//                        patient.getNurseAssigned().set(patient.getNurseAssigned().indexOf(staffID), "None");
//                        patientSet.put(patient.getPersonId(), patient);
//                    }
//                }
//            }
//        }

    }


    public void updateByName(ArrayList<Nurse> nurse) {
        if (!nurse.isEmpty()) {
            Input input = new Input();
            String choiceYN = "Y";
            if (nurse.size() == 1) {
                for (Nurse nurseTmp : nurse) {
                    System.out.println(nurseTmp.getStaffID() + " " + nurseTmp.displayNurse());
                }
                add(nurse.get(0));
            } else if (nurse.size() > 1) {
                do {

                    String staffID;

                    for (Nurse nurseTmp : nurse) {
                        System.out.println(nurseTmp.getStaffID() + " " + nurseTmp.displayNurse());
                    }

                    while (true) {
                        try {
                            staffID = input.getString("Enter a StaffID to choose:", "Do not enter empty");
                            if (!input.checkStaffID(staffID)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid staffID!");
                        }
                    }

                    for (Nurse n : nurse) {
                        if (n.getStaffID().equals(staffID)) {
                            System.out.println(n.displayNurse());
                            add(n);
                        }
                    }
                    choiceYN = input.getString("Do you want to update more in this list [Y/N]:", "Invalid option").toUpperCase();
                } while (choiceYN.equals("Y"));
            }
            System.out.println("Update Successfully");
        } else {
            System.out.println("No Nurse Found");
        }
    }

    @Override
    public void updateById(String staffID) {
        add(findById(staffID));
    }

    public boolean checkMaxAssigned(String staffID, PatientSet patientSet) {
        int maxNurseAssigned = 0;

        for (Map.Entry<String, Patient> patientTmp : patientSet.entrySet()) {
            Patient patient = patientTmp.getValue();
            for (String s : patient.getNurseAssigned()) {
                if (s.equals(staffID)) {
                    maxNurseAssigned++;
                }
            }
        }
        if (maxNurseAssigned >= 2) {
            return false;
        }
        return true;
    }

    public ArrayList<String> getCanAssigned(PatientSet patientSet) {
        ArrayList<String> nursesTmp = new ArrayList<String>();
        for (Map.Entry<String, Nurse> entry : this.entrySet()) {
            if (checkMaxAssigned(entry.getKey(), patientSet)) {
                nursesTmp.add(entry.getKey());
            }
        }
        return nursesTmp;
    }

    public void displayByDate(String startDate, String endDate) {

    }

    @Override
    public void sortByPersonID(String type) {

    }

    @Override
    public void sortByName(String type) {

    }

    @Override
    public void sortByAdmissiondate(String type) {

    }

}
