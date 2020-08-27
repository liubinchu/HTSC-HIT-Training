package org.StarBucks.Condiment;

/**
 * @author liubi
 * @date 2020-08-27 17:50
 **/

import org.StarBucks.Coffee.Beverage;

/**
 * 调料类
 */
public abstract class CondimentDecorator extends Beverage {

    //所有的调料装饰者都必须重新实现该方法
    @Override
    public abstract String getDescription();
}
