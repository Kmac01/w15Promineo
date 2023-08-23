package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

//created week 14. Requested by page 3.4

//default response code = 200. Deals with JSON
@RestController
//the additional ("/pet_store") is required for proper mapping
@RequestMapping("/pet_store") 
//Lombok gibberish = logger. instance variable named log
//use like this: log.info("This is a log line"):
@Slf4j
public class PetStoreController {
	
	//3.4.b autowire injects PetStoreService as instance variable
	@Autowired
	private PetStoreService petStoreService;
	
	
	//to complete post method http://localhost:8080/pet_store/petstore
	//then name Accept, Value application/json
	//then Name Content-Type Value application/json
	//w14 3.4.c 
	@PostMapping("/petstore")
	//response status should be 201(Created)
	@ResponseStatus(code = HttpStatus.CREATED)
	//Create a public method that maps an HTTP POST request to "/pet_store".
	//Pass the contents of the request body as a parameter (type PetStoreData) to the method. 
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		//Log the request. before return, as return terminates method.
		log.info("Trying: creation of pet store {}", petStoreData);
		//The method should return a PetStoreData object
		return petStoreService.savePetStore(petStoreData);
		//Call a method in the service class (savePetStore) that will insert or modify the pet store data.

}
	
	
	//to complete put method http://localhost:8080/pet_store/petstore/the id you wish to change
	//then name Accept, Value application/json
	//then Name Content-Type Value application/json
	@PutMapping("/petstore/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPet_store_id(petStoreId);
		log.info("Trying: updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	//w15 method that adds employee to employee table
	//http://localhost:8080/pet_store/petstore/#/employee
	//dont even think about touching it. dont even think about not touching it because that means youre thinking about it
	//if you look at it in a way that upsets it it will break
	@PostMapping("/petstore/{petStoreId}/employee")
	@PutMapping
	public PetStoreEmployee insertEmployee(@PathVariable Long petStoreId,@RequestBody PetStoreEmployee petStoreEmployee){
		log.info("Trying: creation for employee {} at pet store {}", petStoreEmployee, petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	//method to add customer 
	//http://localhost:8080/pet_store/petstore/#/customer
	@PostMapping("/petstore/{petStoreId}/customer")
	@PutMapping
	public PetStoreCustomer insertCustomer(@PathVariable Long petStoreId,@RequestBody PetStoreCustomer petStoreCustomer){
		log.info("Trying: creation for customer {} at pet store {}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	
	//controller method that retrieves all stores
	//http://localhost:8080/pet_store/petstore
	@GetMapping("/petstore")
	public List<PetStoreData> listAllPetStores() {
		log.info("Trying: displaying all stores.");
		return petStoreService.retrieveAllPetStores();
	}
	//method to retrieve a single pet store
	//http://localhost:8080/pet_store/petstore/#
	@GetMapping("/petstore/{petStoreId}")
	public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
		log.info("Trying: store id {} display", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	//obviously be careful with the delete function. 
	//http://localhost:8080/pet_store/petstore/#
	@DeleteMapping("/petstore/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Trying: store {} DELETION", petStoreId);
		//calling service layer delete method with pet store id as parameter
		petStoreService.deletePetStoreById(petStoreId);
		//return map with "message" and successful deletion message values
		return Map.of("message", "Success: " + petStoreId + " DELETED");
		
	}

	
	
}
