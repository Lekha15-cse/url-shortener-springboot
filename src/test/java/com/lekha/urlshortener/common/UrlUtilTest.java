package com.lekha.urlshortener.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UrlUtilTest {

    @Test
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithoutProtocol() {
        assertThrows(MalformedURLException.class, () -> UrlUtil.getBaseUrl("malformed url dummy text"));
    }

    @Test
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithIllegalChars() {
        assertThrows(MalformedURLException.class, () -> UrlUtil.getBaseUrl("malformed://example.com/foo"));
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithoutPort() throws MalformedURLException {
        assertEquals("http://example.com/", UrlUtil.getBaseUrl("http://example.com/foo"));
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithPort() throws MalformedURLException {
        assertEquals("http://example.com:8080/", UrlUtil.getBaseUrl("http://example.com:8080/foo"));
    }
}