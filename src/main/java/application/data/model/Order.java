package application.data.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "dbo_order")
public class Order {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    @Id
    private int id;

    @Column(name="guid")
    private String guid;

    @Column(name = "username")
    private String userName;

    @Column(name ="customer_name")
    private String customerName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "price")
    private double price;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_date_show")
    private Date createdDateShow;

    @Column(name = "delivery_status_id")
    private int deliveryStatusId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderProduct> listProductOrders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderDeliveryStatus> orderDeliveryStatusList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderProduct> getListProductOrders() {
        return listProductOrders;
    }

    public void setListProductOrders(List<OrderProduct> listProductOrders) {
        this.listProductOrders = listProductOrders;
    }

    public List<OrderDeliveryStatus> getOrderDeliveryStatusList() {
        return orderDeliveryStatusList;
    }

    public void setOrderDeliveryStatusList(List<OrderDeliveryStatus> orderDeliveryStatusList) {
        this.orderDeliveryStatusList = orderDeliveryStatusList;
    }

    public Date getCreatedDateShow() {
        return createdDateShow;
    }

    public void setCreatedDateShow(Date createdDateShow) {
        this.createdDateShow = createdDateShow;
    }

    public int getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public void setDeliveryStatusId(int deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
    }

    @Override
    public String toString() {
        return "Đơn đặt hàng không đủ số lượng trong kho{" +
                "Mã đơn hàng: " + id +
                ", Tên khách hàng:'" + customerName + '\'' +
                ", Địa chỉ: " + address + '\'' +
                ", Số điện thoại: " + phoneNumber + '\'' +
                ", Email: " + email + '\'' +
                ", Ngày đặt hàng: " + createdDate +
                '}';
    }
}
