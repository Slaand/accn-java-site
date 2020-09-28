package com.slaand.site.model.entity;

import com.slaand.site.patterns.state.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_order")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity itemId;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    @Embedded
    private Status status;

    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity)) return false;
        return id != null && id.equals(((OrderEntity) o).getId());
    }
    @Override
    public int hashCode() {
        return 31;
    }

    public void informUser(Status newStatus) {
        this.status = newStatus;
        this.status.onEnterState(this);
    }

    public void observe() {
        this.status.observe();
    }

}
