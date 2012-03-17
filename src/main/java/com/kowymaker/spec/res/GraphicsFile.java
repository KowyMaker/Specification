package com.kowymaker.spec.res;

import java.lang.reflect.Constructor;

public class GraphicsFile extends ResourceFile
{
    protected String          handlerClassName = null;
    protected GraphicsHandler handler          = null;
    
    public void setHandlerClassName(String handlerClassName)
    {
        this.handlerClassName = handlerClassName;
        try
        {
            this.handler = createHandler();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String getHandlerClassName()
    {
        return handlerClassName;
    }
    
    @SuppressWarnings("unchecked")
    public Class<GraphicsHandler> getHandlerClass() throws Exception
    {
        Class<GraphicsHandler> handlerClass = (Class<GraphicsHandler>) getClass()
                .getClassLoader().loadClass(handlerClassName);
        return handlerClass;
    }
    
    public void setHandler(GraphicsHandler handler)
    {
        this.handler = handler;
    }
    
    public GraphicsHandler getHandler()
    {
        return handler;
    }
    
    protected GraphicsHandler createHandler() throws Exception
    {
        if (getHandlerClassName() == null || getHandlerClassName().isEmpty())
        {
            return null;
        }
        
        Class<GraphicsHandler> clazz = getHandlerClass();
        Constructor<GraphicsHandler> constructor = clazz
                .getConstructor(GraphicsFile.class);
        GraphicsHandler handler = constructor.newInstance(this);
        
        return handler;
    }
}
