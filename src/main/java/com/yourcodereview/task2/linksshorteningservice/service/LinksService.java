package com.yourcodereview.task2.linksshorteningservice.service;

import com.yourcodereview.task2.linksshorteningservice.api.model.OriginalLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.ShortLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.Statistics;

import java.util.List;

public interface LinksService {
    ShortLink generateShortLink(OriginalLink originalLink);

    String findOriginalUrlAndIncrementVisits(String link);

    List<Statistics> findAllLinksStats(Integer page, Integer count);

    Statistics findStatsForLink(String shortLink);
}
