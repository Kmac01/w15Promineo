package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.PetStore;
//created week 14 page 3.2
public interface PetStoreDao extends JpaRepository<PetStore, Long> {



	
	//w14 page 3.4.c requests a public method that mapps HTTP POST request to "/pet_store". 
	//should be 201 (Created). 
	//at the moment i have 0 idea what this is supposed to mean
}
