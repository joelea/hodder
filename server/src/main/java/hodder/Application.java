package hodder;

import static spark.Spark.get;
import static spark.Spark.post;

public class Application {
    private static final String TOPIC = "example-topic";

    public static void main(String[] args) throws InterruptedException {
        Counts counts = Counts.createDefault();
        Events events = new Events(TOPIC);

        get("/count", (request, response) -> counts.get(1) );
        post("/increment", (request, response) -> events.write(new Increment()) );

        System.out.println("Hodder is flowing strong!");
    }

}
