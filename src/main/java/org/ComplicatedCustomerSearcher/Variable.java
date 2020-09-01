package org.ComplicatedCustomerSearcher;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @author liubi
 * @date 2020-08-31 18:25
 **/
@AllArgsConstructor
public class Variable implements Expression {
    String name;

    @Override
    public String interpretSQL() {
        return (" "+name+" ");
    }
}

