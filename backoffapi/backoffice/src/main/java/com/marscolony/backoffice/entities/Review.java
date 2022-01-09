package com.marscolony.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table( name = "reviews",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"userId", "unit_id"})
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;
    private String description;
    @Column(nullable = false)
    private int stars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private Unit unit;
}
