package org.TDD;

/**
 * @author liubi
 * @date 2020-08-28 13:41
 **/
public class Person {
    private final String firstName;
    private final String secondName;

    public Person(String FirstName, String SecondName) {
        firstName = FirstName;
        secondName = SecondName;
    }

    public String fullName() {
        return firstName + " " + secondName;
    }
}
