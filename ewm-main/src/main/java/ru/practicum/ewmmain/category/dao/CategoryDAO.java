package ru.practicum.ewmmain.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmmain.category.model.Category;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * " +
            "FROM categories " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Category> findll(@Param("offset") int offset, @Param("limit") int limit);
}