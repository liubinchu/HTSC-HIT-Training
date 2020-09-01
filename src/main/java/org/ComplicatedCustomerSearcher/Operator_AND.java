package org.ComplicatedCustomerSearcher;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @author liubi
 * @date 2020-08-31 18:24
 **/
@AllArgsConstructor
public class Operator_AND implements Expression {
    Expression right;
    Expression left;

    @Override
    public String interpretSQL() {
        return " ( "+right.interpretSQL() + " AND "+left.interpretSQL()+" ) ";
    }
}
