package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    Optional<Company> findByIdAndName(int id, String name);
    boolean existsByIdAndName(int id, String name);

    Optional<Company> findByEmailAndPassword(String email, String password);

}
