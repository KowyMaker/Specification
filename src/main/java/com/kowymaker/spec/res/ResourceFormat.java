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
        if (!testSignature.equals(signature))
        {
            return null;
        }
        
        // Test version
        byte testVersion = buf.readByte();
        if (testVersion < version)
        {
            return null;
        }
        
        // Name
        String name = buf.readString();
        
        int dataLength = buf.getReadableBytesSize() - buf.getReadPointer() - 4;
        
        byte[] dataBytes = new byte[dataLength];
        buf.read(dataBytes);
        
        byte[] checksum = new byte[4];
        buf.read(checksum, buf.getReadableBytesSize() - 4, 4);
        
        T res = null;
        
        if (Checksum.verify(dataBytes, checksum))
        {
            res = load(buf);
            res.setName(name);
        }
        else
        {
            throw new Exception("Checksum doesn't correspond!");
        }
        
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
        buf.writeBytes(signature);
        buf.writeByte(version);
        buf.writeString(res.getName());
        
        DataBuffer dataBuffer = new DynamicDataBuffer();
        save(res, dataBuffer);
        
        byte[] dataBytes = dataBuffer.getWritedBytes();
        byte[] checksum = Checksum.generate(dataBytes);
        
        dataBuffer.copyWritedBytesToReadableBytes();
        
        buf.writeDataBuffer(dataBuffer);
        buf.writeBytes(checksum);
        
        IOUtils.write(buf.getWritedBytes(), out);
    }
    
    public abstract void save(T res, DataBuffer buf);
    
    public static class Checksum
    {
        private static MessageDigest digest;
        
        static
        {
            try
            {
                digest = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
        
        public static byte[] generate(byte[] bytes)
        {
            byte[] checksum = new byte[4];
            
            byte[] digested = digest.digest(bytes);
            System.arraycopy(digested, 0, checksum, 0, 4);
            
            return checksum;
        }
        
        public static boolean verify(byte[] bytes, byte[] signature)
        {
            byte[] checksum = new byte[4];
            
            byte[] digested = digest.digest(bytes);
            System.arraycopy(digested, 0, checksum, 0, 4);
            
            boolean result = Arrays.equals(signature, checksum);
            
            return result;
        }
    }
    
}
