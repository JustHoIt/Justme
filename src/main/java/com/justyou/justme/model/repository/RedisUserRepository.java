package com.justyou.justme.model.repository;


import com.justyou.justme.model.entity.Redis.RedisUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRepository extends CrudRepository<RedisUser, Long> {
}
