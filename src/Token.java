public class Token 
{
    public enum tokenType { REGISTER, NUMBER, NEWLINE, MATH, ADD, SUBTRACT, MULTIPLY, AND, OR, NOT, XOR, COPY, HALT,
                            BRANCH, JUMP, CALL, PUSH, LOAD, RETURN, STORE, PEEK, POP, INTERRUPT, EQUAL, UNEQUAL, GREATER, LESS,
                            GREATEROREQUAL, LESSOREQUAL, SHIFT, LEFT, RIGHT}

    private tokenType m_TokenType;
    private String m_Value;

    public Token(tokenType type, String value) 
    {
        this.m_TokenType = type;
        this.m_Value = value;
    }
    public Token(tokenType p_TokenType)
    {
        m_TokenType = p_TokenType;
        m_Value = null;
    }

    public tokenType getType() 
    {
        return m_TokenType;
    }

    public String getValue() 
    {
        return m_Value;
    }
    
    //toString method
    @Override
    public String toString()
    {
        if(this.m_Value == null)    
            return (this.m_TokenType + "");
        return (this.m_TokenType + "(" + this.m_Value + ")");
    }
}
