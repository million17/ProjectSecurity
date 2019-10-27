package application.data.service;


import application.data.model.DeliveryStatus;
import application.data.repository.DeliveryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryStatusService {

    @Autowired
    DeliveryStatusRepository deliveryStatusRepository;

    public DeliveryStatus findOne(int deliveryStatusId){
        return deliveryStatusRepository.findOne(deliveryStatusId);
    }

    public List<DeliveryStatus> getAll(){
        return deliveryStatusRepository.findAll();
    }
}
