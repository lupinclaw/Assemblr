import static org.junit.Assert.*;
import org.junit.Test;

//tests for Bit and Words classes and methods
public class Tests 
{
    //bit tests
    @Test 
    public void testBitGetValue() throws Exception
    {
        Bit bitTest = new Bit();
        assertNotNull(bitTest);
        assertTrue(bitTest.getValue() == false);

    }
    @Test 
    public void testBitSet() throws Exception
    {
        Bit bitTest = new Bit();
        bitTest.set();
        assertTrue(bitTest.getValue());
        bitTest.set(false);
        assertTrue(bitTest.getValue() == false);
    }
    @Test 
    public void testBitToggle() throws Exception
    {
        Bit bitTest = new Bit();
        bitTest.toggle();
        assertTrue(bitTest.getValue());
    }
    @Test 
    public void testBitClear() throws Exception
    {
        Bit bitTest = new Bit(true);
        bitTest.clear();
        assertTrue(bitTest.getValue() == false);
    }
    @Test 
    public void testBitAnd() throws Exception
    {
        Bit bitTest0 = new Bit();
        Bit bitTest1 = new Bit(true);

        assertTrue(bitTest0.and(bitTest0).getValue() == false);
        assertTrue(bitTest0.and(bitTest1).getValue() == false);
        assertTrue(bitTest1.and(bitTest0).getValue() == false);
        assertTrue(bitTest1.and(bitTest1).getValue());
    }
    @Test 
    public void testBitOr() throws Exception
    {
        Bit bitTest0 = new Bit();
        Bit bitTest1 = new Bit(true);

        assertTrue(bitTest0.or(bitTest0).getValue() == false);
        assertTrue(bitTest0.or(bitTest1).getValue());
        assertTrue(bitTest1.or(bitTest0).getValue());
        assertTrue(bitTest1.or(bitTest1).getValue());
    }
    @Test 
    public void testBitXor() throws Exception
    {
        Bit bitTest0 = new Bit();
        Bit bitTest1 = new Bit(true);

        assertTrue(bitTest0.xor(bitTest0).getValue() == false);
        assertTrue(bitTest0.xor(bitTest1).getValue());
        assertTrue(bitTest1.xor(bitTest0).getValue());
        assertTrue(bitTest1.xor(bitTest1).getValue() == false);
    }
    @Test 
    public void testBitNot() throws Exception
    {
        Bit bitTest0 = new Bit();
        Bit bitTest1 = new Bit(true);

        assertTrue(bitTest0.not().getValue());
        assertTrue(bitTest1.not().getValue() == false);
    }
    @Test 
    public void testBitToString() throws Exception
    {
        Bit bitTest = new Bit();
        assertTrue(bitTest.toString().equals("f"));
        bitTest.toggle();
        assertTrue(bitTest.toString().equals("t"));
    }

