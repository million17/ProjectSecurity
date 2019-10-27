package application.data.service;


import application.data.model.Order;
import application.data.model.Product;
import application.data.repository.OrderRepository;
import application.model.viewmodel.admin.ChartLabelDataVM2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;


    public void update(Order order){
        orderRepository.save(order);
    }
    public void addNewOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOne(int orderId) {
        return orderRepository.findOne(orderId);
    }

    public List<Order> findOrderByGuidOrUserName(String guid, String userName) {
        return orderRepository.findOrderByGuidOrUserName(guid,userName);
    }

    public  Page<Order> getListOrderByCustomerName(Pageable pageable, String customerName){
        return  orderRepository.getListOrderByCustomerName(pageable,customerName);
    }
    public  Page<Order> getListOrderByCustomerName2(Pageable pageable, String customerName, Integer deliveryStatusId){
        return  orderRepository.getListOrderByCustomerName2(pageable,customerName,deliveryStatusId);
    }

    public List<Order> findOrderByGuidOrUserName2(String guid, String userName){
        return orderRepository.findOrderByGuidOrUserName2(guid,userName);
    }

    public List<Order> findOrderByPhoneNumber( String phoneNumber){
        return orderRepository.findOrderByPhoneNumber(phoneNumber);
    }

    public Double totalPriceOfWeek(){
        return orderRepository.totalPriceOfWeek();
    }

    public Integer totalOrderOfWeek(){
        return orderRepository.totalOrderOfWeek();
    }

    public List<ChartLabelDataVM2> profitInWeek(){
        return orderRepository.profitInWeek();
    }





}
