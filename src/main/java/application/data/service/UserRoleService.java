package application.data.service;

import application.data.model.Role;
import application.data.model.UserRole;
import application.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;


    public Role getRoleByUser(Integer userId){
        return userRoleRepository.getRoleByUser(userId);
    }

    public void update(UserRole userRole){
        userRoleRepository.save(userRole);
    }
    public UserRole getUserRoleByUser(int id){
        return userRoleRepository.getUserRoleByUser(id);


    }

    public void delete(int id){
        userRoleRepository.delete(id);
    }
}
