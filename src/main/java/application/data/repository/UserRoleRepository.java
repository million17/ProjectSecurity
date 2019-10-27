package application.data.repository;

import application.data.model.Role;
import application.data.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface  UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Transactional(readOnly = true)
    @Query("select u from dbo_user_role u where u.userId= :id")
    Iterable<UserRole> finRolesOfUser(@Param("id") int userId);

    @Query(value = "select r.*" +
            " from dbo_user_role ur" +
            " inner join dbo_user u" +
            " on (u.user_id = ur.user_id) " +
            "inner join dbo_role r " +
            "on(r.role_id = ur.role_id ) " +
            "where ur.user_id =:id", nativeQuery = true)
    Role getRoleByUser(@Param("id") Integer userId);

    @Query(value = "select ur.* " +
            "from dbo_user_role ur " +
            "where ur.user_id=:id", nativeQuery = true)
    UserRole getUserRoleByUser(@Param("id")Integer userId);

}
