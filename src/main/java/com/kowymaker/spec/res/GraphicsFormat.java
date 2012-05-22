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
package com.kowymaker.spec.res;

import com.kowymaker.spec.utils.data.DataBuffer;

public abstract class GraphicsFormat<T extends GraphicsFile> extends
        ResourceFormat<T>
{
    
    public GraphicsFormat(byte version, byte[] signature)
    {
        super(version, signature);
    }
    
    @Override
    public T load(DataBuffer buf)
    {
        final String handlerClassName = buf.readString();
        
        final T res = loadData(buf);
        res.setHandlerClassName(handlerClassName);
        
        return res;
    }
    
    @Override
    protected void saveToBuffer(T res, DataBuffer buf)
    {
        buf.writeString(res.getHandlerClassName());
        
        saveData(res, buf);
    }
    
    protected abstract T loadData(DataBuffer buf);
    
    protected abstract void saveData(T res, DataBuffer buf);
    
}
