package org.ComplicatedCustomerSearcher;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liubi
 * @date 2020-09-01 17:54
 **/
public class SQLParserTest {
    private final String EQ_Clause = "companyName == 'HTSC'";
    private final String NEQ_Clause = "companyName != 'HTSC'";
    private final String LT_Clause = "age < 30";
    private final String GT_Clause = "age > 30";
    private final String AND_Clause = "( age < 30 ) AND ( sex != \"Male\" )";
    private final String OR_Clause = "( age < 30 ) AND ( sex != \"Male\" )";

    private final String Empty_Quote = "( )";

    private final String OR_AND_Clause = "companyName == 'HTSC' OR age < 30 AND sex != \"Male\"";
    private final String OR_AND_Quote_Clause = "companyName == 'HTSC' OR ( ( age < 30 ) AND ( sex != \"Male\" ) )";

    @Test
    public void EQ_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(EQ_Clause).toString(),
                "[companyName, 'HTSC', ==]");
        Assert.assertEquals(SQLParser.parse(EQ_Clause).interpretSQL(),
                " 'HTSC' == companyName ");
    }

    @Test
    public void NEQ_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(NEQ_Clause).toString(),
                "[companyName, 'HTSC', !=]");
        Assert.assertEquals(SQLParser.parse(NEQ_Clause).interpretSQL(),
                " 'HTSC' != companyName ");
    }

    @Test
    public void LT_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(LT_Clause).toString(),
                "[age, 30, <]");
        Assert.assertEquals(SQLParser.parse(LT_Clause).interpretSQL(),
                " 30 < age ");
    }

    @Test
    public void GT_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(GT_Clause).toString(),
                "[age, 30, >]");
        Assert.assertEquals(SQLParser.parse(GT_Clause).interpretSQL(),
                " 30 > age ");
    }

    @Test
    public void AND_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(AND_Clause).toString(),
                "[age, 30, <, sex, \"Male\", !=, AND]");
        Assert.assertEquals(SQLParser.parse(AND_Clause).interpretSQL(),
                "( \"Male\" != sex AND 30 < age )");
    }

    @Test
    public void OR_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(OR_Clause).toString(),
                "[age, 30, <, sex, \"Male\", !=, AND]");
        Assert.assertEquals(SQLParser.parse(OR_Clause).interpretSQL(),
                "( \"Male\" != sex AND 30 < age )");
    }

    @Test
    public void Empty_Quote_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(Empty_Quote).toString(),
                "[]");
        Assert.assertEquals(SQLParser.parse(Empty_Quote).interpretSQL(),
                "   ");
    }

    public void OR_AND_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(OR_AND_Clause).toString(),
                "[companyName, 'HTSC', ==, age, 30, <, OR, sex, \"Male\", !=, AND]");
        Assert.assertEquals(SQLParser.parse(OR_AND_Clause).interpretSQL(),
                "( \"Male\" != sex AND( 30 < age OR 'HTSC' == companyName ))");
    }

    public void OR_AND_Quote_Test() {
        Assert.assertEquals(SQLParser.toSuffixExpression(OR_AND_Quote_Clause).toString(),
                "[companyName, 'HTSC', ==, age, 30, <, sex, \"Male\", !=, AND, OR]");
        Assert.assertEquals(SQLParser.parse(OR_AND_Quote_Clause).interpretSQL(),
                "(( \"Male\" != sex AND 30 < age )OR 'HTSC' == companyName )");
    }
}
