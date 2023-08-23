package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

//week 14 new


@Data
@NoArgsConstructor
public class PetStoreCustomer {

	
	
	private Long customer_id;
	private String customer_first_name;
	private String customer_last_name;
	private String customer_email;
	
	
	//2.g PetStoreCustomer external class
	public PetStoreCustomer(Customer customer) {
		customer_id = customer.getCustomer_id();
		customer_first_name = customer.getCustomer_first_name();
		customer_last_name = customer.getCustomer_last_name();
		customer_email = customer.getCustomer_email();
	}
	
	
}
