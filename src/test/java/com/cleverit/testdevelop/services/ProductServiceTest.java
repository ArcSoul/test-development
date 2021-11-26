package com.cleverit.testdevelop.services;

import java.util.Optional;

import com.cleverit.testdevelop.config.SharedConfig;
import com.cleverit.testdevelop.models.entities.ProductEntity;
import com.cleverit.testdevelop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest extends SharedConfig {

	private ProductRepository productRepository;
	private ProductService productService;


	@BeforeEach
	void setUp() {
		this.productRepository = mock(ProductRepository.class);
		this.productService = new ProductService(this.productRepository);
	}

	@Test
	void getProducts() {
		when(this.productRepository.findAll(any(Pageable.class))).thenReturn(getMockProducts());

		assertEquals(getMockProducts(), this.productService.getProducts(0, 10));
	}

	@Test
	void getProduct() {
		Optional<ProductEntity> optional = Optional.of(getMockProduct1());
		when(this.productRepository.findById(anyString())).thenReturn(optional);

		assertEquals(optional.get(), this.productService.getProduct("FAL-8406270"));
	}

	@Test
	void getProduct1() {
		when(this.productRepository.findById(anyString())).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> this.productService.getProduct("FAL-8406270"));
		assertEquals("404 NOT_FOUND \"The product with sku [FAL-8406270] no exits\"", exception.getMessage());
	}

	@Test
	void createProduct() {
		Optional<ProductEntity> optional = Optional.of(getMockProduct1());
		when(this.productRepository.findById(anyString())).thenReturn(optional);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> this.productService.createProduct(getMockProduct1()));
		assertEquals("409 CONFLICT \"The product with sku [" + getMockProduct1().getSku() +"] already exits\"", exception.getMessage());
	}

	@Test
	void createProduct1() {
		ProductEntity product = getMockProduct1();

		when(this.productRepository.findById(anyString())).thenReturn(Optional.empty());
		when(this.productRepository.save(any(ProductEntity.class))).thenReturn(product);

		assertEquals(product, this.productService.createProduct(product));
	}

	@Test
	void updateProduct() {
		Optional<ProductEntity> optional = Optional.of(getMockProduct1());
		when(this.productRepository.findById(anyString())).thenReturn(optional);
		when(this.productRepository.save(any(ProductEntity.class))).thenReturn(optional.get());

		assertEquals(optional.get(), this.productService.updateProduct("FAL-8406270", getMockProduct1()));
	}

	@Test
	void updateProduct1() {
		when(this.productRepository.findById(anyString())).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> this.productService.updateProduct("FAL-8406270", getMockProduct1()));
		assertEquals("404 NOT_FOUND \"The product with sku [FAL-8406270] no exits\"", exception.getMessage());
	}

	@Test
	void deleteProduct() {
		Optional<ProductEntity> optional = Optional.of(getMockProduct1());
		when(this.productRepository.findById(anyString())).thenReturn(optional);
		when(this.productRepository.save(any(ProductEntity.class))).thenReturn(optional.get());

		this.productService.deleteProduct("FAL-8406270");

		verify(this.productRepository).deleteById(anyString());
	}

	@Test
	void deleteProduct1() {
		when(this.productRepository.findById(anyString())).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> this.productService.deleteProduct("FAL-8406270"));
		assertEquals("404 NOT_FOUND \"The product with sku [FAL-8406270] no exits\"", exception.getMessage());
	}
}
