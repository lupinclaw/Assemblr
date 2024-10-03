//represents our processor abstraction

import java.util.Arrays;

public class Processor 
{
    public static int CurrentClockCycle;

    private Word PC;
    private Word SP;
    private Word currentInstruction;
    private Bit halted;
    private ALU m_ALU;

    //storage for decode() -> execute()
    private Word immediate = new Word();
    private Word Rd = new Word();
    private Word function = new Word();
    private Word Rs1 = new Word();
    private Word Rs2 = new Word();

    //registers
    private final Word R0 = new Word();
    private Word R1 = new Word();
    private Word R2 = new Word();
    private Word R3 = new Word();
    private Word R4 = new Word();
    private Word R5 = new Word();
    private Word R6 = new Word();
    private Word R7 = new Word();
    private Word R8 = new Word();
    private Word R9 = new Word();
    private Word R10 = new Word();
    private Word R11 = new Word();
    private Word R12 = new Word();
    private Word R13 = new Word();
    private Word R14 = new Word();
    private Word R15 = new Word();
    private Word R16 = new Word();
    private Word R17 = new Word();
    private Word R18 = new Word();
    private Word R19 = new Word();
    private Word R20 = new Word();
    private Word R21 = new Word();
    private Word R22 = new Word();
    private Word R23 = new Word();
    private Word R24 = new Word();
    private Word R25 = new Word();
    private Word R26 = new Word();
    private Word R27 = new Word();
    private Word R28 = new Word();
    private Word R29 = new Word();
    private Word R30 = new Word();
    private Word R31 = new Word();
    private Word[] m_Registers = {R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27, R28, R29, R30, R31};
    //so ik this looks crazy but i made R0 an object with final and just substantiated all the registers as objects before putting them into an array so R0 could stay read only
    //ive been flip flopping languages so i wasnt sure about javas behaviour if i just assigned the first word objhect in an array to a final object if it would pass the finality to the other object


