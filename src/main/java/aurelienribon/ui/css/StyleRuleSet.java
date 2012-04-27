package aurelienribon.ui.css;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class StyleRuleSet
{
    private final List<StyleRule>              rules;
    private final Map<StyleRule, List<Object>> rulesParams;
    
    public StyleRuleSet(List<StyleRule> rules,
            Map<StyleRule, List<Object>> rulesParams)
    {
        this.rules = Collections.unmodifiableList(new ArrayList<StyleRule>(
                rules));
        this.rulesParams = Collections
                .unmodifiableMap(new HashMap<StyleRule, List<Object>>(
                        rulesParams));
    }
    
    public StyleRuleSet(Style style, Object target, List<String> stack)
    {
        final List<StyleRule> tRules = new ArrayList<StyleRule>();
        final Map<StyleRule, List<Object>> tRulesParams = new HashMap<StyleRule, List<Object>>();
        
        for (final StyleClass sc : style.getClasses())
        {
            if (isLastSelectorValid(sc.getLastSelector(), target)
                    && isStackValid(sc.getSelectors(), stack))
            {
                tRules.addAll(sc.getRuleSet().getRules());
                tRulesParams.putAll(sc.getRuleSet().getRulesParams());
            }
        }
        
        rules = Collections.unmodifiableList(tRules);
        rulesParams = Collections.unmodifiableMap(tRulesParams);
    }
    
    public StyleRuleSet(StyleRuleSet rs, StyleRule[] rulesToKeep)
    {
        final List<StyleRule> tRules = new ArrayList<StyleRule>();
        final Map<StyleRule, List<Object>> tRulesParams = new HashMap<StyleRule, List<Object>>();
        
        for (final StyleRule rule : rulesToKeep)
        {
            if (rs.getRules().contains(rule))
            {
                tRules.add(rule);
                tRulesParams.put(rule, rs.getParams(rule));
            }
        }
        
        rules = Collections.unmodifiableList(new ArrayList<StyleRule>(tRules));
        rulesParams = Collections
                .unmodifiableMap(new HashMap<StyleRule, List<Object>>(
                        tRulesParams));
    }
    
    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------
    
    public List<StyleRule> getRules()
    {
        return rules;
    }
    
    public Map<StyleRule, List<Object>> getRulesParams()
    {
        return rulesParams;
    }
    
    public List<Object> getParams(StyleRule rule)
    {
        return rulesParams.get(rule);
    }
    
    public boolean contains(StyleRule rule)
    {
        return rules.contains(rule);
    }
    
    public Boolean asBoolean(StyleRule rule, int paramId)
    {
        return (Boolean) rulesParams.get(rule).get(paramId);
    }
    
    public Integer asInteger(StyleRule rule, int paramId)
    {
        return (Integer) rulesParams.get(rule).get(paramId);
    }
    
    public Float asFloat(StyleRule rule, int paramId)
    {
        return (Float) rulesParams.get(rule).get(paramId);
    }
    
    public String asString(StyleRule rule, int paramId)
    {
        return (String) rulesParams.get(rule).get(paramId);
    }
    
    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------
    
    private boolean isLastSelectorValid(String selector, Object target)
    {
        if (selector.startsWith("."))
        {
            final String className = Style.getRegisteredTargetClassName(target);
            return className != null && className.equals(selector);
            
        }
        else
        {
            try
            {
                final Class clazz = Class
                        .forName(selector.replaceAll("-", "."));
                return clazz.isInstance(target);
                
            }
            catch (final ClassNotFoundException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }
    
    private boolean isStackValid(List<String> selectors, List<String> stack)
    {
        if (selectors.size() == 1)
        {
            return true;
        }
        
        for (int i = 0; i < selectors.size() - 1; i++)
        {
            final int idx = stack.indexOf(selectors.get(i));
            if (idx == -1)
            {
                return false;
            }
        }
        
        for (int i = 1; i < selectors.size() - 1; i++)
        {
            final int idx1 = stack.indexOf(selectors.get(i - 1));
            final int idx2 = stack.lastIndexOf(selectors.get(i));
            if (idx1 >= idx2)
            {
                return false;
            }
        }
        
        return true;
    }
}
