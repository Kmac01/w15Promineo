package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

//week 14 new
@Data
@NoArgsConstructor
public class PetStoreData {

	
	//create location video 6 minutes in 

	private Long pet_store_id;
	private String pet_store_name;
	private String pet_store_address;
	private String pet_store_city;
	private String pet_store_state;
	private String pet_store_zip;
	private String pet_store_phone;
	
	private Set<PetStoreCustomer> customers = new HashSet<>();
	
	private Set<PetStoreEmployee> employees = new HashSet<>();
	
	
	// page 2.f constructor that takes PetStore as parameter
	//use a loop for this... how?
	//( ͡° ͜ʖ ͡°)
	public PetStoreData(PetStore petStore) {
		pet_store_id = petStore.getPet_store_id();
		pet_store_name = petStore.getPet_store_name();
		pet_store_address = petStore.getPet_store_address();
		pet_store_city = petStore.getPet_store_city();
		pet_store_state = petStore.getPet_store_state();
		pet_store_zip = petStore.getPet_store_zip();
		pet_store_phone = petStore.getPet_store_phone();
		
		
		//this is how you do the loops
		
		for (Employee employee : petStore.getEmployees()) {
			PetStoreEmployee petStoreEmployee = new PetStoreEmployee(employee);
			employees.add(petStoreEmployee);
		}
		for (Customer customer : petStore.getCustomers()) {
            PetStoreCustomer petStoreCustomer = new PetStoreCustomer(customer);
            customers.add(petStoreCustomer);
			
	//past this is 2.g which is external classes for parameters
	//located in PetStoreCustomer, and PetStoreEmployee toward the bottom
		}
	}



	}

