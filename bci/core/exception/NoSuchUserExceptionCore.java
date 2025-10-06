package bci.core.exception;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Class encoding reference to an invalid user id.
 */
public class NoSuchUserExceptionCore extends CommandException {

  @java.io.Serial
  private static final long serialVersionUID = 202501091828L;

  /**
   * @param id unknown user id
   */
  public NoSuchUserExceptionCore(int idUser) {
    super("O utente " + idUser + " não existe.");
  }
}