    //word tests
    @Test 
    public void testWordNot() throws Exception
    {
        Word wordTest = new Word();
        assertNotNull(wordTest);
        wordTest = wordTest.not();
        assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", wordTest.toString());
    }
    @Test
    public void testWordToStringAndSet() {
        Word wordTest = new Word();
        wordTest.set(12);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,f,f", wordTest.toString());
    }
    @Test
    public void testWordRightShift() 
    {
        Word wordTest = new Word();
        wordTest.set(10);
        Word retWord = wordTest.rightShift(2);
        assertEquals(2, retWord.getUnsigned());
    }
    @Test
    public void testWordLeftShift() 
    {
        Word wordTest = new Word();
        wordTest.set(5);
        Word retWord = wordTest.leftShift(3);
        assertEquals(40, retWord.getUnsigned());
    }
    @Test
    public void testWordGetUnsigned() 
    {
        Word wordTest = new Word();
        wordTest.set(7);
        assertEquals(7, wordTest.getUnsigned());
    }
    @Test
    public void testWordGetSigned() 
    {
        Word wordTest = new Word();
        wordTest.set(25);
        assertEquals(25, wordTest.getSigned());
    }
    @Test
    public void testWordCopy() 
    {
        Word wordTest1 = new Word();
        wordTest1.set(42);
        Word wordTest2 = new Word();
        wordTest2.copy(wordTest1);
        assertEquals(wordTest1.toString(), wordTest2.toString());
    }
    @Test
    public void testWordGetBit() 
    {
        Word wordTest = new Word();
        wordTest.setBit(2, new Bit(true));
        Bit retBit = wordTest.getBit(2);
        assertTrue(retBit.getValue());
    }
    @Test
    public void testWordSetBit() 
    {
        Word wordTest = new Word();
        Bit bitTest = new Bit(true);
        wordTest.setBit(5, bitTest);
        assertTrue(wordTest.getBit(5).getValue());
    }
    @Test
    public void testWordAnd() 
    {
        Word wordTest1 = new Word();
        wordTest1.set(10);
        Word wordTest2 = new Word();
        wordTest2.set(5);
        Word retWord = wordTest1.and(wordTest2);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", retWord.toString());
    }
    @Test
    public void testWordOr() 
    {
        Word wordTest1 = new Word();
        wordTest1.set(10);
        Word wordTest2 = new Word();
        wordTest2.set(5);
        Word retWord = wordTest1.or(wordTest2);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,t", retWord.toString());
    }
    @Test
    public void testWordXor() 
    {
        Word wordTest1 = new Word();
        wordTest1.set(10);
        Word wordTest2 = new Word();
        wordTest2.set(6);
        Word retWord = wordTest1.xor(wordTest2);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,f,f", retWord.toString());
    }
    @Test
    public void testWordSetAndGetSignedNegative() 
    {
        Word wordTest = new Word();
        wordTest.set(-13);
        assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,f,t,t", wordTest.toString());
        assertEquals(-13, wordTest.getSigned());
    }
    @Test
    public void testWordSetAndGetSignedPositive() 
    {
        Word wordTest = new Word();
        wordTest.set(25);
        assertEquals(25, wordTest.getSigned());
    }
    @Test
    public void testWordSetAndGetSignedZero() 
    {
        Word wordTest = new Word();
        wordTest.set(0);
        assertEquals(0, wordTest.getSigned());
    }

    //ALU tests
    @Test
    public void testAdd2() 
    {
        ALU alu = new ALU();
        Word a = new Word();
        Word b = new Word();

        a.set(5);
        b.set(3);

        Word result = alu.add2(a, b);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f", result.toString());
    }
    @Test
    public void testAdd4() 
    {
        ALU alu = new ALU();
        Word a = new Word();
        Word b = new Word();
        Word c = new Word();
        Word d = new Word();

        a.set(5);
        b.set(3);
        c.set(7);
        d.set(2);

        Word result = alu.add4(a, b, c, d);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,t", result.toString());
    }
    @Test
    public void testAddOperation() 
    {
        ALU alu = new ALU();
        alu.op1.set(5);
        alu.op2.set(3);

        Bit[] op = {new Bit(true),new Bit(true), new Bit(true), new Bit()}; //1110
        alu.doOperation(op);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f", alu.result.toString());
    }
    @Test
    public void testMultiplyOperation() 
    {
        ALU alu = new ALU();
        alu.op1.set(5);
        alu.op2.set(3);

        Bit[] op = {new Bit(),new Bit(true), new Bit(true), new Bit(true)}; //0111
        alu.doOperation(op);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,t", alu.result.toString());
    }
    @Test
    public void testMultiplyOperationCarry() 
    {
        ALU alu = new ALU();
        alu.op1.set(5);
        alu.op2.set(4);

        Bit[] op = {new Bit(),new Bit(true), new Bit(true), new Bit(true)}; //0111
        alu.doOperation(op);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,f,f", alu.result.toString());
    }
    @Test
    public void testSubtractOperation() 
    {
        ALU alu = new ALU();
        alu.op1.set(5);
        alu.op2.set(3);

        Bit[] op = {new Bit(true),new Bit(true), new Bit(true), new Bit(true)}; //1111
        alu.doOperation(op);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f", alu.result.toString());
    }
    @Test
    public void testMultiplyWithZero() 
    {
        ALU alu = new ALU();
        alu.op1.set(123);
        alu.op2.set(0);

        Bit[] op = {new Bit(),new Bit(true), new Bit(true), new Bit(true)}; //0111
        alu.doOperation(op);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", alu.result.toString());
    }
    @Test
    public void testMultiplyWithOne() 
    {
        ALU alu = new ALU();
        alu.op1.set(987);
        alu.op2.set(1);
        Word test = new Word();
        test.set(987);

        Bit[] op = {new Bit(),new Bit(true), new Bit(true), new Bit(true)}; //0111
        alu.doOperation(op);

        assertEquals(test.toString(), alu.result.toString());
    }
    @Test
    public void testMultiplyWithLargeValues() 
    {
        ALU alu = new ALU();
        alu.op1.set(50000);
        alu.op2.set(30000);
        
        Word test = new Word();
        test.set(50000*30000);

        Bit[] op = {new Bit(),new Bit(true), new Bit(true), new Bit(true)}; //0111
        alu.doOperation(op);

        assertEquals(test.toString(), alu.result.toString());
    }
    @Test
    public void testAdd2WithZero() 
    {
        ALU alu = new ALU();
        Word a = new Word();
        Word b = new Word();

        a.set(0);
        b.set(0);

        Word result = alu.add2(a, b);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", result.toString());
    }
    @Test
    public void testAdd2WithMaxValue() 
    {
        ALU alu = new ALU();
        Word a = new Word();
        Word b = new Word();

        a.set(Integer.MAX_VALUE);
        b.set(1);

        Word result = alu.add2(a, b);

        assertEquals("t,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", result.toString());
    }
    @Test
    public void testAdd4WithZero() 
    {
        ALU alu = new ALU();
        Word a = new Word();
        Word b = new Word();
        Word c = new Word();
        Word d = new Word();

        a.set(0);
        b.set(0);
        c.set(0);
        d.set(0);

        Word result = alu.add4(a, b, c, d);

        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", result.toString());
    }
    @Test
    public void testAdd4WithLargeValues() 
    {
        ALU alu = new ALU();
        Word a = new Word();
        Word b = new Word();
        Word c = new Word();
        Word d = new Word();

        a.set(100000);
        b.set(200000);
        c.set(300000);
        d.set(400000);

        Word test = new Word();
        test.set(100000+200000+300000+400000);

        Word result = alu.add4(a, b, c, d);

        assertEquals(test.toString(), result.toString());
    }

