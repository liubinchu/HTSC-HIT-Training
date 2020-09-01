package org.ComplicatedCustomerSearcher;

import java.util.ArrayDeque;
import java.util.List;

/**
 * @author liubi
 * @date 2020-08-31 18:21
 **/
public class ComplicatedCustomerSearcher {
    public static Expression parseToken(String token, ArrayDeque<Expression> stack) {
        Expression left, right;
        switch (token) {
            case "AND":
                right = stack.pop();
                left = stack.pop();
                return new Operator_AND(right, left);
            case "OR":
                right = stack.pop();
                left = stack.pop();
                return new Operator_OR(right, left);
            case "==":
                right = stack.pop();
                left = stack.pop();
                return new Operator_EQ(right, left);
            case "!=":
                right = stack.pop();
                left = stack.pop();
                return new Operator_NEQ(right, left);
            case ">":
                right = stack.pop();
                left = stack.pop();
                return new Operator_GT(right, left);
            case "<":
                right = stack.pop();
                left = stack.pop();
                return new Operator_LT(right, left);
            default:
                return new Variable(token);
        }
    }

    public static Expression parse(List<String> expressionList) {
        ArrayDeque<Expression> stack = new ArrayDeque<>();
        for (String token : expressionList) {
            stack.push(parseToken(token, stack));
        }
        return stack.pop();
    }
    public static void main(String[] args) {
        String whereExpression = "companyName == 'HTSC' OR ( ( age < 30 ) AND ( sex != \"Male\" ) )";
        List<String> suffixList = SQLParser.parseToSuffixExpression(whereExpression);
        System.out.println(suffixList);
        Expression whereExpr = parse(suffixList);
        System.out.println(whereExpr.interpretSQL());
    }
}
