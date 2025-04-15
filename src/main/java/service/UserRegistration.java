package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

@Service
public class UserRegistration {

    private final UserRepository userRepository;

    @Autowired
    public UserRegistration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void register(User user) {
        userRepository.save(user);
    }
}
