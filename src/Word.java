import java.lang.Math;

//represents a word as an array of 32 Bits with leftmost bit being the least significant
public class Word 
{
    //member
    private Bit[] m_Word = new Bit[32];

    //constructor
    public Word()
    {
        for(int i = 31; i>=0; i--)
        {
            m_Word[i] = new Bit();//fill with empty bits
        }
    }
    public Word(int p_Value)
    {
        for(int i = 31; i>=0; i--)
        {
            m_Word[i] = new Bit();//fill with empty bits
        }
        this.set(p_Value);
    }
    public Word(String p_Value)
    {
        char[] binary = p_Value.toCharArray();

        for(int i = 31; i>=0; i--)
        {
            if(binary[i] == '1')
            {
                m_Word[i] = new Bit(true);
            }
            else
            {
                m_Word[i] = new Bit();
            }   
        }
    }

    // Get a new Bit that has the same value as bit i
    public Bit getBit(int i)
    {
        return new Bit(m_Word[i].getValue());
    }
    // set bit i's value
    public void setBit(int i, Bit value)
    {
        this.m_Word[i] = value;
    }
    // and two words, returning a new Word
    public Word and(Word other)
    {
        Word retWord = new Word();
        for(int i = 31; i>=0; i--)
        {
            Bit andValue = this.getBit(i).and(other.getBit(i)); //and the two bits
            retWord.setBit(i,andValue); //set value equal to the bit returned from and()
        }
        return retWord;
    }
    // or two words, returning a new Word
    public Word or(Word other)
    {
        Word retWord = new Word();
        for(int i = 31; i>=0; i--)
        {
            Bit andValue = this.getBit(i).or(other.getBit(i)); //or the two bits
            retWord.setBit(i,andValue); //set value equal to the bit returned from or()
        }
        return retWord;
    }
    // xor two words, returning a new Word
    public Word xor(Word other)
    {
        Word retWord = new Word();
        for(int i = 31; i>=0; i--)
        {
            Bit andValue = this.getBit(i).xor(other.getBit(i)); //xor the two bits
            retWord.setBit(i,andValue); //set value equal to the bit returned from xor()
        }
        return retWord;
    }
    // negate this word, creating a new Word
    public Word not()
    {
        Word retWord = new Word();

        for(int i = 31; i>=0; i--)
        {
            Bit notValue = this.getBit(i).not(); // not the bit
            retWord.setBit(i, notValue); // set value equal to the bit returned from not()
        }
        return retWord;
    }
    // right shift this word by amount bits, creating a new Word
    public Word rightShift(int amount)
    {
        Word retWord = new Word();
        for(int i = 31; i >= amount; i--)
        {
            retWord.setBit(i, this.getBit(i - amount)); // right shift by copying bits to the right
        }
        return retWord;    
    }
    // left shift this word by amount bits, creating a new Word
    public Word leftShift(int amount)
    {
        Word retWord = new Word();
        for(int i = 0; i <= 31 - amount; i++)
        {
            retWord.setBit(i, this.getBit(i + amount)); // left shift by copying bits to the left
        }
        return retWord;
    }
    // returns a comma separated string t’s and f’s
    public String toString()
    {
        StringBuilder retString = new StringBuilder();
        for(int i = 0; i < 32; i++)
        {
            retString.append(this.getBit(i).toString()); // 't' for true, 'f' for false using the toString from the bit obj
            if(i < 31)
            {
                retString.append(','); // add comma except for the last bit
            } 
        }
        return retString.toString();
    }
    // returns the value of this word as a long
    public long getUnsigned()
    {
        long retVal = 0;
        for(int i = 31; i>=0; i--)
        {
            if(this.getBit(i).getValue()) //if true bit is 1
            {
                retVal += (Math.pow(2, 31-i)); //add 2^signicance of the index (leftmost is least significant)
            }
        }
        return retVal;
    }
    // returns the value of this word as an int
    public int getSigned()
    {
        int retVal = 0;
        if (this.getBit(0).getValue()) // Check if the sign is negative
        {
            //apply/reserve 2s comp
            this.copy(this.not()); //not the word
            this.copy(this.addOne()); //add one
            retVal = -(int)this.getUnsigned(); //get unsigned(absolute) value and cast to int and make it negative
        } 
        else 
        {
            retVal = (int)this.getUnsigned(); // if pos use unsigend and cast to int
        }
        return retVal;
    }
    // copies the values of the bits from another Word into this one
    public void copy(Word other)
    {
        for(int i = 31; i>=0; i--)
        {
            this.setBit(i, other.getBit(i));
        }
    }
    // set the value of the bits of this Word (used for tests)
    public void set(int value)
    {
        if (value < 0) //if neg make 2's compliment
        {
            value *= -1; //get absolute value in binary
            for (int i = 31; i >= 0; i--) //convert to bits
            {
                this.setBit(i, new Bit((value % 2) == 1)); // set bits based on the remainder
                value /= 2; // right shift by dividing by 2
            }
            this.copy(this.not()); //not the word
            this.copy(this.addOne()); //add one
        }
        else //else set normal value
        {
            for (int i = 31; i >= 0; i--) //convert to bits
            {
                this.setBit(i, new Bit((value % 2) == 1)); // set bits based on the remainder
                value /= 2; // right shift by dividing by 2
            }
        }
    }
    //adds one bit to a word with carry
    public Word addOne() //2s compliment helper function
    {
        Word retWord = new Word();
        retWord.copy(this);

        for (int i = 31; i >= 0; i--)
        {
            Bit temp = this.getBit(i).xor(new Bit(true)); 
            if(temp.getValue()) 
            {
                retWord.setBit(i, temp);
                break;
            }
            retWord.setBit(i, temp);
        }
        return retWord;
    }
    // returns the value of the lowest 5 bits for shifting purposes
    public int getLowerFiveBits()
    {
        int retVal = 0;
        for(int i = 31; i>=27; i--)
        {
            if(this.getBit(i).getValue()) //if true bit is 1
            {
                retVal += (Math.pow(2, 31-i)); //add 2^signicance of the index (leftmost is least significant)
            }
        }
        return retVal;
    }
    //checks if a word is empty
    public boolean isEmpty()
    {
        boolean retVal = true;
        for(int i = 31; i>=27; i--)
        {
            if(this.getBit(i).getValue())
            {
                retVal=false;
            }
        }
        return retVal;
    }
    //increment word for processor
    public void increment()
    {
        this.copy(addOne());
    }
    //decrement word for processor
    public void decrement()
    {
        Word temp = new Word(this.getSigned()-1);
        this.copy(temp);
    }
}
