package bci.app.request;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.NoSuchWorkException;
import bci.app.exception.WorkNotBorrowedByUserException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

  DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    addIntegerField("userID", bci.app.user.Prompt.userId());
    addIntegerField("workID", bci.app.work.Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException, NoSuchUserException, NoSuchWorkException {
    int userId = integerField("userID");
    int workId = integerField("workID");

    if(!_receiver.validUser(userId)) throw new NoSuchUserException(userId);
    if(!_receiver.validWork(workId)) throw new NoSuchWorkException(workId);

    if(!_receiver.returnWork(userId, workId))
      throw new WorkNotBorrowedByUserException();
    if()
  }
}
