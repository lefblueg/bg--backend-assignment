package com.marscolony.backoffice.repositories;

import com.marscolony.backoffice.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    List<Review> findByUnitId(Long unitId);
}
