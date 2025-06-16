package com.mallan.yujeongran.icebreaking.review.repository;

import com.mallan.yujeongran.icebreaking.review.enitity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
