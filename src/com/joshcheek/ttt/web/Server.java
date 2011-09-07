package com.joshcheek.ttt.web;

import com.joshcheek.server.webFramework.WebFramework;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: joshuajcheek
 * Date: 9/7/11
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Server extends WebFramework {

    public static void main(String[] args) throws IOException {
        Server server = new Server(8080);
        server.startRunning();
    }

    public Server(int port) {
        super(port);
    }

    public void defineRoutes() {
        new GetRequest("/") {
            public String controller() {
                return "Go <a href='first'>first</a> or <a href='second'>second</a>.";
            }
        };
    }
}
