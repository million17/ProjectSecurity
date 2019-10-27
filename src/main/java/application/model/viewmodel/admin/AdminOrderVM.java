package application.model.viewmodel.admin;

import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.order.DeliveryStatusVM;
import application.model.viewmodel.order.OrderVM;

import java.util.List;

public class AdminOrderVM {
    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<OrderVM> orderVMList;
    private List<DeliveryStatusVM> deliveryStatusVMS;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<OrderVM> getOrderVMList() {
        return orderVMList;
    }

    public void setOrderVMList(List<OrderVM> orderVMList) {
        this.orderVMList = orderVMList;
    }

    public List<DeliveryStatusVM> getDeliveryStatusVMS() {
        return deliveryStatusVMS;
    }

    public void setDeliveryStatusVMS(List<DeliveryStatusVM> deliveryStatusVMS) {
        this.deliveryStatusVMS = deliveryStatusVMS;
    }
}
