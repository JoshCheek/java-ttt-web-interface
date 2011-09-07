package com.joshcheek.ttt.web;

import com.joshcheek.server.HTTPInteraction;

import java.io.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: joshuajcheek
 * Date: 9/7/11
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestHelpers {
    public static final int DEFAULT_PORT = 1234;

    public static HTTPInteraction getInteractionFor(String request) throws IOException {
        return new HTTPInteraction(mockReader(request), mockWriter());
    }

    public static BufferedReader mockReader(String request) {
        return new BufferedReader(new StringReader(request));
    }

    public static PrintStream mockWriter() {
        return new PrintStream(new ByteArrayOutputStream());
    }

    public static void assertContains(String haystack, String needle) {
        assertTrue("Expected \"" + haystack + "\" to contain \"" + needle + "\"",
                haystack.contains(needle));
    }

    public static TTTServer getServer() {
        return new TTTServer(DEFAULT_PORT);
    }

}
