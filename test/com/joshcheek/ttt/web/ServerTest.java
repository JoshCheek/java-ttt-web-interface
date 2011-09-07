package com.joshcheek.ttt.web;

import com.joshcheek.server.HTTPInteraction;
import com.joshcheek.ttt.library.Game;

import java.io.IOException;

import static com.joshcheek.ttt.web.TestHelpers.*;

/**
 * Created by IntelliJ IDEA.
 * User: joshuajcheek
 * Date: 9/7/11
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerTest extends junit.framework.TestCase {
    private String response;
    private Game game;

    // given i go to the homepage then i should be prompted to go first or second
    public void testGivenIGoToTheHomepageThenIAmPromptedToGoFirstOrSecond() throws IOException {
        visit("/");
        assertContains(response, "<a href='first'>first</a>");
        assertContains(response, "<a href='second'>second</a>");
    }

    public void testWhenIgoFirstIAmPromptedWithAnEmptyBoard() throws IOException {
        visit("/first");
        assertEquals("000000000", game.board());
        assertContains(response, "<div id='game'>");
    }

    public void testWhenIgoSecondIAmPromptedWithABoardThatPlayer1HasMovedOn() throws IOException {
        visit("/second");
        int move = game.board().indexOf('1');
        assertTrue(-1 != move);
        String boardWithoutMove = game.board().substring(0, move) + game.board().substring(move+1);
        assertEquals("00000000", boardWithoutMove);
        assertContains(response, "<div id='game'>");
    }

//    public void testWhenIHave100000000

        // given i have a board with one x in the upper left corner, when i render the board i should see a game with no link in the upper left corner

        // given i have a board with nothing clicked and i click a square, it should be filled in and so should the computer's move

        // given the computer wins the game then the game should be over

        // given the game is over then there should be nowhere for me to move and there should be a link to play again and when i click it i should be taken to the homepage

    private void visit(String uri) throws IOException {
        String request = "GET " + uri + " HTTP/1.1\r\n\r\n";
        TTTServer server = getServer();
        HTTPInteraction interaction = getInteractionFor(request);
        server.respondTo(interaction.requestMethod(), interaction.requestUri(), interaction);
        game = server.getLastGame();
        response = interaction.getContent();
    }

}
