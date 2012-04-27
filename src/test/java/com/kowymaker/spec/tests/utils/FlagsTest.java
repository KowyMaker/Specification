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
