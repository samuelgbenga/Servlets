import sockets.Server;
import sockets.pojos.HttpResponse;

import static sockets.contract.HttpMethod.GET;

public class App {


    public static void main(String[] args) throws Exception {
        Server myServer = new Server(8080);
        myServer.addRoute(GET, "/",
                (req) -> new HttpResponse.Builder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "text/html")
                        .setEntity("<HTML> <P> first page... </P> </HTML>")
                        .build());
        myServer.addRoute(GET, "/testing",
                (req) -> new HttpResponse.Builder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "text/html")
                        .setEntity("<HTML> <P> second page... </P> </HTML>")
                        .build());
        myServer.start();
    }
}
