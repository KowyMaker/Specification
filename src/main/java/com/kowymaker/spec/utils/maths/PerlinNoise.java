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
package com.kowymaker.spec.utils.maths;

/**
 * Improved Perlin noise based on the reference implementation by Ken Perlin.
 * 
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class PerlinNoise
{
    
    private final int[] _noisePermutations, _noiseTable;
    
    /**
     * @param seed
     */
    public PerlinNoise(int seed)
    {
        final FastRandom rand = new FastRandom(seed);
        _noisePermutations = new int[512];
        _noiseTable = new int[256];
        
        for (int i = 0; i < 256; i++)
        {
            _noiseTable[i] = i;
        }
        
        for (int i = 0; i < 256; i++)
        {
            int j = rand.randomInt() % 256;
            j = j < 0 ? -j : j;
            
            final int swap = _noiseTable[i];
            _noiseTable[i] = _noiseTable[j];
            _noiseTable[j] = swap;
        }
        
        for (int i = 0; i < 256; i++)
        {
            _noisePermutations[i] = _noisePermutations[i + 256] = _noiseTable[i];
        }
        
    }
    
    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public double noise(double x, double y, double z)
    {
        final int X = (int) Math.floor(x) & 255, Y = (int) Math.floor(y) & 255, Z = (int) Math
                .floor(z) & 255;
        
        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);
        
        final double u = fade(x), v = fade(y), w = fade(z);
        final int A = _noisePermutations[X] + Y, AA = _noisePermutations[A] + Z, AB = _noisePermutations[A + 1]
                + Z, B = _noisePermutations[X + 1] + Y, BA = _noisePermutations[B]
                + Z, BB = _noisePermutations[B + 1] + Z;
        
        return lerp(
                w,
                lerp(v,
                        lerp(u, grad(_noisePermutations[AA], x, y, z),
                                grad(_noisePermutations[BA], x - 1, y, z)),
                        lerp(u, grad(_noisePermutations[AB], x, y - 1, z),
                                grad(_noisePermutations[BB], x - 1, y - 1, z))),
                lerp(v,
                        lerp(u,
                                grad(_noisePermutations[AA + 1], x, y, z - 1),
                                grad(_noisePermutations[BA + 1], x - 1, y,
                                        z - 1)),
                        lerp(u,
                                grad(_noisePermutations[AB + 1], x, y - 1,
                                        z - 1),
                                grad(_noisePermutations[BB + 1], x - 1, y - 1,
                                        z - 1))));
    }
    
    /**
     * @param t
     * @return
     */
    private static double fade(double t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    /**
     * @param t
     * @param a
     * @param b
     * @return
     */
    private static double lerp(double t, double a, double b)
    {
        return a + t * (b - a);
    }
    
    /**
     * @param hash
     * @param x
     * @param y
     * @param z
     * @return
     */
    private static double grad(int hash, double x, double y, double z)
    {
        final int h = hash & 15;
        final double u = h < 8 ? x : y, v = h < 4 ? y : h == 12 || h == 14 ? x
                : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
    
    /**
     * @param x
     * @param y
     * @param z
     * @param octaves
     * @param lacunarity
     * @param h
     * @return
     */
    public double fBm(double x, double y, double z, int octaves,
            double lacunarity, double h)
    {
        double result = 0.0;
        
        for (int i = 0; i < octaves; i++)
        {
            result += noise(x, y, z) * Math.pow(lacunarity, -h * i);
            
            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
        }
        
        return result;
    }
}
