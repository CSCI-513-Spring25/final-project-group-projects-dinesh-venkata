
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

    public App() throws IOException {
        super(8080);
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
            this.game = this.game.play(Integer.parseInt(params.get("keyEvent")));
        } else if(uri.equals("/createObject")){            
            this.game = this.game.createObject(Integer.parseInt(params.get("index")),params.get("type").charAt(0));
        }
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
    }   

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
}