package com.justyou.justme.domains.resume.model.repository;

;
import com.justyou.justme.domains.resume.model.entity.Etc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtcRepository extends JpaRepository<Etc, Long> {
    void deleteByResumeId(Long resumeId);
}
