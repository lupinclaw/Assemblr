//represents our cpus memory
public class MainMemory 
{
    private static Word[] m_memory = new Word[1024];

    //Reading should return a new Word copy of the word at address in meory
    public static Word read(Word address) 
    {
        int index = (int)address.getUnsigned();
        Word retWord = new Word();
        if(m_memory[index] == null) 
        {
            m_memory[index] = new Word();
        }
        retWord.copy(m_memory[index]); 
        return retWord;
    }
    //put a word into mem at address
    public static void write(Word address, Word value) 
    {
        int index = (int)address.getUnsigned();
        if(m_memory[index] == null) 
        {
            m_memory[index] = new Word();
        }
        m_memory[index].copy(value);
    }
    //load a string[] of strings parse into words and load into mem
    public static void load(String[] data) 
    {
        for (int i = 0; i < data.length && i < m_memory.length; i++) 
        {
            Word tempWord = new Word();
            char[] stringWord = data[i].toCharArray();
            for(int j = 0 ; j < stringWord.length; j++)
            {
                if (stringWord[j] == '1')//default word inits with all 0s so we just need to flip 1s
                {
                    tempWord.setBit(j, new Bit(true));
                }
            }
            if(m_memory[i] == null) 
            {
                m_memory[i] = new Word();
            }
            m_memory[i].copy(tempWord); //load into mem
        }
    }
}
