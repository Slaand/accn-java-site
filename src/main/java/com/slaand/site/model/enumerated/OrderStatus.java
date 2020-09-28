package com.slaand.site.model.enumerated;

import com.slaand.site.model.entity.OrderEntity;
import com.slaand.site.patterns.state.ConfirmedOrder;
import com.slaand.site.patterns.state.DeliveredOrder;
import com.slaand.site.patterns.state.NewOrder;
import com.slaand.site.patterns.state.ProcessingOrder;
import com.slaand.site.patterns.state.ReadyOrder;
import com.slaand.site.patterns.state.Status;

public enum OrderStatus {

    NEW {
        public Status get(OrderEntity entity) {
            return new NewOrder();
        }
    }, CONFIRMED{
        public Status get(OrderEntity entity) {
            return new ConfirmedOrder();
        }
    }, PROCESSING{
        public Status get(OrderEntity entity) {
            return new ProcessingOrder();
        }
    }, READY{
        public Status get(OrderEntity entity) {
            return new ReadyOrder();
        }
    }, DELIVERED {
        public Status get(OrderEntity entity) {
            return new DeliveredOrder();
        }
    };

    public abstract Status get(OrderEntity entity);

}
