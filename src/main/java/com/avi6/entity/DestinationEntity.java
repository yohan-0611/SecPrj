package com.avi6.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "destinations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String place_id;
    private String name;
    private String travelModeEnum;
    private String origin_lat;
    private String origin_lng;
    private String email;  // Added email field
    private String local;
    private int num;
}
