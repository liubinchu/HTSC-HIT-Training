package org.CustomerSearcher.WhereClause;

import org.CustomerSearcher.SQLBase;

/**
 * @author liubi
 * @date 2020-08-29 19:53
 **/
public class WhereClause extends WhereDecorator {

    private SQLBase sqlBase;
    private String ConnectPredicate;
    private String subject;
    private String predicate;
    private String object;
    protected String SQL;

    public WhereClause(SQLBase sqlBase, String connectPredicate, String subject, String predicate, String object) {
        this.sqlBase = sqlBase;
        this.ConnectPredicate = connectPredicate;
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.SQL = (sqlBase.getSQL() + " "
                + connectPredicate + " "
                + subject + " "
                + predicate + " "
                + object);
    }

    @Override
    public String getSQL() {
        return SQL;
    }
}
