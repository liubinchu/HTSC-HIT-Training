package org.ComplicatedCustomerSearcher;

import lombok.AllArgsConstructor;

/**
 * @author liubi
 * @date 2020-09-01 17:15
 **/
@AllArgsConstructor
public class Operator_OR implements Expression {
    Expression right;
    Expression left;

    @Override
    public String interpretSQL() {
        return " ( "+right.interpretSQL() + " OR "+left.interpretSQL()+" ) ";
    }
}
