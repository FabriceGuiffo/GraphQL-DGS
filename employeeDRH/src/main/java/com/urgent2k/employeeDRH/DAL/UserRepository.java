package com.urgent2k.employeeDRH.DAL;

import com.urgent2k.employeeDRH.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsername(String username);
}
