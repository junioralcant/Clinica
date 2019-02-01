package com.webtolls.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webtolls.springboot.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
	
	// lista telefones de uma unica pessoa 
	@Query("select t from Telefone t where t.medico.id = ?1")
	public List<Telefone> getTelefones(Long medicoid);
}
