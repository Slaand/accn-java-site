package com.slaand.site.repository;

import com.slaand.site.model.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    List<OrderEntity> findTop10ByOrderByIdDesc();

    List<OrderEntity> findAllByUserId(Long email);

}