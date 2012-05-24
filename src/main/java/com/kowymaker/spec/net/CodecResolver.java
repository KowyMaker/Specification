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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.kowymaker.spec.net.codec.ConnectCodec;
import com.kowymaker.spec.net.codec.DisconnectCodec;
import com.kowymaker.spec.net.codec.MessageCodec;
import com.kowymaker.spec.net.msg.ConnectMessage;
import com.kowymaker.spec.net.msg.DisconnectMessage;
import com.kowymaker.spec.net.msg.Message;

/**
 * Kowy Maker Project Codec Manager.<br />
 * 
 * Contains all specifications used to transmit packets between Client and
 * Server.
 * 
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
            // Register basic codecs.
            registerCodec(ConnectMessage.class, ConnectCodec.class);
            registerCodec(DisconnectMessage.class, DisconnectCodec.class);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Register codec with his {@link MessageCodec} class.
     * 
     * @param msgClazz
     *            Message class to bind to.
     * @param clazz
     *            Message codec class.
     * @throws Exception
     *             if classes doesn't exists.
     */
    public <V extends Message, T extends MessageCodec<V>> void registerCodec(
            Class<V> msgClazz, Class<T> clazz) throws Exception
    {
        // Codec
        final Constructor<T> constructor = clazz.getConstructor();
        final T codec = constructor.newInstance();
        registerCodec(msgClazz, codec);
    }
    
    /**
     * Register codec.
     * 
     * @param msgClazz
     *            Message class to bind to.
     * @param codec
     *            Message codec
     */
    public <V extends Message, T extends MessageCodec<V>> void registerCodec(
            Class<V> msgClazz, T codec)
    {
        codecs.put(codec.getOpcode(), codec);
        opcodes.put(msgClazz, codec.getOpcode());
    }
    
    /**
     * Register handler.
     * 
     * @param msgClazz
     *            Message class to bind to.
     * @param handler
     *            Message handler
     */
    public <V extends Message, T extends MessageHandler<V>> void registerHandler(
            Class<V> msgClazz, T handler)
    {
        handlers.put(msgClazz, handler);
    }
    
    /**
     * Get the codec bound to a specific opcode.
     * 
     * @param opcode
     *            Message opcode.
     * @return Codec bound to this opcode.
     */
    public <V extends Message, T extends MessageCodec<V>> T getCodec(byte opcode)
    {
        return (T) codecs.get(opcode);
    }
    
    /**
     * Get the codec bound to a specific message class.
     * 
     * @param clazz
     *            Message class.
     * @return Codec bound to this message class.
     */
    public <V extends Message, T extends MessageCodec<V>> T getCodec(
            Class<V> clazz)
    {
        return (T) codecs.get(opcodes.get(clazz));
    }
    
    /**
     * Get the handler bound to a specific message handler.
     * 
     * @param clazz
     *            Message class.
     * @return Handler bound to this message class.
     */
    public <V extends Message, T extends MessageHandler<V>> T getHandler(
            Class<V> clazz)
    {
        return (T) handlers.get(clazz);
    }
}
