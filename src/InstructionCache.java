//L1 cache implementation
public class InstructionCache 
{
    public static Word chache[] = new Word[8];
    public static Word cacheAddress = new Word();


    //Reading should return a new Word copy of the word at address in meory
    public static Word read(Word address) 
    {
        int index = (int)address.getUnsigned();

        Word retWord = new Word();

        for(int i = 0; i < 8; i++)
        {
            if(chache[i] == null) 
            {
                chache[i] = new Word(); continue;
            }
            if(index == (int)chache[i].getUnsigned()) //a hit
            {
                Processor.CurrentClockCycle += 10;
                retWord.copy(chache[i]);
                return retWord;
            }
        }
        //miss
        Processor.CurrentClockCycle += 50;
        retWord.copy(L2.read(address));
        chache[0].copy(L2.read(address));

        for(int i = 1; i < 8; i++)
        {
            chache[i].copy(L2.read(address.addOne()));
        }

        cacheAddress.copy(chache[0]);
        return retWord;
    }
    
}
