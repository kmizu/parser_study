grammar SimpleExpression;

expression returns [SimpleExpressionAst.Expression e]
   : l=number '+' r=number {$e = new SimpleExpressionAst.BinaryExpression(SimpleExpressionAst.Operator.ADD, $l.value, $r.value);}
   | l=number '-' r=number {$e = new SimpleExpressionAst.BinaryExpression(SimpleExpressionAst.Operator.SUBTRACT, $l.value, $r.value);}
   | l=number '*' r=number {$e = new SimpleExpressionAst.BinaryExpression(SimpleExpressionAst.Operator.MULTIPLY, $l.value, $r.value);}
   | l=number '/' r=number {$e = new SimpleExpressionAst.BinaryExpression(SimpleExpressionAst.Operator.DIVIDE, $l.value, $r.value);}
   | v=number {$e = $v.value;}
   ;

number returns [SimpleExpressionAst.NumberExpression value]
   : n=NUMBER {$value = new SimpleExpressionAst.NumberExpression(Integer.parseInt($n.getText()));}
   ;

NUMBER: ('0' | [1-9] [0-9]*); // no leading zeros
PLUS:  '+';
MINUS: '-';
STAR:  '*';
SLASH: '/';

WS  :   [ \t\n\r]+ -> skip ;