    //constructor
    public Processor() 
    {
        CurrentClockCycle = 0;
        PC = new Word();
        SP = new Word(1024);
        currentInstruction = new Word();
        halted = new Bit();
        m_ALU = new ALU();
    }
    //the main processor loop
    public void run() 
    {
        while (!halted.getValue()) 
        {
            fetch();
            decode();
            execute();
            store();
        }
    }
    //gets loads the word at PC into the currentinstructioon and increments the PC
    private void fetch() 
    {
        currentInstruction = InstructionCache.read(PC);  System.out.println("Current instruction is " + currentInstruction.toString() + " PC is " + PC.getSigned() + " SP is " + SP.getSigned());
        PC.increment();
    }
    //decodes the opcode
    private void decode() 
    {
        Word mask = new Word("00000000000000000000000000011111");
        Word opCode = currentInstruction.and(mask);
        Word registerNumber = new Word();
        Word register;

        if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
        {
            //get the immediate value from the instruction
            mask = new Word("11111111111111111111111111100000");
            immediate = (currentInstruction.and(mask)).rightShift(5);

            //test output
            System.out.print("NO R ");
        }
        else if (!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
        {
            //Get the Rd value from the registers and the immediate value from the instruction
            mask = new Word("11111111111111111100000000000000");
            immediate = (currentInstruction.and(mask)).rightShift(14);
            mask = new Word("00000000000000000000001111100000");
            registerNumber = (currentInstruction.and(mask)).rightShift(5);
            register = regAddressDecodeIfBlock(registerNumber);
            Rd.copy(register); 
            //test output
            System.out.println("Rd is at R" + Arrays.asList(m_Registers).indexOf(register) + " and holds " + register.getSigned());
            System.out.print("Dest Only ");
        }
        else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
        {
            //Get the Rs1, Rs2 and Rd values from the registers and the immediate value from the instruction
            mask = new Word("11111111000000000000000000000000");
            immediate = (currentInstruction.and(mask)).rightShift(24);

            mask = new Word("00000000000000000000001111100000");
            registerNumber = (currentInstruction.and(mask)).rightShift(5);
            register = regAddressDecodeIfBlock(registerNumber);
            Rd.copy(register);
            //test output
            System.out.println("Rd is at R" + Arrays.asList(m_Registers).indexOf(register) + " and holds " + register.getSigned());

            mask = new Word("00000000111110000000000000000000");
            registerNumber = (currentInstruction.and(mask)).rightShift(19);
            register = regAddressDecodeIfBlock(registerNumber);
            Rs1.copy(register);
            //test output
            System.out.println("Rs1 is at R" + Arrays.asList(m_Registers).indexOf(register) + " and holds " + register.getSigned());
            mask = new Word("00000000000001111100000000000000");
            registerNumber = (currentInstruction.and(mask)).rightShift(14);
            register = regAddressDecodeIfBlock(registerNumber);
            Rs2.copy(register); 
            //test output
            System.out.println("Rs2 is at R" + Arrays.asList(m_Registers).indexOf(register) + " and holds " + register.getSigned());
            System.out.print("3R ");
        }
        else //11 2R
        {
            //Get the Rs and Rd values from the registers and the immediate value from the instruction
            mask = new Word("11111111111110000000000000000000");
            immediate = (currentInstruction.and(mask)).rightShift(19);

            mask = new Word("00000000000000000000001111100000");
            registerNumber = (currentInstruction.and(mask)).rightShift(5);
            register = regAddressDecodeIfBlock(registerNumber);
            Rd.copy(register); 
            //test output
            System.out.println("Rd is at R" + Arrays.asList(m_Registers).indexOf(register) + " and holds " + register.getSigned());

            mask = new Word("00000000000001111100000000000000");
            registerNumber = (currentInstruction.and(mask)).rightShift(14);
            register = regAddressDecodeIfBlock(registerNumber);
            Rs1.copy(register);  
            //test output
            System.out.println("Rs1 is at R" + Arrays.asList(m_Registers).indexOf(register) + " and holds " + register.getSigned());
            System.out.print("2R ");
        }
    }
    // executes the op code
    private void execute() 
    {
        Word mask = new Word("00000000000000000000000000011100");
        Word opCode = (currentInstruction.and(mask)).rightShift(2);
        Bit[] addCode = {new Bit(true), new Bit(true), new Bit(true), new Bit(false)}; // for pc + 

        if(!opCode.getBit(29).getValue() && !opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //000 mathOp 
        {
            //test output
            System.out.println("MATH");

            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};

            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
            {
                //halt
                halted.set(true);
            }
            else if (!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
            {
                //COPY: Rd <- imm
                Rd.copy(immediate);

            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                //Rd <- Rs1 MOP Rs2
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rs2);
                m_ALU.doOperation(MOP);
                Rd.copy(m_ALU.result);

                //test output
                System.out.println("OP1 = " + m_ALU.op1.getSigned() + "\nOP2 = " + m_ALU.op2.getSigned() + "\nresult = " + m_ALU.result.getSigned());
            }
            else //11 2R
            {            
                // Rd <- Rd MOP Rs
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(Rs1);
                m_ALU.doOperation(MOP);
                Rd.copy(m_ALU.result);

                //test output
                System.out.println("OP1 =" + m_ALU.op1.getSigned() + "\nOP2 = " + m_ALU.op2.getSigned() + "\nresult = " + m_ALU.result.getSigned());
            }
        }
        else if(!opCode.getBit(29).getValue() && !opCode.getBit(30).getValue() && opCode.getBit(31).getValue())//001 branch
        {
            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};
            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R JUMP
            {
                //pc <- imm
                PC.copy(immediate);

                //test output
                System.out.println("JUMP to " + PC.getSigned());
            }
            else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only JUMP
            {
                //pc <- pc + imm
                m_ALU.op1.copy(PC);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(MOP);
                PC.copy(m_ALU.result);
                //test output

                System.out.println("JUMP to " + PC.getSigned());
            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                //test output
                System.out.print("BRANCH");

                //pc <- Rs1 BOP Rs2 ? pc + imm : pc
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rs2);
                m_ALU.doOperation(MOP);

                if(m_ALU.boolResult.getValue())
                {
                    m_ALU.op1.copy(PC);
                    m_ALU.op2.copy(immediate);
                    m_ALU.doOperation(addCode);

                    PC.copy(m_ALU.result);
                    //test output
                    System.out.println(" evaluated true branch to PC = " + PC.getSigned());
                }
                //test output
                System.out.println(" false branch");
            }
            else //11 2R
            {
                //test output
                System.out.print("BRANCH");

                //pc <- Rs1 BOP Rd ? pc + imm : pc
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rd);
                m_ALU.doOperation(MOP);

                if(m_ALU.boolResult.getValue())
                {
                    m_ALU.op1.copy(PC);
                    m_ALU.op2.copy(immediate);
                    m_ALU.doOperation(addCode);

                    PC.copy(m_ALU.result);
                    //test output
                    System.out.println(" evaluated true branch to PC = " + PC.getSigned());
                }
                else
                {
                    //test output
                    System.out.println(" false branch");
                }
                
            }
        }
        else if(!opCode.getBit(29).getValue() && opCode.getBit(30).getValue() && !opCode.getBit(31).getValue())//010 call
        {
            //test output
            System.out.print("CALL");

            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};
            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
            {
                //push pc; pc <- imm
                pushPC();
                PC.copy(immediate);
            }
            else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
            {
                //push pc; pc <- Rd + imm
                pushPC();
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(addCode);

                PC.copy(m_ALU.result);
            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                //pc <- Rs1 BOP Rs2 ? push pc; Rd + imm : pc
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rs2);
                m_ALU.doOperation(MOP);

                if(m_ALU.boolResult.getValue())
                {
                    pushPC();
                    m_ALU.op1.copy(Rd);
                    m_ALU.op2.copy(immediate);
                    m_ALU.doOperation(addCode);

                    PC.copy(m_ALU.result);
                    //test output
                    System.out.println(" evaluated true, PC = " + PC.getSigned());
                }
                else
                {
                    //test output
                    System.out.println(" evaluated false");
                }
            }
            else //11 2R
            {
                //pc <- Rs BOP Rd ? push pc; pc + imm : pc
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rd);
                m_ALU.doOperation(MOP);

                if(m_ALU.boolResult.getValue())
                {
                    pushPC();
                    m_ALU.op1.copy(PC);
                    m_ALU.op2.copy(immediate);
                    m_ALU.doOperation(addCode);

                    PC.copy(m_ALU.result);
                    //test output
                    System.out.println(" evaluated true, PC = " + PC.getSigned());
                }
                else
                {
                    //test output
                    System.out.println(" evaluated false");
                }
                
            }
        }
        else if(!opCode.getBit(29).getValue() && opCode.getBit(30).getValue() && opCode.getBit(31).getValue())//011 push
        {
            //test output
            System.out.print("PUSH");

            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};
            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
            {
                //UNUSED
            }
            else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
            {
                CurrentClockCycle += 300;
                //mem[--sp] <- Rd MOP imm
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(MOP);
                SP.decrement();
                MainMemory.write(SP, m_ALU.result);

                //test output
                System.out.println(" " + m_ALU.result +" pushed to stack");
            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                CurrentClockCycle += 300;
                //mem[--sp] <- Rs1 MOP Rs2
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rs2);
                m_ALU.doOperation(MOP);
                SP.decrement();
                MainMemory.write(SP, m_ALU.result);

                //test output
                System.out.println(" " + m_ALU.result +" pushed to stack");
            }
            else //11 2R
            {
                CurrentClockCycle += 300;
                //mem[--sp] <- Rd MOP Rs
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(Rs1);
                m_ALU.doOperation(MOP);
                SP.decrement();
                MainMemory.write(SP, m_ALU.result);

                //test output
                System.out.println(" " + m_ALU.result +" pushed to stack");
            }
        }
        else if(opCode.getBit(29).getValue() && !opCode.getBit(30).getValue() && !opCode.getBit(31).getValue())//100 load
        {
            CurrentClockCycle += 300;

            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};
            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
            {
                //RETURN (pc <- pop)
                PC.copy(MainMemory.read(SP)); SP.increment();
        
                //test output
                System.out.println("RETURN");
            }
            else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
            {
                //Rd <- mem [Rd + imm]
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(addCode);
                Rd.copy(MainMemory.read(m_ALU.result));

                //test output
                System.out.println("LOAD Rd = " + Rd.getSigned());
            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                //Rd <- mem [Rs1+ Rs2]
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rs2);
                m_ALU.doOperation(addCode);

                System.out.println(" aaaa   " + m_ALU.result.getSigned());
                Rd.copy(MainMemory.read(m_ALU.result));

                //test output
                System.out.println("LOAD Rd = " + Rd.getSigned());
            }
            else //11 2R
            {
                //Rd <- mem[Rs + imm]
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(addCode);
                Rd.copy(MainMemory.read(m_ALU.result));

                //test output
                System.out.println("LOAD Rd = " + Rd.getSigned());
            }
        }
        else if(opCode.getBit(29).getValue() && !opCode.getBit(30).getValue() && opCode.getBit(31).getValue())//101 store
        {
            //test output
            System.out.print("STORE");

            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};
            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
            {
                //UNUSED
            }
            else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
            {
                CurrentClockCycle += 300;
                //Mem[Rd] <- imm
                MainMemory.write(Rd, immediate);

                //test output
                System.out.println(" " + immediate.getSigned() + " put into memory at " + Rd.getSigned());
            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                CurrentClockCycle += 300;
                //Mem[Rd + Rs1] <- Rs2 
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(Rs1);
                m_ALU.doOperation(addCode);
                MainMemory.write(m_ALU.result, Rs2);

                //test output
                System.out.println(" " + Rs2.getSigned() + " put into memory at " + m_ALU.result.getSigned());
            }
            else //11 2R
            {
                CurrentClockCycle += 300;
                //mem[Rd + imm] <- Rs
                m_ALU.op1.copy(Rd);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(addCode);
                MainMemory.write(m_ALU.result, Rs1);

                //test output
                System.out.println(" " + Rs1.getSigned() + " put into memory at " + m_ALU.result.getSigned());
            }
        }
        if(opCode.getBit(29).getValue() && opCode.getBit(30).getValue() && !opCode.getBit(31).getValue())//110 Pop
        {
            mask = new Word("00000000000000000011110000000000");
            function = (currentInstruction.and(mask)).rightShift(10);
            Bit[] MOP = {function.getBit(28), function.getBit(29), function.getBit(30), function.getBit(31)};
            mask = new Word("00000000000000000000000000011111");
            opCode = currentInstruction.and(mask);

            if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //00 no R
            {
                //INTERRUPT not implied in this version assigment
            }
            else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) //01 dest only
            {
                CurrentClockCycle += 300;
                //POP: Rd <- mem[sp++]
                Rd.copy(MainMemory.read(SP)); SP.increment();

                //test output
                System.out.println("POP Rd is now " + Rd.getSigned());
            }
            else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) //10 3R
            {
                CurrentClockCycle += 300;
                //PEEK: Rd <- mem [sp – (Rs1+ Rs2)]
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(Rs2);
                m_ALU.doOperation(addCode);

                m_ALU.op1.copy(SP);
                m_ALU.op2.copy(m_ALU.result);
                m_ALU.doOperation(addCode);

                Rd.copy(MainMemory.read(m_ALU.result));

                //test output
                System.out.println("PEEK Rd is now " + Rd.getSigned());
            }
            else //11 2R
            {
                CurrentClockCycle += 300;
                //PEEK: Rd <- mem[sp – (Rs +  imm)]
                m_ALU.op1.copy(Rs1);
                m_ALU.op2.copy(immediate);
                m_ALU.doOperation(addCode);

                m_ALU.op1.copy(SP);
                m_ALU.op2.copy(m_ALU.result);
                m_ALU.doOperation(addCode);

                Rd.copy(MainMemory.read(m_ALU.result));

                //test output
                System.out.println("PEEK Rd is now " + Rd.getSigned());
            }
        }
    }
    // stores values/update Rd
    private void store() 
    {
        if(!currentInstruction.getBit(27).getValue() && !currentInstruction.getBit(28).getValue() && !currentInstruction.getBit(29).getValue() &&!currentInstruction.getBit(30).getValue() && !currentInstruction.getBit(31).getValue())
        {
            System.out.println("HALTED current clock cycle = " + CurrentClockCycle);
        }
        else
        {
            Word mask = new Word("00000000000000000000001111100000");
            Word registerNumber = (currentInstruction.and(mask)).rightShift(5);

            Word register = regAddressDecodeIfBlock(registerNumber);
            register.copy(Rd);
            
            //test output
            System.out.println("(ignore if not math op)putting " + Rd.getSigned() + " into the register R" + Arrays.asList(m_Registers).indexOf(register));
        }
        
    }
    //if logic block to get reg addresses
    private Word regAddressDecodeIfBlock (Word p_Address)
    {
        if(p_Address.getBit(27).getValue()) //1xxxx
        {
            if(p_Address.getBit(28).getValue())//11xxx
            {
                if(p_Address.getBit(29).getValue())//111xx
                {
                    if(p_Address.getBit(30).getValue())//1111x
                    {
                        if(p_Address.getBit(31).getValue())//11111
                        {
                            return R31;
                        }
                        else//11110
                        {
                            return R30;
                        }
                    }
                    else//1110x
                    {
                        if(p_Address.getBit(31).getValue())//11101
                        {
                            return R29;
                        }
                        else//11100
                        {
                            return R28;
                        }
                    }
                }
                else//110xx
                {
                    if(p_Address.getBit(30).getValue())//1101x
                    {
                        if(p_Address.getBit(31).getValue())//11011
                        {
                            return R27;
                        }
                        else//11010
                        {
                            return R26;
                        }
                    }
                    else//1100x
                    {
                        if(p_Address.getBit(31).getValue())//11001
                        {
                            return R25;
                        }
                        else//11000
                        {
                            return R24;
                        }
                    }
                }
            }
            else//10xxx
            {
                if(p_Address.getBit(29).getValue())//101xx
                {
                    if(p_Address.getBit(30).getValue())//1011x
                    {
                        if(p_Address.getBit(31).getValue())//10111
                        {
                            return R23;
                        }
                        else//10110
                        {
                            return R22;
                        }
                    }
                    else//1010x
                    {
                        if(p_Address.getBit(31).getValue())//10101
                        {
                            return R21;
                        }
                        else//10100
                        {
                            return R20;
                        }
                    }
                }
                else//100xx
                {
                    if(p_Address.getBit(30).getValue())//1001x
                    {
                        if(p_Address.getBit(31).getValue())//10011
                        {
                            return R19;
                        }
                        else//10010
                        {
                            return R18;
                        }
                    }
                    else//1000x
                    {
                        if(p_Address.getBit(31).getValue())//10001
                        {
                            return R17;
                        }
                        else//10000
                        {
                            return R16;
                        }
                    }
                }
            }
        }
        else //0xxxx
        {
            if(p_Address.getBit(28).getValue())//01xxx
            {
                if(p_Address.getBit(29).getValue())//011xx
                {
                    if(p_Address.getBit(30).getValue())//0111x
                    {
                        if(p_Address.getBit(31).getValue())//01111
                        {
                            return R15;
                        }
                        else//01110
                        {
                            return R14;
                        }
                    }
                    else//0110x
                    {
                        if(p_Address.getBit(31).getValue())//01101
                        {
                            return R13;
                        }
                        else//01100
                        {
                            return R12;
                        }
                    }
                }
                else//010xx
                {
                    if(p_Address.getBit(30).getValue())//0101x
                    {
                        if(p_Address.getBit(31).getValue())//01011
                        {
                            return R11;
                        }
                        else//01010
                        {
                            return R10;
                        }
                    }
                    else//0100x
                    {
                        if(p_Address.getBit(31).getValue())//01001
                        {
                            return R9;
                        }
                        else//01000
                        {
                            return R8;
                        }
                    }
                }
            }
            else//00xxx
            {
                if(p_Address.getBit(29).getValue())//001xx
                {
                    if(p_Address.getBit(30).getValue())//0011x
                    {
                        if(p_Address.getBit(31).getValue())//00111
                        {
                            return R7;
                        }
                        else//00110
                        {
                            return R6;
                        }
                    }
                    else//0010x
                    {
                        if(p_Address.getBit(31).getValue())//00101
                        {
                            return R5;
                        }
                        else//00100
                        {
                            return R4;
                        }
                    }
                }
                else//000xx
                {
                    if(p_Address.getBit(30).getValue())//0001x
                    {
                        if(p_Address.getBit(31).getValue())//00011
                        {
                            return R3;
                        }
                        else//00010
                        {
                            return R2;
                        }
                    }
                    else//0000x
                    {
                        if(p_Address.getBit(31).getValue())//00001
                        {
                            return R1;
                        }
                        else//00000
                        {
                            return R0;
                        }
                    }
                }
            }
        }
    }
    //pushes PC 
    private void pushPC()
    {
        CurrentClockCycle += 300;
        //-- SP -> write to addy
        SP.decrement();
        MainMemory.write(SP, PC);
    }

}
