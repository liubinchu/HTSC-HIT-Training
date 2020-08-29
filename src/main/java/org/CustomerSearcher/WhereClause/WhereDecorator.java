package org.CustomerSearcher.WhereClause;

/**
 * @author liubi
 * @date 2020-08-27 17:50
 **/

import org.CustomerSearcher.SQLBase;


public abstract class WhereDecorator extends SQLBase {
    @Override
    public abstract String getSQL();
}
