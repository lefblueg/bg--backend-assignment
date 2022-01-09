package com.marscolony.reviews.query.api.repositories;

import com.marscolony.reviews.core.api.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {
}
