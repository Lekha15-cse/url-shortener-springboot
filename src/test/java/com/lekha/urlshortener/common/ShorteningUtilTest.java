package com.lekha.urlshortener.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class ShorteningUtilTest {

    @Test
    public void shouldConvertMaxLongToShortString() {
        String maxIdShortString = ShorteningUtil.idToStr(Long.MAX_VALUE);
        assertNotNull(maxIdShortString);
        assertNotEquals("", maxIdShortString);
    }

    @Test
    public void shouldThrowExceptionWhenShortStrLongerThanTenChars() {
        long id = ShorteningUtil.strToId("sclqgMAPqi2Z");
    }

}