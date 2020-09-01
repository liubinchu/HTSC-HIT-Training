package org.ComplicatedCustomerSearcher;

import lombok.AllArgsConstructor;

/**
 * @author liubi
 * @date 2020-09-01 16:48
 * operator of ==, "equal,EQ"
 **/
@AllArgsConstructor
public class Operator_EQ implements Expression{

    Expression right;
    Expression left;

    @Override
    public String interpretSQL() {
        return right.interpretSQL() + " == "+left.interpretSQL();
    }
}
