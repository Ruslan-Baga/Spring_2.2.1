package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
      if (user.getCar() != null) {
//         user.getCar().setUser(user);
      sessionFactory.getCurrentSession().save(user.getCar());
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u LEFT JOIN FETCH u.car");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      return sessionFactory.getCurrentSession()
              .createQuery("SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .getSingleResult();
   }
}
