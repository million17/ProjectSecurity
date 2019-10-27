package application.controller.api;


import application.data.model.Role;
import application.data.model.User;
import application.data.model.UserRole;
import application.data.service.RoleService;
import application.data.service.UserRoleService;
import application.data.service.UserService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.viewmodel.user.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserApiController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;
    @PostMapping(value = "/changeRole")
    public BaseApiResult changeRole(@RequestBody UserVM userVM){
        DataApiResult result = new DataApiResult();

        try {
            User user= userService.findOne(userVM.getId());
            Role role = roleService.findOne(userVM.getRoleId());

            if(user!=null&&role!=null){
                UserRole userRole = userRoleService.getUserRoleByUser(user.getId());
                userRole.setRoleId(userVM.getRoleId());
                userRoleService.update(userRole);
                result.setMessage("Thay đổi thành công");
                result.setSuccess(true);
            } else {
                result.setMessage("Lỗi ghi dữ liệu");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @GetMapping("/delete/{userId}")
    public @ResponseBody
    BaseApiResult deleteUser(@PathVariable int userId){
        BaseApiResult result = new BaseApiResult();
        try {
            User user = userService.findOne(userId);
            user.setStatus(0);
            userService.deleteUser(userId);
            result.setMessage("Delete Success !");
            result.setSuccess(true);
        }catch (Exception e){
                result.setSuccess(false);
                result.setMessage(e.getMessage());
        }

        return result;

    }
}
