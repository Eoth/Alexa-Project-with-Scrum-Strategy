package com.game.alexaloiegame.Classes;

public class ResetCase extends Case {

		public ResetCase(int id) {
			super(id);
		}

		@Override
		public void effect(Player p) {
			/* If the player stops on this case , the player will return to start .*/
			System.out.print("Retour au départ  ; ");
			p.setPosition(0);		
		}

	}

