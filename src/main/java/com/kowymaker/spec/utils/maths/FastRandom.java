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
package com.kowymaker.spec.utils.maths;

/**
 * Random number generator based on the Xorshift generator by George Marsaglia.
 * 
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class FastRandom
{
    
    private long _seed = System.currentTimeMillis();
    
    /**
     * Initializes a new instance of the random number generator using a
     * specified seed.
     * 
     * @param seed
     *            The seed to use
     */
    public FastRandom(long seed)
    {
        _seed = seed;
    }
    
    /**
     * Initializes a new instance of the random number generator using
     * System.currentTimeMillis() as seed.
     */
    public FastRandom()
    {
    }
    
    /**
     * Returns a random value as long.
     * 
     * @return Random value
     */
    long randomLong()
    {
        _seed ^= _seed << 21;
        _seed ^= _seed >>> 35;
        _seed ^= _seed << 4;
        return _seed;
    }
    
    /**
     * Returns a random value as integer.
     * 
     * @return Random value
     */
    public int randomInt()
    {
        return (int) randomLong();
    }
    
    /**
     * Returns a random value as double.
     * 
     * @return Random value
     */
    public double randomDouble()
    {
        return randomLong() / (Long.MAX_VALUE - 1d);
    }
    
    /**
     * Returns a random value as boolean.
     * 
     * @return Random value
     */
    public boolean randomBoolean()
    {
        return randomLong() > 0;
    }
    
    /**
     * Returns a random character string with a specified length.
     * 
     * @param length
     *            The length of the generated string
     * @return Random character string
     */
    public String randomCharacterString(int length)
    {
        final StringBuilder s = new StringBuilder();
        
        for (int i = 0; i < length / 2; i++)
        {
            s.append((char) ('a' + Math.abs(randomDouble()) * 26d));
            s.append((char) ('A' + Math.abs(randomDouble()) * 26d));
        }
        
        return s.toString();
    }
    
    /**
     * Calculates a standardized normal distributed value (using the polar
     * method).
     * 
     * @return
     */
    public double standNormalDistrDouble()
    {
        
        double q = Double.MAX_VALUE;
        double u1 = 0;
        double u2;
        
        while (q >= 1d || q == 0)
        {
            u1 = randomDouble();
            u2 = randomDouble();
            
            q = Math.pow(u1, 2) + Math.pow(u2, 2);
        }
        
        final double p = Math.sqrt(-2d * Math.log(q) / q);
        return u1 * p; // or u2 * p
    }
    
    /**
     * Some random noise.
     * 
     * @param x
     * @param y
     * @param z
     * @param seed
     * @return
     */
    public static double randomNoise(double x, double y, double z, int seed)
    {
        int u = (int) x * 21342412 + (int) y * 423241324 + (int) z * 4123241
                + seed * 41234234;
        u = u << 13 ^ u;
        return 1.0 - (u * (u * u * 15731 + 789221) + 1376312589 & 0x7fffffff) / 441441557.0;
    }
}
