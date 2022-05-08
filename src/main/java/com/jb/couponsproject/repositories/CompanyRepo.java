package com.jb.couponsproject.repositories;

import com.jb.couponsproject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

}
