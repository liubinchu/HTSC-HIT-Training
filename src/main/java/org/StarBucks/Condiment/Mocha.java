package org.StarBucks.Condiment;

/**
 * @author liubi
 * @date 2020-08-27 17:54
 **/

import lombok.AllArgsConstructor;
import org.StarBucks.Coffee.Beverage;

/**
 * 摩卡,一种调料
 */
@AllArgsConstructor
public class Mocha extends CondimentDecorator {

    Beverage beverage;

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Mocha";
    }

    @Override
    public double calPrice() {
        return 5 + beverage.calPrice();
    }
}

