package com.justyou.justme.domains.member.model.repository;


import com.justyou.justme.domains.member.model.entity.RedisUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRepository extends CrudRepository<RedisUser, Long> {
}
