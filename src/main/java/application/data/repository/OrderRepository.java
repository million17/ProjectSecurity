package application.data.repository;

import application.data.model.Order;
import application.model.viewmodel.admin.ChartLabelDataVM2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Query("select  count (p.id) from dbo_order p")
    long getTotalOrders();

    @Query("SELECT o FROM dbo_order o " +
    "WHERE (:guid IS NULL OR (o.guid = :guid))" +
    "AND (:userName IS NULL OR (o.userName = :userName))")
    List<Order> findOrderByGuidOrUserName(@Param("guid") String guid, @Param("userName") String userName);
    @Query("SELECT o FROM dbo_order o " +
            "WHERE (:guid IS NULL OR (o.guid = :guid))" +
            "AND (:userName IS NULL OR (o.userName = :userName))" +
            "AND o.deliveryStatusId<3 ")
    List<Order> findOrderByGuidOrUserName2(@Param("guid") String guid, @Param("userName") String userName);


    @Query("SELECT o FROM dbo_order o " +
            "WHERE  o.phoneNumber=:phoneNumber " +
            "AND o.deliveryStatusId<3 ")
    List<Order> findOrderByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT p FROM dbo_order p " +
            "WHERE  (:customerName IS NULL OR UPPER(p.customerName) LIKE CONCAT('%',UPPER(:customerName),'%')) " +
            "OR  (:customerName IS NULL OR UPPER(p.phoneNumber) LIKE CONCAT('%',UPPER(:customerName),'%')) " +
            "OR  (:customerName IS NULL OR UPPER(p.id) LIKE CONCAT('%',UPPER(:customerName),'%')) " +
            "ORDER BY p.createdDate desc ")
    Page<Order> getListOrderByCustomerName(Pageable pageable, @Param("customerName") String customerName);

    @Query("SELECT p FROM dbo_order p " +
            "WHERE  ((:customerName IS NULL OR UPPER(p.customerName) LIKE CONCAT('%',UPPER(:customerName),'%')) " +
            "OR  (:customerName IS NULL OR UPPER(p.phoneNumber) LIKE CONCAT('%',UPPER(:customerName),'%')) " +
            "OR  (:customerName IS NULL OR UPPER(p.id) LIKE CONCAT('%',UPPER(:customerName),'%'))) " +
            "AND p.deliveryStatusId=:deliveryStatusId " +
            "ORDER BY p.createdDate desc ")
    Page<Order> getListOrderByCustomerName2(Pageable pageable, @Param("customerName") String customerName,@Param("deliveryStatusId") Integer deliveryStatusId);

    @Query(value = "CALL TotalPriceOfWeek()", nativeQuery = true)
    Double totalPriceOfWeek();

    @Query(value = "CALL TotalOrderOfWeek()", nativeQuery = true)
    Integer totalOrderOfWeek();

    @Query("select distinct new application.model.viewmodel.admin.ChartLabelDataVM2(o.createdDateShow,sum(o.price)) " +
    "from dbo_order o " +
    "where o.createdDate BETWEEN (SUBDATE(now(), weekday(now()))) AND (SUBDATE(now(),weekday(now()) -6)) " +
    "group by o.createdDateShow " +
    "order by o.createdDateShow asc")
    List<ChartLabelDataVM2> profitInWeek();



}
