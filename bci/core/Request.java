package bci.core;

class Request {
    private int _userId;
    private int _workId;
    private int _endOfRequest;

    Request(int userId, int workId, int endOfRequest){
        _userId = userId;
        _workId = workId;
        _endOfRequest = endOfRequest;
    }

    int get_userId() {
        return _userId;
    }

    int get_workId() {
        return _workId;
    }

    int get_endOfRequest() {
        return _endOfRequest;
    }
    

}
