package com.slaand.site.repository;

import com.slaand.site.model.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {

    List<ItemEntity> findTop6ByOrderByIdDesc();

    List<ItemEntity> findAllByCategoryId(final Long id);

}
