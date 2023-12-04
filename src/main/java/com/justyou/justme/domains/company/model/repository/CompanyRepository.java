package com.justyou.justme.domains.company.model.repository;


import com.justyou.justme.domains.company.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
