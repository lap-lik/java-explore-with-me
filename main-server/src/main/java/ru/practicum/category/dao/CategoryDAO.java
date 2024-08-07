package ru.practicum.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.model.Category;

public interface CategoryDAO extends JpaRepository<Category, Long> {
}
