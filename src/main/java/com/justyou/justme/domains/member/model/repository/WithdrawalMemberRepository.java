package com.justyou.justme.domains.member.model.repository;


import com.justyou.justme.domains.member.model.entity.WithdrawalMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalMemberRepository extends JpaRepository<WithdrawalMember, Long> {
}
