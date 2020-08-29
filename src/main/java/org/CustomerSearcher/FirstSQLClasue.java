package org.CustomerSearcher;

/**
 * @author liubi
 * @date 2020-08-29 19:44
 **/

public class FirstSQLClasue extends SQLBase {
    private String subject;
    private String predicate;
    private String object;
    protected String SQL;

    public FirstSQLClasue(String subject, String predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.SQL = ("SELECT * FROM DB WHERE " + subject + " " + predicate + " " + object);
    }

    @Override
    public String getSQL() {
        return SQL;
    }
}
