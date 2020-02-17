package application.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.Entity.GameParty;

@Repository
public interface GamePartyRepository extends CrudRepository<GameParty, Long>{
	GameParty findById(long id);
	
	List<GameParty> findAll();

}
