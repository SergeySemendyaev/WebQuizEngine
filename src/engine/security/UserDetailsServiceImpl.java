package engine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@org.springframework.stereotype.Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> data = service.findUserByEmail(username);
        if (data.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        return new UserDetailsImpl(data.get());
    }
}
