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
/**
 *  This file is part of Kowy Maker - Specification.
 *
 *  Kowy Maker - Specification is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Kowy Maker - Specification is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Kowy Maker - Specification.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kowymaker.spec.net.codec;

import com.kowymaker.spec.net.msg.DisconnectMessage;
import com.kowymaker.spec.utils.data.DataBuffer;

/**
 * @author Koka El Kiwi
 * 
 */
public class DisconnectCodec extends MessageCodec<DisconnectMessage>
{
    
    public DisconnectCodec()
    {
        super((byte) 0x02);
    }
    
    @Override
    public DisconnectMessage decode(DataBuffer buf)
    {
        final DisconnectMessage msg = new DisconnectMessage();
        
        final String name = buf.readString(buf.getReadableBytesSize()
                - buf.getReadPointer());
        msg.setName(name);
        
        return msg;
    }
    
    @Override
    public void encode(DataBuffer buf, DisconnectMessage msg)
    {
        buf.writeString(msg.getName());
    }
    
}
