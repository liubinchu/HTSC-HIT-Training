package org.ComplicatedCustomerSearcher;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author liubi
 * @date 2020-08-31 18:21
 **/
public class SQLParser {
    /**
     * 将SQL where子句从中缀表达式 转换为 后缀表达式
     * @param whereExpression
     * 输入要求：1. 符号，变量之间使用 空格 间隔
     *          2. 支持的括号“{” 、“}”、“[”、“]”、“(”、“)”
     *          3. 支持的运算符：OR、AND、==、!=、<、>
     *
     * @return where子句的后缀表达式
     */
    public static List<String> toSuffixExpression(String whereExpression) {
        whereExpression.replace('{','(')
                .replace('}',')')
                .replace('[','(')
                .replace(']',')');
        String[] expressionList = whereExpression.split(" ");
        Stack<String> opStack = new Stack<>();
        List<String> suffixList = new ArrayList<>();
        for (String item : expressionList) {
            item = item.trim();
            if (isOperator(item)) {
                if (!opStack.isEmpty() && !"(".equals(opStack.peek()) &&
                        priority(item) <= priority(opStack.peek())) {
                    while (!opStack.isEmpty() && !"(".equals(opStack.peek())) {
                        if (priority(item) <= priority(opStack.peek())) {
                            suffixList.add(opStack.pop());
                        }
                    }
                }
                opStack.push(item);
            } else if ("(".equals(item)) {
                opStack.push(item);
            } else if (")".equals(item)) {
                while (!opStack.isEmpty()) {
                    if ("(".equals(opStack.peek())) {
                        opStack.pop();
                        break;
                    } else {
                        suffixList.add(opStack.pop());
                    }
                }
            } else {
                suffixList.add(item);
            }
        }
        while (!opStack.isEmpty()) {
            suffixList.add(opStack.pop());
        }
        return suffixList;
    }

    /**
     * 判断字符串是否为操作符
     * @param op
     * @return
     */
    public static boolean isOperator(String op) {
        return op.equals("==") || op.equals("!=") ||
                op.equals("<") || op.equals(">") ||
                op.equals("OR") || op.equals("AND");
    }

    /**
     * 获取操作符的优先级
     * @param op
     * @return
     */
    public static int priority(String op) {
        if (op.equals("AND") || op.equals("OR")) {
            return 0;
        } else if (op.equals("==") || op.equals("!=")||
                op.equals("<") || op.equals(">")) {
            return 1;
        }
        return -1;
    }

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

    public static Expression parse(String whereExpression) {
        List<String> expressionList = toSuffixExpression(whereExpression);
        ArrayDeque<Expression> stack = new ArrayDeque<>();
        for (String token : expressionList) {
            stack.push(parseToken(token, stack));
        }

        if(stack.isEmpty()){
            return new Variable(" ");
        }else{
            return stack.pop();
        }

    }

    public static void main(String[] args) {
        String whereExpression = "companyName == 'HTSC' OR ( ( age < 30 ) AND ( sex != \"Male\" ) )";
        //将中缀表达式转换为后缀表达式
        List<String> suffixList = SQLParser.toSuffixExpression(whereExpression);

        System.out.println(suffixList);
        Expression whereExpr = SQLParser.parse(whereExpression);
        System.out.println(whereExpr.interpretSQL());
    }
}
