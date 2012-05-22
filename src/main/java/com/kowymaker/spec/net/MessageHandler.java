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
 * along with Kowy Maker.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *  This file is part of Kowy Maker - Specification.
 *
 *  Kowy Maker - Specification is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Kowy Maker - Specification is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Kowy Maker - Specification.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kowymaker.spec.net;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.spec.net.msg.Message;

/**
 * @author Koka El Kiwi
 * 
 */
public abstract class MessageHandler<T extends Message>
{
    protected final Map<String, Object> properties = new HashMap<String, Object>();
    
    public void addProperty(String name, Object value)
    {
        properties.put(name, value);
    }
    
    public void addProperties(Map<String, Object> properties)
    {
        if (properties != null)
        {
            this.properties.putAll(properties);
        }
    }
    
    public void init()
    {
        
    }
    
    @SuppressWarnings("unchecked")
    public boolean handle(ChannelHandlerContext ctx, MessageEvent e)
    {
        final T msg = (T) e.getMessage();
        return handle(ctx, e, msg);
    }
    
    public abstract boolean handle(ChannelHandlerContext ctx, MessageEvent e,
            T msg);
}
