package com.github.kmizu.parser_study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class ArithmeticParserTest {
    static int evaluate(String input) throws Exception {
        return new ArithmeticAst.Evaluator().evaluate(input);
    }

    @Test
    public void testZero() throws Exception {
        assertEquals(0, evaluate("0"));
        assertEquals(0, evaluate("+0"));
        assertEquals(0, evaluate("-0"));
    }

    @Test
    public void testOne() throws Exception {
        assertEquals(1, evaluate("1"));
        assertEquals(1, evaluate("+1"));
        assertEquals(-1, evaluate("-1"));
    }

    @Test
    public void testTwo() throws Exception {
        assertEquals(2, evaluate("2"));
        assertEquals(2, evaluate("+2"));
        assertEquals(-2, evaluate("-2"));
    }

    @Test
    public void testMany() throws Exception {
        assertEquals(Integer.MAX_VALUE, evaluate("2147483647"));
        assertEquals(Integer.MIN_VALUE + 1, evaluate("-2147483647"));
    }

    @Test(expected = NumberFormatException.class)
    public void testTooBigPositive() throws Exception {
        evaluate("2147483648");
    }

    @Test(expected = NumberFormatException.class)
    public void testTooBigNegative() throws Exception {
        evaluate("-2147483648");
    }

    @Test
    public void testAddition() throws Exception {
        assertEquals(1, evaluate("1+0"));
        assertEquals(1, evaluate("0+1"));
        assertEquals(3, evaluate("1+2"));
    }
}
