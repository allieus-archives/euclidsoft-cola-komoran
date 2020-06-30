package kr.re.keit;

import io.grpc.stub.StreamObserver;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.re.keit.KomoranOuterClass.TokenizeRequest;
import kr.re.keit.KomoranOuterClass.TokenizeResponse;

import java.util.ArrayList;

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
        var builder = TokenizeResponse.newBuilder();
        TokenizeResponse response;

        try {
            var komoran = this.getKomoran(request.getDicType());
            var nouns = komoran.analyze(request.getSentence()).getNouns();
            response = builder.addAllKeyword(nouns).build();
        }
        catch (Exception e) {
            var nouns = new ArrayList<String>();
            response = builder.addAllKeyword(nouns).build();

            e.printStackTrace();
        }

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
