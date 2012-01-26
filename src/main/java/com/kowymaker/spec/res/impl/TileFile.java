package com.kowymaker.spec.res.impl;

import com.kowymaker.spec.res.GraphicsFile;
import com.kowymaker.spec.utils.res.Coordinates;
import com.kowymaker.spec.utils.res.Dimension;
import com.kowymaker.spec.utils.res.Sequencer;

public class TileFile extends GraphicsFile
{
    private Coordinates origin    = new Coordinates();
    private Coordinates left      = new Coordinates();
    private Coordinates center    = new Coordinates();
    private Dimension   dimension = new Dimension();
    
    private Sequencer   sequencer = new Sequencer();
    
    public Coordinates getOrigin()
    {
        return origin;
    }
    
    public void setOrigin(Coordinates origin)
    {
        this.origin = origin;
    }
    
    public Coordinates getLeft()
    {
        return left;
    }
    
    public void setLeft(Coordinates left)
    {
        this.left = left;
    }
    
    public Coordinates getCenter()
    {
        return center;
    }
    
    public void setCenter(Coordinates center)
    {
        this.center = center;
    }
    
    public Dimension getDimension()
    {
        return dimension;
    }
    
    public void setDimension(Dimension dimension)
    {
        this.dimension = dimension;
    }
    
    public Sequencer getSequencer()
    {
        return sequencer;
    }
    
    public void setSequencer(Sequencer sequencer)
    {
        this.sequencer = sequencer;
    }
}
