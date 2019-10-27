package application.controller.web;


import application.data.model.*;
import application.data.service.*;
import application.model.api.DataApiResult;
import application.model.dto.OrderDTO;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.order.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/order")
public class OrderController extends BaseController{

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    private OrderDeliveryStatusService orderDeliveryStatusService;

    @GetMapping("/checkout")
    public String checkout(Model model,
                           @Valid @ModelAttribute("productname")ProductVM productName,
                           final Principal principal){
        CheckoutVM vm = new CheckoutVM();
        OrderVM order = new OrderVM();

        if(principal !=null){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User userEntity = userService.findUserByUsername(username);
            if(userEntity !=null){
                order.setAddress(userEntity.getAddress());
                order.setCustomerName(userEntity.getName());
                order.setPhoneNumber(userEntity.getPhoneNumber());
                order.setEmail(userEntity.getEmail());
            }
        }
        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());
        model.addAttribute("order",order);
        model.addAttribute("vm",vm);

        return "/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("order") OrderVM orderVM,
                           @Valid @ModelAttribute("productname") ProductVM productName,
                           HttpServletResponse response,
                           HttpServletRequest request,
                           final Principal principal){
        Order order = new Order();

        boolean flag = false;

        Cookie cookie[] = request.getCookies();

        String guid = null;

        if(cookie!=null){
            for (Cookie c : cookie){
                if(c.getName().equals("guid")){
                    flag = true;
                    guid = c.getValue();
                }
            }
        }

        if (flag == true){

            double totalPrice = 0;

            if(principal!=null){
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                order.setUserName(username);
            }

            order.setGuid(guid);
            order.setAddress(orderVM.getAddress());
            order.setEmail(orderVM.getEmail());
            order.setPhoneNumber(orderVM.getPhoneNumber());
            order.setCustomerName(orderVM.getCustomerName());
            order.setCreatedDate(new Date());

            Cart cartEntity = cartService.findFirstCartByGuid(guid);
            if (cartEntity !=null){
                List<OrderProduct> orderProducts = new ArrayList<>();
                for (CartProduct cartProduct : cartEntity.getListCartProducts()){
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(cartProduct.getProduct());
                    orderProduct.setAmount(cartProduct.getAmount());

                    double price = cartProduct.getAmount() * cartProduct.getProduct().getPrice();
                    totalPrice+=price;

                    orderProduct.setPrice(price);

                    orderProducts.add(orderProduct);
                }

                order.setListProductOrders(orderProducts);
                order.setPrice(totalPrice);

                orderService.addNewOrder(order);

                cartService.deletaCart(cartEntity.getId());

            }
        }
        return "redirect:/order/history";
    }


    @GetMapping("/history")
    public String orderHistory(Model model,
                               @Valid @ModelAttribute("productname") ProductVM productName,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               final Principal principal){
        OrderHistoryVM vm = new OrderHistoryVM();

        DecimalFormat df = new DecimalFormat("####0.00");

        List<OrderVM> orderVMS= new ArrayList<>();

        Cookie[] cookie = request.getCookies();

        String guid = null;
        boolean flag = false;

        List<Order> orderEntityList = null;

        if(principal !=null){
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            orderEntityList = orderService.findOrderByGuidOrUserName(null,username);

        }else{
            if (cookie !=null){
                for (Cookie c : cookie){
                    if (c.getName().equals("guid")){
                        flag = true;
                        guid= c.getValue();
                    }
                }
                if (flag == true){
                    orderEntityList = orderService.findOrderByGuidOrUserName(guid,null);
                }
            }
        }
        if (orderEntityList !=null){
            for (Order order : orderEntityList){
                OrderVM orderVM = new OrderVM();
                orderVM.setId(order.getId());
                orderVM.setCustomerName(order.getCustomerName());
                orderVM.setEmail(order.getEmail());
                orderVM.setPhoneNumber(order.getPhoneNumber());
                orderVM.setAddress(order.getAddress());
                orderVM.setCreatedDate(order.getCreatedDate());
                orderVM.setPrice(order.getPrice());

                orderVMS.add(orderVM);
            }
        }

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());
        vm.setOrderVMS(orderVMS);

        model.addAttribute("vm",vm);

        return "/order-history";
    }


    @GetMapping("/history/{orderId}")
    public String getOrderDetail(Model model,
                                 @Valid @ModelAttribute("productname") ProductVM productName,
                                 @PathVariable("orderId") int orderId){
        OrderDetailVM vm = new OrderDetailVM();

        DecimalFormat df = new DecimalFormat("####0.00");

        double totalPrice = 0;

        List<OrderProductVM> orderProductVMS = new ArrayList<>();

        Order orderEntity = orderService.findOne(orderId);

        if (orderEntity !=null){
            for (OrderProduct orderProduct : orderEntity.getListProductOrders()){
                OrderProductVM orderProductVM = new OrderProductVM();

                orderProductVM.setProductId(orderProduct.getProduct().getId());
                orderProductVM.setMainImage(orderProduct.getProduct().getMainImage());
                orderProduct.setAmount(orderProduct.getAmount());
                orderProductVM.setName(orderProduct.getProduct().getName());

                orderProductVM.setPrice(df.format(orderProduct.getPrice()));

                totalPrice +=orderProduct.getPrice();

                orderProductVMS.add(orderProductVM);
            }
        }

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());
        vm.setOrderProductVMS(orderProductVMS);
        vm.setTotalPrice(df.format(totalPrice));
        vm.setTotalProduct(orderProductVMS.size());

        model.addAttribute("vm",vm);

        return "/order-detail";
    }


    @PostMapping(value = "/changeStatus")
    public @ResponseBody
    DataApiResult changleStatus(@Valid @RequestBody OrderDTO dto){
        DataApiResult result = new DataApiResult();
        Order order = orderService.findOne(dto.getId());
        if (order!=null){
            order.setDeliveryStatusId(dto.getStatus());
            orderService.update(order);


            OrderDeliveryStatus orderDeliveryStatus = new OrderDeliveryStatus();
            orderDeliveryStatus.setOrder(order);
            orderDeliveryStatus.setDeliveryStatus(deliveryStatusService.findOne(dto.getStatus()));
            orderDeliveryStatus.setCreatedDate(new Date());

            orderDeliveryStatusService.add(orderDeliveryStatus);
            result.setSuccess(true);
            result.setMessage("Success");
        }
        else {
            result.setMessage("Fail");
            result.setSuccess(false);
        }

        return result;
    }
}
