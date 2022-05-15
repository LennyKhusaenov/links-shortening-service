package com.yourcodereview.task2.linksshorteningservice.repository;

import com.yourcodereview.task2.linksshorteningservice.repository.model.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinksRepository extends JpaRepository<LinkEntity, UUID> {
    boolean existsByOriginalLink(String originalLink);

    Optional<LinkEntity> findByShortLink(String shortLink);

    @Query(value = "SELECT nextval('short_url_id_sequence')", nativeQuery = true)
    public Long findNextIdForShortUrl();

}
