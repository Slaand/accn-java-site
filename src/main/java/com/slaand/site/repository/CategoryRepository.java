package com.slaand.site.repository;

import com.slaand.site.model.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    List<CategoryEntity> findTop12ByOrderByIdDesc();

    Optional<CategoryEntity> findById(Long id);

    List<CategoryEntity> findAll();

}
