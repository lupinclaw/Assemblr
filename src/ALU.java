//represents our arithmatic logic unit of our chip
public class ALU 
{
    public Word op1 = new Word();
    public Word op2 = new Word();
    public Word result = new Word();
    public Bit boolResult = new Bit();    


    //determines opcode and sets results accordingly
    public void doOperation(Bit[] operation)
    {

        if(operation[0].getValue()) //first bit =1  
        {
            if(operation[1].getValue()) //second bit =1
            {
                if(operation[2].getValue()) //third bit =1
                {
                    if(operation[3].getValue()) // 1111 substract
                    {
                        subtract();
                    }
                    else //1110 add
                    {
                        add();
                    }
                }
                else //110-
                {
                    if(operation[3].getValue()) //1101 rightshift
                    {
                        result.copy(op1.rightShift(op2.getLowerFiveBits())); Processor.CurrentClockCycle += 2;
                    }
                    else //1100
                    {
                        result.copy(op1.leftShift(op2.getLowerFiveBits())); Processor.CurrentClockCycle += 2;
                    }
                }
            }
            else //10--
            {
                if(operation[2].getValue()) //101-
                {
                    if(operation[3].getValue()) //1011 not
                    {
                        result.copy(op1.not()); Processor.CurrentClockCycle += 2;
                    }
                    else //1010 xor
                    {
                        result.copy(op1.xor(op2)); Processor.CurrentClockCycle += 2;
                    }
                }
                else //100-
                {
                    if(operation[3].getValue()) //1001 or
                    {
                        result.copy(op1.or(op2)); Processor.CurrentClockCycle += 2;
                    }
                    else //1000 and
                    {
                        result.copy(op1.and(op2)); Processor.CurrentClockCycle += 2;
                    }
                }
            }
        }
        else //first bit =0 
        {
            if(operation[1].getValue())//01**
            {
                if(operation[2].getValue())//011*
                {
                    if(operation[3].getValue())//0111 mult
                    {
                        multiply();
                    }
                    else//0110 not used
                    {

                    }
                }
                else//010*
                {
                    if(operation[3].getValue())//0101 Less than or equal (le)
                    {
                        subtract();
                        if(result.getBit(0).getValue()) //if the lead sign bit is 1 i.e. is neg
                        {
                            boolResult.set(true);
                        }
                        else if (result.isEmpty())// = 0
                        {
                            boolResult.set(true);
                        }
                        else //not neg and not 0 therefore is pos
                        {
                            boolResult.set(false);
                        }
                    }
                    else//0100 Greater than (gt)
                    {
                        subtract();
                        if(result.getBit(0).getValue()) //if the lead sign bit is 1 i.e. is neg
                        {
                            boolResult.set(false);
                        }
                        else if (result.isEmpty())// = 0
                        {
                            boolResult.set(false);
                        }
                        else //not neg and not 0 therefore is pos
                        {
                            boolResult.set(true);
                        }
                    }
                }
            }
            else//00**
            {
                if(operation[2].getValue())//001
                {
                    if(operation[3].getValue())//0011 Greater than or equal (ge)
                    {
                        subtract();
                        if(result.getBit(0).getValue()) //if the lead sign bit is 1 i.e. is neg
                        {
                            boolResult.set(false);
                        }
                        else if (result.isEmpty())// = 0
                        {
                            boolResult.set(true);
                        }
                        else //not neg and not 0 therefore is pos
                        {
                            boolResult.set(true);
                        }
                    }
                    else//0010 Less than (lt)
                    {
                        subtract();
                        if(result.getBit(0).getValue()) //if the lead sign bit is 1 i.e. is neg
                        {
                            boolResult.set(true);
                        }
                        else if (result.isEmpty())// = 0
                        {
                            boolResult.set(false);
                        }
                        else //not neg and not 0 therefore is pos
                        {
                            boolResult.set(false);
                        }
                    }
                }
                else//000*
                {
                    if(operation[3].getValue())//0001 Not Equal (neq)
                    {
                        subtract();
                        if(result.getBit(0).getValue()) //if the lead sign bit is 1 i.e. is neg
                        {
                            boolResult.set(true);
                        }
                        else if (result.isEmpty())// = 0
                        {
                            boolResult.set(false);
                        }
                        else //not neg and not 0 therefore is pos
                        {
                            boolResult.set(true);
                        }
                    }
                    else//0000 Equals (eq)
                    {
                        subtract();
                        if(result.getBit(0).getValue()) //if the lead sign bit is 1 i.e. is neg
                        {
                            boolResult.set(false);
                        }
                        else if (result.isEmpty())// = 0
                        {
                            boolResult.set(true);
                        }
                        else //not neg and not 0 therefore is pos
                        {
                            boolResult.set(false); 
                        }
                    }
                }            
            }
        }

    }

