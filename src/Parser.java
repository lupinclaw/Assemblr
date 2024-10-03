import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//parser makes bninary output
public class Parser 
{
    //FileWriter writer;
    PrintWriter out;
    ArrayList<Token> m_tokens;
    StringBuilder instruction = new StringBuilder("00000000000000000000000000000000");
    
    //constructer
    public Parser(String p_outputFileName) throws Exception
    {
        out = new PrintWriter(p_outputFileName);
    }

    public void parse(ArrayList<Token> p_tokens) throws Exception
    {
        m_tokens = p_tokens;

        
        while(m_tokens.size() > 0)
        {
            statment();
            System.out.println(instruction.toString());
            out.println(instruction.toString());
            instruction.replace(0, 32, "00000000000000000000000000000000");
        }
        out.close();
    }
    public void statment()
    {
        if(matchAndRemove(Token.tokenType.NEWLINE) != null)
            return;
        if(matchAndRemove(Token.tokenType.MATH) != null)
            math();
        else if(matchAndRemove(Token.tokenType.BRANCH) != null)
            branch();
        else if(matchAndRemove(Token.tokenType.HALT) != null)
            halt();
        else if(matchAndRemove(Token.tokenType.COPY) != null)
            copy();
        else if(matchAndRemove(Token.tokenType.JUMP) != null)
            jump();
        else if(matchAndRemove(Token.tokenType.CALL) != null)
            call();
        else if(matchAndRemove(Token.tokenType.PUSH) != null)
            push();
        else if(matchAndRemove(Token.tokenType.POP) != null)
            pop();
        else if(matchAndRemove(Token.tokenType.LOAD) != null)
            load();
        else if(matchAndRemove(Token.tokenType.STORE) != null)
            store();
        else if(matchAndRemove(Token.tokenType.RETURN) != null)
            returns();
        else if(matchAndRemove(Token.tokenType.PEEK) != null)
            copy();
        else if(matchAndRemove(Token.tokenType.INTERRUPT) != null)
            interrupt();
            
    }
    public void math()
    {
        instruction.replace(27,30,"000");
        if(matchAndRemove(Token.tokenType.AND) != null)
        {
            instruction.replace(18,22,"1000");
            parseParams();
        }
        else if(matchAndRemove(Token.tokenType.OR) != null)
        {
            instruction.replace(18,22,"1001");
            parseParams();
        }
        else if(matchAndRemove(Token.tokenType.XOR) != null)
        {
            instruction.replace(18,22,"1010");
            parseParams();
        }
        else if(matchAndRemove(Token.tokenType.NOT) != null)
        {
            instruction.replace(18,22,"1011");
            parseParams();
        }
        else if(matchAndRemove(Token.tokenType.LEFT) != null)
        {
            instruction.replace(18,22,"1100");
            shift();
        }
        else if(matchAndRemove(Token.tokenType.RIGHT) != null)
        {
            instruction.replace(18,22,"1101");
            shift();
        }
        else if(matchAndRemove(Token.tokenType.ADD) != null)
        {
            instruction.replace(18,22,"1110");
            parseParams();
        }
        else if(matchAndRemove(Token.tokenType.SUBTRACT) != null)
        {
            instruction.replace(18,22,"1111");
            parseParams();
        }
        else if(matchAndRemove(Token.tokenType.MULTIPLY) != null)
        {
            instruction.replace(18,22,"1000");
            parseParams();
        }
    }
    public void shift()
    {
        parseParams();
    }
    public void copy()
    {
        instruction.replace(27,32, "00001");
        Token temptoken = matchAndRemove(Token.tokenType.NUMBER);
        String val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(13,18, val);

    }
    public void jump()
    {
        instruction.replace(27,32, "00100");
        Token temptoken = matchAndRemove(Token.tokenType.NUMBER);
        if(temptoken != null)
        {
            parseParams();
            String val = numtoBin(18, Integer.parseInt(temptoken.getValue()));
            instruction.replace(0,18, val);
        }
    }
    public void interrupt()
    {
        instruction.replace(27,32,"11000");
        parseParams();
    }
    public void store()
    {
        instruction.replace(27,30,"101");
        Token temptoken = matchAndRemove(Token.tokenType.NUMBER);
        if(temptoken != null)
        {
            parseParams();
            String val = numtoBin(18, Integer.parseInt(temptoken.getValue()));
            instruction.replace(0,18, val);
        }
    }
    public void load()
    {
        instruction.replace(27,30,"100");
        Token temptoken = matchAndRemove(Token.tokenType.NUMBER);
        if(temptoken != null)
        {
            parseParams();
            String val = numtoBin(18, Integer.parseInt(temptoken.getValue()));
            instruction.replace(0,18, val);
        }
    }
    public void push()
    {
        instruction.replace(27,30,"011");
        Token temptoken = matchAndRemove(Token.tokenType.NUMBER);
        if(temptoken != null)
        {
            String val = numtoBin(18, Integer.parseInt(temptoken.getValue()));
            instruction.replace(0,18, val);
        }
        parseParams();
    }
    public void pop()
    {
        instruction.replace(27,30,"110");
        parseParams();
    }
    public void call()
    {
        instruction.replace(27,30,"010");
        parseParams();
    }
    public void halt()
    {
        instruction.replace(0,32,"00000000000000000000000000000000");
        matchAndRemove(Token.tokenType.NEWLINE);
    }
    public void returns()
    {
        instruction.replace(0,32,"00000000000000000000000000011000");
        matchAndRemove(Token.tokenType.NEWLINE);
    }
    public void branch()
    {
        instruction.replace(27,30,"001");
        parseParams();
    }
    public void twoR()
    {
        instruction.replace(30,32,"11");
        Token temptoken = matchAndRemove(Token.tokenType.REGISTER);
        String val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(13,18, val);

        temptoken = matchAndRemove(Token.tokenType.REGISTER);
        val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(22,27, val);

        matchAndRemove(Token.tokenType.NEWLINE);
    }
    public void threeR()
    {
        instruction.replace(30,32,"10");
        Token temptoken = matchAndRemove(Token.tokenType.REGISTER);
        String val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(8,13, val);

        temptoken = matchAndRemove(Token.tokenType.REGISTER);
        val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(13,18, val);

        temptoken = matchAndRemove(Token.tokenType.REGISTER);
        val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(22,27, val);
        
        matchAndRemove(Token.tokenType.NEWLINE);
    }
    public void destOnly()
    {
        instruction.replace(30,32,"01");
        Token temptoken = matchAndRemove(Token.tokenType.REGISTER);
        String val = numtoBin(5, Integer.parseInt(temptoken.getValue()));
        instruction.replace(22,27, val);

        matchAndRemove(Token.tokenType.NEWLINE);
    }
    public void noR()
    {
        instruction.replace(30,32,"00");
        matchAndRemove(Token.tokenType.NEWLINE);
    }

