package com.yourcodereview.task2.linksshorteningservice.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private Long visitsCount = 0L;
    //todo add logic for calculating rank
    private Long rank;
    @Column(unique = true)
    private String shortLink;
    @Column(unique = true)
    private String originalLink;
    private Instant creationDateTime;

}
