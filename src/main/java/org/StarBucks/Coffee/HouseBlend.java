package org.StarBucks.Coffee;

/**
 * @author liubi
 * @date 2020-08-27 17:53
 **/

/**
 * 混合咖啡
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        description = "House Blend Coffee";
    }

    @Override
    public double calPrice() {
        return 16;
    }
}
