package com.game.alexaloiegame.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class BeginNewpartyHandler  implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("BeginNewpartyIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        String readLine;
        URL url;
        try {
            url = new URL("http://35.205.140.234:8080/game_party/delete_party?party_name=partie");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                con.disconnect();
                url = new URL("http://35.205.140.234:8080/player/all");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int responsecod = con.getResponseCode();
                if (responsecod == HttpURLConnection.HTTP_OK) {
                    con.disconnect();
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    while ((readLine = in.readLine()) != null) {
                        response.append(readLine);
                    }
                    in.close();
                    con.disconnect();
                    JSONArray arrayPlayers = new JSONArray(response.toString());
                    for (int i=0 ; i<arrayPlayers.length() ; i++) {

                        JSONObject jsonPlayer = arrayPlayers.getJSONObject(i);
                        String stringPlayer =  jsonPlayer.getString("name");
                        url = new URL("http://35.205.140.234:8080/player/delete?name="+stringPlayer);
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("DELETE");
                        con.connect();
                        con.disconnect();
                    }
                }
                speechText = "Dites on est prêt pour commencer ";
            } else
                speechText = " Un problème est survenu lors de reconfigurer une partie ! arrêter et recommencer jeu .";
        } catch (IOException e) {
            speechText = " Un problème est survenu lors de reconfigurer une partie ! arrêter et recommencer jeu .";
        }
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Leave game", speechText)
                .build();
    }
}
