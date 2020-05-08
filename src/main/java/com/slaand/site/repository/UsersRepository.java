package com.slaand.site.repository;

import com.slaand.site.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {



}
