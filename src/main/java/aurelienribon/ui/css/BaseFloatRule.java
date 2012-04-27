package aurelienribon.ui.css;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class BaseFloatRule extends BaseRule
{
    public BaseFloatRule(String name)
    {
        super(name);
    }
    
    public Class[][] getParams()
    {
        return new Class[][] { { Float.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "f" } };
    }
    
    public boolean canBeNull(int paramsId, int paramId)
    {
        return false;
    }
}
