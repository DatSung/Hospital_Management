package main;

import obj_data.*;
import sys_data.NurseSet;
import sys_data.PatientSet;
import ult.Input;
import ult.Menu;
import java.util.Scanner;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hopistal {
    public static void main(String[] args) {

        Input input = new Input();
        //Init menu
        Menu hospitalMenu = new Menu();
        hospitalMenu.addOption("1.Nurse's Management         [1]");
        hospitalMenu.addOption("2.Patient’s management       [2]");
        hospitalMenu.addOption("0.Exist                      [0]");

        Menu nurseMenu = new Menu();
        nurseMenu.addOption("1.Create a nurse             [1]");
        nurseMenu.addOption("2.Find the nurse             [2]");
        nurseMenu.addOption("3.Update the nurse           [3]");
        nurseMenu.addOption("4.Delete the nurse           [4]");
        nurseMenu.addOption("0.Back                       [0]");

        Menu patientMenu = new Menu();
        patientMenu.addOption("1.Add a patient              [1]");
        patientMenu.addOption("2.Display patients           [2]");
        patientMenu.addOption("3.Sort the patients list     [3]");
        patientMenu.addOption("4.Save data                  [4]");
        patientMenu.addOption("5.Load data                  [5]");
        patientMenu.addOption("0.Back                       [0]");

        int mainKey;
        int subKey;

        NurseSet nurseSet = new NurseSet();
        PatientSet patientSet = new PatientSet();
        File fn = new File("data/nurse.dat");
        File pn = new File("data/patient.dat");

        //Main program

        do {
//            try {
//                if (!nurseSet.readFile(fn)) {
//                    throw new Exception();
//                }
//
//            } catch (Exception e) {
//                System.out.println("NurseFile is empty!");
//            }
//
//            try {
//                if (!patientSet.readFile(pn)) {
//                    throw new Exception();
//                }
//
//            } catch (Exception e) {
//                System.out.println("PatientFile is empty!");
//            }

            hospitalMenu.showOption("---[Welcome to Hospital Management System]---");
            mainKey = hospitalMenu.getOption("Enter your option:", "Please enter a number!");
            switch (mainKey) {

                case 1: {

                    do {

                        nurseMenu.showOption("---[Welcome to Nurse's Management]---");
                        subKey = nurseMenu.getOption("Enter your option:", "Please enter a number!");

                        switch (subKey) {
                            case 1: {
                                String choiceYN = "N";
                                do {

                                    if (nurseSet.readFile(fn)) {
                                        System.out.println("Nurse's data loaded automatic!");
                                    }

                                    Person person = new Nurse();
                                    if (nurseSet.add(person)) {
                                        System.out.println("Add new nurse successfully");
                                    } else {
                                        System.out.println("Add Nurse fail!");
                                    }

//                                    try {
//                                        if (!nurseSet.saveFile(fn)) {
//                                            throw new Exception();
//                                        }
//
//                                    } catch (Exception e) {
//                                        System.out.println("NurseFile save fail!");
//                                    }

                                    choiceYN = input.getString("Do you want to creat more [Y/N]:", "Invalid option!").toUpperCase();

                                } while (choiceYN.equals("Y"));

//                                if (!nurseSet.saveFile(fn)) {
//                                    System.out.println("Fail to save NurseFile!");
//                                } else {
//                                    System.out.println("Save NurseFile Successfully!");
//                                }

                                break;
                            }
                            case 2: {

//                                try {
//                                    if (!nurseSet.readFile(fn)) {
//                                        throw new Exception();
//                                    }
//
//                                } catch (Exception e) {
//                                    System.out.println("NurseFile loaded fail!");
//                                }

                                while (true) {

                                    if (nurseSet.isEmpty()) {
                                        System.out.println("There are no Nurse's data loaded!");
                                        break;
                                    }

                                    try {

                                        String name = input.getString("Enter nurse's name to find:", "Do not enter empty").trim();
                                        if (nurseSet.findByName(name).isEmpty()) {
                                            throw new Exception();
                                        } else {
                                            System.out.println("+-------------------------+-------+------------+--------------------------------+---------------+---------------------------+---------------+----------------------+");
                                            System.out.println("| Full name               | Age   | Gender     | Address                        | Phone         | Department                | Shift         | Salary               |");
                                            for (Nurse nurse : nurseSet.findByName(name)) {
                                                System.out.println("+-------------------------+-------+------------+--------------------------------+---------------+---------------------------+---------------+----------------------+");

                                                System.out.println(nurse.displayNurse());
                                            }
                                            System.out.println("+-------------------------+-------+------------+--------------------------------+---------------+---------------------------+---------------+----------------------+");

                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("“The nurse does not exist");
                                        String choiceYN = input.getString("Do you want to find again [Y/N]:", "Invalid option!").toUpperCase();
                                        if (!choiceYN.equals("Y")) {
                                            break;
                                        }

                                    }
                                }
                                break;
                            }
                            case 3: {


                                while (true) {

                                    if (nurseSet.isEmpty()) {
                                        System.out.println("There are no Nurse's data loaded!");
                                        break;
                                    }

                                    try {

                                        String choiceYN = "Y";
                                        String staffID;

                                        do {
                                            while (true) {
                                                try {
                                                    staffID = input.getString("Enter a StaffID to choose:", "Do not enter empty").toUpperCase();
                                                    if (!input.checkStaffID(staffID)) {
                                                        throw new Exception();
                                                    }
                                                    break;
                                                } catch (Exception e) {
                                                    System.out.println("Invalid staffID!");
                                                }
                                            }


                                            if (nurseSet.findById(staffID).equals(null)) {
                                                throw new Exception();
                                            }

                                            System.out.println(nurseSet.findById(staffID).displayNurse());
                                            nurseSet.updateById(staffID);

//                                            try {
//                                                if (!nurseSet.saveFile(fn)) {
//                                                    throw new Exception();
//                                                }
//                                            } catch (Exception e) {
//                                                System.out.println("NurseFile save fail!");
//                                            }

                                            choiceYN = input.getString("Do you want to update new nurse [Y/N]:", "Invalid option").toUpperCase();
                                        } while (choiceYN.equals("Y"));

                                        break;
                                    } catch (Exception e) {

                                        System.out.println("“The nurse does not exist!");
                                        String choiceYN = input.getString("Do you want to find again [Y/N]:", "Invalid option!").toUpperCase();

                                        if (!choiceYN.equals("Y")) {
                                            break;
                                        }

                                    }
                                }
                                break;
                            }

                            case 4: {
                                while (true) {

                                    if (nurseSet.isEmpty()) {
                                        System.out.println("There are no Nurse's data loaded!");
                                        break;
                                    }

                                    try {
                                        String staffID = input.getString("Enter nurse's staff id to find:", "Do not enter empty").toUpperCase().trim();
                                        if (!input.checkStaffID(staffID)) {
                                            System.out.println("Invalid staffID!");
                                            throw new Exception();
                                        }
                                        if (nurseSet.findById(staffID) == null) {
                                            System.out.println("The nurse does not exist!");
                                            throw new Exception();
                                        }
                                        String choiceYN = input.getString("Are you sure want to remove this Nurse [Y/N]", "Do not enter empty");
                                        if (choiceYN.equalsIgnoreCase("Y")) {
                                            nurseSet.remove(staffID, patientSet);
                                        } else {
                                            System.out.println("Cancel remove!");
                                        }
                                        break;
                                    } catch (Exception e) {
                                        String choiceYN = input.getString("Do you want to find again [Y/N]:", "Do not enter empty!").toUpperCase();
                                        if (!choiceYN.equals("Y")) {
                                            break;
                                        }

                                    }
                                }

//                                try {
//                                    if (!nurseSet.saveFile(fn)) {
//                                        throw new Exception();
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("NurseFile save fail!");
//                                }
//
//                                try {
//                                    if (!patientSet.saveFile(pn)) {
//                                        throw new Exception();
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("PatientFile save fail!");
//                                }

                                break;
                            }

                        }
                    } while (subKey != 0);

                    break;
                }

                case 2: {

                    do {

                        patientMenu.showOption("Well come to Patient's Management ");
                        subKey = nurseMenu.getOption("Enter your option:", "Please enter a number!");

                        switch (subKey) {
                            case 1: {
                                String choiceYN = "Y";
                                do {

                                    if (patientSet.readFile(pn)) {
                                        System.out.println("Patient's data loaded automatic!");
                                    }
                                    if (nurseSet.readFile(fn)) {
                                        System.out.println("Nurse's data loaded automatic!");
                                    }

                                    if (!nurseSet.isEmpty() && nurseSet.getCanAssigned(patientSet).size() >= 2) {
                                        Person person = new Patient();
                                        if (patientSet.add(person, nurseSet)) {
                                            System.out.println("Add new patient successfully");
                                        } else {
                                            System.out.println("Failure to add");
                                        }

//                                        try {
//                                            if (!patientSet.saveFile(pn)) {
//                                                throw new Exception();
//                                            }
//                                        } catch (Exception e) {
//                                            System.out.println("PatientFile save fail!");
//                                        }
                                    } else {
                                        System.out.println("There are not enough Nurse. Please add more nurse!");
                                        break;
                                    }
                                    choiceYN = input.getString("Do you want to continue [Y/N]:", "Invalid option!");
                                } while (choiceYN.equalsIgnoreCase("Y"));
                                break;
                            }
                            case 2: {

                                while (true) {

                                    if (patientSet.isEmpty()) {
                                        System.out.println("There are no Patient's data loaded!");
                                        break;
                                    }

                                    try {
                                        String startDate;
                                        String endDate;

                                        startDate = input.getString("Enter startDate [dd/MM/yyyy]:", "Do not enter empty");
                                        if (!input.checkDateFormat(startDate)) {
                                            System.out.println("Invalid date!");
                                            throw new Exception();
                                        }
                                        endDate = input.getString("Enter endDate [dd/MM/yyyy]:", "Do not enter empty");

                                        if (!input.checkDateFormat(endDate)) {
                                            System.out.println("Invalid date!");
                                            throw new Exception();
                                        }
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        Date start = format.parse(startDate);
                                        Date end = format.parse(endDate);

                                        if (end.after(start)) {
                                            patientSet.displayByDate(startDate, endDate);
                                        } else {
                                            throw new Exception();
                                        }

                                        break;
                                    } catch (Exception e) {
                                        String choiceYN = input.getString("Do you want to enter again [Y/N]:", "Do not enter empty");
                                        if (choiceYN.equalsIgnoreCase("N")) {
                                            break;
                                        }
                                    }
                                }

                                break;
                            }

                            case 3: {
                                while (true) {

                                    if (patientSet.isEmpty()) {
                                        System.out.println("There are no Patient's data loaded!");
                                        break;
                                    }

                                    try {
                                        String typeSort;
                                        String sortChoice = input.getString("Which ones do you want to sort by [Name/Id]:", "Do not enter empty!");
                                        if (sortChoice.equalsIgnoreCase("Name")) {
                                            typeSort = input.getString("Enter your type of sort [ASC/DESC]: ", "Do not enter empty!");
                                            if (!typeSort.equalsIgnoreCase("ASC") && !typeSort.equalsIgnoreCase("DESC")) {
                                                throw new Exception();
                                            }
                                            patientSet.sortByAdmissiondate(typeSort);
                                        } else if (sortChoice.equalsIgnoreCase("ID")) {
                                            typeSort = input.getString("Enter your type of sort [ASC/DESC]: ", "Do not enter empty!");
                                            if (!typeSort.equalsIgnoreCase("ASC") && !typeSort.equalsIgnoreCase("DESC")) {
                                                throw new Exception();
                                            }
                                            patientSet.sortByPersonID(typeSort);
                                        } else {
                                            throw new Exception();
                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Invalid option!");
                                    }
                                }
                                break;
                            }

                            case 4: {
                                if (!nurseSet.isEmpty()) {
                                    if (!nurseSet.saveFile(fn)) {
                                        System.out.println("Fail to save NurseFile!");
                                        nurseSet.count();
                                    } else {
                                        System.out.println("Save NurseFile Successfully!");
                                        nurseSet.count();
                                    }
                                } else {
                                    System.out.println("There are no Nurse's data to save!");
                                }
                                if (!patientSet.isEmpty()) {
                                    if (!patientSet.saveFile(pn)) {
                                        System.out.println("Fail to save PatientFile!");
                                        patientSet.count();
                                    } else {
                                        System.out.println("Save PatientFile Successfully!");
                                        patientSet.count();
                                    }
                                } else {
                                    System.out.println("There are no Patient's data to save!");
                                }
                                break;
                            }
                            case 5: {
                                if (!nurseSet.readFile(fn)) {
                                    System.out.println("No data to load NurseFile!");
                                } else {
                                    System.out.println("[Load Nurse's data Successfully!]");
                                }
                                if (!patientSet.readFile(pn)) {
                                    System.out.println("No data to load PatientFile!");
                                } else {
                                    System.out.println("[Load Patient's data Successfully!]");
                                }
                                break;
                            }
                        }

                    } while (subKey != 0);
                    break;
                }
                case 0: {
                    if (!nurseSet.saveFile(fn)) {
                        System.out.println("Fail to save NurseFile!");
                    } else {
                        System.out.println("Save NurseFile Successfully!");
                    }
                    if (!patientSet.saveFile(pn)) {
                        System.out.println("Fail to save PatientFile!");
                    } else {
                        System.out.println("Save PatientFile Successfully!");
                    }
                    System.out.println("---System Exit---");
                }
            }
        } while (mainKey != 0);
    }
}