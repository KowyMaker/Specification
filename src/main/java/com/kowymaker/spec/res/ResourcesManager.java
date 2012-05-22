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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.kowymaker.spec.res.impl.TileFormat;

@SuppressWarnings("unused")
public class ResourcesManager
{
    private final static Map<Class<? extends ResourceFormat<? extends ResourceFile>>, ResourceFormat<? extends ResourceFile>> formats = new HashMap<Class<? extends ResourceFormat<? extends ResourceFile>>, ResourceFormat<? extends ResourceFile>>();
    
    static
    {
        try
        {
            register(TileFormat.class);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void register(
            Class<? extends ResourceFormat<? extends ResourceFile>>... classes)
            throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        for (final Class<? extends ResourceFormat<? extends ResourceFile>> clazz : classes)
        {
            register(clazz);
        }
    }
    
    public static <T extends ResourceFormat<? extends ResourceFile>> void register(
            Class<T> clazz) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        final Constructor<T> constructor = clazz.getConstructor();
        final T format = constructor.newInstance();
        formats.put(clazz, format);
    }
    
    @SuppressWarnings("unchecked")
    public static <V extends ResourceFile, T extends ResourceFormat<V>> T get(
            Class<T> clazz)
    {
        return (T) formats.get(clazz);
    }
}
