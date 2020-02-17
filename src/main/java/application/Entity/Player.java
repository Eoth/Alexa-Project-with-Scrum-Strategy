package application.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "player")
public class Player {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "player_id")
    private long id;

    private String Name; // name of player
    
    private int Position; // current position of the player
    
    public Player() {}
    
    public Player(String name, int position) {
		super();
		Name = name;
		Position = position;
	}

	public Player(long id, String name, int position) {
		super();
		this.id = id;
		Name = name;
		Position = position;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getPosition() {
		return Position;
	}

	public void setPosition(int position) {
		Position = position;
	}
    
    

}
