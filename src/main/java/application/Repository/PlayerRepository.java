package application.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.Entity.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long>{
	
	Player findById(long id);
    
//	@Query("SELECT p FROM Player p WHERE p.name=?1")
//	Player findByName(String name);
	
	List<Player> findAll();

}
