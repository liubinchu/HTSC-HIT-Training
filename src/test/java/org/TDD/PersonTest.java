package org.TDD;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liubi
 * @date 2020-08-28 13:39
 **/
public class PersonTest {
    @Test
    public void shouldConcatFirstNameAndLastName() {
        Person person = new Person("Jeff", "Xiong");
        Assert.assertTrue(person.fullName().equals("Jeff Xiong"));
    }
    public void PersonTest() {
    }
}
