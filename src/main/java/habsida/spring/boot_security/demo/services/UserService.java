package habsida.spring.boot_security.demo.services;


import habsida.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void modify(User user);
    void remove(User user);

    List<User> listUsers();

    User findUserById(Long id);
}
