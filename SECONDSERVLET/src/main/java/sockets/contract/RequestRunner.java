package sockets.contract;

import sockets.pojos.HttpRequest;
import sockets.pojos.HttpResponse;

public interface RequestRunner {

    HttpResponse run(HttpRequest request);
}
