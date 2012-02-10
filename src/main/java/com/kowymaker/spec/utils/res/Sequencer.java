package com.kowymaker.spec.utils.res;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.Encodable;

public class Sequencer implements Encodable
{
    private final Map<String, Sequence> sequences = new LinkedHashMap<String, Sequence>();
    
    public Sequence createSequence(String name)
    {
        Sequence sequence = new Sequence(name);
        sequences.put(name, sequence);
        
        return sequence;
    }
    
    public Map<String, Sequence> getSequences()
    {
        return sequences;
    }
    
    public Sequence getSequence(String name)
    {
        return sequences.get(name);
    }
    
    public void encode(DataBuffer buf)
    {
        buf.writeInteger(sequences.size());
        for (Sequence sequence : sequences.values())
        {
            sequence.encode(buf);
        }
    }
    
    public void decode(DataBuffer buf)
    {
        int num = buf.readInt();
        for (int i = 0; i < num; i++)
        {
            Sequence sequence = new Sequence(null);
            sequence.decode(buf);
            
            sequences.put(sequence.name, sequence);
        }
    }
    
    public static class Sequence implements Encodable
    {
        private String            name;
        private int               fps;
        private final List<Frame> frames = new ArrayList<Frame>();
        
        public Sequence(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public int getFps()
        {
            return fps;
        }
        
        public void setFps(int fps)
        {
            this.fps = fps;
        }
        
        public List<Frame> getFrames()
        {
            return frames;
        }
        
        public Frame createFrame()
        {
            Frame frame = new Frame();
            
            frames.add(frame);
            
            return frame;
        }
        
        public Frame getFrame(int index)
        {
            Frame frame = null;
            
            if (index < frames.size())
            {
                return frames.get(index);
            }
            
            return frame;
        }
        
        public int getLength()
        {
            return frames.size();
        }
        
        public void encode(DataBuffer buf)
        {
            buf.writeString(name);
            buf.writeInteger(fps);
            buf.writeInteger(frames.size());
            for (Frame frame : frames)
            {
                frame.encode(buf);
            }
        }
        
        public void decode(DataBuffer buf)
        {
            name = buf.readString();
            fps = buf.readInt();
            int length = buf.readInt();
            for (int i = 0; i < length; i++)
            {
                Frame frame = new Frame();
                frames.add(frame);
                
                frame.decode(buf);
            }
        }
    }
    
    public static class SequenceRunner
    {
        private final Sequence sequence;
        private long           start = 0;
        
        public SequenceRunner(Sequence sequence)
        {
            this.sequence = sequence;
        }
        
        public Sequence getSequence()
        {
            return sequence;
        }
        
        public long getStart()
        {
            return start;
        }
        
        public void setStart()
        {
            setStart(System.currentTimeMillis());
        }
        
        public void setStart(long start)
        {
            this.start = start;
        }
        
        public Frame get()
        {
            return get(System.currentTimeMillis());
        }
        
        public Frame get(long time)
        {
            Frame frame = null;
            
            long diff = time - start;
            
            if (diff > 0)
            {
                int r = (int) (new BigDecimal(diff)
                        .divide(new BigDecimal(1000))
                        .multiply(new BigDecimal(sequence.fps)).floatValue() % sequence
                        .getLength());
                
                frame = sequence.getFrame(r);
            }
            
            return frame;
        }
    }
    
    public static class Frame implements Encodable
    {
        private int    width;
        private int    height;
        private byte[] data;
        
        public Frame()
        {
            this(new byte[0]);
        }
        
        public Frame(byte[] data)
        {
            this.data = data;
        }
        
        public int getWidth()
        {
            return width;
        }
        
        public void setWidth(int width)
        {
            this.width = width;
        }
        
        public int getHeight()
        {
            return height;
        }
        
        public void setHeight(int height)
        {
            this.height = height;
        }
        
        public byte[] getData()
        {
            return data;
        }
        
        public void setData(byte[] data)
        {
            this.data = data;
        }
        
        public void encode(DataBuffer buf)
        {
            buf.writeInteger(width);
            buf.writeInteger(height);
            buf.writeInteger(data.length);
            buf.writeBytes(data);
        }
        
        public void decode(DataBuffer buf)
        {
            width = buf.readInt();
            height = buf.readInt();
            int size = buf.readInt();
            data = buf.readBytes(size);
        }
    }
}
