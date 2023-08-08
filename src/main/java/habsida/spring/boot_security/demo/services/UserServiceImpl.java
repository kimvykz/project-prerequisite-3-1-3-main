package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.dao.UserDao;
import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    List<User> addIfEmpty = new ArrayList<>(Arrays.asList(
            new User("Ivan", "Sidorov", "ivan@mail.ru", "ivan", "ivan",
                    new ArrayList<>(Arrays.asList(new Role("USER")))),
            new User("Oleg", "Semyonov", "oleg@gmail.ru", "oleg", "oleg",
                    new ArrayList<>(Arrays.asList(new Role("USER"), new Role("ADMIN")))),
            new User("Denis", "Komarov", "den273@mail.ru", "denis", "denis",
                    new ArrayList<>(Arrays.asList(new Role("ADMIN"))))
//            ,
//            new User("Oleg", "Semyonov","oleg@gmail.ru"),
//            new User("Dmitriy", "Petrov","dimasik@gmail.com"),
//            new User("Denis", "Komarov","den273@mail.ru"),
//            new User("Sergey", "Fedorov","serega374@gmail.com")
    ));

    private  UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(){

    };

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void add(User user) {

        if (user.getFirstName() != "" &&
            user.getLastName() != "" &&
            user.getEmail() != "") {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.add(user);
        }
    }

    @Transactional
    @Override
    public void modify(User user) {
        if (user.getFirstName() != "" &&
                user.getLastName() != "" &&
                user.getEmail() != "") {
            userDao.modify(user);
        }
    }

    @Transactional
    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Transactional()
    @Override
    public List<User> listUsers() {
        List<User> listUsers = userDao.listUsers();
        if (listUsers.size() == 0){
            addIfEmpty.stream().forEach(t-> this.add(t));
            listUsers = List.copyOf(addIfEmpty);
        }
        return listUsers;
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }


}
