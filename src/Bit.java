//represents a bit true=1, false=0
public class Bit 
{
    //member
    private boolean m_Value;

    //contructer
    public Bit(Boolean value)
    {
        m_Value = value;
    }
    public Bit()
    {
        m_Value = false;
    }

    // sets the value of the bit
    public void set(Boolean value)
    {
        m_Value = value;
    }
    // changes the value from true to false or false to true
    public void toggle()
    {
        if(m_Value)
        {
            m_Value = false;
        }
        else if (!m_Value)
        {
            m_Value = true;
        }
    }
    // sets the bit to true
    public void set()
    {
        m_Value = true;
    }
    // sets the bit to false
    public void clear()
    {
        m_Value = false;
    }
    // returns the current value
    public Boolean getValue()
    {
        return m_Value;
    }
    // performs and on two bits and returns a new bit set to the result
    public Bit and(Bit other)
    {
        boolean leftside = this.getValue();
        boolean rightside = other.getValue();
        if (leftside == true) 
        {
            if (leftside == rightside) 
            {
                return new Bit(true);
            }
        }
        return new Bit(false);
    }
    // performs or on two bits and returns a new bit set to the result
    public Bit or(Bit other)
    {
        boolean leftside = this.getValue();
        boolean rightside = other.getValue();
        if (leftside == false) 
        {
            if (leftside == rightside) 
            {
                return new Bit();
            }
            return new Bit(true);
        }
        return new Bit(true);

    }
    // performs xor on two bits and returns a new bit set to the result
    public Bit xor(Bit other)
    {
        boolean leftside = this.getValue();
        boolean rightside = other.getValue();
        if (leftside == rightside) 
        {
            return new Bit(false);
        }
        return new Bit(true);
    }
    // performs not on the existing bit, returning the result as a new bit
    public Bit not()
    {
        if (m_Value == false) 
        {
            return new Bit(true);
        }
        return new Bit(false);
    }
    // returns “t” or “f”
    public String toString()
    {
        if (m_Value == false) 
        {
            return "f";
        }
        return "t";
    }
}
