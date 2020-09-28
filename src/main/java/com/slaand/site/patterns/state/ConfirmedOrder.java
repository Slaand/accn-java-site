package com.slaand.site.patterns.state;

import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Slf4j
@Embeddable
public class ConfirmedOrder extends Status {

//    @Basic
//    private String status;

    private final OrderEntity orderEntity;

    public ConfirmedOrder(OrderEntity orderEntity) {
        status = OrderStatus.CONFIRMED.name();
        this.orderEntity = orderEntity;
    }

    @Override
    public void onEnterState(final OrderEntity order) {

        log.info("===== ORDER =================" +
                "\nTitle: Order status change" +
                "\nHello, " + order.getUserId().getName() +
                "\nYour item's " + order.getId() + " status was changed to" + OrderStatus.CONFIRMED +
                "\nWith best wishes, " +
                "Your mega ultra shop"
        );
    }

    @Override
    public void observe() {
        log.info("Current status: " + OrderStatus.CONFIRMED);
    }
}
