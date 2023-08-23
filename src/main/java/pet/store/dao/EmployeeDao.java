package pet.store.dao;
//w15 employee specific DAO creation
import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Employee;



// performs "crud"(which i cannot capitalize because eclipse loses its mind)
//				   operations on employee table
// used by service to find existing employee rows
// very simple. do not touch.
public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
