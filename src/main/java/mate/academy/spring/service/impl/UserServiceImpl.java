package mate.academy.spring.service.impl;

import java.util.Optional;
import mate.academy.spring.dao.UserDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final ApplicationContext context;
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao, ApplicationContext context) {
        this.userDao = userDao;
        this.context = context;
    }

    @Override
    public User add(User user) {
        PasswordEncoder passwordEncoder = (PasswordEncoder) context.getBean("getEncoder");
        String securePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(securePassword);
        return userDao.add(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow(
                () -> new DataProcessingException("User with id " + id + " not found"));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
