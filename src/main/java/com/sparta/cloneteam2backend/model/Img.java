package com.sparta.cloneteam2backend.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Img {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgId;
	@Column
	private Imgtarget imgTarget;
	@Column
	private Long targetId;
	@Column
	private String imgUrl;

	@Builder
	public Img(Imgtarget imgTarget, Long targetId, String imgUrl) {
		this.imgTarget = imgTarget;
		this.targetId = targetId;
		this.imgUrl = imgUrl;
	}
}