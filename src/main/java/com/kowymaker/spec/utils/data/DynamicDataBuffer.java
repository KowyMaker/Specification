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

public class DynamicDataBuffer extends DataBuffer
{
    
    @Override
    protected byte[] read(int start, int length)
    {
        if (start + length > readableBytes.length)
        {
            length = readableBytes.length - start;
        }
        final byte[] out = new byte[length];
        
        for (int i = 0; i < length; i++)
        {
            out[i] = readableBytes[start + i];
        }
        
        return out;
    }
    
    @Override
    protected void write(byte[] bytes, int start, int length)
    {
        final int diff = start + length - writeableBytes.length;
        if (diff > 0)
        {
            final byte[] newBytes = new byte[writeableBytes.length + diff];
            for (int i = 0; i < writeableBytes.length; i++)
            {
                newBytes[i] = writeableBytes[i];
            }
            writeableBytes = newBytes;
        }
        
        for (int i = 0; i < length; i++)
        {
            writeableBytes[start + i] = bytes[i];
        }
    }
    
    public static DynamicDataBuffer merge(DynamicDataBuffer... buffers)
    {
        final DynamicDataBuffer buffer = new DynamicDataBuffer();
        
        for (final DynamicDataBuffer buf : buffers)
        {
            buffer.writeBytes(buf.getWritedBytes());
        }
        
        return buffer;
    }
}
