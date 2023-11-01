package com.justyou.justme.model.repository;


import com.justyou.justme.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Member> findByEmail(String email);
}
