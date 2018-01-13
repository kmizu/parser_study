grammar Arithmetic;

@header {
  import static com.github.kmizu.parser_study.ArithmeticAst.*;
}

expression returns [Expression e]
   : v=additive {$e = $v.e;}
   ;

additive returns [Expression e]
    : l=additive '+' r=multitive {$e = new BinaryExpression(Operator.ADD, $l.e, $r.e);}
    | l=additive '-' r=multitive {$e = new BinaryExpression(Operator.SUBTRACT, $l.e, $r.e);}
    | v=multitive {$e = $v.e;}
    ;

multitive returns [Expression e]
    : l=multitive '*' r=primary {$e = new BinaryExpression(Operator.MULTIPLY, $l.e, $r.e);}
    | l=multitive '/' r=primary {$e = new BinaryExpression(Operator.DIVIDE, $l.e, $r.e);}
    | v=primary {$e = $v.e;}
    ;

primary returns [Expression e]
    : v=number {$e = new IntegerLiteral($v.value);}
    | '(' r=expression ')' {$e = $r.e;}
    ;

number returns [int value]
   : n=NUMBER {$value = Integer.parseInt($n.getText());}
   ;

NUMBER: ('0' | [1-9] [0-9]*);

WS  :   [ \t\n\r]+ -> skip ;
