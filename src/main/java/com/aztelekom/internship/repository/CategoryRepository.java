package com.aztelekom.internship.repository;

import com.aztelekom.internship.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
