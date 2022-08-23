package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Facilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

    @Query("SELECT f FROM Facilities f WHERE f.postId = :postId")
    List<Facilities> findAllByPostId(Long postId);
    void deleteByPostId(Long postId);
}
