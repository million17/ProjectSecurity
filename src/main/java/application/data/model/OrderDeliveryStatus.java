package application.data.model;


import javax.persistence.*;
import java.util.Date;

@Entity(name = "dbo_order_delivery_status")
public class OrderDeliveryStatus {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_delivery_status_id")
    @Id
    private int Id;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_status_id")
    private DeliveryStatus deliveryStatus;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
