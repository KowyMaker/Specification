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
package com.kowymaker.spec.utils.data;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferFactory;
import org.jboss.netty.buffer.DynamicChannelBuffer;

public class DataBuffer extends DynamicChannelBuffer
{
    public DataBuffer()
    {
        this(16);
    }
    
    public DataBuffer(byte[] data)
    {
        this(data.length);
        setBytes(0, data);
    }
    
    public DataBuffer(ChannelBuffer buf)
    {
        this(buf.capacity());
        setBytes(0, buf);
    }
    
    public DataBuffer(int estimatedLength)
    {
        super(estimatedLength);
    }
    
    public DataBuffer(ByteOrder endianness, int estimatedLength)
    {
        super(endianness, estimatedLength);
    }
    
    public DataBuffer(ByteOrder endianness, int estimatedLength,
            ChannelBufferFactory factory)
    {
        super(endianness, estimatedLength, factory);
    }
    
    public void writeString(String str)
    {
        writeInt(str.length());
        writeBytes(Primitives.toByta(str));
    }
    
    public String readString()
    {
        int length = readInt();
        byte[] buf = new byte[length];
        
        readBytes(buf);
        
        return Primitives.toString(buf);
    }
    
    public int size()
    {
        return writerIndex();
    }
}
