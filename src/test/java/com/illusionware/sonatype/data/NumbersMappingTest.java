package com.illusionware.sonatype.data;

import org.junit.Assert;
import org.junit.Test;

public class NumbersMappingTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnArgumentOutsideOfRangeUpperBound() {
        NumbersMapping.getNumberMappingOrNearest(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnArgumentOutsideOfRangeLowerBound() {
        NumbersMapping.getNumberMappingOrNearest(-1);
    }

    @Test
    public void shouldMapCorrectlyToNearestWord() {
        String nearestNumberMapping = NumbersMapping.getNumberMappingOrNearest(45);
        Assert.assertEquals("The value 45 should be mapped to forty", "forty", nearestNumberMapping);
    }

    @Test
    public void shouldMapCorrectlyToExactKeyIfAvailableWord() {
        String exactNumberMapping = NumbersMapping.getNumberMappingOrNearest(13);
        Assert.assertEquals("The value 13 should be mapped to thirteen", "thirteen", exactNumberMapping);
    }
}
