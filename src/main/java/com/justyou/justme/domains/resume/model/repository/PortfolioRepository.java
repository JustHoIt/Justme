package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    void deleteByResumeId(Long resumeId);
}
