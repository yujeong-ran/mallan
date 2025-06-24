package com.mallan.yujeongran.icebreaking.review.repository;

import com.mallan.yujeongran.icebreaking.review.enitity.Review;
import com.mallan.yujeongran.icebreaking.review.enums.GameType;
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

    @Query("""
        SELECT r FROM Review r
        WHERE (:gameTypes IS NULL OR r.gameType In :gameTypes)
        AND (:minGrade IS NULL OR r.grade >= :minGrade)
        AND (:maxGrade IS NULL OR r.grade <= :maxGrade)
        AND (:keyword IS NULL OR r.content LIKE CONCAT('%', :keyword, '%'))
    """)
    List<Review> filterReviews(
      @Param("gameTypes") List<GameType> gameTypes,
      @Param("minGrade") Integer minGrade,
      @Param("maxGrade") Integer maxGrade,
      @Param("keyword") String keyword
    );

}
