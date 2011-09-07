package com.joshcheek.ttt.web;

import com.joshcheek.server.HTTPInteraction;

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

    // given i go to the homepage then i should be prompted to go first or second
    public void testGivenIGoToTheHomepageThenIShouldBePromptedToGoFirstOrSecond() throws IOException {
        visit("/");
        assertContains(response, "<a href='first'>first</a>");
        assertContains(response, "<a href='second'>second</a>");
    }

    // given i have been prompted to go first or second when i click first then i should be presented with an empty board

        // given i have been prompted to go first or second when i click second i should be presented with a board that has 1 x on it

        // given i have a board with one x in the upper left corner, when i render the board i should see a game with no link in the upper left corner

        // given i have a board with nothing clicked and i click a square, it should be filled in and so should the computer's move

        // given the computer wins the game then the game should be over

        // given the game is over then there should be nowhere for me to move and there should be a link to play again and when i click it i should be taken to the homepage

    private void visit(String uri) throws IOException {
        String request = "GET " + uri + " HTTP/1.1\r\n\r\n";
        Server server = getServer();
        HTTPInteraction interaction = getInteractionFor(request);
        server.respondTo(interaction.requestMethod(), interaction.requestUri(), interaction);
        response = interaction.getContent();
    }

}
