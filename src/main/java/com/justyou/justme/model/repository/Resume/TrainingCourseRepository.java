package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.resume.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, Long> {
    void deleteByResumeId(Long resumeId);
}
