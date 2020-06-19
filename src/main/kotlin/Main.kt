import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.re.keit.KomoranGrpc
import kr.re.keit.KomoranOuterClass.TokenizeRequest
import kr.re.keit.KomoranOuterClass.TokenizeResponse


val port = System.getenv("port")?.toInt() ?: 50051

class KomoranService : KomoranGrpc.KomoranImplBase() {
    val komoran: Komoran = Komoran(DEFAULT_MODEL.LIGHT)

    init {
        // Docker 내에서는 OutOfMemory 예외가 발생할 수 있습니다.
        // val userDir = System.getProperty("user.dir")
        // this.komoran.setUserDic("$userDir/userdic.txt")
    }

    override fun tokenize(
        request: TokenizeRequest,
        responseObserver: StreamObserver<TokenizeResponse>
    ) {
        val nouns = this.komoran.analyze(request.sentence).nouns
        val response = TokenizeResponse.newBuilder()
            .addAllKeyword(nouns).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}

fun main(args: Array<String>) {
    val server = ServerBuilder.forPort(port)
        .addService(KomoranService())
        .build()
    server.start().awaitTermination()
}
