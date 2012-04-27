package aurelienribon.ui.css;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class BaseBooleanRule extends BaseRule
{
    public BaseBooleanRule(String name)
    {
        super(name);
    }
    
    public Class[][] getParams()
    {
        return new Class[][] { { Boolean.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "b" } };
    }
    
    public boolean canBeNull(int paramsId, int paramId)
    {
        return false;
    }
}
