package com.joshcheek.ttt.web;

import com.joshcheek.server.webFramework.WebFramework;
import com.joshcheek.ttt.library.ComputerPlayer;
import com.joshcheek.ttt.library.Game;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: joshuajcheek
 * Date: 9/7/11
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class TTTServer extends WebFramework {

    private Game lastGame;

    public static void main(String[] args) throws IOException {
        TTTServer server = new TTTServer(8080);
        server.startRunning();
    }

    public TTTServer(int port) {
        super(port);
    }

    public void defineRoutes() {
        new GetRequest("/") {
            public String controller() {
                return "Go <a href='first'>first</a> or <a href='second'>second</a>.";
            }
        };

        new GetRequest("/first") {
            public String controller() {
                lastGame = new Game();
                return "<div id='game'>";
            }
        };

        new GetRequest("/second") {
            public String controller() {
                lastGame = new Game();
                new ComputerPlayer(lastGame).takeTurn();
                return "<div id='game'>";
            }
        };
    }


    public Game getLastGame() {
        return lastGame;
    }
}
