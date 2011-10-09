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
    private final static Map<Byte, MessageCodec<? extends Message>>                       codecs   = new HashMap<Byte, MessageCodec<? extends Message>>();
    private final static Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers = new HashMap<Class<? extends Message>, MessageHandler<? extends Message>>();
    private final static Map<Class<? extends Message>, Byte>                              opcodes  = new HashMap<Class<? extends Message>, Byte>();
    
    static
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
    
    public static void registerCodec(
            Class<? extends MessageCodec<? extends Message>>... classes)
            throws Exception
    {
        for (Class<? extends MessageCodec<? extends Message>> clazz : classes)
        {
            registerCodec(clazz);
        }
    }
    
    public static <V extends Message, T extends MessageCodec<V>> void registerCodec(
            Class<T> clazz) throws Exception
    {
        //Codec
        Constructor<T> constructor = clazz.getConstructor();
        T codec = constructor.newInstance();
        codecs.put(codec.getOpcode(), codec);
        final Class<V> msgClazz = (Class<V>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[0];
        opcodes.put(msgClazz, codec.getOpcode());
    }
    
    public static void registerHandler(
            Class<? extends MessageHandler<? extends Message>>... classes)
            throws Exception
    {
        for (Class<? extends MessageHandler<? extends Message>> clazz : classes)
        {
            registerHandler(clazz);
        }
    }
    
    public static <V extends Message, T extends MessageHandler<V>> void registerHandler(
            Class<T> clazz) throws Exception
    {
        registerHandler(clazz, null);
    }
    
    public static void registerHandler(Map<String, Object> properties,
            Class<? extends MessageHandler<? extends Message>>... classes)
            throws Exception
    {
        for (Class<? extends MessageHandler<? extends Message>> clazz : classes)
        {
            registerHandler(clazz, properties);
        }
    }
    
    public static <V extends Message, T extends MessageHandler<V>> void registerHandler(
            Class<T> clazz, Map<String, Object> properties) throws Exception
    {
        Constructor<T> constructor = clazz.getConstructor();
        T handler = constructor.newInstance();
        handler.addProperties(properties);
        handler.init();
        final Class<V> msgClazz = (Class<V>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[0];
        handlers.put(msgClazz, handler);
    }
    
    public static <V extends Message, T extends MessageCodec<V>> T getCodec(
            byte opcode)
    {
        return (T) codecs.get(opcode);
    }
    
    public static <V extends Message, T extends MessageCodec<V>> T getCodec(
            Class<V> clazz)
    {
        return (T) codecs.get(opcodes.get(clazz));
    }
    
    public static <V extends Message, T extends MessageHandler<V>> T getHandler(
            Class<V> clazz)
    {
        return (T) handlers.get(clazz);
    }
}
