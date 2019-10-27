package application.data.model;


import javax.persistence.*;
import java.util.List;

@Entity(name = "dbo_delivery_status")
public class DeliveryStatus {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_status_id")
    @Id
    private int Id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "dbo_order_product",
            joinColumns = @JoinColumn(name = "delivery_status_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orderList;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
