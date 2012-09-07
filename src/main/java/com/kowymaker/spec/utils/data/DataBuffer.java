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
