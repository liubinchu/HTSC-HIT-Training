package org.ComplicatedCustomerSearcher;

import lombok.AllArgsConstructor;

/**
 * @author liubi
 * @date 2020-09-01 16:45
 * operator of >, "great than,GT"
 **/
@AllArgsConstructor
public class Operator_GT implements Expression {
    Expression right;
    Expression left;

    @Override
    public String interpretSQL() {
        return right.interpretSQL() + ">"+left.interpretSQL();
    }
}
