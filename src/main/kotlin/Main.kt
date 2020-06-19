import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Logger
import spark.Spark.*
import java.lang.reflect.Type


// https://www.eclipse.org/jetty/documentation/current/high-load.html
// https://www.eclipse.org/jetty/documentation/current/optimizing.html

val port = System.getenv("port")?.toInt() ?: 80
val maxThreads = System.getenv("MAX_THREADS")?.toInt() ?: 64
val minThreads = System.getenv("Min_THREADS")?.toInt() ?: 2
val idleTimeoutMills = System.getenv("IDLE_TIMEOUT_MILLS")?.toInt() ?: 30 * 1000

fun main(args: Array<String>) {
    // BasicConfigurator.configure()

    val logger = Logger.getLogger("KomoranApi")
    logger.info("port : $port")
    logger.info("maxThreads : $maxThreads")
    logger.info("minThreads : $minThreads")
    logger.info("idleTimeoutMills : $idleTimeoutMills")

    port(port)
    threadPool(maxThreads, minThreads, idleTimeoutMills)

    val komoran = Komoran(DEFAULT_MODEL.LIGHT)

    // Docker 내에서는 OutOfMemory 예외가 발생할 수 있습니다.
    val userDir = System.getProperty("user.dir")
    komoran.setUserDic("$userDir/userdic.txt")

    val type: Type = object : TypeToken<Map<String?, String?>?>() {}.type

    post("/tokenize", { request, response ->
        response.type("application/json")
        val postMap: Map<String, String> = Gson().fromJson(request.body(), type)
        val sentence = postMap["sentence"] ?: ""
        komoran.analyze(sentence).nouns
    }, Gson()::toJson)
}