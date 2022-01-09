package com.marscolony.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="units", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "imageUrl"})})
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private String region;
    private String description;
    private String cancellationPolicy;
    @Column(nullable = false, unique = true)
    private String imageUrl;
    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Review> reviews = new HashSet<>();
}