    public void parseParams()
    {
        int numbReg = 0;
        Token tempToken = m_tokens.get(0);

        for(int i = 1; !tempToken.getType().equals(Token.tokenType.NEWLINE); i++)
        {
            if(tempToken.getType().equals(Token.tokenType.REGISTER))
            {
                numbReg++;
            }
            tempToken = m_tokens.get(i);
        }
        if( numbReg == 0)
        {
            noR();
        }
        if( numbReg == 1)
        {
            destOnly();
        }
        if( numbReg == 2)
        {
            twoR();
        }
        if( numbReg == 3)
        {
            threeR();
        }

    }


    //match the types of the passed tokentype to the toketype of the next token in the list
    public Token matchAndRemove(Token.tokenType p_GivenTokenType)
    {
        if(m_tokens.size() <= 0)
            return null;
        if(m_tokens.get(0).getType() == p_GivenTokenType)
        {
            Token returnToken = m_tokens.get(0);
            m_tokens.remove(0);
            return returnToken;
        }
        else
            return null;
    } 


    public String numtoBin(int size, int value)
    {
        StringBuilder temp = new StringBuilder();

        for(int i = 0; i < size; i++)
        {
            if((value % 2) == 1)
            {
                temp.insert(0, "1");
            }
            else
            {
                temp.insert(0, "0");
            }
            value /= 2; // right shift by dividing by 2
        }
        return temp.toString();
    }


}
