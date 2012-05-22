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
 * along with Kowy Maker.  If not, see <http://www.gnu.org/licenses/>.
 */
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
        final TileFile file = new TileFile();
        
        final Coordinates left = new Coordinates();
        left.decode(buf);
        file.setLeft(left);
        
        final Coordinates right = new Coordinates();
        right.decode(buf);
        file.setRight(right);
        
        final Coordinates center = new Coordinates();
        center.decode(buf);
        file.setCenter(center);
        
        final Coordinates bottom = new Coordinates();
        bottom.decode(buf);
        file.setBottom(bottom);
        
        final Sequencer sequencer = new Sequencer();
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
