package com.slaand.site.repository;

import com.slaand.site.model.entity.ImageItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<ImageItemEntity, Long> {



}
