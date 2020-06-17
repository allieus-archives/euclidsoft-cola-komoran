import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import org.apache.log4j.BasicConfigurator
import spark.Spark.post
import java.lang.reflect.Type


fun main(args: Array<String>) {
    BasicConfigurator.configure();

    val komoran = Komoran(DEFAULT_MODEL.LIGHT)

    // FIXME: 메모리 이슈로 임시 주석 처리
    // val userDir = System.getProperty("user.dir")
    // komoran.setUserDic("$userDir/userdic.txt")

    val type: Type = object : TypeToken<Map<String?, String?>?>() {}.type

    post("/tokenize", { request, response ->
        response.type("application/json")
        val postMap: Map<String, String> = Gson().fromJson(request.body(), type)
        val sentence = postMap["sentence"] ?: ""
        komoran.analyze(sentence).nouns
    }, Gson()::toJson)
}