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
package com.kowymaker.spec.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.spec.net.msg.Message;

/**
 * Class used by client/server (or others) to handle packets easily.
 * 
 * @author Koka El Kiwi
 * 
 * @param <T>
 *            Message type to bind to.
 */
public abstract class MessageHandler<T extends Message>
{
    @SuppressWarnings("unchecked")
    public boolean handle(ChannelHandlerContext ctx, MessageEvent e)
    {
        final T msg = (T) e.getMessage();
        return handle(ctx, e, msg);
    }
    
    /**
     * Handle a packet, called by internal network manager with Netty/NIO
     * variables.
     * 
     * @param ctx Channel context when receiving the packet.
     * @param e Message event, containing the packet informations.
     * @param msg Message to handle.
     * @return <i>true</i> if it was correctly handled, <i>false</i> else
     */
    public abstract boolean handle(ChannelHandlerContext ctx, MessageEvent e,
            T msg);
}
