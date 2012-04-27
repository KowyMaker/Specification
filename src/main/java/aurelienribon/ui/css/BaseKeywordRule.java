package aurelienribon.ui.css;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class BaseKeywordRule extends BaseRule
{
    private final String[] keywords;
    
    public BaseKeywordRule(String name, String... keywords)
    {
        super(name);
        this.keywords = keywords;
    }
    
    public Class[][] getParams()
    {
        return new Class[][] { { String.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "keyword" } };
    }
    
    @Override
    public String[] getKeywords(int paramsId, int paramId)
    {
        return keywords;
    }
    
    public boolean canBeNull(int paramsId, int paramId)
    {
        return false;
    }
}
