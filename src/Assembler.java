//our assembler 

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//given assembly language input, outputs a 'bianry' file our load() can use
public class Assembler 
{
    public static void main(String args[]) throws Exception
    {
        String inputFilename = args[0]; 
        String outputFilename = args[1]; 

        Path filePath = Paths.get(inputFilename); 
        // Read all lines into a List
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        Lexer lexer = new Lexer();
        Parser parser = new Parser(outputFilename);

        for(String line : lines)
        { 
            lexer.lex(line);
        }

        lexer.showTokens();//test

        parser.parse(lexer.Tokens);

    }

}
