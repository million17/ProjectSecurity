package application.data.repository;

import application.data.model.DeliveryStatus;
import application.model.viewmodel.order.DeliveryStatusVM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Integer> {

    @Query(value = "SELECT new application.model.viewmodel.order.DeliveryStatusVM(ds.o) " +
    "FROM dbo_order_delivery_status ods " +
    "JOIN dbo_delivery_status ds on ods.delivery_status_id = ds.delivery_status_id " +
    "WHERE ods.order_id=:orderId", nativeQuery =  true)
    List<DeliveryStatusVM> getByOrderId(@Param("orderId") Integer orderId);
}
