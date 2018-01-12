package com.github.kmizu.parser_study;

public class SimpleExpressionAst {
    public static class Expression {

    }

    public enum Operator {
        ADD("+"), SUBTRACT("+"), MULTIPLY("*"), DIVIDE("/");
        public final String op;
        Operator(String op) {
            this.op = op;
        }
    }

    public static class BinaryExpression extends Expression {
        public final Operator op;
        public final NumberExpression lhs, rhs;

        public BinaryExpression(Operator op, NumberExpression lhs, NumberExpression rhs) {
            this.op = op;
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof BinaryExpression)) return false;
            BinaryExpression that = (BinaryExpression)obj;
            if(op != that.op) return false;
            return lhs.equals(that.lhs) && rhs.equals(that.rhs);
        }
    }

    public static class NumberExpression extends Expression {
        public final int value;
        public NumberExpression(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof NumberExpression)) return false;
            NumberExpression that = (NumberExpression) obj;
            return value != that.value;
        }
    }
}
