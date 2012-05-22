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
package com.kowymaker.spec.utils.res;

import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.Encodable;

public class Dimension implements Encodable
{
    private int width;
    private int height;
    
    public Dimension()
    {
        this(0, 0);
    }
    
    public Dimension(int width, int height)
    {
        this.width = width;
        this.height = height;
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
    
    public void setDimension(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    
    public void encode(DataBuffer buf)
    {
        buf.writeInteger(width);
        buf.writeInteger(height);
    }
    
    public void decode(DataBuffer buf)
    {
        width = buf.readInt();
        height = buf.readInt();
    }
}