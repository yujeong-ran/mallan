package com.mallan.yujeongran.icebreaking.review.repository;

import com.mallan.yujeongran.icebreaking.review.enitity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.grade) FROM Review r")
    Float findAverageGrade();

    @Query("SELECT COUNT(r) FROM Review r WHERE YEAR(r.createdAt) = :year AND MONTH(r.createdAt) = :month")
    int countReviewsByMonth(@Param("year") int year, @Param("month") int month);

    List<Review> findTop2ByOrderByCreatedAtDesc();
    List<Review> findTop3ByOrderByCreatedAtDesc();

}
