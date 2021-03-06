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
package com.kowymaker.spec.res;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.netty.buffer.ChannelBufferInputStream;

import com.kowymaker.spec.utils.data.DataBuffer;

public abstract class ResourceFormat<T extends ResourceFile>
{
    protected final byte[] signature;
    protected final byte   version;
    
    public ResourceFormat(byte version, byte... signature)
    {
        this.signature = signature;
        this.version = version;
    }
    
    // Getters, setters
    
    public byte[] getSignature()
    {
        return signature;
    }
    
    public byte getVersion()
    {
        return version;
    }
    
    // Utilities
    
    protected static byte[] sign(String signature)
    {
        byte[] b = signature.getBytes(Charset.forName("US-ASCII"));
        
        b = Arrays.copyOf(b, 5);
        
        return b;
    }
    
    public T load(File file) throws Exception
    {
        final byte[] data = FileUtils.readFileToByteArray(file);
        
        return load(data);
    }
    
    public T load(InputStream in) throws Exception
    {
        final byte[] data = IOUtils.toByteArray(in);
        
        return load(data);
    }
    
    public T load(byte[] data) throws Exception
    {
        // final DataBuffer buf = new DynamicDataBuffer();
        final DataBuffer buf = new DataBuffer(data);
        
        // Test signature
        final byte[] testSignature = new byte[5];
        buf.readBytes(testSignature);
        if (!Arrays.equals(testSignature, signature))
        {
            throw new RuntimeException("Signature mismatch!");
        }
        
        // Test version
        final byte testVersion = buf.readByte();
        if (testVersion < version)
        {
            throw new RuntimeException("Too old version!");
        }
        
        // Name
        final String name = buf.readString();
        
        final T res = load(buf);
        res.setName(name);
        
        return res;
    }
    
    public abstract T load(DataBuffer buf);
    
    public void save(T res, File file) throws IOException
    {
        save(res, new FileOutputStream(file));
    }
    
    public void save(T res, OutputStream out) throws IOException
    {
        final DataBuffer buf = new DataBuffer();
        
        save(res, buf);
        
        IOUtils.copy(new ChannelBufferInputStream(buf), out);
    }
    
    public void save(T res, DataBuffer buf)
    {
        buf.writeBytes(signature);
        buf.writeByte(version);
        buf.writeString(res.getName());
        
        saveToBuffer(res, buf);
    }
    
    protected abstract void saveToBuffer(T res, DataBuffer buf);
    
}
