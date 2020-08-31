package org.TDD;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liubi
 * @date 2020-08-31 09:52
 **/
public class FizzBuzzTest {
    @Test
    public void FizzBuzzTest1() {
        Assert.assertEquals(new String[]{"1", "2"}, FizzBuzz.test(2));
    }

    @Test
    public void FizzBuzzTest2() {
        Assert.assertEquals(new String[]{"1", "2", "Fizz"}, FizzBuzz.test(3));
    }

    @Test
    public void FizzBuzzTest3() {
        Assert.assertEquals(new String[]{"1", "2", "Fizz", "4"}, FizzBuzz.test(4));
    }

    @Test
    public void FizzBuzzTest4() {
        Assert.assertEquals(new String[]{"1", "2", "Fizz", "4", "Buzz"}, FizzBuzz.test(5));
    }

    @Test
    public void FizzBuzzTest5() {
        Assert.assertEquals(new String[]{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11"
                , "Fizz", "13", "14", "FizzBuzz"}, FizzBuzz.test(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void FizzBuzzTest6() throws IllegalArgumentException {
        FizzBuzz.test(0);
    }
}
