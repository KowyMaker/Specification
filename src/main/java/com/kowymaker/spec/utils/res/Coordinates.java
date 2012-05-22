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

public class Coordinates implements Encodable
{
    private double x;
    private double y;
    
    public Coordinates()
    {
        this(0, 0);
    }
    
    public Coordinates(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public void setLocation(Coordinates coord)
    {
        setLocation(coord.getX(), coord.getY());
    }
    
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void encode(DataBuffer buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
    }
    
    public void decode(DataBuffer buf)
    {
        x = buf.readDouble();
        y = buf.readDouble();
    }
    
    @Override
    public String toString()
    {
        return "[" + x + ", " + y + "]";
    }
}