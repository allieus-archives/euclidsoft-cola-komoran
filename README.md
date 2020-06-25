# Grpc Tokenizer

## Komoran 라이브러리

파이썬의 `konlpy`는 JVM을 임베딩하여 사용되는 형태이기에, 멀티 프로세싱 방식에서는 konlpy의 Komoran 객체를 공유하기 힘듭니다. 파이썬 코드에 Komoran 모듈에 대한 `병렬성을 부여`하기 위해서, gRPC를 지원하는 Java 서버를 개발했습니다. 이제 JVM 의존성이 없고 가벼운 객체들로 구성이 되므로, 좀 더 수월하게 `멀티 프로세싱`이 가능해지게 되었습니다.

> 참고 : gRPC는 구글에서 개발한 오픈소스 "원격 프로시저 호출 시스템"이며, 이 기종 간의 API 호출을 보다 높은 성능으로 호출할 수 있는 방법입니다. Java, Python, Node.js 등의 언어를 지원합니다.

### 성능

아래와 같이 Multi Workers를 통해 summarize 처리가 진행되고 있습니다.

- 샘플 : 100개 행, 행별 200개 리스트 문자열
    1. 파이썬 komoran을 적용하지 않고, "문자열.split()"과 textrank만 적용한 Linear한 처리
        - 5개행 : 10.3초
        - 10개행 : 19.7초
        - 20개행 : 39.8초
    2. 파이썬 komoan라이브러리를 쓰고, Linear한 처리
        - 5개행 : 41.8초 ⇒ 1번 경우 대비 4배의 시간
        - 10개행 : 80초
        - 20개행 : 157초
    3. Ray 및 gRPC 코드 적용 ⇒ 1번 경우보다 나은 성능을 보이며, 2번 경우보다 **최소 10배 이상의 속도**를 보이고 있습니다. ⇒ 12코어 머신. 멀티 Worker 수 만큼 병렬처리 성능 증가. 증가된 Worker로 인한 메모리 사용률 증가는 거의 없지만, CPU는 100% 사용.
        - 5개행 : 4.56초 ⇒ 2번 경우 대비 9.6배의 성능
        - 10개행 : 4.69초 ⇒ 17배의 성능
        - 20개행 : 9.48초 ⇒ 17배의 성능
        - 100개행 : 44.6초

### 장점

1. Multi Workers를 늘린다고 해서, 그 Worker만큼 Komoran 객체를 늘릴 필요가 없습니다. 자바 gRPC 서버의 단일 Komoran 객체를 활용합니다.
2. 원하는 만큼의 Multi Workers를 가볍게 늘릴 수 있습니다.
3. 머신의 CPU 100%를 뽑아냅니다.

## 실행 예시

### Docker로 구동한 서버

현 userdic.txt에서는 최소 4G 이상의 메모리 할당이 필요합니다. userdic.txt 에 따라 더 큰 메모리 할당이 필요할 수도 있습니다. 현 userdic.txt 파일에서는 초기화에 약 10초 정도 소요됩니다. 메모리 할당에 실패하면 `Exception in thread "main" java.lang.OutOfMemoryError: Java heap space` 예외가 발생합니다. 필히 로그를 확인해주세요.

아래 예시는 docker를 활용한 예시이며, docker swarm이나 k8s 활용을 추천합니다.

```bash
docker build -t cola-komoran-test .

docker run -d \
    --restart=always \
    -p 50051:50051 \
    --memory 4G \
    --name cola-komoran \
    cola-komoran-test 
```

사용되는 cpu 갯수 제한을 걸고자 한다면, `--cpus 6` 식으로 위 명령에 갯수를 지정해주세요.

이를 줄여서 `deploy.sh` 스크립트를 만들었습니다.


### 클라이언트

[cola-komoran-python-client](https://github.com/euclidsoft/cola-komoran-python-client)를 설치해주시고, [cola\_komoran\_python\_client/\_\_init\_\_.py](https://github.com/euclidsoft/cola-komoran-python-client/blob/master/cola_komoran_python_client/__init__.py)코드를 차근차근 살펴보시기를 권장드립니다.

```python
from grpc import insecure_channel
from cola_komoran_python_client.kr.re.keit.Komoran_pb2_grpc import KomoranStub
from cola_komoran_python_client.kr.re.keit.Komoran_pb2 import TokenizeRequest

class GrpcTokenizer:
    def __init__(self, target, dic_type=0):
        channel = insecure_channel(target)
        self.stub = KomoranStub(channel)
        self.dic_type = dic_type

    def __call__(self, sentence):
        request = TokenizeRequest(dicType=self.dic_type, sentence=sentence)
        response = self.stub.tokenize(request)
        keyword_list = response.keyword
        return keyword_list
```

```python
sentence = """
    워크에 대해 권선 동작을 수행하기 위한 방법 및 그 장치가 개시되는데,
    클램프에 의해 그 단부가 파지 되는 와이어는 훅의 의해 걸리고(hooked),
    상기 걸려진 와이어는 워크에 제공된 구멍으로 삽입되며, 그 다음 클램프는
    와이어가 훅을 끌어 당길 때 훅과는 반대 위치로 이동되고, 와이어의 단부를
    파지하는 클램프는 워크에 대해 선회되어 워크에 와이어를 감는다. 따라서,
    상기 구멍의 에지와 와이어 사이의 접 촉 압력은 제거된다.
"""
tokenize = GrpcTokenizer("localhost:50051")
print(tokenize(sentence))
# ['워크', '대해', '권선', '동작', '수행', '하기', '위한', '방법', '장치가', '개시', '클램프', '의해', '단부', '파지', '와이어', '훅', '의해', '걸리', '상기', '와이어', '워크', '제공', '구멍', '삽입', '다음', '클램프', '와이어', '당기', '훅', '반대', '위치', '이동', '와이어', '단부', '파지', '클램프', '워크', '대해', '선회', '워크', '와이어', '상기', '구멍', '에지', '와 와', '이어', '사이', '접', '압력', '제거']
```


## 코드

### proto 파일 생성

proto/kr/re/keit/Komoran.proto 파일

### protoc를 통해 소스코드 생성

예시일 뿐, `proto/build.sh` 명령을 통해 수행해주세요.

```sh
docker run --rm \
    -v `pwd`:/defs namely/protoc-all \
    -f ./kr/re/keit/Komoran.proto \
    -l java
```

생성한 소스파일 `proto/gen/` 디렉토리에 위치해있으며, 각 언어 별로 복사해서 쓰시면 됩니다.

소스 파일은 수정하지 않고 쓰시되, import 경로명은 수정이 필요할 수도 있습니다.

### Grpc Server 샘플

`src/main/java/kr/re/keit/Main.java` 참고

