


import java.io.IOException;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        //this.game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.game = new Game();
        } else if (uri.equals("/play")) {
            // e.g., /play?x=1&y=1
            // System.out.println("Player entered key");
            this.game = this.game.play(Integer.parseInt(params.get("keyEvent")));
        }else if(uri.equals("/createObject")){
            System.out.println("Called Create object " +params.get("index")+" type: "+params.get("type"));
            this.game = this.game.createObject(Integer.parseInt(params.get("index")),params.get("type").charAt(0));
        }
        else if(uri.equals("/undo")){
            // this.game=this.game.undo();
            // System.out.println("Calling undo");            
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        //System.out.println(gameplay);
        return newFixedLengthResponse(gameplay.toString());
    }   

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
}