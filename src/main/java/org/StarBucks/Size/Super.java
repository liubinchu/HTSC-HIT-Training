package org.StarBucks.Size;

import lombok.AllArgsConstructor;
import org.StarBucks.Coffee.Beverage;

/**
 * @author liubi
 * @date 2020-08-27 18:11
 **/
@AllArgsConstructor
public class Super extends SizeDecorator {

    Beverage beverage;

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Super Large";
    }

    @Override
    public double calPrice() {
        return beverage.calPrice() + 9;
    }
}
