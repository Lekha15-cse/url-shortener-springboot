package com.lekha.urlshortener.controller;

import com.lekha.urlshortener.common.UrlUtil;
import com.lekha.urlshortener.dto.ShortUrl;
import com.lekha.urlshortener.error.InvalidUrlError;
import com.lekha.urlshortener.dto.FullUrl;
import com.lekha.urlshortener.service.UrlService;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

@Controller
public class UrlController {

    Logger logger = LoggerFactory.getLogger(UrlController.class);

    protected final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // Show home page
    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    // Handle form submission from Thymeleaf page
    @PostMapping("/shorten-page")
    public String shortenFromPage(
            @RequestParam("fullUrl") String fullUrl,
            HttpServletRequest request,
            Model model) {

        UrlValidator validator = new UrlValidator(new String[]{"http", "https"});

        if (!validator.isValid(fullUrl)) {
            model.addAttribute("error", "Invalid URL! Please enter a valid URL starting with http:// or https://");
            return "index";
        }

        try {
            String baseUrl = UrlUtil.getBaseUrl(request.getRequestURL().toString());
            ShortUrl shortUrl = urlService.getShortUrl(new FullUrl(fullUrl));
            shortUrl.setShortUrl(baseUrl + shortUrl.getShortUrl());
            model.addAttribute("shortUrl", shortUrl.getShortUrl());
        } catch (MalformedURLException e) {
            model.addAttribute("error", "Something went wrong. Please try again.");
        }

        return "index";
    }

    // REST API - shorten URL
    @PostMapping("/shorten")
    @ResponseBody
    public ResponseEntity<Object> saveUrl(@RequestBody FullUrl fullUrl, HttpServletRequest request) {

        UrlValidator validator = new UrlValidator(new String[]{"http", "https"});
        String url = fullUrl.getFullUrl();
        if (!validator.isValid(url)) {
            logger.error("Malformed Url provided");
            InvalidUrlError error = new InvalidUrlError("url", fullUrl.getFullUrl(), "Invalid URL");
            return ResponseEntity.badRequest().body(error);
        }

        String baseUrl = null;
        try {
            baseUrl = UrlUtil.getBaseUrl(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            logger.error("Malformed request url");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request url is invalid", e);
        }

        ShortUrl shortUrl = urlService.getShortUrl(fullUrl);
        shortUrl.setShortUrl(baseUrl + shortUrl.getShortUrl());
        logger.debug(String.format("ShortUrl for FullUrl %s is %s", fullUrl.getFullUrl(), shortUrl.getShortUrl()));
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    // Redirect short URL to full URL
    @GetMapping("/{shortenString}")
    public void redirectToFullUrl(HttpServletResponse response, @PathVariable String shortenString) {
        try {
            FullUrl fullUrl = urlService.getFullUrl(shortenString);
            logger.info(String.format("Redirecting to %s", fullUrl.getFullUrl()));
            response.sendRedirect(fullUrl.getFullUrl());
        } catch (NoSuchElementException e) {
            logger.error(String.format("No URL found for %s in the db", shortenString));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found", e);
        } catch (IOException e) {
            logger.error("Could not redirect to the full url");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the full url", e);
        }
    }
}