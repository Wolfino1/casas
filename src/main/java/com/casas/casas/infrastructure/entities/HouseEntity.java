package com.casas.casas.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "house")
@NoArgsConstructor
@AllArgsConstructor
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String description;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private int price;
    private LocalDateTime publishActivationDate;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime publishDate;
    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne(targetEntity = LocationEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private LocationEntity location;
    @ManyToOne(targetEntity = PubStatusEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pub_status_id")
    private PubStatusEntity pubStatus;
}
