package org.StarBucks.Coffee;

/**
 * @author liubi
 * @date 2020-08-27 17:51
 **/

/**
 * 浓咖啡,是一种饮料
 */
public class Espresso extends Beverage {

    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double calPrice() {
        return 18;
    }
}
