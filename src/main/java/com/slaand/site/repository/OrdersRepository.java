package com.slaand.site.repository;

import com.slaand.site.model.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface OrdersRepository extends CrudRepository<OrderEntity, Long> {



}