package obj_data;

import java.io.Serializable;

public class Person implements Serializable {
    // Properties of person
    private String personId;
    private String personName;
    private int personAge;
    private String personGender;
    private String personAddress;
    private String personPhone;

    // Constructor
    public Person() {

    }

    public Person(String personId, String personName, int personAge, String personGender, String personAddress, String personPhone) {
        this.personId = personId;
        this.personName = personName;
        this.personAge = personAge;
        this.personGender = personGender;
        this.personAddress = personAddress;
        this.personPhone = personPhone;
    }
    //Getter Setter

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }


    //Methods


    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", personName='" + personName + '\'' +
                ", personAge=" + personAge +
                ", personGender='" + personGender + '\'' +
                ", personAddress='" + personAddress + '\'' +
                ", personPhone=" + personPhone +
                '}';
    }

    public void inputPerson() {
    }
}
