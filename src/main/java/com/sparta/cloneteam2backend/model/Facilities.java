package com.sparta.cloneteam2backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Facilities {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Id
    private Long facilitiesId;

    @Column
    @JsonIgnore
    private Long postId;

    @Column
    private Boolean facilitiesParking;

    @Column
    private Boolean facilitiesWifi;

    @Column
    private Boolean facilitiesSwimmingpool;

    @Column
    private Boolean facilitiesAirconditioner;

    @Column
    private Boolean facilitiesTv;

    @Builder
    public Facilities(Long postId, boolean facilitiesParking, boolean facilitiesWifi, boolean facilitiesSwimmingpool, boolean facilitiesAirconditioner, boolean facilitiesTv){
        this.postId = postId;
        this.facilitiesParking = facilitiesParking;
        this.facilitiesWifi = facilitiesWifi;
        this.facilitiesSwimmingpool = facilitiesSwimmingpool;
        this.facilitiesAirconditioner = facilitiesAirconditioner;
        this.facilitiesTv = facilitiesTv;
    }
}