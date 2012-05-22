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
package com.kowymaker.spec.tests.utils.res;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.kowymaker.spec.utils.res.ImageCodec;

public class DecoderTest
{
    
    @Test
    public void test() throws IOException
    {
        final File file = new File("decode_png.png");
        
        final long start = System.currentTimeMillis();
        final byte[] bytes = ImageCodec.decode(file, ImageCodec.Goal.RESOURCE);
        final long end = System.currentTimeMillis();
        final long diff = end - start;
        
        System.out.println("Diff      : " + diff + " ms.");
        
        System.out.println("Data size : " + bytes.length + " bytes.");
        
        final BufferedImage image = ImageIO
                .read(new ByteArrayInputStream(bytes));
        
        System.out.println("Width     : " + image.getWidth());
        System.out.println("Height    : " + image.getHeight());
    }
    
}
