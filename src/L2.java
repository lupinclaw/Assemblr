//implementaion of L2 cache
public class L2 
{
    static Word group1[] = new Word[8];
    static Word group2[] = new Word[8];
    static Word group3[] = new Word[8];
    static Word group4[] = new Word[8];

    static Word cache[][] = {group1, group2, group3, group4};

    static Word address1 = new Word();
    static Word address2 = new Word();
    static Word address3 = new Word();
    static Word address4 = new Word();

    //Reading should return a new Word copy of the word at address in meory
    public static Word read(Word address) 
    {
        int index = (int)address.getUnsigned();

        Word retWord = new Word();

        for(int i = 0; i < 4 ; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(cache[i][j] == null) 
                {
                    cache[i][j] = new Word(); continue;
                }
                if(index == (int)cache[i][j].getUnsigned()) //a hit
                {
                    Processor.CurrentClockCycle += 20;
                    retWord.copy(cache[i][j]);
                    return retWord;
                }
            }
        }
        //miss
        Processor.CurrentClockCycle += 350;
        retWord.copy(MainMemory.read(address));
        cache[0][0].copy(MainMemory.read(address));
        address1.copy(cache[0][0]);
        for(int i = 1; i < 8; i++)
        {
            cache[0][i].copy(MainMemory.read(address.addOne()));
        }
        return retWord;
    }
    
}
