package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Page<Resume> findByMemberIdOrderByModifiedAtDesc(Long id, Pageable pageable);
}
