package service;


import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;
import security.CustomUserDetails;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE user_access CASCADE""";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS user_access CASCADE""";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
              CREATE TABLE IF NOT EXISTS user_access
              (
                  id                  BIGSERIAL,
                  user_login 		VARCHAR(255),
                  user_password	VARCHAR(255),
                  full_name		VARCHAR(255),
                  user_role 		VARCHAR(255)
              );
                """;
        jdbcTemplate.execute(sql);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new CustomUserDetails(user.get());
    }
}