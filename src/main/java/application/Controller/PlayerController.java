package application.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.Entity.Player;
import application.Service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "jeuxDeLoie", description = "Opérations relatives à la gestion des joueurs",produces = "application/json")
public class PlayerController {
	
	@Autowired private PlayerService playerService;
	
	@ApiOperation(value = "Créé un nouvel joueur", response = String.class)
    @PostMapping("/player/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestParam("name") String name,
                                           @RequestParam("position") int position) {
		Player player = playerService.findPlayerByName(name);
        if(player != null) {
            return new ResponseEntity<>("Cet nom est déjà enrégistré.", HttpStatus.NOT_MODIFIED);
        } else {
            Player newPlayer = new Player(name, position);
            if(this.playerService.savePlayer(newPlayer) != null) {
                return new ResponseEntity<>("Le nom du joueur a correctement été créé.", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Une erreur est survenue lors de la création de votre compte.",
                    HttpStatus.NOT_MODIFIED);
        }
    }
	
	@ApiOperation(value = "supprimer joueur", response = String.class)
    @DeleteMapping("/player/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam("name") String name) {
		Player _player = playerService.findPlayerByName(name);
        if(_player != null) {
        	if(this.playerService.deletePlayer(_player)) {
        		return new ResponseEntity<>("Le joueur a correctement été supprimé.", HttpStatus.OK);
             } else {
            	 return new ResponseEntity<>("Une erreur est survenue lors de la suppression du joueur.",
                            HttpStatus.NOT_MODIFIED);
             }
         }
        
		return new ResponseEntity<>("ce joueur n'existe pas.", HttpStatus.BAD_REQUEST);
       
    }
	
	@ApiOperation(value = "Retourne la liste de tous les joueurs", response = List.class)
    @GetMapping("/player/all")
    @ResponseBody
    public ResponseEntity<List<Player>> getAllUsers() {
        return new ResponseEntity<>(this.playerService.findAllPlayers(), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Modifier la position du joueur", response = String.class)
    @PutMapping("/player/update")
    @ResponseBody
    public ResponseEntity<String> updatePlayerPosition(@RequestParam("name") String name,
                                                       @RequestParam("position") int position) {
		Player player = playerService.findPlayerByName(name);
        if(player != null) {
        	player.setPosition(position);
        	if(this.playerService.savePlayer(player) != null) {
                return new ResponseEntity<>("La position du joueur a été correctement mis à jour.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Une erreur est survenue lors de la mise à jour de la position du joueur.",
                    HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>("Aucun joueur avec nom.", HttpStatus.NOT_FOUND);
    }

}
