package org.TDD;

/**
 * @author liubi
 * @date 2020-08-31 09:34
 **/
public class XO {

    public static boolean check(String xxoo) {
        int xCount = 0;
        int oCount = 0;

        for (char c : xxoo.toCharArray()) {
            if ('O' == c) {
                oCount++;
            } else if ('X' == c) {
                xCount++;
            }
        }
        return xCount == oCount;
    }
}
