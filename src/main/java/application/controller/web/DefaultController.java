package application.controller.web;


import application.constant.StatusRegisterUserEnum;
import application.data.model.User;
import application.data.service.UserService;
import application.model.dto.ProductDTO;
import application.model.viewmodel.home.HomeVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class DefaultController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model,
                        @Valid @ModelAttribute("productname")ProductDTO productName){
        HomeVM vm = new HomeVM();
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm",vm);
        return "/login";
    }


    @GetMapping("/403")
    public String error403(){
        return "/error/403";

    }

    @GetMapping(path = "/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "/register";
    }

    @RequestMapping(path = "/register-user",method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute("user") User user){
        StatusRegisterUserEnum status = userService.registerNewUser(user);
        if (status==StatusRegisterUserEnum.Existed_Username)
            return "redirect:/register?exit_username";

        return "redirect:/login";
    }
}
