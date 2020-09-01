package org.CustomerSearcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.CustomerSearcher.WhereClause.WhereClause;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubi
 * @date 2020-08-29 20:03
 **/
public class CustomerSearcherTest {
    @Data
    @AllArgsConstructor
    class SearchClauseElement {
        private String ConnectPredicate;
        private String subject;
        private String predicate;
        private String object;
    }


    public SQLBase SQLGenerator(List<SearchClauseElement> searchElements) {
        SQLBase sqlBase = null;
        for (SearchClauseElement searchElement : searchElements) {
            if (null == sqlBase) {
                sqlBase = new FirstSQLClasue(searchElement.getSubject(),
                        searchElement.getPredicate(),
                        searchElement.getObject());
            } else {
                sqlBase = new WhereClause(sqlBase, searchElement.getConnectPredicate(),
                        searchElement.getSubject(),
                        searchElement.getPredicate(),
                        searchElement.getObject());
            }
        }
        return sqlBase;
    }

    public StringBuilder CorrectSQLGenerator(List<SearchClauseElement> searchElements) {

        StringBuilder SQLCorrect = null;
        for (SearchClauseElement searchElement : searchElements) {
            if (null == SQLCorrect) {
                SQLCorrect = new StringBuilder("SELECT * FROM DB WHERE " +
                        searchElement.getSubject() + " " +
                        searchElement.getPredicate() + " " +
                        searchElement.getObject());
            } else {
                SQLCorrect.append(" ").append(searchElement.getConnectPredicate()).append(" ")
                        .append(searchElement.getSubject()).append(" ")
                        .append(searchElement.getPredicate()).append(" ")
                        .append(searchElement.getObject());
            }
        }

        return SQLCorrect;
    }


    public void customerSearcherTestFunc(SQLBase sqlBase
            , StringBuilder SQLCorrect) {
        System.out.println("SQL generated: " + sqlBase.getSQL());
        System.out.println("correct SQL : " + SQLCorrect.toString());
        Assert.assertTrue(sqlBase.getSQL().equals(SQLCorrect.toString()));
    }

    @Test
    public void AndTest() {
        List<SearchClauseElement> elements = new ArrayList<>();
        elements.add(new SearchClauseElement(null,
                "Company Name", "=", "aaa"));
        elements.add(new SearchClauseElement("AND",
                "Contact Title", "!=", "bbb"));

        customerSearcherTestFunc(SQLGenerator(elements), CorrectSQLGenerator(elements));
    }

    @Test
    public void OrTest() {
        List<SearchClauseElement> elements = new ArrayList<>();
        elements.add(new SearchClauseElement(null,
                "Company Name", "=", "aaa"));
        elements.add(new SearchClauseElement("Or",
                "Phone", "=", "820 880 8807"));
        customerSearcherTestFunc(SQLGenerator(elements), CorrectSQLGenerator(elements));
    }

    @Test
    public void ComprehensiveTest() {
        List<SearchClauseElement> elements = new ArrayList<>();
        elements.add(new SearchClauseElement(null,
                "Company Name", "=", "aaa"));
        elements.add(new SearchClauseElement("AND",
                "Contact Title", "!=", "bbb"));
        elements.add(new SearchClauseElement("Or",
                "Phone", "=", "820 880 8807"));
        customerSearcherTestFunc(SQLGenerator(elements), CorrectSQLGenerator(elements));
    }
}
