package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

//week 14 new



@Data
@NoArgsConstructor
public class PetStoreEmployee {
	
	
	private Long employee_id;
	private String employee_first_name;
	private String employee_last_name;
	private String employee_phone;
	private String employee_job_title;
	
	//2.g PetStoreEmployee external class
	public PetStoreEmployee(Employee employee) {
		employee_id = employee.getEmployee_id();
		employee_first_name = employee.getEmployee_first_name();
		employee_last_name = employee.getEmployee_last_name();
		employee_phone = employee.getEmployee_phone();
		employee_job_title = employee.getEmployee_job_title();
		
	}

}
