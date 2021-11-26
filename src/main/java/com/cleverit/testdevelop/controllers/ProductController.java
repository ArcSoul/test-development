package com.cleverit.testdevelop.controllers;

import javax.validation.Valid;

import com.cleverit.testdevelop.models.entities.ProductEntity;
import com.cleverit.testdevelop.services.ProductService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping
	@Timed(value = "product.getAll", description = "Metrics of get in a range of products")
	public ResponseEntity<Page<ProductEntity>> getProducts(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "1000") int size
	) {
		return new ResponseEntity<>(productService.getProducts(page, size), HttpStatus.OK);
	}

	@GetMapping("/{productSku}")
	@Timed(value = "product.getOne", description = "Metrics of get one product")
	public ResponseEntity<ProductEntity> getProduct(@PathVariable("productSku") String sku) {
		return new ResponseEntity<>(productService.getProduct(sku), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ProductEntity> createProducts(@Valid @RequestBody ProductEntity product) {
		return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
	}

	@PutMapping("/{productSku}")
	public ResponseEntity<ProductEntity> updateProduct(@PathVariable("productSku") String sku, @Valid @RequestBody ProductEntity product) {
		return new ResponseEntity<>(productService.updateProduct(sku, product), HttpStatus.OK);
	}

	@DeleteMapping("/{productSku}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productSku") String sku) {
		productService.deleteProduct(sku);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
