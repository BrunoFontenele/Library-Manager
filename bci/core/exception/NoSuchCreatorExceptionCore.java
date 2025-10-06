package bci.core.exception;

import pt.tecnico.uilib.menus.CommandException;

public class NoSuchCreatorExceptionCore extends CommandException {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public NoSuchCreatorExceptionCore(String id) {
        super("O criador '" + id + "' não existe.");
    }
}
