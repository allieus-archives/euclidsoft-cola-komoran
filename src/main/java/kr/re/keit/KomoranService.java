package kr.re.keit;

import io.grpc.stub.StreamObserver;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.re.keit.KomoranOuterClass.TokenizeRequest;
import kr.re.keit.KomoranOuterClass.TokenizeResponse;

public class KomoranService extends KomoranGrpc.KomoranImplBase {
    final Komoran komoran_default;
    final Komoran komoran_overall;
    final Komoran komoran_minimal;

    KomoranService() {
        this.komoran_default = initKomoranUserDic("userdic.txt");
        this.komoran_overall = initKomoranUserDic("userdic-overall.txt");
        this.komoran_minimal = initKomoranUserDic("userdic-minimal.txt");
    }

    Komoran initKomoranUserDic(String userDicName) {
        var userDir = System.getProperty("user.dir");

        System.out.println(String.format("Loading %s ...", userDicName));
        var komoran = new Komoran(DEFAULT_MODEL.FULL);
        komoran.setUserDic(userDir + "/" + userDicName);

        return komoran;
    }

    @Override
    public void tokenize(TokenizeRequest request, StreamObserver<TokenizeResponse> responseObserver) {
        var komoran = this.getKomoran(request.getDicType());
        var nouns = komoran.analyze(request.getSentence()).getNouns();
        var response = TokenizeResponse.newBuilder()
            .addAllKeyword(nouns).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    Komoran getKomoran(TokenizeRequest.DicType dicType) {
        if ( dicType == TokenizeRequest.DicType.OVERALL )
            return this.komoran_overall;
        else if ( dicType == TokenizeRequest.DicType.MINIMAL )
            return this.komoran_minimal;
        return this.komoran_default;
    }
}
