package com.avi6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinationDTO {
    private Long id;
    private String place_id;
    private String name;
    private String travelModeEnum;
    private String origin_lat;
    private String origin_lng;
    private String email;
    private String local;
    private int num;
}
