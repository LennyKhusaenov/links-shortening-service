package com.yourcodereview.task2.linksshorteningservice.service;

import com.yourcodereview.task2.linksshorteningservice.api.model.OriginalLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.ShortLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.Statistics;
import com.yourcodereview.task2.linksshorteningservice.component.LinkDataMapper;
import com.yourcodereview.task2.linksshorteningservice.component.ShortLinkGenerator;
import com.yourcodereview.task2.linksshorteningservice.repository.EntityAlreadyExistsException;
import com.yourcodereview.task2.linksshorteningservice.repository.LinksRepository;
import com.yourcodereview.task2.linksshorteningservice.repository.model.LinkEntity;
import com.yourcodereview.task2.linksshorteningservice.repository.model.LinkEntity_;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class LinksServiceImpl implements LinksService {

    private final LinksRepository linksRepository;
    private final ShortLinkGenerator shortLinkGenerator;
    private final LinkDataMapper linkDataMapper;

    @Override
    @Transactional
    public ShortLink generateShortLink(OriginalLink originalLink) {
        var original = originalLink.getOriginal();
        checkIsNewOriginalLink(original);
        Long nextIdForShortUrl = linksRepository.findNextIdForShortUrl();
        var shortLink = shortLinkGenerator.createUniqueId(nextIdForShortUrl);
        var linkEntity = linkDataMapper.mapToLinkEntity(shortLink, original);
        LinkEntity savedEntity = linksRepository.saveAndFlush(linkEntity);
        return linkDataMapper.mapToShortLink(savedEntity);
    }

    @Override
    @Transactional
    public String findOriginalUrlAndIncrementVisits(String shortLink) {
        var linkEntity = linksRepository.findByShortLink(shortLink)
                .orElseThrow(EntityNotFoundException::new);
        Long visitsCount = linkEntity.getVisitsCount();
        visitsCount++;
        linkEntity.setVisitsCount(visitsCount);
        linksRepository.save(linkEntity);
        return linkEntity.getOriginalLink();
    }

    @Override
    public List<Statistics> findAllLinksStats(Integer page, Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(desc(LinkEntity_.VISITS_COUNT)));
        return linksRepository.findAll(pageRequest)
                .stream()
                .map(linkDataMapper::mapToStatistic)
                .collect(toList());
    }

    @Override
    public Statistics findStatsForLink(String shortLink) {
        LinkEntity linkEntity = linksRepository.findByShortLink(shortLink).orElseThrow(EntityNotFoundException::new);
        return linkDataMapper.mapToStatistic(linkEntity);
    }

    private void checkIsNewOriginalLink(String original) {
        if (linksRepository.existsByOriginalLink(original)) {
            throw new EntityAlreadyExistsException("Such shortLink already exists");
        }
    }
}
