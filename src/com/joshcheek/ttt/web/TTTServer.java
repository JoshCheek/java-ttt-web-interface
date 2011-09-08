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
        if(args.length == 0) args = new String[]{"8084"};
        TTTServer server = new TTTServer(Integer.parseInt(args[0]));
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
                return htmlLayout(newGameLinks());
            }
        };

        new GetRequest("/first") {
            public String controller() {
                return htmlLayout(htmlForBoard(lastGame = new Game()));
            }
        };

        new GetRequest("/second") {
            public String controller() {
                Game game = lastGame = new Game();
                new ComputerPlayer(game).takeTurn();
                return htmlLayout(htmlForBoard(game));
            }
        };

        new GetRequest("/styles.css") {
            public String controller() {
                return  getCSS();
            }
        };

        new GetRequest("/:board") {
            public String controller() {
                Game game = lastGame = new Game(getParam("board"));
                ComputerPlayer comp = new ComputerPlayer(game);
                if(!game.isOver()) comp.takeTurn();
                String content = htmlForBoard(game);
                if(game.isOver()) content += newGameLinks();
                return htmlLayout(content);
            }
        };

    }

    public String newGameLinks() {
        return "<div id='new-game'>Go <a href='first'>first</a> or <a href='second'>second</a>.</div>";
    }

    public static String htmlLayout(String content) {
        return  "<html lang='en' xml:lang='en' xmlns='http://www.w3.org/1999/xhtml'>" +
                "  <head>" +
                "    <title>Tic Tac Toe</title>" +
                "    <meta content='text/html; charset=utf-8' http-equiv='Content-Type' />" +
                "    <link href='/styles.css' media='all' rel='stylesheet' type='text/css' />" +
                "  </head>" +
                "  <body> " +
                     content +
                "  </body>" +
                "</html>";
    }

    public static String htmlForBoard(Game game) {
        return  "<div id='game'>" +
                    "<div id='row1'>"+
                        htmlForCell(game, 1)+
                        htmlForCell(game, 2)+
                        htmlForCell(game, 3)+
                    "</div><div id='row2'>" +
                        htmlForCell(game, 4)+
                        htmlForCell(game, 5)+
                        htmlForCell(game, 6)+
                    "</div><div id='row3'>" +
                        htmlForCell(game, 7)+
                        htmlForCell(game, 8)+
                        htmlForCell(game, 9)+
                    "</div>" +
                "</div>";
    }

    public static String getCSS() {
        return "body {\n" +
                "  background-color: gray;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "#new-game { \n" +
                "  margin: 100px;\n" +
                "  font-size: 25px;\n" +
                "}\n" +
                "a { color: black; }\n" +
                "#game {\n" +
                "  width: 500px;\n" +
                "  margin: 100px auto 0px auto;\n" +
                "  height: 450px;\n" +
                "}\n" +
                "#cell1, #cell2, #cell3, #cell4, #cell5, #cell6, #cell7, #cell8, #cell9 { \n" +
                "  height: 150px; \n" +
                "  width: 150px; \n" +
                "  position: relative;\n" +
                "} \n" +
                "#game span.moved, #game a {\n" +
                "  color: black;\n" +
                "  position: absolute;\n" +
                "  top: 1em;\n" +
                "  left: 1em;\n" +
                "  font-size: 50px;\n" +
                "  font-family: sans-serif;\n" +
                "  font-weight: bold;\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "#game span.X { color: red; }\n" +
                "#game span.O { color: blue; }\n" +
                "#cell1, #cell2, #cell3 { border-right: 5px solid black; }\n" +
                "#cell7, #cell8, #cell9 { border-left: 5px solid black; }\n" +
                "#cell1, #cell4, #cell7 { border-bottom: 5px solid black; }\n" +
                "#cell3, #cell6, #cell9 { border-top: 5px solid black; }\n" +
                "#row1, #row2, #row3 { height: 150px; float: left; }";
    }

    private static String htmlForCell(Game game, int cell) {
        if (game.isAvailable(cell))
            return "<div id='cell" + cell + "'><a href='/" + game.pristineMove(cell).board() + "'>:)</a></div>";
        String letter = letterFor(game, cell);
        return "<div id='cell" + cell + "'><span class='moved " + letter + "'>" + letter + "</span></div>";
    }

    private static String letterFor(Game game, int cell) {
        return  game.playerAt(cell) == 1 ? "X" :
                game.playerAt(cell) == 2 ? "O" :
                                           " " ;
    }

}
