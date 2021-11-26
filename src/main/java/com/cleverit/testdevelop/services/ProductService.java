package com.cleverit.testdevelop.services;

import java.util.Optional;

import com.cleverit.testdevelop.models.entities.ProductEntity;
import com.cleverit.testdevelop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public Page<ProductEntity> getProducts(int page, int size) {
		return productRepository.findAll(PageRequest.of(page, size));
	}

	public ProductEntity getProduct(String sku) {
		Optional<ProductEntity> result = productRepository.findById(sku);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The product with sku [%s] no exits", sku));
		}
	}

	public ProductEntity createProduct(ProductEntity product) {
		Optional<ProductEntity> result = productRepository.findById(product.getSku());

		if (result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("The product with sku [%s] already exits", product.getSku()));
		} else {
			return productRepository.save(product);
		}
	}

	public ProductEntity updateProduct(String sku, ProductEntity product) {
		Optional<ProductEntity> result = productRepository.findById(sku);

		if (result.isPresent()) {
			return productRepository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The product with sku [%s] no exits", sku));
		}
	}

	public void deleteProduct(String sku) {
		Optional<ProductEntity> result = productRepository.findById(sku);

		if (result.isPresent()) {
			productRepository.deleteById(sku);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The product with sku [%s] no exits", sku));
		}
	}

}
