package com.github.kmizu.parser_study;

import java.util.List;

public class JsonAst {
    public interface Visitor<C, R> {
        R visitJNumber(JNumber value, C context);
        R visitJString(JString value, C context);
        R visitJBoolean(JBoolean value, C context);
        R visitJNull(JNull value, C context);
        R visitJArray(JArray value, C context);
        R visitJObject(JObject value, C context);
    }

    public static class Pair<A, B> {
        public final A _1;
        public final B _2;

        public Pair(A _1, B _2) {
            this._1 = _1;
            this._2 = _2;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Pair<?, ?>)) return false;
            Pair<?, ?> that = (Pair<?, ?>)obj;
            return that._1.equals(_1) && that._2.equals(_2);
        }

    }

    public static abstract class JValue {
        public abstract <C, R> R accept(Visitor<C, R> visitor, C context);
    }

    public static class JNull extends JValue {
        public static JNull instance = new JNull();
        private JNull() {
        }

        @Override
        public <C, R> R accept(Visitor<C, R> visitor, C context) {
            return visitor.visitJNull(this, context);
        }
    }

    public static class JArray extends JValue {
        public final List<? extends JValue> elements;

        public JArray(List<? extends JValue> elements) {
            this.elements = elements;
        }

        @Override
        public <C, R> R accept(Visitor<C, R> visitor, C context) {
            return visitor.visitJArray(this, context);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof JArray)) return false;
            JArray that = (JArray)obj;
            if(elements.size() != that.elements.size()) return false;
            for(int i = 0; i < elements.size(); i++) {
                if(!elements.get(i).equals(that.elements.get(i))) return false;
            }
            return true;
        }
    }

    public static class JObject extends JValue {
        public final List<? extends Pair<String, JValue>> elements;

        public JObject(List<? extends Pair<String, JValue>> elements) {
            this.elements = elements;
        }

        @Override
        public <C, R> R accept(Visitor<C, R> visitor, C context) {
            return visitor.visitJObject(this, context);
        }
    }


    public static class JString extends JValue {
        public final String value;

        public JString(String value) {
            this.value = value;
        }

        @Override  public <C, R> R accept(Visitor<C, R> visitor, C context) {
            return visitor.visitJString(this, context);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof JString)) return false;
            JString that = (JString)obj;
            return value.equals(that.value);
        }
    }

    public static class JBoolean extends JValue {
        public final boolean value;

        public JBoolean(boolean value) {
            this.value = value;
        }

        @Override  public <C, R> R accept(Visitor<C, R> visitor, C context) {
            return visitor.visitJBoolean(this, context);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof JBoolean)) return false;
            JBoolean that = (JBoolean) obj;
            return value == that.value;
        }
    }

    public static class JNumber extends JValue {
        public final double value;

        public JNumber(double value) {
            this.value = value;
        }

        @Override  public <C, R> R accept(Visitor<C, R> visitor, C context) {
            return visitor.visitJNumber(this, context);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof JNumber)) return false;
            JNumber e = (JNumber)obj;
            return this.value == e.value;
        }
    }

    public static class Parser {
        public JValue parse(String input) throws Exception {
            return new JsonParser(JsonLexerCommons.streamOf(input)).jvalue().value;
        }
    }
}
