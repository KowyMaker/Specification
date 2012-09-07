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

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.kowymaker.spec.proto.NetworkCodecs;

/**
 * Kowy Maker Project Codec Manager.<br />
 * 
 * Contains all specifications used to transmit packets between Client and
 * Server.
 * 
 * @author Koka El Kiwi
 * 
 */
public class CodecResolver
{
    private final Map<Integer, Message.Builder>   codecs   = Maps.newLinkedHashMap();
    private final Map<Integer, MessageHandler<?>> handlers = Maps.newLinkedHashMap();
    
    public CodecResolver()
    {
        registerCodec(0, NetworkCodecs.ConnectMessage.newBuilder());
    }
    
    public void registerCodec(int opcode, Message.Builder codec)
    {
        codecs.put(opcode, codec);
    }
    
    public Message.Builder getCodec(int opcode)
    {
        return codecs.get(opcode);
    }
    
    public void registerHandler(int opcode, MessageHandler<?> handler)
    {
        handlers.put(opcode, handler);
    }
    
    public void registerHandler(Class<? extends MessageOrBuilder> messageClass,
            MessageHandler<?> handler)
    {
        registerHandler(getOpcode(messageClass), handler);
    }
    
    @SuppressWarnings("unchecked")
    public <T> MessageHandler<T> getHandler(int opcode)
    {
        return (MessageHandler<T>) handlers.get(opcode);
    }
    
    public <T> MessageHandler<T> getHandler(
            Class<? extends MessageOrBuilder> clazz)
    {
        return getHandler(getOpcode(clazz));
    }
    
    public int getOpcode(Class<? extends MessageOrBuilder> clazz)
    {
        Class<?> testClass = clazz.getInterfaces()[0];
        
        for (int opcode : codecs.keySet())
        {
            Message.Builder codec = codecs.get(opcode);
            
            if (testClass.isInstance(codec))
            {
                return opcode;
            }
        }
        
        return -1;
    }
}
