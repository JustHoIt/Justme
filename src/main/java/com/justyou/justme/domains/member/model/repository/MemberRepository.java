package com.justyou.justme.domains.member.model.repository;


import com.justyou.justme.domains.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndName(String email, String name);

    Optional<Member> findByPasswordChangeKey(String uuid);
}
