package com.slaand.site.patterns.state;

import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.model.enumerated.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Embeddable;

@Slf4j
@Embeddable
public class NewOrder extends Status {

    public NewOrder() {
        status = OrderStatus.NEW.name();
    }

    @Override
    public void onEnterState(final OrderEntity order) {

        log.info("===== ORDER =================" +
                "\nTitle: Order status change" +
                "\nHello, " + order.getUserId().getName() +
                "\nYour item's " + order.getId() + " status was changed to " + OrderStatus.NEW +
                "\nWith best wishes, " +
                "Your mega ultra shop"
        );
    }

    @Override
    public void observe() {
        log.info("Current status: " + OrderStatus.NEW);
    }
}
