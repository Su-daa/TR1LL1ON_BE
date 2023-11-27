package com.ybe.tr1ll1on.domain.product.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccommodationDetailResponse {
    private long accommodationId;

    private String name;

    private String address;

    private String areaCode;

    private String phone;

    private String category;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int personNumber;

    private double score;

    private List<AccommodationImageResponse> image;

    private List<ProductResponse> rooms;

    private AccommodationFacilityResponse facility;

    private String latitude;

    private String longitude;

}
