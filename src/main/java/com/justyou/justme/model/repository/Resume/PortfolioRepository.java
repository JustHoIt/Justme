package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.resume.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    void deleteByResumeId(Long resumeId);
}
