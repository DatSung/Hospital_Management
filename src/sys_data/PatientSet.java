package sys_data;

import action.ISet;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import obj_data.Nurse;
import obj_data.Patient;
import obj_data.Person;
import ult.Input;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import jdk.nashorn.internal.ir.TryNode;

public class PatientSet extends HashMap<String, Patient> implements ISet, Serializable {

    private static final long serialVersionUID = -6140363508500781797L;

    public PatientSet() {
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
                        if (key instanceof String && value instanceof Patient) {
                            put((String) key, (Patient) value);
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
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean add(Person person) {
        return false;
    }

    public boolean add(Person person, NurseSet nurseSet) {

        if (person instanceof Patient) {

            Patient patient = (Patient) person;
            Input input = new Input();

            while (true) {
                try {

                    String personID;

                    while (true) {
                        try {
                            personID = input.getString("Enter person id [P****]:", "Do not enter empty").toUpperCase().trim();
                            if (!input.checkPersonID(personID)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid personID!");
                        }
                    }

                    for (Map.Entry<String, Patient> entry : this.entrySet()) {
                        String key = entry.getKey();
                        Patient personTmp = entry.getValue();
                        if (personTmp.getPersonId().equals(personID)) {
                            throw new Exception();
                        }
                    }

                    patient.setPersonId(personID);

                    break;
                } catch (Exception e) {
                    System.out.println("Duplicate PersonID, please Enter again!");
                }
            }

            while (true) {
                try {
                    String personName = input.getString("Enter person name:", "Do not enter empty!").trim();
                    if (personName.matches(".*[0-9].*")) {
                        throw new Exception();
                    }
                    patient.setPersonName(personName);
                    break;
                } catch (Exception e) {
                    System.out.println("The name do not contains a number!");
                }
            }

            while (true) {
                try {
                    int age = input.getInt("Enter person age", "Do not enter empty");
                    if (!input.checkAge(age, "Patient")) {
                        throw new Exception();
                    }
                    patient.setPersonAge(age);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid age!");
                }
            }

            while (true) {
                try {
                    String gender = input.getString("Enter person gender [Male/Female/Other]:", "Do not enter empty").trim();
                    if (!input.checkGender(gender)) {
                        throw new Exception();
                    }
                    patient.setPersonGender(gender);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid option!");
                }
            }

            patient.setPersonAddress(input.getString("Enter person address:", "Do not enter empty!").trim());

            while (true) {
                try {
                    String phone = input.getString("Enter phone:", "Do not enter empty").trim();
                    if (!phone.matches("^(\\+?\\d{1,3}[- ]?)?\\d{10}$")) {
                        throw new Exception();
                    }
                    patient.setPersonPhone(phone);
                    break;
                } catch (Exception e) {
                    System.out.println("Phone number invalid!");
                }
            }

            patient.setDiagnosis(input.getString("Enter diagnosis:", "Do not enter empty!").trim());

            while (true) {
                try {
                    String date = input.getString("Enter AdmissionDate [dd/MM/yyyy]:", "Do not enter empty").trim();
                    if (!input.checkDateFormat(date)) {
                        throw new Exception();
                    }
                    patient.setAdmissionDate(date);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date");
                }
            }

            while (true) {
                try {
                    String date;
                    while (true) {
                        try {
                            date = input.getString("Enter DischargeDate [dd/MM/yyyy]:", "Do not enter empty").trim();
                            if (!input.checkDateFormat(date)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid date");
                        }
                    }

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date admissionDate = format.parse(patient.getAdmissionDate());
                    Date dischargeDate = format.parse(date);

                    if (dischargeDate.after(admissionDate) || dischargeDate.equals(admissionDate)) {
                        patient.setDischargeDate(date);
                    } else {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Discharge Date must be after AdmissionDate!");
                }
            }

            for (int i = 0; i < 2; i++) {

                String staffID = null;

                while (true) {
                    try {
                        if (i == 0) {
                            System.out.println("Add first nurse:");
                        }
                        if (i == 1) {
                            System.out.println("Add second nurse:");
                        }

                        nurseSet.showAvailable(this);

                        staffID = input.getString("Enter staffID to add nurseAssign:", "Do not enter empty").toUpperCase().trim();

                        if (!input.checkStaffID(staffID)) {
                            System.out.println("Invalid staffID!");
                            throw new Exception();
                        }
                        if (nurseSet.findById(staffID) == null) {
                            System.out.println("The nurse does not exist!");
                            throw new Exception();
                        }

                        for (String s : patient.getNurseAssigned()) {
                            if (s.equals(staffID)) {
                                System.out.println("Duplicate with the first nurse!");
                                throw new Exception();
                            }
                        }

                        if (!checkMaxNurseAssigned(staffID)) {
                            System.out.println("This nurse had max patient to assigned!");
                            throw new Exception();
                        }

                        patient.getNurseAssigned().add(staffID);

                        break;
                    } catch (Exception e) {
                        System.out.println("Enter again:");
                    }
                }
            }

            if (this.put(patient.getPersonId(), patient) == null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMaxNurseAssigned(String staffID) {
        int maxNurseAssigned = 0;

        for (Map.Entry<String, Patient> entry : this.entrySet()) {
            String key = entry.getKey();
            Patient patientTmp = entry.getValue();
            for (String s : patientTmp.getNurseAssigned()) {
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

    public void printSet() {

        if (!this.isEmpty()) {
            for (Map.Entry<String, Patient> entry : this.entrySet()) {
                String key = entry.getKey();
                Patient patient = entry.getValue();
                System.out.println(patient.toString());
            }
        } else {
            System.out.println("There are no patient to display!");
        }
    }

    public void displaySet() {

    }

    public void showAvailable(PatientSet patientSet) {

    }

    public ArrayList<Patient> findPatientBeAssigned(String nurseStaffID, PatientSet patientSet) {
        return null;
    }

    public Nurse findById(String staffID) {
        return null;
    }

    public ArrayList<Nurse> findByName(String name) {
        return null;
    }

    public void remove(String staffID, PatientSet patientSet) {

    }

    @Override
    public void updateByName(ArrayList<Nurse> nurse) {

    }

    @Override
    public void updateById(String staffID) {

    }

    public boolean checkMaxAssigned(String staffID, PatientSet patientSet) {
        return false;
    }

    @Override
    public ArrayList<String> getCanAssigned(PatientSet patientSet) {
        return null;
    }

    public void displayByDate(String startDate, String endDate) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date start = format.parse(startDate);
            Date end = format.parse(endDate);

            if (this.isEmpty()) {
                System.out.println("There are no Patients!");
                return;
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : this.entrySet()) {
                String key = entry.getKey();
                Patient patient = entry.getValue();
                Date admissionDate = format.parse(patient.getAdmissionDate());

                if ((admissionDate.after(start) || admissionDate.equals(start)) && (admissionDate.before(end) || admissionDate.equals(end))) {
                    System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
                    System.out.println(patient.displayPatient());
                }
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
        } catch (Exception e) {
            System.out.println("Invalid date input!");
        }

    }

    public void sortByAdmissiondate(String type) {
        if (this.isEmpty()) {
            System.out.println("There are no Patients!");
            return;
        }
        if (type.equalsIgnoreCase("ASC")) {
            List<Map.Entry<String, Patient>> listByName = new ArrayList<Map.Entry<String, Patient>>(this.entrySet());

            Collections.sort(listByName, new Comparator<Map.Entry<String, Patient>>() {
                public int compare(Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date start = format.parse(o1.getValue().getAdmissionDate());
                        Date end = format.parse(o2.getValue().getAdmissionDate());
                        return start.compareTo(end);
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                    return 0;
                }
            }
            );
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : listByName) {
                System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

                System.out.println(entry.getValue().displayPatient());
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
        }

        if (type.equalsIgnoreCase("DESC")) {
            List<Map.Entry<String, Patient>> listByName = new ArrayList<Map.Entry<String, Patient>>(this.entrySet());
            Collections.sort(listByName, new Comparator<Map.Entry<String, Patient>>() {
                public int compare(Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date start = format.parse(o1.getValue().getAdmissionDate());
                        Date end = format.parse(o2.getValue().getAdmissionDate());
                        return end.compareTo(start);
                    } catch (Exception e) {
                    }
                    return 0;
                }
            }
            );
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : listByName) {
                System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

                System.out.println(entry.getValue().displayPatient());
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
        }

    }
    
    
    

    public void sortByPersonID(String type) {

        if (this.isEmpty()) {
            System.out.println("There are no Patients!");
            return;
        }

        if (type.equalsIgnoreCase("ASC")) {
            List<Map.Entry<String, Patient>> listByName = new ArrayList<Map.Entry<String, Patient>>(this.entrySet());

            Collections.sort(listByName, new Comparator<Map.Entry<String, Patient>>() {
                public int compare(Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) {
                    return o1.getValue().getPersonId().compareTo(o2.getValue().getPersonId());
                }
            }
            );

            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : listByName) {
                System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

                System.out.println(entry.getValue().displayPatient());
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

        }

        if (type.equalsIgnoreCase("DESC")) {
            List<Map.Entry<String, Patient>> listByName = new ArrayList<Map.Entry<String, Patient>>(this.entrySet());

            Collections.sort(listByName, new Comparator<Map.Entry<String, Patient>>() {
                public int compare(Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) {
                    return o2.getValue().getPersonId().compareTo(o1.getValue().getPersonId());
                }
            }
            );

            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : listByName) {
                System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

                System.out.println(entry.getValue().displayPatient());
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

        }

    }

    public void sortByName(String type) {

        if (this.isEmpty()) {
            System.out.println("There are no Patients!");
            return;
        }

        if (type.equalsIgnoreCase("ASC")) {
            List<Map.Entry<String, Patient>> listByName = new ArrayList<Map.Entry<String, Patient>>(this.entrySet());

            Collections.sort(listByName, new Comparator<Map.Entry<String, Patient>>() {
                public int compare(Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) {
                    return o1.getValue().getPersonName().compareTo(o2.getValue().getPersonName());
                }
            }
            );

            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : listByName) {
                System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

                System.out.println(entry.getValue().displayPatient());
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

            return;
        }

        if (type.equalsIgnoreCase("DESC")) {
            List<Map.Entry<String, Patient>> listByName = new ArrayList<Map.Entry<String, Patient>>(this.entrySet());

            Collections.sort(listByName, new Comparator<Map.Entry<String, Patient>>() {
                public int compare(Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) {
                    return o2.getValue().getPersonName().compareTo(o1.getValue().getPersonName());
                }
            }
            );
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
            System.out.println("| Patient ID  | Admission Date     | Full Name               | Phone         | Diagnosis     |");
            for (Map.Entry<String, Patient> entry : listByName) {
                System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");
                System.out.println(entry.getValue().displayPatient());
            }
            System.out.println("+-------------+--------------------+-------------------------+---------------+---------------+");

            return;
        }

    }
}
