package hodder;

import static spark.Spark.get;

public class Application {
    public static void main(String[] args) {
        get("/count", (request, response) -> {
            return "hello";
        });
    }
}