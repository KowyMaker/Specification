package aurelienribon.ui.css;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class BaseStringRule extends BaseRule
{
    public BaseStringRule(String name)
    {
        super(name);
    }
    
    public Class[][] getParams()
    {
        return new Class[][] { { String.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "s" } };
    }
    
    public boolean canBeNull(int paramsId, int paramId)
    {
        return false;
    }
}
