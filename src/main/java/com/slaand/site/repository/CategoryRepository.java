package com.slaand.site.repository;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    List<CategoryEntity> findTop12ByOrderByIdDesc();

    List<CategoryEntity> findAll();

}
