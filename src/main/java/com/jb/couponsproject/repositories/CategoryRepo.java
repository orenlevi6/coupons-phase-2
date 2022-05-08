package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
