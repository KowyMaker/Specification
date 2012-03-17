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

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import com.kowymaker.spec.net.codec.*;
import com.kowymaker.spec.net.msg.*;

/**
 * @author Koka El Kiwi
 * 
 */
@SuppressWarnings("unchecked")
public class CodecResolver
{
    private final Map<Byte, MessageCodec<? extends Message>>                       codecs   = new HashMap<Byte, MessageCodec<? extends Message>>();
    private final Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers = new HashMap<Class<? extends Message>, MessageHandler<? extends Message>>();
    private final Map<Class<? extends Message>, Byte>                              opcodes  = new HashMap<Class<? extends Message>, Byte>();
    
    {
        try
        {
            registerCodec(ConnectCodec.class, DisconnectCodec.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void registerCodec(
            Class<? extends MessageCodec<? extends Message>>... classes)
            throws Exception
    {
        for (Class<? extends MessageCodec<? extends Message>> clazz : classes)
        {
            registerCodec(clazz);
        }
    }
    
    public void registerCodec(
            Class<? extends MessageCodec<? extends Message>> clazz)
            throws Exception
    {
        // Codec
        Constructor<? extends MessageCodec<? extends Message>> constructor = clazz
                .getConstructor();
        MessageCodec<? extends Message> codec = constructor.newInstance();
        codecs.put(codec.getOpcode(), codec);
        final Class<? extends Message> msgClazz = (Class<? extends Message>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[0];
        opcodes.put(msgClazz, codec.getOpcode());
    }
    
    public void registerHandler(
            Class<? extends MessageHandler<? extends Message>>... classes)
            throws Exception
    {
        for (Class<? extends MessageHandler<? extends Message>> clazz : classes)
        {
            registerHandler(clazz);
        }
    }
    
    public void registerHandler(
            Class<? extends MessageHandler<? extends Message>> clazz)
            throws Exception
    {
        registerHandler(clazz, null);
    }
    
    public void registerHandler(Map<String, Object> properties,
            Class<? extends MessageHandler<? extends Message>>... classes)
            throws Exception
    {
        for (Class<? extends MessageHandler<? extends Message>> clazz : classes)
        {
            registerHandler(clazz, properties);
        }
    }
    
    public void registerHandler(
            Class<? extends MessageHandler<? extends Message>> clazz,
            Map<String, Object> properties) throws Exception
    {
        Constructor<? extends MessageHandler<? extends Message>> constructor = clazz
                .getConstructor();
        MessageHandler<? extends Message> handler = constructor.newInstance();
        handler.addProperties(properties);
        handler.init();
        final Class<? extends Message> msgClazz = (Class<? extends Message>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[0];
        handlers.put(msgClazz, handler);
    }
    
    public <V extends Message, T extends MessageCodec<V>> T getCodec(byte opcode)
    {
        return (T) codecs.get(opcode);
    }
    
    public <V extends Message, T extends MessageCodec<V>> T getCodec(
            Class<V> clazz)
    {
        return (T) codecs.get(opcodes.get(clazz));
    }
    
    public <V extends Message, T extends MessageHandler<V>> T getHandler(
            Class<V> clazz)
    {
        return (T) handlers.get(clazz);
    }
}
