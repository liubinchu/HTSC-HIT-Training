package org.StarBucks.Condiment;

import lombok.AllArgsConstructor;
import org.StarBucks.Coffee.Beverage;

/**
 * @author liubi
 * @date 2020-08-27 18:15
 * 焦糖
 **/
@AllArgsConstructor
public class Caramel extends CondimentDecorator {

    Beverage beverage;

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Caramel";
    }

    @Override
    public double calPrice() {
        return 5 + beverage.calPrice();
    }
}
