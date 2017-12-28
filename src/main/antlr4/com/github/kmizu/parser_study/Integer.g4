grammar Integer;

integer returns [int value]
   : n=INT {$value = Integer.parseInt($n.getText());}
   ;

INT :   ('+' | '-')? ('0' | [1-9] [0-9]*) ; // no leading zeros
