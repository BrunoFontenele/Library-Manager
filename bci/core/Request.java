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


}
