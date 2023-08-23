package pet.store.dao;
//w15 employee specific DAO creation
import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Customer;



// performs "crud" operations on customer table
// used by service to find existing customer rows
public interface CustomerDao extends JpaRepository<Customer, Long> {

}
