package application.model.viewmodel.admin;

import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.user.RoleVM;
import application.model.viewmodel.user.UserVM;

import java.util.List;

public class AdminUserVM {
    LayoutHeaderAdminVM layoutHeaderAdminVM;
    String keyWord;
    List<UserVM> userVMList;
    List<RoleVM> roleVMList;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<UserVM> getUserVMList() {
        return userVMList;
    }

    public void setUserVMList(List<UserVM> userVMList) {
        this.userVMList = userVMList;
    }

    public List<RoleVM> getRoleVMList() {
        return roleVMList;
    }

    public void setRoleVMList(List<RoleVM> roleVMList) {
        this.roleVMList = roleVMList;
    }
}
