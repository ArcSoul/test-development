package com.cleverit.testdevelop.config;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.cleverit.testdevelop.models.entities.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class SharedConfig {

	public ProductEntity getMockProduct1() {
		return ProductEntity.builder()
				.sku("FAL-8406270")
				.name("500 Zapatilla Urbana Mujer")
				.brand("NEW BALANCE")
				.price(BigDecimal.valueOf(42990.00))
				.principalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1")
				.build();
	}

	public ProductEntity getMockProduct2() {
		return ProductEntity.builder()
				.sku("FAL-88195228")
				.name("Bicicleta Baltoro Aro 29")
				.brand("JEEP")
				.size("ST")
				.price(BigDecimal.valueOf(399990.00))
				.principalImage("https://falabella.scene7.com/is/image/Falabella/881952283_1")
				.otherImages("https://falabella.scene7.com/is/image/Falabella/881952283_2")
				.build();
	}

	public Page<ProductEntity> getMockProducts() {
		return new PageImpl<ProductEntity>(Collections.emptyList());
	}

}
