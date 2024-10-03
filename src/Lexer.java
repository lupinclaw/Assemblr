import java.util.ArrayList;
import java.util.HashMap;


 //the lexer gens tokens
public class Lexer 
{
    public enum state {NONE, KEYWORD, REGISTER, NUMBER}

    public ArrayList<Token> Tokens;
    private state m_CurrentState;
    private String m_Accumulator;
    private HashMap<String, Token.tokenType> m_KnownWords = new HashMap<String, Token.tokenType>();

    //constructer
    public Lexer() throws Exception
    {
        m_CurrentState = state.NONE;
        m_Accumulator = "";
        Tokens = new ArrayList<Token>();

        fillKnownWords();
    }

    public void lex(String p_inputLine)
    {
        char[] inputAsCharArray = p_inputLine.toCharArray();

        for(char c : inputAsCharArray)
        {
            switch(m_CurrentState)
            {
                case NONE:
                    if (Character.isLetter(c) && c == 'R')
                    {
                        m_CurrentState = state.REGISTER;
                        break;
                    }
                    else if(Character.isLetter(c) && c != 'R')
                    {
                        m_Accumulator += c;
                        m_CurrentState = state.KEYWORD;
                        break;
                    }
                    else if (Character.isDigit(c))
                    {
                        m_Accumulator += c;
                        m_CurrentState = state.NUMBER;
                        break;
                    }
                    else if (c == ' ')
                    {
                        break;
                    }
                case REGISTER: 
                    if (Character.isDigit(c))
                    {
                        m_Accumulator += c;
                        break;
                    }
                    else if(c == ' ')
                    {
                        Tokens.add(new Token(Token.tokenType.REGISTER, m_Accumulator));
                        m_Accumulator ="";
                        m_CurrentState = state.NONE;
                        break;
                    }
                case KEYWORD: 
                    if (c == ' ' )
                    {
                        Tokens.add(new Token(m_KnownWords.get(m_Accumulator)));
                        m_Accumulator = "";
                        m_CurrentState = state.NONE;
                        break;
                    }
                    else if(Character.isLetter(c))
                    {
                        m_Accumulator += c;
                        break;
                    }
                case NUMBER: 
                    if (c == ' ' )
                    {
                        Tokens.add(new Token(Token.tokenType.NUMBER, m_Accumulator));
                        m_Accumulator = "";
                        m_CurrentState = state.NONE;
                        break;
                    }
                    else if (Character.isDigit(c))
                    {
                        m_Accumulator += c;
                        break;
                    } 
                default:
                    break;     
            }
        }
        if (m_Accumulator != "")  //if we are at the end of a line of all text that never hit a space to emit a token, we add that to Tokens here
        {
            if(m_CurrentState == state.REGISTER)
                Tokens.add(new Token(Token.tokenType.REGISTER, m_Accumulator));
            if(m_CurrentState == state.NUMBER)
                Tokens.add(new Token(Token.tokenType.NUMBER, m_Accumulator));
            if(m_CurrentState == state.KEYWORD)
                Tokens.add(new Token(m_KnownWords.get(m_Accumulator)));

            m_Accumulator = "";
            m_CurrentState = state.NONE;
        }

        Tokens.add(new Token(Token.tokenType.NEWLINE));
    }
    //shows lexed tokens
    public void showTokens()
    {
        for(Token token : Tokens)
        {
            if(token.getType().equals(Token.tokenType.NEWLINE))
                System.out.print(token.toString() +'\n');
            else
                System.out.print(token.toString()+" ");
        }
    }
    //fill map with keywords
    private void fillKnownWords() //commenting out built in functions so parser will parse them as identifiers and call parse functionNode on them
    {
        //math, add, subtract, multiply, and, or, not, xor, copy, halt, 
        //branch, jump, call, push, load, return, store, peek, pop, interrupt, equal, unequal, greater, less, greaterOrEqual, lessOrEqual, shift, left, right
        m_KnownWords.put("math", Token.tokenType.MATH);
        m_KnownWords.put("add", Token.tokenType.ADD);
        m_KnownWords.put("subtract", Token.tokenType.SUBTRACT);
        m_KnownWords.put("multiply", Token.tokenType.MULTIPLY);
        m_KnownWords.put("and", Token.tokenType.AND);
        m_KnownWords.put("or", Token.tokenType.OR);
        m_KnownWords.put("not", Token.tokenType.NOT);  
        m_KnownWords.put("xor", Token.tokenType.XOR);
        m_KnownWords.put("copy", Token.tokenType.COPY);
        m_KnownWords.put("halt", Token.tokenType.HALT);
        m_KnownWords.put("branch", Token.tokenType.BRANCH);
        m_KnownWords.put("jump", Token.tokenType.JUMP);
        m_KnownWords.put("call", Token.tokenType.CALL);
        m_KnownWords.put("push", Token.tokenType.PUSH);
        m_KnownWords.put("load", Token.tokenType.LOAD);
        m_KnownWords.put("return", Token.tokenType.RETURN);
        m_KnownWords.put("store", Token.tokenType.STORE);
        m_KnownWords.put("peek", Token.tokenType.PEEK);
        m_KnownWords.put("pop", Token.tokenType.POP);
        m_KnownWords.put("interrupt", Token.tokenType.INTERRUPT);
        m_KnownWords.put("equal", Token.tokenType.EQUAL);
        m_KnownWords.put("unequal", Token.tokenType.UNEQUAL);
        m_KnownWords.put("greater", Token.tokenType.GREATER);
        m_KnownWords.put("less", Token.tokenType.LESS);
        m_KnownWords.put("greaterOrEqual", Token.tokenType.GREATEROREQUAL);
        m_KnownWords.put("lessOrEqual", Token.tokenType.LESSOREQUAL);
        m_KnownWords.put("shift", Token.tokenType.SHIFT);
        m_KnownWords.put("left", Token.tokenType.LEFT);
        m_KnownWords.put("right", Token.tokenType.RIGHT);
    }
}
