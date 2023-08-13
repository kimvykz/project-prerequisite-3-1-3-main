package habsida.spring.boot_security.demo.dao;

import habsida.spring.boot_security.demo.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void modify(User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
        User userForRemove = findUserById(user.getId());
        entityManager.remove(userForRemove);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("from User u").getResultList();
    }

    @Override
    public User findUserById(Long id) {

        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        return (User) entityManager
                .createQuery("from User u where u.username = :uname")
                .setParameter("uname", username)
                .getSingleResult();
    }

    @Override
    public User findUserByEmail(String email) {
        return (User) entityManager
                .createQuery("from User u where u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }
}
