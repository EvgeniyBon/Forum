package telran.java51.accounting.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import telran.java51.accounting.model.User;
@Repository
public interface UserRepository extends CrudRepository<User,String> {

}
