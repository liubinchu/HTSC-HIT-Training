package org.ComplicatedCustomerSearcher;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import lombok.AllArgsConstructor;

/**
 * @author liubi
 * @date 2020-09-01 16:47
 * operator of <, "less than,LT"
 **/
@AllArgsConstructor
public class Operator_LT implements Expression {
    Expression right;
    Expression left;

    @Override
    public String interpretSQL() {
        return right.interpretSQL() + " < "+left.interpretSQL();
    }
}
