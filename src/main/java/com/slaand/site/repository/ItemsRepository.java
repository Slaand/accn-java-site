package com.slaand.site.repository;

import com.slaand.site.model.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<ItemEntity, Long> {



}
