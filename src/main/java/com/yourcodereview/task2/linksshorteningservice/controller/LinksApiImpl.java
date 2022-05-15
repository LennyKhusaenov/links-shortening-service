package com.yourcodereview.task2.linksshorteningservice.controller;

import com.yourcodereview.task2.linksshorteningservice.api.handler.LinksApi;
import com.yourcodereview.task2.linksshorteningservice.api.model.OriginalLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.ShortLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.Statistics;
import com.yourcodereview.task2.linksshorteningservice.service.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class LinksApiImpl implements LinksApi {

    private final LinksService linksService;

    @Override
    public ResponseEntity<List<Statistics>> findAllStats(Integer page, Integer count) {
        return ResponseEntity.ok().body(linksService.findAllLinksStats(page, count));
    }

    @Override
    public ResponseEntity<Statistics> findStatsForLink(String link) {
        return ResponseEntity.ok().body(linksService.findStatsForLink(link));
    }

    @Override
    public ResponseEntity<ShortLink> generateShortLink(OriginalLink originalLink) {
        return ResponseEntity.ok().body(linksService.generateShortLink(originalLink));
    }

    @Override
    public ResponseEntity<Void> redirectToLink(String shortLink) {
        String originalUrl = linksService.findOriginalUrlAndIncrementVisits(shortLink);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
