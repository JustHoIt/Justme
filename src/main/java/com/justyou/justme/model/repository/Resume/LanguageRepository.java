package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.MySQL.resume.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    void deleteByResumeId(Long resumeId);
}
