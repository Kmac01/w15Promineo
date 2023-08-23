package pet.store.service;


import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

//created week 14 requested by page 3.3
@Service
public class PetStoreService {

	// private instance variable object. annotation injects DAO into variable
	@Autowired
	private PetStoreDao petStoreDao;
	
	//week 15 injection for employeeDao
	@Autowired
	private EmployeeDao employeeDao;
	
	//injection for customerDao
	@Autowired
	private CustomerDao customerDao;

	// page 3.5 service class savePetStore method as continuation of 3.4.c
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long pet_Store_Id = petStoreData.getPet_store_id();
		PetStore petStore = findOrCreatePetStore(pet_Store_Id);
		
		// 4.5.b Do not copy the customers or employees fields.
		copyPetStoreFields(petStore, petStoreData);

		// 4.5.c Calling PetStoreDao save(petStore)
		// return new object created from return value of save() method
		return new PetStoreData(petStoreDao.save(petStore));
	}

	// called by 4.5.b
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPet_store_id(petStoreData.getPet_store_id());
		petStore.setPet_store_name(petStoreData.getPet_store_name());
		petStore.setPet_store_address(petStoreData.getPet_store_address());
		petStore.setPet_store_city(petStoreData.getPet_store_city());
		petStore.setPet_store_state(petStoreData.getPet_store_state());
		petStore.setPet_store_zip(petStoreData.getPet_store_zip());
		petStore.setPet_store_phone(petStoreData.getPet_store_phone());

	}
	
	
	//4.5.a second reference: returns a new PetStore object if the pet store ID is null
	private PetStore findOrCreatePetStore(Long petStoreId) {


		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return findPetStoreById(petStoreId);
		}

	}

	//4.5.a third reference: returns a PetStore object if a pet store with matching ID exists in the database. 
	public PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
	//If no matching pet store is found, the method should throw a NoSuchElementException with an appropriate message.
				.orElseThrow(() -> new NoSuchElementException(
						"Pet store " + petStoreId + " not found."));
	}
	
	//w15
//*********************************************************************************************************************
	//employee methods begin
//*********************************************************************************************************************
//organized according to command by assignment. It breaks if you move it
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId); //says its already written. what does that mean?
		Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployee_id());
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		//set petStore in employee
		employee.setPetStore(petStore);
		//add employee to pet store list of employees
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		return new PetStoreEmployee(dbEmployee);
	}
	
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {

		Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Invalid id: " + employeeId + " not found"));
		//is employee pet store id equals petStoreId return new employee
		if(employee.getPetStore().getPet_store_id() == petStoreId) {
			return employee;
		//else throw IllegalArgumentExcpetion
		} else {
			throw new IllegalArgumentException("Error: employee " + employeeId + " not found at " + petStoreId);
		}
	}
	
	//currently throws an undefined error for petstoreservice. I think its out of memory. Ill restart later
	//issue resolved. Make sure to reset after using many tasks.
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		//instantiating employee again
		Employee employee;
		
		//if employee is null
		if (Objects.isNull(employeeId)) {
			//then return new employee
			employee = new Employee();
		} else {
			employee = findEmployeeById(petStoreId, employeeId);
		}
		//separated return from if block to reduce risk of complications
		return employee;
	}
	
	// copies the employee fields much like the pet store fields
	//goes in order relative to the ERD both created, and supplied in W13
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployee_id(petStoreEmployee.getEmployee_id());
		employee.setEmployee_first_name(petStoreEmployee.getEmployee_first_name());
		employee.setEmployee_last_name(petStoreEmployee.getEmployee_last_name());
		employee.setEmployee_phone(petStoreEmployee.getEmployee_phone());
		employee.setEmployee_job_title(petStoreEmployee.getEmployee_job_title());
	}
	
//*********************************************************************************************************************
	//customer methods begin
//*********************************************************************************************************************
//remember entity identification copied from ERD, meaning that getPet_store_id is correct, while getPetStoreId is not	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId); 
		Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomer_id());
		//found this through external research, unnecessary but its too extraneous to change it back
		copyCustomerFields(customer, petStoreCustomer);
		petStore.getCustomers().add(customer);
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		Customer dbCustomer = customerDao.save(customer);
		return new PetStoreCustomer(dbCustomer);
	}
	
	private Customer findCustomerById(Long customerId, Long petStoreId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Error: customer " + customerId + " invalid"));
		boolean found = false;
		for (PetStore petStore : customer.getPetStores()) {
			//getPet_store_id caused many issues. Naming convention errors do not display proper error logs
			//will have to spend much more time thinking about this for final
			if (petStore.getPet_store_id()==(petStoreId)) {
				found = true;
				break;
			} 
		} 
		if(!found) {
			throw new IllegalArgumentException(
					"Pet Store with ID=" + petStoreId + " not found for the Customer with ID=" + customerId);
		}
		return customer;
	}
	
	
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer;
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(petStoreId, customerId);
		}
		return customer;
	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomer_id(petStoreCustomer.getCustomer_id());
		customer.setCustomer_first_name(petStoreCustomer.getCustomer_first_name());
		customer.setCustomer_last_name(petStoreCustomer.getCustomer_last_name());
		customer.setCustomer_email(petStoreCustomer.getCustomer_email());
	}

	//referenced by call in controller
	//instructs transactional annotation, but does not specify readonly status. I assume this means readOnly = true
	@Transactional(readOnly = true)
	//method refines a gigantic text wall into a readable selection of all stores.
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStoreData> petStoreData = new LinkedList<>();
		// findall method call in the pet store dao
		//enhanced for loop that converts petstore objects to petstoredata objects
		for (PetStore petStore : petStoreDao.findAll()) {
			PetStoreData psd = new PetStoreData(petStore);
			//loop according to instructional diagram
			//removes customer and employee objects
			psd.getCustomers().clear();
			psd.getEmployees().clear();

			petStoreData.add(psd);
		}
		//summary list returned
		return petStoreData;
	}
	
	@Transactional(readOnly = true)
	//call findid method and convert to petstoredata object
	//successfully returns store customer and employee data
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		return new PetStoreData(findPetStoreById(petStoreId));
	}
	
	//should be final command, delete
	//obviously this is the service method reference for the controller method
	public void deletePetStoreById(Long petStoreId) {
		//finding pet store by id to get specific entity
		PetStore petStore = findPetStoreById(petStoreId);
		// deletion
		petStoreDao.delete(petStore);
	}


}
