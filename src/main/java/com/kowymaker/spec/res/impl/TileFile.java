package com.kowymaker.spec.res.impl;

import com.kowymaker.spec.res.GraphicsFile;
import com.kowymaker.spec.utils.res.Coordinates;
import com.kowymaker.spec.utils.res.Sequencer;

public class TileFile extends GraphicsFile
{
    private Coordinates left      = new Coordinates();
    private Coordinates right     = new Coordinates();
    private Coordinates center    = new Coordinates();
    private Coordinates bottom    = new Coordinates();
    
    private Sequencer   sequencer = new Sequencer();
    
    public Coordinates getLeft()
    {
        return left;
    }
    
    public void setLeft(Coordinates left)
    {
        this.left = left;
    }
    
    public Coordinates getRight()
    {
        return right;
    }
    
    public void setRight(Coordinates right)
    {
        this.right = right;
    }
    
    public Coordinates getCenter()
    {
        return center;
    }
    
    public void setCenter(Coordinates center)
    {
        this.center = center;
    }
    
    public Coordinates getBottom()
    {
        return bottom;
    }
    
    public void setBottom(Coordinates bottom)
    {
        this.bottom = bottom;
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
