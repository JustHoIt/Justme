package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    void deleteByResumeId(Long resumeId);
}
