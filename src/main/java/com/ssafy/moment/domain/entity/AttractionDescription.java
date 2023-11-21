package com.ssafy.moment.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDescription {

    @Id
    @Column(name = "content_id")
    private Integer id;

    private String homepage;
    private String overview;
    private String telname;

}