    //memory and processor tests
    @Test
    public void testReadAndWrite() 
    {
        Word addy = new Word(); 
        addy.set(5);
        Word value = new Word();
        value.set(42949672);
        MainMemory.write(addy, value);
        Word result = MainMemory.read(addy);

        assertEquals(value.toString(), result.toString());
    }
    @Test
    public void testLoad() 
    {
        Word addy = new Word(); 
        String[] data = 
        {
                "01101010101010101010010101010101",
                "11111111111111111111111111111111",
        };
        MainMemory.load(data);
        Word result = MainMemory.read(addy);

        assertEquals("f,t,t,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,t,f,f,t,f,t,f,t,f,t,f,t,f,t", result.toString());

        result = MainMemory.read(addy.addOne());
        assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", result.toString());
    }

    //simple programs tests
    @Test
    public void testProgram1() 
    {
        Processor cpu = new Processor();
        String[] data = 
        {
                "00000000000000010100000000100001", // MATH DestOnly 5, R1
                "00000000000010000111100001000010", // MATH ADD R1 R1 R2
                "00000000000000001011100001000011", // MATH ADD R2 R2
                "00000000000100000111100001100010", // MATH ADD R2 R1 R3
                "00000000000000000000000000000000", // HALT
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
        //puts 25 into R3 
    }
    @Test
    public void testProgram2() 
    {
        Processor cpu = new Processor();
        String[] data = 
        {
                "00000000000001010100000000100001", // MATH DestOnly 21, R1
                "00000000000000110100000001000001", // MATH DestOnly 13, R2
                "00000000000010001011110001100010", // MATH Sub R1 R2 R3
                "00000000000000001101110001100011", // MATH Multi R3 R3
                "00000000000000001111100010000010", // MATH add R3 R0 R4
                "00000000000000000000000000000000", // HALT
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
        //puts 64 into R4 
    }
    @Test
    public void testProgram3() 
    {
        Processor cpu = new Processor();
        String[] data = 
        {
                "00000000000000100000000000100001", // MATH DestOnly 8, R1
                "00000000000000110100000001000001", // MATH DestOnly 13, R2
                "00000000000010001011110001100010", // MATH Sub R1 R2 R3
                "00000000000000001101111101100010", // MATH Multi R3 R0 R27
                "00000000110110001111101111000010", // MATH add R3 R27 R30
                "00000000000000000000000000000000", // HALT
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
        //puts 5 into R30 and tests multpliy by 0 and negatives from substraction
    }
    //the following test programs test non math opcodes
    @Test
    public void testProgram4() //branches 
    {
        Processor cpu = new Processor();
        String[] data = 
        {
                "00000000000000000100000000100001", // MATH DestOnly 1, R1
                "00000000000000010100000001000001", // MATH DestOnly 5, R2
                "00000000000010001000110000100111", //BRANCH 2R R1 GE R2 1 
                "00000000000000001011100000100011", //MATH add R2 R1 
                "00000000000000000000000011100100", //JUMP 1
                "00000000000010001000110000100111", //BRANCH 2R R1 GE R2 1 
                "00000000000000000000000000000000", // HALT
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
    }
    @Test
    public void testProgram5() //load stores + push/pop 
    {                          
        Processor cpu = new Processor();
        String[] data = 
        {
                "00000000000000000100000000100001", // MATH DestOnly 1, R1
                "00000000000000001000000000110101", // STORE DestOnly R1, 2
                "00000000000000000100000001110011", // LOAD 2R R1 R3
                "00000000000000010111100000001101", // PUSH dest only 5
                "00000000000000000000000000111101", // POP R1
                "00000000000110000111100010100010", // MATH add R1 R3 R5
                //"00000000000110000100000010110110", // STORE 3R R1 R3 R5
                "00000000000110000100000010110010", // LOAD 3R R1 R3 R5
                //"00000000001010000100000000000110", // BRANCH 3R R1 neq R5 1 
                "00000000000010001111100000100010", // MATH add R1 R3 R1
                "00000000000000000000000000000000", // HALT
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
    }
    @Test
    public void testProgram6Fib() //its fib time 7th fib number
    {
        Processor cpu = new Processor();
        String[] data = 
        {
                "00000000000000011100000011100001", // MATH DestOnly 7, R7
                "00000000000000000100000000100001", // MATH DestOnly 1, R1

                "00000000000000000000000001000001", // MATH DestOnly 0, R2
                "00000000000000000100000001100001", // MATH DestOnly 1, R3

                "00000000000010011100010000001011", // Call 2R R7 neq R0, 1
                "00000000000000000000000000000000", // HALT

                "00000000000000000111110011100011", // MATH sub R1 R7
                "00000000000000001100000000110111", // STORE 2R R3 R1
                "00000000000000001011100001100011", // MATH add R2 R3 
                "00000000000000000100000001010011", // LOAD 2R R1 R2
                "00000000000000000000000000010000", // RETURN
                
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
    }
    @Test
    public void testProgramArraysum() //sum array
    {
        Processor cpu = new Processor();
        String[] data = 
        {

                "00000000000000011100000011100001",
                "00000000000001010100000000100001", 
                "00000000000000110100000001000001", 
                "00000000000010001011110001100010", 
                "00000000000000001101110001100011", 
                "00000000000000001111100010000010", 
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000011100000011100001",
                "00000000000000000100000000100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000011100000011100001",
                "00000000000000000100000000100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000011100000011100001",
                "00000000000000000100000000100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000011100000011100001",
                "00000000000000000100000000100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000011100000011100001",
                "00000000000000000100000000100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000011100000011100001",
                "00000000000000000100000000100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000000000000001000001",
                "00000000000000000100000001100001",
                "00000000000000000000000000000000"
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
    }
    @Test
    public void testProgramLinksum() //sum linked list
    {
        Processor cpu = new Processor();
        String[] data = 
        {
            "00000000000000011100000011100001",
            "00000000000001010100000000100001", 
            "00000000000000110100000001000001", 
            "00000000000010001011110001100010", 
            "00000000000000001101110001100011", 
            "00000000000000001111100010000010", 
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000001010100000000100001", 
            "00000000000000110100000001000001", 
            "00000000000010001011110001100010",
            "00000000000000011100000011100001",
            "00000000000001010100000000100001", 
            "00000000000000110100000001000001", 
            "00000000000010001011110001100010", 
            "00000000000000001101110001100011", 
            "00000000000000001111100010000010", 
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001", 
            "00000000000000001101110001100011", 
            "00000000000000001111100010000010", 
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000000000000"
                
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
    }
    @Test
    public void testProgramArrayBackwardssum() //sum array backwards
    {
        Processor cpu = new Processor();
        String[] data = 
        {
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000011100000011100001",
            "00000000000000000100000000100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000001000001",
            "00000000000000000100000001100001",
            "00000000000000000000000000000000"
                
        };
        MainMemory.load(data);
        cpu.run();
        //Check Debug/console output to see out put as text for each CPU cycle
    }

}


