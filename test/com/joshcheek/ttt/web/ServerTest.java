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

    public void testTheBoard100000000RendersWithNoLinkInUpperLeft() throws IOException {
        Game game = new Game("100000000");
        String rendered = TTTServer.htmlForBoard(game);
        assertDoesntContain(rendered, "href='/200000000'");
        assertContains(rendered, "href='/120000000'");
        assertContains(rendered, "href='/102000000'");
        assertContains(rendered, "href='/100200000'");
        assertContains(rendered, "href='/100020000'");
        assertContains(rendered, "href='/100002000'");
        assertContains(rendered, "href='/100000200'");
        assertContains(rendered, "href='/100000020'");
        assertContains(rendered, "href='/100000002'");

    }

    public void testGivenIHaveABoardWithNothingClickedWhenIClickASquareThenItShouldBeFilledInAndSoShouldTheComputersMove() throws IOException {
        visit("/100000000");
        assertEquals("100020000", game.board());
        assertContains(response, "href='/110020000'");
        assertContains(response, "href='/101020000'");
        assertContains(response, "href='/100120000'");
        assertContains(response, "href='/100021000'");
        assertContains(response, "href='/100020100'");
        assertContains(response, "href='/100020010'");
        assertContains(response, "href='/100020001'");
        assertDoesntContain(response, "href='/100010000'");
        assertDoesntContain(response, "href='/100020000'");
    }

    public void testCompletedGamesRenderWithNoMoves() {
        assertNoMovesFor("111220000"); // player1 wins
        assertNoMovesFor("112221210"); // player2 wins
        assertNoMovesFor("112221121"); // tie game
    }

    public void testWhenGameIsOverThereIsALinkBackToTheHompage() throws IOException {
        visit("/112221121");
        assertDoesntContain(response, "href='/'");
    }



    private void visit(String uri) throws IOException {
        String request = "GET " + uri + " HTTP/1.1\r\n\r\n";
        TTTServer server = getServer();
        HTTPInteraction interaction = getInteractionFor(request);
        server.respondTo(interaction.requestMethod(), interaction.requestUri(), interaction);
        game = server.getLastGame();
        response = interaction.getContent();
    }

    private void assertNoMovesFor(String board) {
        Game game = new Game(board);
        String rendered = TTTServer.htmlForBoard(game);
        assertDoesntContain(rendered, "href");
    }


}
