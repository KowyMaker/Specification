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
package com.kowymaker.spec.res;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.kowymaker.spec.utils.FileUtils;
import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.DynamicDataBuffer;

/**
 * @author Koka El Kiwi
 * 
 */
public abstract class GraphicFormat<T extends GraphicFile> extends
        ResourceFormat<T>
{
    
    /**
     * @param version
     * @param signature
     */
    public GraphicFormat(byte version, byte[] signature)
    {
        super(version, signature);
    }
    
    public T load(File file) throws Exception
    {
        byte[] data = FileUtils.load(file);
        DataBuffer buf = new DynamicDataBuffer();
        buf.setReadableBytes(data);
        
        //Test signature
        byte[] testSignature = buf.readBytes(3);
        if (!Arrays.equals(testSignature, signature))
        {
            throw new Exception("Signature not match!");
        }
        
        //Test version
        byte testVersion = buf.readByte();
        if (testVersion < version)
        {
            throw new Exception("Old version!");
        }
        
        String handlerClassName = buf.readString(buf.readInt());
        int width = buf.readInt();
        int height = buf.readInt();
        
        T res = load(buf);
        
        res.setHandlerClassName(handlerClassName);
        res.setWidth(width);
        res.setHeight(height);
        
        return res;
    }
    
    public abstract T load(DataBuffer buf);
    
    public void save(T res, File file) throws IOException
    {
        DataBuffer buf = new DynamicDataBuffer();
        buf.writeBytes(signature);
        buf.writeByte(version);
        buf.writeInteger(res.getHandlerClassName().length());
        buf.writeString(res.getHandlerClassName());
        buf.writeInteger(res.getWidth());
        buf.writeInteger(res.getHeight());
        
        save(res, buf);
        
        FileUtils.save(file, buf.getWritedBytes());
    }
    
    public abstract void save(T res, DataBuffer buf);
    
}
