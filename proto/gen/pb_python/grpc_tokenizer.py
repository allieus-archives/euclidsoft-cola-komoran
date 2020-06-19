#!/usr/bin/env python
# -*- coding: utf-8 -*-

from grpc import insecure_channel
from .kr.re.keit.Komoran_pb2_grpc import KomoranStub
from .kr.re.keit.Komoran_pb2 import TokenizeRequest

class GrpcTokenizer:
    def __init__(self, target):
        channel = insecure_channel(target)
        self.stub = KomoranStub(channel)

    def __call__(self, sentence):
        request = TokenizeRequest(sentence=sentence)
        response = self.stub.tokenize(request)
        keyword_list = response.keyword
        return keyword_list

__call__ = ["GrpcTokenizer"]
