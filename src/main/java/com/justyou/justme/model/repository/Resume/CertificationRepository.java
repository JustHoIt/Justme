package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.resume.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    void deleteByResumeId(Long resumeId);
}
