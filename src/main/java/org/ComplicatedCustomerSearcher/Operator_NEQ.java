package org.ComplicatedCustomerSearcher;

import lombok.AllArgsConstructor;

/**
 * @author liubi
 * @date 2020-09-01 16:53
 * operator of ==, "not equal, NEQ"
 **/
@AllArgsConstructor
public class Operator_NEQ implements Expression{
    Expression right;
    Expression left;
    @Override
    public String interpretSQL() {
        return right.interpretSQL() + " != "+left.interpretSQL();
    }
}
