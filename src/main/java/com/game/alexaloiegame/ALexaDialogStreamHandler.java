package com.game.alexaloiegame;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.game.alexaloiegame.handlers.DiceRollHandler;
import com.game.alexaloiegame.handlers.LaunchRequestHandler;
import com.game.alexaloiegame.handlers.StopIntentHandler;
import com.game.alexaloiegame.handlers.WhatsNameOfPlayerhandler;
import com.game.alexaloiegame.handlers.WhatsNumberOfPlayershandler;

//assembly:assembly -DdescriptorId=jar-with-dependencies package

public class ALexaDialogStreamHandler extends SkillStreamHandler{
	static int nb = 0;
	@SuppressWarnings("unchecked")
	
	private static Skill getSkill() {
        return Skills.standard()
                .withSkillId("amzn1.ask.skill.033a2338-3274-424a-9b47-3a681a168eb7") //amzn1.ask.skill.cadc5bfd-fc6c-49f9-a49a-eeed66ac510a
                .addRequestHandlers(
                    new WhatsNumberOfPlayershandler(),
                    new WhatsNameOfPlayerhandler(),
                    new LaunchRequestHandler(),
                    new DiceRollHandler(),
                    new StopIntentHandler())
                .build();
    }

    public ALexaDialogStreamHandler() {
        super(getSkill());
    }

}

