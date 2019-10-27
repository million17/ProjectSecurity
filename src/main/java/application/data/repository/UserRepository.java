package application.data.repository;

import application.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, Integer>{


    @Transactional(readOnly = true )
    @Query("select u from dbo_user u where u.email = :email")
    Iterable<User> findByEmail(@Param("email") String email);

    @Transactional(readOnly = true)
    @Query("select u from dbo_user u where u.userName = :username")
    Iterable<User> findByUsername(@Param("username") String userName);

    @Query("SELECT u FROM dbo_user u " +
            "WHERE((:key IS NULL OR UPPER(u.name) LIKE CONCAT('%',UPPER(:key),'%')) " +
            "OR (:key IS NULL OR UPPER(u.userName) LIKE CONCAT('%',UPPER(:key),'%')) " +
            "OR (:key IS NULL OR UPPER(u.email) LIKE CONCAT('%',UPPER(:key),'%')) " +
            "OR (:key IS NULL OR UPPER(u.phoneNumber) LIKE CONCAT('%',UPPER(:key),'%')))" )
    Page<User> getList(Pageable pageable, @Param("key") String key);
}