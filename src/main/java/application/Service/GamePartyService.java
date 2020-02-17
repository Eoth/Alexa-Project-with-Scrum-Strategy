package application.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.Entity.GameParty;
import application.Repository.GamePartyRepository;

@Service
public class GamePartyService {
	
	@Autowired private GamePartyRepository gamePartyrRepository;
	
	public GameParty saveGameParty(GameParty gameParty) {
		return gamePartyrRepository.save(gameParty);
    }
	
	public boolean deleteGameParty(GameParty gameParty) {
        this.gamePartyrRepository.delete(gameParty);
        return this.gamePartyrRepository.findById(gameParty.getId()) == null;
    }
	
	public GameParty updateGameParty(GameParty gameParty) {
        return gamePartyrRepository.save(gameParty);
    }
	
	public List<GameParty> findAllGameParty() {
        return this.gamePartyrRepository.findAll();
    }
	
	public GameParty findGamePartyById(long id) {
        return  this.gamePartyrRepository.findById(id);
    }
	
	public GameParty findGamePartyByName(String name) {
		List<GameParty> gameParties = findAllGameParty();
		if(gameParties != null) {
			for(GameParty gameParty : gameParties) {
				if(gameParty.getName().equals(name)) {
					return gameParty;
                }
            }
		}
		return null;
	}

}
