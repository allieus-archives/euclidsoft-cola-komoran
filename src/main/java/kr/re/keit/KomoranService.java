package kr.re.keit;

import io.grpc.stub.StreamObserver;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.re.keit.KomoranOuterClass.TokenizeRequest;
import kr.re.keit.KomoranOuterClass.TokenizeResponse;

import java.util.List;

public class KomoranService extends KomoranGrpc.KomoranImplBase {
    final Komoran komoran;

    KomoranService() {
        var userDir = System.getProperty("user.dir");
        this.komoran = new Komoran(DEFAULT_MODEL.FULL);
        this.komoran.setUserDic(userDir + "/userdic.txt");
    }

    @Override
    public void tokenize(TokenizeRequest request, StreamObserver<TokenizeResponse> responseObserver) {
        var nouns = this.komoran.analyze(request.getSentence()).getNouns();
        var response = TokenizeResponse.newBuilder()
            .addAllKeyword(nouns).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
