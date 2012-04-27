package aurelienribon.tweenengine.primitives;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MutableInteger extends Number implements
        TweenAccessor<MutableInteger>
{
    /**
     * 
     */
    private static final long serialVersionUID = -5075306047983512974L;
    private int               value;
    
    public MutableInteger(int value)
    {
        this.value = value;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    @Override
    public int intValue()
    {
        return value;
    }
    
    @Override
    public long longValue()
    {
        return value;
    }
    
    @Override
    public float floatValue()
    {
        return value;
    }
    
    @Override
    public double doubleValue()
    {
        return value;
    }
    
    public int getValues(MutableInteger target, int tweenType,
            float[] returnValues)
    {
        returnValues[0] = target.value;
        return 1;
    }
    
    public void setValues(MutableInteger target, int tweenType,
            float[] newValues)
    {
        target.value = (int) newValues[0];
    }
}
