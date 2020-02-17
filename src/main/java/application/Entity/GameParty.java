package application.Entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "game_party")
public class GameParty {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "game_party_id")
    private long id;
	
	private String name;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "players_of_game_party", joinColumns = @JoinColumn(name = "game_party_id"), inverseJoinColumns
			= @JoinColumn(name = "player_id"))
    private Set<Player> players;

	public GameParty() {}
	
	public GameParty(String name, Set<Player> players) {//
		super();
		this.name = name;
		this.players = players;
	}
	
	public GameParty(long id, String name, Set<Player> players) {
		super();
		this.id = id;
		this.name = name;
		this.players = players;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
