package application.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.Entity.GameParty;
import application.Entity.Player;
import application.Service.GamePartyService;
import application.Service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "jeuxDeLoie", description = "Opérations relatives à la gestion de la partie de jeu",produces = "application/json")
public class GamePartyController {
	
	@Autowired private GamePartyService gamePartyService;
	@Autowired private PlayerService playerService;
	
	@ApiOperation(value = "Retourne la liste des partis ", response = List.class)
    @GetMapping("/game_party/all")
    @ResponseBody
    public ResponseEntity<List<GameParty>> getAllGameParties() {
        return new ResponseEntity<>(this.gamePartyService.findAllGameParty(), HttpStatus.OK);
    }
	
	    @ApiOperation(value = "Ajoute un joueur à une partie", response = String.class)
	    @PostMapping("/game_party/add")
	    @ResponseBody
	    public ResponseEntity<String> addPlayer(@RequestParam("party_name") String party_name, 
	    		                                @RequestParam("player_name") String player_name) {
		   
		 Player _player = playerService.findPlayerByName(player_name);
		 
	        if(_player != null) {
	            GameParty gameParty = gamePartyService.findGamePartyByName(party_name);
	            if(gameParty != null) {
	            	gameParty.getPlayers().add(_player);
	            	gamePartyService.saveGameParty(gameParty);
	                return new ResponseEntity<>("player add Successful.", HttpStatus.ACCEPTED);
	            } else {
	            	//GameParty _gameParty = new GameParty();
	                // Creating an empty Set
	                Set<Player> players = new HashSet<Player>();
	                players.add(_player);
	                GameParty _gameParty = new GameParty(party_name, players);
	                gamePartyService.saveGameParty(_gameParty);
	                return new ResponseEntity<>("player add Successful.", HttpStatus.CREATED);
	            }
	        }
	        return new ResponseEntity<>("An error occurred during your add.", HttpStatus.NOT_FOUND);
	    }
	    
	    @ApiOperation(value = "supprimer joueur d'une partie", response = String.class)
	    @DeleteMapping("/game_party/delete_player")
	    @ResponseBody
	    public ResponseEntity<String> deletePlayerOnGameParty(@RequestParam("party_name") String party_name, 
                                             @RequestParam("player_name") String player_name) {
	    	
	    	Player _player = playerService.findPlayerByName(player_name);
	        if(_player != null) {
	        	GameParty gameParty = gamePartyService.findGamePartyByName(party_name);
	            if(gameParty != null) {
	            	Set<Player> players = new HashSet<Player>();
	            	for (Player player : gameParty.getPlayers()) {
	            		if(!player.getName().equals(player_name)) {
	            			players.add(player);
	            		}
	            	}
	            	gameParty.setPlayers(players);
	            	gamePartyService.saveGameParty(gameParty);
	                return new ResponseEntity<>("player remove Successful.", HttpStatus.ACCEPTED);
	            	
	            } else {
	            	 return new ResponseEntity<>("Une erreur est survenue lors de la suppression du joueur de la partie.",
	                            HttpStatus.NOT_MODIFIED);
	             }
	         }
	        
			return new ResponseEntity<>("ce joueur n'existe pas.", HttpStatus.BAD_REQUEST);
	       
	    }
	    
	    @ApiOperation(value = "supprimer une partie", response = String.class)
	    @DeleteMapping("/game_party/delete_party")
	    @ResponseBody
	    public ResponseEntity<String> deleteGameParty(@RequestParam("party_name") String party_name) {
	    	
	    	GameParty gameParty = gamePartyService.findGamePartyByName(party_name);
	    	if(gameParty != null) {
	        	if(this.gamePartyService.deleteGameParty(gameParty)) {
	        		return new ResponseEntity<>("La party correctement été supprimé.", HttpStatus.OK);
	             } else {
	            	 return new ResponseEntity<>("Une erreur est survenue lors de la suppression de la partie.",
	                            HttpStatus.NOT_MODIFIED);
	             }
	         }
			return new ResponseEntity<>("cette partie n'existe pas.", HttpStatus.BAD_REQUEST);
	       
	    }

}
