package com.example.board.boardController.repository;

import com.example.board.boardController.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

     Category findByName(String categoryName);

}