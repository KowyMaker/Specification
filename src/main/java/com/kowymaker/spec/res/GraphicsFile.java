package com.kowymaker.spec.res;

public class GraphicsFile extends ResourceFile
{
    protected String handlerClass = null;
    
    public String getHandlerClassName()
    {
        return handlerClass;
    }
    
    public Class<?> getHandlerClass() throws ClassNotFoundException
    {
        return getClass().getClassLoader().loadClass(handlerClass);
    }
    
    public void setHandlerClass(String handlerClass)
    {
        this.handlerClass = handlerClass;
    }
}
