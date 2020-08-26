package org.OnlineShoppingSearcher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liubi
 * @date 2020-08-26 18:07
 **/
@AllArgsConstructor
@ToString
public  class Computer {
    @Getter
    private String serialNumber;

    @Getter
    @Setter
    private double price;

    @Getter
    private ComputerSpec computerSpec;
}
