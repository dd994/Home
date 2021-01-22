package com.softserveinc.ita.homeproject.homedata.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cooperation")
public class Cooperation extends BaseEntity {


    @Column(name = "usreo")
    private String USREO;

    @Column(name = "name")
    private String name;

    @Column(name = "iban")
    private String IBAN;

    @Column(name = "registerDate")
    private LocalDateTime registerDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Addresses addresses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Houses> houses;

}
