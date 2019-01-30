package com.webtolls.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webtolls.springboot.model.Medico;

@Repository // informa que a class é um repositorio
@Transactional // vai cuidar das transções com o BD
public interface MedicoRepository extends CrudRepository<Medico, Long> { // passamos a class que e Medico e o tipo de id que usamos na class Medico, que no caso é Long
	
	@Query("select m from Medico m where m.nome like %?1%") // faz a pesquisa no bd
	List<Medico> findMedicoByNome(String nome);
}
