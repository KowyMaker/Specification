package com.kowymaker.spec.res;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.DynamicDataBuffer;

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
        byte[] data = FileUtils.readFileToByteArray(file);
        
        return load(data);
    }
    
    public T load(InputStream in) throws Exception
    {
        byte[] data = IOUtils.toByteArray(in);
        
        return load(data);
    }
    
    public T load(byte[] data) throws Exception
    {
        DataBuffer buf = new DynamicDataBuffer();
        buf.setReadableBytes(data);
        
        // Test signature
        byte[] testSignature = buf.readBytes(5);
        if (!Arrays.equals(testSignature, signature))
        {
            throw new RuntimeException("Signature mismatch!");
        }
        
        // Test version
        byte testVersion = buf.readByte();
        if (testVersion < version)
        {
            throw new RuntimeException("Too old version!");
        }
        
        // Name
        String name = buf.readString();
        
        T res = load(buf);
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
        DataBuffer buf = new DynamicDataBuffer();
        
        save(res, buf);
        
        IOUtils.write(buf.getWritedBytes(), out);
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
