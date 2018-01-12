package com.github.kmizu.parser_study;

import org.antlr.v4.runtime.ANTLRInputStream;

import java.io.StringReader;

public class ArithmeticAst {
    public interface Visitor<R> {
        R visitBinaryExpression(BinaryExpression expression);
        R visitIntegerLiteral(IntegerLiteral expression);
    }

    public enum Operator {
        ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");
        public final String op;
        Operator(String op) {
            this.op = op;
        }
    }

    public static abstract class Expression {
        public abstract <R> R accept(Visitor<R> visitor);
    }

    public static class BinaryExpression extends Expression {
        public final Operator operator;
        public final Expression lhs, rhs;

        public BinaryExpression(Operator operator, Expression lhs, Expression rhs) {
            this.operator = operator;
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override  public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpression(this);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof BinaryExpression)) return false;
            BinaryExpression e = (BinaryExpression)obj;
            return    this.operator == e.operator
                    && this.lhs.equals(e.lhs)
                    && this.rhs.equals(e.rhs);
        }
    }

    public static class IntegerLiteral extends Expression {
        public final int value;

        public IntegerLiteral(int value) {
            this.value = value;
        }

        @Override  public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIntegerLiteral(this);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof IntegerLiteral)) return false;
            IntegerLiteral e = (IntegerLiteral)obj;
            return this.value == e.value;
        }
    }

    public static class Evaluator implements Visitor<Integer> {
        @Override
        public Integer visitBinaryExpression(BinaryExpression expression) {
            switch (expression.operator) {
                case ADD:
                    return   expression.lhs.accept(this)
                            + expression.rhs.accept(this);
                case SUBTRACT:
                    return   expression.lhs.accept(this)
                            - expression.rhs.accept(this);
                case MULTIPLY:
                    return   expression.lhs.accept(this)
                            * expression.rhs.accept(this);
                case DIVIDE:
                    return   expression.lhs.accept(this)
                            / expression.rhs.accept(this);
                default:
                    throw new RuntimeException("cannot reach here");
            }
        }

        @Override
        public Integer visitIntegerLiteral(IntegerLiteral expression) {
            return expression.value;
        }
        public int evaluate(String input) throws Exception {
            ArithmeticAst.Expression e = new ArithmeticParser(
                    Commons.streamOf(new ArithmeticLexer(
                            new ANTLRInputStream(new StringReader(input))
                    ))
            ).expression().e;
            return e.accept(this);
        }
    }
}
