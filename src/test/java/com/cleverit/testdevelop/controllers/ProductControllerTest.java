package com.cleverit.testdevelop.controllers;

import java.math.BigDecimal;

import com.cleverit.testdevelop.config.SharedConfig;
import com.cleverit.testdevelop.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends SharedConfig {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ProductService productService;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		this.objectMapper = new ObjectMapper();
	}

	@Test
	void getProduct() throws Exception {
		when(productService.getProduct("FAL-8406270")).thenReturn(getMockProduct1());

		mvc.perform(get("/v1/product/FAL-8406270").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.sku").value("FAL-8406270"))
				.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(42990.00)));

		verify(productService).getProduct(anyString());
	}

	@Test
	void getProductBySkuNotFound() throws Exception {
		when(productService.getProduct(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The product with sku [FAL-8406271] no exits")));

		mvc.perform(get("/v1/product/FAL-8406271").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(productService).getProduct(anyString());
	}

	@Test
	void createProducts() throws Exception {
		when(productService.createProduct(any())).thenReturn(getMockProduct1());
		mvc.perform(post("/v1/product").contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(getMockProduct1())))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.sku").value(getMockProduct1().getSku()))
				.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(42990.00)));
	}

	@Test
	void deleteProduct() throws Exception{
		mvc.perform(delete("/v1/product/{productSku}", "FAL-8406270").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
