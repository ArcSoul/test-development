package com.cleverit.testdevelop.models.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cleverit.testdevelop.utils.NullOrNotBlank;
import com.cleverit.testdevelop.utils.SkuConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FB_PRODUCTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

	@Id
	@Column(name = "PDT_SKU", columnDefinition = "VARCHAR(12)")
	@SkuConstraint
	private String sku;

	@Column(name = "PDT_NAME", columnDefinition = "VARCHAR(50)", nullable = false)
	@NotBlank(message = "The short description of the product can't be blank")
	@Size(min = 3, max = 50, message = "The short description of the product must be 3 to 50 characters")
	private String name;

	@Column(name = "PDT_BRAND", columnDefinition = "VARCHAR(50)", nullable = false)
	@NotBlank(message = "The name of the brand can't be blank")
	@Size(min = 3, max = 50, message = "The name of the brand must be 3 to 50 characters")
	private String brand;

	@Column(name = "PDT_SIZE", columnDefinition = "VARCHAR(5)")
	@NullOrNotBlank(message = "The size of the product can't be blank but can be null")
	private String size;

	@Column(name = "PDT_PRICE", columnDefinition = "DECIMAL(8,2)", nullable = false)
	@NotNull(message = "The sell price can't be null")
	@DecimalMin(value = "1.00", message = "The sell price should be greater than 1.00")
	@DecimalMax(value = "99999999.00", message = "The sell price should be less than 99999999.00")
	private BigDecimal price;

	@Column(name = "PDT_PRINCIPAL_IMAGE", columnDefinition = "VARCHAR(2048)", nullable = false)
	@Pattern(
			regexp = "^(http|https)://[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,3}(/\\S*)?$",
			message = "The principal image of the product must be the correct url format"
	)
	private String principalImage;

	@Column(name = "PDT_OTHER_IMAGES", columnDefinition = "VARCHAR(2048)")
	@Pattern(
			regexp = "^(http|https)://[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,3}(/\\S*)?$",
			message = "The list of images of the product must be the correct url format"
	)
	private String otherImages;

}
