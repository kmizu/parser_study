package com.github.kmizu.parser_study;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

public class JsonLexerCommons {
    public static CommonTokenStream streamOf(String input) throws IOException {
        ANTLRInputStream lrStream = new ANTLRInputStream(new StringReader(input));
        JsonLexer lexer = new JsonLexer(lrStream);
        return new CommonTokenStream(lexer);
    }
}
