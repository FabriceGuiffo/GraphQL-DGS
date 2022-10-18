package com.urgent2k.employeeDRH.DAL;

import com.urgent2k.employeeDRH.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //we will create a few default users in our database in case there are none upon startup
        List<User> allusers=userRepository.findAll();
        if(allusers.isEmpty()){
            User testuser1=new User("Fabrice", passwordEncoder.encode("UrgentAdmin"),"ADMIN" );
            User testuser2=new User("Jude", passwordEncoder.encode("Bayiha"),"VIEWER" );
            allusers.add(testuser1);
            allusers.add(testuser2);

            userRepository.saveAll(allusers);
            /*userRepository.save(testuser1);
            userRepository.save(testuser2);*/
        }


    }
}
