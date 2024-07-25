package com.kh.house.product.model.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="TB_HOUSE")
public class Product {
	
	@Id
	private int houseNo;
	
	private String title;
	private String rateCount;
	private String type;
	private LocalDateTime createDate; 


}
