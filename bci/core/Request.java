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

    int getUserId() {
        return _userId;
    }

    int getWorkId() {
        return _workId;
    }

    int getEndOfRequest() {
        return _endOfRequest;
    }
    

}
