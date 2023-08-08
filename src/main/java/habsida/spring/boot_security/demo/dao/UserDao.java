package habsida.spring.boot_security.demo.dao;


import habsida.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserDao {
    void add(User user);
    void modify(User user);
    void remove(User user);
    List<User> listUsers();
    User findUserById(Long id);
    User findUserByUsername(String username);
}
