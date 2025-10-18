package bci.core.exception;

import bci.core.exception.CoreRuleException;

public class NotEnoughInventoryExceptionCore extends CoreRuleException {

    private static final long serialVersionUID = 20251009L;

    public NotEnoughInventoryExceptionCore() {
        super();
    }

    public NotEnoughInventoryExceptionCore(String message) {
        super(message);
    }
}
