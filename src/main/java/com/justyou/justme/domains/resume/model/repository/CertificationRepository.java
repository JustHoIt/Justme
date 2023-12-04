package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    void deleteByResumeId(Long resumeId);
}
