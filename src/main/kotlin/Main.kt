import com.google.gson.Gson
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import org.apache.log4j.BasicConfigurator
import spark.Spark.post

fun main(args: Array<String>) {
    BasicConfigurator.configure();

    val komoran = Komoran(DEFAULT_MODEL.LIGHT)

    val userDir = System.getProperty("user.dir")
     komoran.setUserDic("$userDir/userdic.txt")

    post("/summarize", { request, response ->
        response.type("application/json")
        val sentenceList = Gson().fromJson(request.body(), Array<String>::class.java)
        val data = arrayOf("hello", "world", "python", "django")
        data
    }, Gson()::toJson)
}
