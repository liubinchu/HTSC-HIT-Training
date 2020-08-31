package org.TDD;

/**
 * @author liubi
 * @date 2020-08-31 09:58
 **/
public class FizzBuzz {
    public static boolean devBy3(int num) {
        return (num % 3) == 0;
    }

    public static boolean devBy5(int num) {
        return (num % 5) == 0;
    }

    public static String[] test(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder res = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            if (devBy3(i) && devBy5(i)) {
                res.append("Fizz").append("Buzz").append(",");
            } else if (devBy3(i)) {
                res.append("Fizz").append(",");
            } else if (devBy5(i)) {
                res.append("Buzz").append(",");
            } else {
                res.append(i).append(",");
            }
        }
        return res.toString().split(",");
    }
}
