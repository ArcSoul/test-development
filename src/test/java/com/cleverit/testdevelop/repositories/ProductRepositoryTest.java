package com.cleverit.testdevelop.repositories;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.cleverit.testdevelop.models.entities.ProductEntity;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Sql(
		statements = "INSERT INTO FB_PRODUCTS(PDT_SKU, PDT_NAME, PDT_BRAND, PDT_SIZE, PDT_PRICE, PDT_PRINCIPAL_IMAGE, PDT_OTHER_IMAGES) VALUES ( 'FAL-8406270', '500 Zapatilla Urbana Mujer', 'NEW BALANCE', null, 42990.00, 'https://falabella.scene7.com/is/image/Falabella/8406270_1', null), ('FAL-88195228', 'Bicicleta Baltoro Aro 29', 'JEEP', 'ST', 399990.00, 'https://falabella.scene7.com/is/image/Falabella/881952283_1', 'https://falabella.scene7.com/is/image/Falabella/881952283_2')",
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void testFindById() {
		Optional<ProductEntity> optionalProduct = this.productRepository.findById("FAL-8406270");

		assertTrue(optionalProduct.isPresent());
		assertEquals("500 Zapatilla Urbana Mujer", optionalProduct.get().getName());
	}

	@Test
	void testFindByIdThrowException() {
		Optional<ProductEntity> optionalProduct = this.productRepository.findById("FAL-8406271");

		assertThrows(NoSuchElementException.class, optionalProduct::get);
		assertFalse(optionalProduct.isPresent());
	}
}
