package com.illusionware.sonatype.data;

import com.illusionware.sonatype.service.ConversionService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StaticNumbersMapperTest {

    private StaticNumbersMapper numbersMapper;

    @Before
    public void setup() {
        numbersMapper = new StaticNumbersMapper();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnArgumentOutsideOfRangeUpperBound() {
        numbersMapper.getNumberMappingOrNearest(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnArgumentOutsideOfRangeLowerBound() {
        numbersMapper.getNumberMappingOrNearest(-1);
    }

    @Test
    public void shouldMapCorrectlyToNearestWord() {
        String nearestNumberMapping = numbersMapper.getNumberMappingOrNearest(45);
        Assert.assertEquals("The value 45 should be mapped to forty", "forty", nearestNumberMapping);
    }

    @Test
    public void shouldMapCorrectlyToExactKeyIfAvailableWord() {
        String exactNumberMapping = numbersMapper.getNumberMappingOrNearest(13);
        Assert.assertEquals("The value 13 should be mapped to thirteen", "thirteen", exactNumberMapping);
    }
}
