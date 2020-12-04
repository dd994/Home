package com.softserveinc.ita.homeproject.homedata.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class News extends BaseEntity {

    @Column(name = "create_date")
    LocalDateTime createDate;
    
    @Column(name = "update_date")
    LocalDateTime updateDate;

    @Column(name = "title")
    String title;

    @Column(name = "text")
    String text;

    @Column(name = "description")
    String description;

    @Column(name = "photo_url")
    String photoUrl;

    @Column(name = "source")
    String source;

}