    //adds 2 words together bit by bit with carry
    public Word add2(Word a, Word b)
    {
        Word retWord = new Word();
        Bit carry = new Bit();
        Bit sum = new Bit();

        for (int i = 31; i >= 0; i--)
        {
            sum = a.getBit(i).xor(b.getBit(i).xor(carry));
            carry = a.getBit(i).and(b.getBit(i)).or((a.getBit(i).xor(b.getBit(i))).and(carry));
            retWord.setBit(i, sum);
        }
        return retWord;
    }
    //adds 4 words together bit by bit with carry
    public Word add4(Word a, Word b, Word c, Word d)
    {
        Word retWord = new Word();
        Word temp1 = new Word();
        Word temp2 = new Word();
        Bit carry = new Bit();
        Bit over = new Bit();
        Bit sum = new Bit();

        for (int i = 31; i >= 0; i--)
        {
            sum = a.getBit(i).xor(b.getBit(i).xor(carry));
            carry = a.getBit(i).and(b.getBit(i)).or((a.getBit(i).xor(b.getBit(i))).and(carry));
            temp1.setBit(i, sum);
            sum = c.getBit(i).xor(d.getBit(i).xor(over));
            over = c.getBit(i).and(d.getBit(i)).or((c.getBit(i).xor(d.getBit(i))).and(over));
            temp2.setBit(i, sum);
        }
        for (int i = 31; i >= 0; i--)
        {
            sum = temp1.getBit(i).xor(temp2.getBit(i).xor(carry));
            carry = temp1.getBit(i).and(temp2.getBit(i)).or((temp1.getBit(i).xor(temp2.getBit(i))).and(carry));
            retWord.setBit(i, sum);
        }
        return retWord;
    }
    //adds op1 and op2 and sets result
    private void add() 
    {
        Processor.CurrentClockCycle += 2;
        Word temp = add2(op1, op2);
        result.copy(temp);
    }
    //multiplys op1 and op2 and sets result
    private void multiply() 
    {
        Processor.CurrentClockCycle += 10;
        Word[] words = new Word[32];
        //make the words
        for (int i = 31; i >= 0; i--) 
        {
            if(op2.getBit(i).getValue())
            {
                words[i] = (op1.leftShift(31-i));
            }
            else
            {
                words[i] = new Word();
            }
        }
        // Round 1 - 8 4-way adders
        Word sum1 = add4(words[0], words[1], words[2], words[3]);
        Word sum2 = add4(words[4], words[5], words[6], words[7]);
        Word sum3 = add4(words[8], words[9], words[10], words[11]);
        Word sum4 = add4(words[12], words[13], words[14], words[15]);
        Word sum5 = add4(words[16], words[17], words[18], words[19]);
        Word sum6 = add4(words[20], words[21], words[22], words[23]);
        Word sum7 = add4(words[24], words[25], words[26], words[27]);
        Word sum8 = add4(words[28], words[29], words[30], words[31]);
        // Round 2 - 2 4-way adders
        Word temp1 = add4(sum1, sum2, sum3, sum4);
        Word temp2 = add4(sum5, sum6, sum7, sum8);
        // Round 3 - 2-way adder
        Word finalSum = add2(temp1, temp2);
        //set result
        result.copy(finalSum);
    }
    //subtracts op1 and op2 and sets result
    private void subtract() 
    {
        Processor.CurrentClockCycle += 2;
        result.copy(add2(op1, op2.not().addOne()));
    }
    
}




