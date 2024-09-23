package com.inn.cafe.JWT;
import com.inn.cafe.dao.userDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; // Import this

import java.util.ArrayList;
import java.util.Objects;

@Service // Add this annotation
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private userDao userDao;

    private com.inn.cafe.POJO.User UserDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails = userDao.findByEmailId(username);
        if (!Objects.isNull(UserDetails)) {
            return new User(UserDetails.getEmail(), UserDetails.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    public com.inn.cafe.POJO.User getUserDetail() {
        return UserDetails;
    }
}
