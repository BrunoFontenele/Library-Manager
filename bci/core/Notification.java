package bci.core;

class Notification {
    private final String _message;
    private final NotificationType _type;
    private final int _workId;

    Notification(NotificationType type, int workId, String message) {
        _type = type;
        _workId = workId;
        _message = message;
    }

    @Override
    public String toString() {
        return _type + ": " + _message;
    }
}

