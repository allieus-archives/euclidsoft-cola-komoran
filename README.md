# Grpc Tokenizer

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

### 클라이언트

```python
from grpc import insecure_channel
from kr.re.keit.Komoran_pb2_grpc import KomoranStub
from kr.re.keit.Komoran_pb2 import TokenizeRequest

class GrpcTokenizer:
    def __init__(self, target):
        channel = insecure_channel(target)
        self.stub = KomoranStub(channel)

    def __call__(self, sentence):
        request = TokenizeRequest(sentence=sentence)
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

`Main.kt` 참고
