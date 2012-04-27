package aurelienribon.ui.css;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class BaseIntegerRule extends BaseRule
{
    public BaseIntegerRule(String name)
    {
        super(name);
    }
    
    public Class[][] getParams()
    {
        return new Class[][] { { Integer.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "i" } };
    }
    
    public boolean canBeNull(int paramsId, int paramId)
    {
        return false;
    }
}