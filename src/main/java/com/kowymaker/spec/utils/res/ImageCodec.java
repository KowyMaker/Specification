package com.kowymaker.spec.utils.res;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import net.sourceforge.fastpng.PNGDecoder;
import net.sourceforge.fastpng.PNGDecoder.TextureFormat;

import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

public class ImageCodec
{
    public static byte[] decode(File file, Goal goal) throws IOException
    {
        String ext = file.getName().substring(
                file.getName().lastIndexOf('.') + 1);
        
        return decode(new FileInputStream(file),
                Format.getFormatFromExtension(ext), goal);
    }
    
    public static byte[] decode(byte[] bytes, Format format, Goal goal)
            throws IOException
    {
        return decode(new ByteArrayInputStream(bytes), format, goal);
    }
    
    public static byte[] decode(InputStream in, Format format, Goal goal)
            throws IOException
    {
        byte[] array = null;
        
        switch (format)
        {
            case PNG:
                array = decodePNG(in, goal);
                break;
        }
        
        return array;
    }
    
    /**
     * Decode an PNG file to bytes array.
     * 
     * @param in
     *            File Input.
     * @return PNG encoded array.
     * @throws IOException
     */
    public static byte[] decodePNG(InputStream in, Goal goal)
            throws IOException
    {
        byte[] bytes = new byte[0];
        
        switch (goal)
        {
            case RESOURCE:
                bytes = ByteStreams.toByteArray(in);
                break;
            
            case RAW:
                PNGDecoder decoder = new PNGDecoder(in);
                ByteBuffer buffer = ByteBuffer.allocate(decoder.getWidth()
                        * decoder.getHeight() * 4);
                TextureFormat format = TextureFormat.RGBA;
                decoder.decode(buffer, 4, format);
                
                bytes = buffer.array();
                break;
        }
        
        return bytes;
    }
    
    public static Dimension getDimension(File file) throws IOException
    {
        String ext = file.getName().substring(
                file.getName().lastIndexOf('.') + 1);
        
        return getDimension(new FileInputStream(file),
                Format.getFormatFromExtension(ext));
    }
    
    public static Dimension getDimension(byte[] bytes, Format format)
            throws IOException
    {
        return getDimension(new ByteArrayInputStream(bytes), format);
    }
    
    public static Dimension getDimension(InputStream in, Format format) throws IOException
    {
        Dimension dim = null;
        
        switch (format)
        {
            case PNG:
                dim = getDimensionPNG(in);
                break;
        }
        
        return dim;
    }
    
    public static Dimension getDimensionPNG(InputStream in) throws IOException
    {
        PNGDecoder decoder = new PNGDecoder(in);
        
        return new Dimension(decoder.getWidth(), decoder.getHeight());
    }
    
    public static enum Format
    {
        PNG("png");
        
        private final String[] extensions;
        
        Format(String... extensions)
        {
            this.extensions = extensions;
        }
        
        public String[] getExtensions()
        {
            return extensions;
        }
        
        private final static Map<String, Format> formats = Maps.newLinkedHashMap();
        
        public static Format getFormatFromExtension(String ext)
        {
            return formats.get(ext);
        }
        
        static
        {
            for (Format format : values())
            {
                for (String ext : format.extensions)
                {
                    formats.put(ext, format);
                }
            }
        }
    }
    
    public static enum Goal
    {
        RAW, RESOURCE;
    }
}
