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

    public static String htmlForBoard(Game game) {
        String toReturn = "<div id='game'>";
        for(int i=1; i<=9; ++i)
            toReturn += htmlForCell(game, i);
        return toReturn + "</div>";
    }

    private static String htmlForCell(Game game, int cell) {
        if(game.isAvailable(cell))
            return "<div id='cell" + cell + "'><a href='/" + game.pristineMove(cell).board() + "'>move</a></div>";
        return "<div id='cell" + cell + "'></div>";
    }

    private Game lastGame;

    public static void main(String[] args) throws IOException {
        TTTServer server = new TTTServer(8082);
        server.startRunning();
    }

    public TTTServer(int port) {
        super(port);
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void defineRoutes() {
        new GetRequest("/") {
            public String controller() {
                return "Go <a href='first'>first</a> or <a href='second'>second</a>.";
            }
        };

        new GetRequest("/first") {
            public String controller() {
                return htmlForBoard(lastGame = new Game());
            }
        };

        new GetRequest("/second") {
            public String controller() {
                Game game = lastGame = new Game();
                new ComputerPlayer(game).takeTurn();
                return htmlForBoard(game);
            }
        };

        new GetRequest("/:board") {
            public String controller() {
                Game game = lastGame = new Game(getParam("board"));
                ComputerPlayer comp = new ComputerPlayer(game);
                if(!game.isOver()) comp.takeTurn();
                return htmlForBoard(game);
            }
        };
    }
}
