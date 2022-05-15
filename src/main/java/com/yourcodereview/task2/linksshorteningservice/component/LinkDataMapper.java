package com.yourcodereview.task2.linksshorteningservice.component;

import com.yourcodereview.task2.linksshorteningservice.api.model.ShortLink;
import com.yourcodereview.task2.linksshorteningservice.api.model.Statistics;
import com.yourcodereview.task2.linksshorteningservice.repository.model.LinkEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface LinkDataMapper {
    String PREFIX_PATH_FOR_SHORT_LINK = "/l/";

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shortLink", source = "shortLink")
    @Mapping(target = "originalLink", source = "originalLink")
    @Mapping(target = "visitsCount", ignore = true)
    @Mapping(target = "rank", ignore = true)
    @Mapping(target = "creationDateTime", expression = "java(java.time.Instant.now())")
    LinkEntity mapToLinkEntity(String shortLink, String originalLink);

    @Mapping(target = "link", expression = "java(PREFIX_PATH_FOR_SHORT_LINK + savedEntity.getShortLink())")
    ShortLink mapToShortLink(LinkEntity savedEntity);

    List<Statistics> mapToStatistics(List<LinkEntity> linkEntities);

    @Mapping(target = "link", expression = "java(PREFIX_PATH_FOR_SHORT_LINK + linkEntity.getShortLink())")
    @Mapping(target = "original", source = "originalLink")
    @Mapping(target = "rank", ignore = true)
    @Mapping(target = "count", source = "visitsCount")
    Statistics mapToStatistic(LinkEntity linkEntity);

}
