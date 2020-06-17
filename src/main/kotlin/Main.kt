import org.apache.log4j.BasicConfigurator
import spark.Spark.*;

fun main(args: Array<String>) {
    BasicConfigurator.configure();

    get("/hello") { req, res ->
        "Hello World"
    }
}
