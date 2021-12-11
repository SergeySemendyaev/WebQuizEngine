package engine.security;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    UserRepository repository;

    public Optional<User> findUserByEmail(String email) {
        return repository.findById(email);
    }

    public void save(User user) {
        repository.save(user);
    }
}
