package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Img;
import com.sparta.cloneteam2backend.model.Imgtarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
	@Query("SELECT i FROM Img i WHERE i.imgTarget = :target AND i.targetId = :targetId")
	List<Img> findAllByTargetId(@Param("target") Imgtarget target, @Param("targetId") Long targetId);

	@Modifying
	@Query("DELETE FROM Img i WHERE i.imgTarget = :target AND i.targetId = :targetId")
	void deleteAllByTargetId(@Param("target") Imgtarget target, @Param("targetId") Long targetId);

	void deleteByImgUrl(String imgUrl);
}