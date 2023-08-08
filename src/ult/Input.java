package ult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Input {
    public Input() {
    }

    public String getString(String message, String errorMessage) {
        String result = "";
        while (true) {
            try {
                System.out.println(message);
                Scanner input = new Scanner(System.in);
                result = input.nextLine().trim();
                if (result.equals("")) {
                    throw new Exception();
                }
                return result;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    public int getInt(String message, String errorMessage) {
        int result = 0;
        while (true) {
            try {
                System.out.println(message);
                Scanner input = new Scanner(System.in);
                result = Integer.parseInt(input.nextLine().trim());
                return result;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    public double getDouble(String message, String errorMessage) {
        double result;
        while (true) {
            try {
                System.out.println(message);
                Scanner input = new Scanner(System.in);
                result = Double.parseDouble(input.nextLine().trim());
                return result;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    public boolean checkAge(int age, String nurse) {
        if (nurse.equalsIgnoreCase("nurse")) {
            if (age < 20) {
                return false;
            }
        }
        if (age < 0) {
            return false;
        }
        return true;
    }


    public boolean checkGender(String gender) {
        String man = "Male";
        String woman = "Female";
        String other = "Other";
        if (gender.toUpperCase().matches(man.toUpperCase())) {
            return true;
        } else if (gender.toUpperCase().matches(woman.toUpperCase())) {
            return true;
        } else if (gender.toUpperCase().matches(other.toUpperCase())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkSalary(Double salary) {
        if (salary < 0) {
            return false;
        }
        return true;
    }

    public boolean checkStaffID(String staffID) {
        if (!staffID.matches("^N\\d{4}")) {
            return false;
        }
        return true;
    }

    public boolean checkPersonID(String personID) {
        if (!personID.matches("^P\\d{4}")) {
            return false;
        }
        return true;
    }

    public boolean checkDateFormat(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
