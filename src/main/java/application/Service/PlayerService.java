package application.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.Entity.Player;
import application.Repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired private PlayerRepository playerRepository;
	
	public Player savePlayer(Player player) {
		return playerRepository.save(player);
    }
	
	public boolean deletePlayer(Player player) {
        this.playerRepository.delete(player);
        return this.playerRepository.findById(player.getId()) == null;
    }
	
	public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }
	
	public List<Player> findAllPlayers() {
        return this.playerRepository.findAll();
    }
	
	public Player findPlayerById(long id) {
        return  this.playerRepository.findById(id);
    }
	
	public Player findPlayerByName(String name) {
		List<Player> players = findAllPlayers();
		for(Player player : players) {
            if(player.getName().equals(name)) {
            	return player;
            }
         }
		return null;
	}
		   

}
