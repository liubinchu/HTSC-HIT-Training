package org.StarBucks.Size;

import lombok.AllArgsConstructor;
import org.StarBucks.Coffee.Beverage;

/**
 * @author liubi
 * @date 2020-08-27 18:07
 **/
@AllArgsConstructor
public class Medium extends SizeDecorator {

    Beverage beverage;

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Medium";
    }

    @Override
    public double calPrice() {
        return beverage.calPrice() + 3;
    }
}
