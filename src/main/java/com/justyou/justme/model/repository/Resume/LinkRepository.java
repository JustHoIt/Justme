package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.resume.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    void deleteByResumeId(Long resumeId);
}
