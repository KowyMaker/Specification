/**
 * This file is part of Kowy Maker.
 *
 * Kowy Maker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kowy Maker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kowy Maker.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 */
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
            handler = createHandler();
        }
        catch (final Exception e)
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
        final Class<GraphicsHandler> handlerClass = (Class<GraphicsHandler>) getClass()
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
        
        final Class<GraphicsHandler> clazz = getHandlerClass();
        final Constructor<GraphicsHandler> constructor = clazz
                .getConstructor(GraphicsFile.class);
        final GraphicsHandler handler = constructor.newInstance(this);
        
        return handler;
    }
}
