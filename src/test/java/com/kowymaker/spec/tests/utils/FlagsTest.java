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
 * along with Kowy Maker.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 */
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
package com.kowymaker.spec.tests.utils;

import org.junit.Test;

import com.kowymaker.spec.utils.data.Flags;

public class FlagsTest
{
    
    @Test
    public void test()
    {
        final Flags flags = new Flags(8);
        
        final Flags.Flag test1 = flags.createFlag("test1");
        final Flags.Flag test2 = flags.createFlag("test2");
        final Flags.Flag test3 = flags.createFlag("test3");
        final Flags.Flag test4 = flags.createFlag("test4");
        
        flags.setFlag(test2, true);
        flags.setFlag(test3, true);
        
        System.out.println(flags.hasFlag(test1));
        System.out.println(flags.hasFlag(test2));
        System.out.println(flags.hasFlag(test3));
        System.out.println(flags.hasFlag(test4));
    }
    
}
