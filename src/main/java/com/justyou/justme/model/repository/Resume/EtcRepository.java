package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.resume.Etc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtcRepository extends JpaRepository<Etc, Long> {
    void deleteByResumeId(Long resumeId);
}