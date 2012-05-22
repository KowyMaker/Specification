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
package com.kowymaker.spec.utils.data;

import java.math.BigInteger;

public abstract class DataBuffer
{
    protected byte[] readableBytes  = new byte[0];
    protected byte[] writeableBytes = new byte[0];
    protected int    readPointer    = 0;
    protected int    writePointer   = 0;
    
    public void setReadableBytes(byte[] bytes)
    {
        readableBytes = bytes;
    }
    
    public void setReadableBytes(byte[] bytes, int length)
    {
        setReadableBytes(bytes, 0, length);
    }
    
    public void setReadableBytes(byte[] bytes, int index, int length)
    {
        readableBytes = new byte[length];
        
        for (int i = 0; i < length; i++)
        {
            readableBytes[i] = bytes[i + index];
        }
    }
    
    public int getReadPointer()
    {
        return readPointer;
    }
    
    public void setReadPointerTo(int pointer)
    {
        readPointer = pointer;
    }
    
    public void resetReadPointer()
    {
        readPointer = 0;
    }
    
    public int getWritePointer()
    {
        return writePointer;
    }
    
    public void setWritePointerTo(int pointer)
    {
        writePointer = pointer;
    }
    
    public void resetWritePointer()
    {
        writePointer = 0;
    }
    
    public void resetPointers()
    {
        readPointer = 0;
        writePointer = 0;
    }
    
    public boolean hasReadableBytes()
    {
        return readPointer < readableBytes.length;
    }
    
    public byte[] getWritedBytes()
    {
        return writeableBytes;
    }
    
    public int getWritedBytesSize()
    {
        return writeableBytes.length;
    }
    
    public byte[] getReadableBytes()
    {
        return readableBytes;
    }
    
    public int getReadableBytesSize()
    {
        return readableBytes.length;
    }
    
    public void copyWritedBytesToReadableBytes()
    {
        readableBytes = writeableBytes;
    }
    
    protected byte[] read(int length)
    {
        final byte[] readed = read(readPointer, length);
        readPointer += length;
        
        return readed;
    }
    
    protected abstract byte[] read(int start, int length);
    
    public void read(byte[] bytes)
    {
        read(bytes, bytes.length);
    }
    
    public void read(byte[] bytes, int length)
    {
        read(bytes, readPointer, length);
    }
    
    public void read(byte[] bytes, int start, int length)
    {
        bytes = read(start, length);
    }
    
    public byte readByte()
    {
        return Primitives.toByte(read(1));
    }
    
    public byte[] readBytes(int size)
    {
        return read(size);
    }
    
    public short readShort()
    {
        return Primitives.toShort(read(2));
    }
    
    public int readInt()
    {
        return Primitives.toInt(read(4));
    }
    
    public BigInteger readBigInt(int size)
    {
        return Primitives.toBigint(read(size));
    }
    
    public long readLong()
    {
        return Primitives.toLong(read(8));
    }
    
    public float readFloat()
    {
        return Primitives.toFloat(read(4));
    }
    
    public double readDouble()
    {
        return Primitives.toDouble(read(8));
    }
    
    public char readChar()
    {
        return Primitives.toChar(read(2));
    }
    
    public String readString()
    {
        final int size = readInt();
        return readString(size);
    }
    
    public String readString(int size)
    {
        return Primitives.toString(read(size));
    }
    
    public boolean readBoolean()
    {
        return Primitives.toBoolean(read(1));
    }
    
    protected void write(byte b)
    {
        write(new byte[] { b });
    }
    
    protected void write(byte[] bytes)
    {
        write(bytes, writePointer);
        writePointer += bytes.length;
    }
    
    protected void write(byte[] bytes, int start)
    {
        write(bytes, start, bytes.length);
    }
    
    protected abstract void write(byte[] bytes, int start, int length);
    
    public void writeByte(byte b)
    {
        write(b);
    }
    
    public void writeBytes(byte[] b)
    {
        write(b);
    }
    
    public void writeBytes(byte[] b, int length)
    {
        write(b, writePointer, length);
        writePointer += length;
    }
    
    public void writeDataBuffer(DataBuffer buf)
    {
        write(buf.getReadableBytes());
    }
    
    public void writeShort(short s)
    {
        write(Primitives.toByta(s));
    }
    
    public void writeShorts(short[] s)
    {
        write(Primitives.toByta(s));
    }
    
    public void writeInteger(int i)
    {
        write(Primitives.toByta(i));
    }
    
    public void writeIntegers(int[] i)
    {
        write(Primitives.toByta(i));
    }
    
    public void writeBigInteger(BigInteger b)
    {
        write(Primitives.toByta(b));
    }
    
    public void writeLong(long l)
    {
        write(Primitives.toByta(l));
    }
    
    public void writeLongs(long[] l)
    {
        write(Primitives.toByta(l));
    }
    
    public void writeFloat(float f)
    {
        write(Primitives.toByta(f));
    }
    
    public void writeFloats(float[] f)
    {
        write(Primitives.toByta(f));
    }
    
    public void writeDouble(double d)
    {
        write(Primitives.toByta(d));
    }
    
    public void writeDoubles(double[] d)
    {
        write(Primitives.toByta(d));
    }
    
    public void writeChar(char c)
    {
        write(Primitives.toByta(c));
    }
    
    public void writeChars(char[] c)
    {
        write(Primitives.toByta(c));
    }
    
    public void writeString(String s)
    {
        write(Primitives.toByta(s.length()));
        write(Primitives.toByta(s));
    }
    
    public void writeBoolean(boolean b)
    {
        write(Primitives.toByta(b));
    }
    
    public int indexOf(byte[] bytes)
    {
        int idx = -1;
        for (int i = 0; i < readableBytes.length - 3; i++)
        {
            if (readableBytes[i] == bytes[0])
            {
                boolean found = true;
                for (int j = 1; j < bytes.length; j++)
                {
                    if (readableBytes[i + j] != bytes[j])
                    {
                        found = false;
                    }
                }
                if (found)
                {
                    idx = i;
                    i = readableBytes.length;
                }
                else
                {
                    i += bytes.length;
                }
            }
        }
        
        return idx;
    }
}
