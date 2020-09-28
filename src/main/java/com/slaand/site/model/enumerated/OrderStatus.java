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
        public OrderStatus next() {
            return CONFIRMED;
        }
        public Status get(OrderEntity entity) {
            return new NewOrder(entity);
        }
    }, CONFIRMED{
        public OrderStatus next() {
            return PROCESSING;
        }
        public Status get(OrderEntity entity) {
            return new ConfirmedOrder(entity);
        }
    }, PROCESSING{
        public OrderStatus next() {
            return READY;
        }
        public Status get(OrderEntity entity) {
            return new ProcessingOrder(entity);
        }
    }, READY{
        public OrderStatus next() {
            return DELIVERED;
        }
        public Status get(OrderEntity entity) {
            return new ReadyOrder(entity);
        }
    }, DELIVERED {
        public OrderStatus next() {
            return null;
        }
        public Status get(OrderEntity entity) {
            return new DeliveredOrder(entity);
        }
    };

    public abstract Status get(OrderEntity entity);
    public abstract OrderStatus next();

}
