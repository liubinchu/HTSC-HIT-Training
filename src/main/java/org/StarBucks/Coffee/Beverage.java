package org.StarBucks.Coffee;

/**
 * @author liubi
 * @date 2020-08-27 17:49
 **/

import lombok.Getter;

/**
 * 饮料类
 */
public abstract class Beverage {
    //描述
    @Getter
    protected String description = "";


    //价格
    public abstract double calPrice();
}
