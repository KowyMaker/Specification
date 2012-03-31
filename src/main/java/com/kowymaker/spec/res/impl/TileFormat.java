package com.kowymaker.spec.res.impl;

import com.kowymaker.spec.res.GraphicsFormat;
import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.res.Coordinates;
import com.kowymaker.spec.utils.res.Sequencer;

public class TileFormat extends GraphicsFormat<TileFile>
{
    
    public TileFormat()
    {
        super((byte) 0x01, sign("kmrt"));
    }
    
    @Override
    protected TileFile loadData(DataBuffer buf)
    {
        TileFile file = new TileFile();
        
        Coordinates left = new Coordinates();
        left.decode(buf);
        file.setLeft(left);
        
        Coordinates right = new Coordinates();
        right.decode(buf);
        file.setRight(right);
        
        Coordinates center = new Coordinates();
        center.decode(buf);
        file.setCenter(center);
        
        Coordinates bottom = new Coordinates();
        bottom.decode(buf);
        file.setBottom(bottom);
        
        Sequencer sequencer = new Sequencer();
        sequencer.decode(buf);
        file.setSequencer(sequencer);
        
        return file;
    }
    
    @Override
    protected void saveData(TileFile res, DataBuffer buf)
    {
        res.getLeft().encode(buf);
        res.getRight().encode(buf);
        res.getCenter().encode(buf);
        res.getBottom().encode(buf);
        res.getSequencer().encode(buf);
    }
    
}
