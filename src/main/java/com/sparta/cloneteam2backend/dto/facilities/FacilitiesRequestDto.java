package com.sparta.cloneteam2backend.dto.facilities;

import com.sparta.cloneteam2backend.model.Facilities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class FacilitiesRequestDto {

    private Boolean facilitiesParking = false;

    private Boolean facilitiesWifi = false;

    private Boolean facilitiesSwimmingpool = false;

    private Boolean facilitiesAirconditioner = false;

    private Boolean facilitiesTv = false;

    public Facilities toFacilities(Long postId) {
        return Facilities.builder()
                .postId(postId)
                .facilitiesParking(facilitiesParking)
                .facilitiesWifi(facilitiesWifi)
                .facilitiesSwimmingpool(facilitiesSwimmingpool)
                .facilitiesAirconditioner(facilitiesAirconditioner)
                .facilitiesTv(facilitiesTv)
                .build();
    }
}
