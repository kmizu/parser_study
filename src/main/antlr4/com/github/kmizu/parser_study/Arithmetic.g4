grammar Arithmetic;

line returns [ArithmeticAst.Expression e]
   : v=expression EOF {$e = $v.e;}
   ;

expression returns [ArithmeticAst.Expression e]
   : v=additive {$e = $v.e;}
   ;

additive returns [ArithmeticAst.Expression e]
    : l=additive op='+' r=multitive {$e = new ArithmeticAst.BinaryExpression(ArithmeticAst.Operator.ADD, $l.e, $r.e);}
    | l=additive op='-' r=multitive {$e = new ArithmeticAst.BinaryExpression(ArithmeticAst.Operator.SUBTRACT, $l.e, $r.e);}
    | v=multitive {$e = $v.e;}
    ;

multitive returns [ArithmeticAst.Expression e]
    : l=multitive op='*' r=primary {$e = new ArithmeticAst.BinaryExpression(ArithmeticAst.Operator.MULTIPLY, $l.e, $r.e);}
    | l=multitive op='/' r=primary {$e = new ArithmeticAst.BinaryExpression(ArithmeticAst.Operator.DIVIDE, $l.e, $r.e);}
    | v=primary {$e = $v.e;}
    ;

primary returns [ArithmeticAst.Expression e]
    : v=number {$e = new ArithmeticAst.IntegerLiteral($v.value);}
    | LP r=expression RP {$e = $r.e;}
    ;

number returns [int value]
   : n=NUMBER {$value = Integer.parseInt($n.getText());}
   ;

NUMBER: ('0' | [1-9] [0-9]*); // no leading zeros
PLUS:  '+';
MINUS: '-';
STAR:  '*';
SLASH: '/';
LP:    '(';
RP:    ')';

WS  :   [ \t\n\r]+ -> skip ;
