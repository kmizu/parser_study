grammar Json;

jvalue returns [JsonAst.JValue value]
   : v1=jstring  {$value = $v1.value;}
   | v2=jnumber  {$value = $v2.value;}
   | v3=jobject  {$value = $v3.value;}
   | v4=jarray   {$value = $v4.value;}
   | v5=jboolean {$value = $v5.value;}
   | v6=jnull    {$value = $v6.value;}
   ;

jarray returns [JsonAst.JArray value]
   @init {
     List<JsonAst.JValue> elements = new ArrayList<JsonAst.JValue>();
   }
   : '['
        (v=jvalue {elements.add($v.value);} (',' v=jvalue {elements.add($v.value);})*)?
     ']' {$value = new JsonAst.JArray(elements);}
   ;

jobject returns [JsonAst.JObject value]
   @init {
     List<JsonAst.Pair<String, JsonAst.JValue>> fields = new ArrayList<JsonAst.Pair<String, JsonAst.JValue>>();
   }
   : '{'
        (
          p=pair {fields.add($p.value);} (',' p=pair {fields.add($p.value);})*
        )?
     '}' {$value = new JsonAst.JObject(fields);}
   ;

pair returns [JsonAst.Pair value]
  : k=jstring ':' v=jvalue {$value = new JsonAst.Pair<String, JsonAst.JValue>($k.value.value, $v.value);}
  ;

jstring returns [JsonAst.JString value]
   : s=STRING {$value = new JsonAst.JString($s.getText().substring(1, $s.getText().length() - 1));}
   ;

jnull returns [JsonAst.JNull value]
   : v=NULL {$value = JsonAst.JNull.instance;}
   ;

jnumber returns [JsonAst.JNumber value]
   : n=NUMBER {$value = new JsonAst.JNumber(Double.parseDouble($n.getText()));}
   ;

jboolean returns [JsonAst.JBoolean value]
   : TRUE  {$value = new JsonAst.JBoolean(true);}
   | FALSE {$value = new JsonAst.JBoolean(false);}
   ;

NUMBER
   : ('-')? ('0' | DIGIT19 DIGIT*) ('.' DIGIT*)? ([eE] ('+'|'-')? DIGIT+)
   ;

NULL
   : 'null'
   ;

TRUE
   : 'true'
   ;

FALSE
   : 'false'
   ;

STRING
   : '"' (ESC | ~ ["\\])* '"'
   ;

LBRACKET
   : '['
   ;

COMMA
   : ','
   ;

RBRACKET
   : ']'
   ;

LBRACE
   : '{'
   ;

RBRACE
   : '}'
   ;

LP
   : '('
   ;

RP
   :')'
   ;

COLON
   : ':'
   ;

WS  :   [ \t\n\r]+ -> skip ;

fragment ESC : '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX;
fragment HEX : [0-9a-fA-F] ;
fragment DIGIT: [0-9];
fragment DIGIT19: [1-9];