package application.data.service;

import application.data.model.OrderDeliveryStatus;
import application.data.repository.OrderDeliveryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDeliveryStatusService {

    @Autowired
    OrderDeliveryStatusRepository orderDeliveryStatusRepository;

    public void add(OrderDeliveryStatus orderDeliveryStatus){
        orderDeliveryStatusRepository.save(orderDeliveryStatus);



    }


    public OrderDeliveryStatus findOne(int id){
        return orderDeliveryStatusRepository.findOne(id);
    }
}
