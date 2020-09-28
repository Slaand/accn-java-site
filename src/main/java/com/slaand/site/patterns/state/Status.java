package com.slaand.site.patterns.state;

import com.slaand.site.model.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Basic
    public String status;

    public void onEnterState(final OrderEntity orderEntity){
        throw new NotImplementedException();
    }

    public void observe() {
        throw new NotImplementedException();
    }

}
