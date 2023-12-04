package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, Long> {
    void deleteByResumeId(Long resumeId);
}
