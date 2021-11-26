package com.cleverit.testdevelop.repositories;

import com.cleverit.testdevelop.models.entities.ProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

}
