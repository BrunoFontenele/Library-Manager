package bci.core;

class Request {
    private int _userId;
    private int _workId;
    private int _requestid;
    private int _endOfRequest;

    Request(int userId, int workId, int endOfRequest, int requestId){
        _userId = userId;
        _workId = workId;
        _requestid = requestId;
        _endOfRequest = endOfRequest;
    }

    int get_userId() {
        return _userId;
    }

    int get_workId() {
        return _workId;
    }

    int get_requestid() {
        return _requestid;
    }

    int get_endOfRequest() {
        return _endOfRequest;
    }
    

}
