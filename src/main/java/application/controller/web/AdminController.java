package application.controller.web;


import application.constant.RoleIdConstant;
import application.data.model.*;
import application.data.service.*;
import application.model.viewmodel.admin.*;
import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.order.DeliveryStatusVM;
import application.model.viewmodel.order.OrderProductVM;
import application.model.viewmodel.order.OrderVM;
import application.model.viewmodel.user.RoleVM;
import application.model.viewmodel.user.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(path = "/admin")
public class AdminController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDeliveryStatusService orderDeliveryStatusService;

    @Autowired
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String admin(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if (userEntity!=null){
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        HomeAdminVM vm = new HomeAdminVM();
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm",vm);
        return "/admin/home";
    }

    @GetMapping("/product")
    public String product(Model model,
                          @Valid @ModelAttribute("productname")ProductVM productName,
                          @RequestParam(name = "page",required = false,defaultValue = "0" )Integer page,
                          @RequestParam(name = "size",required = false,defaultValue = "8")Integer size){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if (userEntity!=null){
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        AdminProductVM vm = new AdminProductVM();
        /**
         * set list categoryVM
         */

        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for (Category category : categoryList){
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }

        Pageable pageable = new PageRequest(page,size);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()){
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
            vm.setKeyWord("Find with key: " + productName.getName());
        }else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
        }

        List<ProductVM> productVMList = new ArrayList<>();

        for (Product product : productPage.getContent()){
            ProductVM productVM = new ProductVM();
            if (product.getCategory() == null){
                productVM.setCategoryName("Unknown");
            }else {
                productVM.setCategoryName(product.getCategory().getName());
            }
            productVM.setId(product.getId());
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
            productVM.setCreatedDate(product.getCreatedDate());
            productVM.setBrand(product.getBrand());

            productVMList.add(productVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        if (productVMList.size() == 0){
            vm.setKeyWord("Not found any product");
        }
        model.addAttribute("vm",vm);
        model.addAttribute("page",productPage);

        return "/admin/product";
    }




    @GetMapping("/category")
    public String category(Model model,
                           @Valid @ModelAttribute("categoryname") CategoryVM categoryName){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if (userEntity!=null){
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        AdminCategoryVM vm = new AdminCategoryVM();
        /**
         * set list categoryVM
         */
        List<Category> categoryList = categoryService.getListAllCategories();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVM.setShortDesc(category.getShortDesc());
            categoryVM.setCreatedDate(category.getCreatedDate());

            categoryVMList.add(categoryVM);
        }
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);

        model.addAttribute("vm",vm);


        return "/admin/category";
    }
    @GetMapping("/user")
    public String user(Model model,
                       @Valid @ModelAttribute("userName")UserVM userName,
                       @RequestParam(name = "page", required = false,defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false,defaultValue = "8") Integer size
                       ){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User userEntity = userService.findUserByUsername(username);
        if(userEntity != null){
            Role role = roleService.getRoleByUser(userEntity.getId());
            if(role.getId() != RoleIdConstant.Role_Admin){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        AdminUserVM vm = new AdminUserVM();
        Pageable pageable = new PageRequest(page,size);

        Page<User> userPage = null;
        if(userName.getUserName() != null && !userName.getUserName().isEmpty() ){
            userPage = userService.getList(pageable,userName.getUserName().trim());
            vm.setKeyWord("Search : " + userName.getUserName());
        }else {
            userPage = userService.getList(pageable,null);
        }

        List<UserVM> userVMList = new ArrayList<>();
        for (User user : userPage.getContent()){
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());
            userVM.setEmail(user.getEmail());
            userVM.setUserName(user.getUserName());
            userVM.setRoleId(roleService.getRoleByUser(user.getId()).getId());
            String pattern = "dd/MM/yyyy";

            SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern);
            Date cr = user.getCreatedDate();
            if(cr!= null){
                String dt = simpleDateFormat.format(cr);
                userVM.setCreatedDate(dt);
            }

            userVMList.add(userVM);

        }

        List<RoleVM> roleVMList = new ArrayList<>();
        for (Role role : roleService.getListAllRole()){
            RoleVM roleVM = new RoleVM();
            roleVM.setName(role.getName());
            roleVM.setId(role.getId());



            roleVMList.add(roleVM);
        }


        LayoutHeaderAdminVM layoutHeaderAdminVM = this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM(layoutHeaderAdminVM);
        vm.setUserVMList(userVMList);
        vm.setRoleVMList(roleVMList);
        model.addAttribute("vm",vm);
        model.addAttribute("page",userPage);

        return "/admin/user";
    }

    @GetMapping("/order")
    public String order(Model model,
                        @Valid @ModelAttribute("customerName") OrderVM customerName,
                        @RequestParam(name = "page", required = false,defaultValue = "0")Integer page,
                        @RequestParam(name = "size",required = false,defaultValue = "8")Integer size){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if (userEntity!=null){
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        AdminOrderVM vm = new AdminOrderVM();
        /**
         * set list orderVM
         */

        Pageable pageable = new PageRequest(page,size);
        Page<Order> orderPage = null;

        orderPage =orderService.getListOrderByCustomerName(pageable,null);




        DecimalFormat df = new DecimalFormat("####0.00");

        List<OrderVM> orderVMList = new ArrayList<>();

        for (Order order : orderPage.getContent()){
            OrderVM orderVM  = new OrderVM();
            DeliveryStatus deliveryStatus = deliveryStatusService.findOne(order.getDeliveryStatusId());
            if (deliveryStatus!=null){
                orderVM.setDeliveryStatus(deliveryStatus.getName());

            }
            orderVM.setCreatedDate(order.getCreatedDate());
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setPrice(order.getPrice());
            orderVM.setId(order.getId());
            orderVM.setAddress(order.getAddress());
            orderVM.setEmail(order.getEmail());
            orderVM.setDeliveryStatusId(order.getDeliveryStatusId());
            orderVMList.add(orderVM);

        }

        List<DeliveryStatusVM> deliveryStatusVMList = new ArrayList<>();
        for (DeliveryStatus deliveryStatus : deliveryStatusService.getAll()){
            DeliveryStatusVM deliveryStatusVM = new DeliveryStatusVM();
            deliveryStatusVM.setId(deliveryStatus.getId());
            deliveryStatusVM.setName(deliveryStatus.getName());
            deliveryStatusVMList.add(deliveryStatusVM);
        }

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setOrderVMList(orderVMList);
        vm.setDeliveryStatusVMS(deliveryStatusVMList);
        model.addAttribute("vm",vm);
        model.addAttribute("page",orderPage);

        return "/admin/order";
    }

//    @GetMapping("/order/{orderId}")
//    public String orderDetail(Model model,
//                              @PathVariable("orderId") Integer orderId){
//
//
//        AdminInvoiceVM vm = new AdminInvoiceVM();
//        Order order = orderService.findOne(orderId);
//        double totalPriceOrder = 0;
//
//        if (order!=null){
//            List<OrderProductVM> orderProductVMS = new ArrayList<>();
//            for (OrderProduct orderProduct : order.getListProductOrders()){
//                OrderProductVM orderProductVM = new OrderProductVM();
//
//                orderProductVM.setProductId(orderProduct.getProductEntity().getProduct().getId());
//                orderProductVM.setMainImage(orderProduct.getProductEntity().getProduct().getMainImage());
//                orderProductVM.setAmount(orderProduct.getAmount());
//                orderProductVM.setName(orderProduct.getProductEntity().getProduct().getName());
//                orderProductVM.setPrice((orderProduct.getProductEntity().getProduct().getPrice()));
//                orderProductVM.setTotalPrice(orderProduct.getProductEntity().getProduct().getPrice()*orderProduct.getAmount());
//                totalPriceOrder += orderProduct.getPrice();
//                orderProductVMS.add(orderProductVM);
//                vm.setOrderProductVMList(orderProductVMS);
//            }
//            OrderVM orderVM = new OrderVM();
//            orderVM.setId(order.getId());
//            orderVM.setCustomerName(order.getCustomerName());
//            orderVM.setEmail(order.getEmail());
//            orderVM.setAddress(order.getAddress());
//            orderVM.setPrice(order.getPrice());
//            orderVM.setCreatedDate(order.getCreatedDate());
//            orderVM.setShipPrice(order.getShipPrice());
//            vm.setOrderVM(orderVM);
//
//        }
//        vm.setTotalPrice(order.getPrice() +order.getShipPrice());
//        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
//        model.addAttribute("vm",vm);
//        return "/admin/invoice";
//
//    }
}
