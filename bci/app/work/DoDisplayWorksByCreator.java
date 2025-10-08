package bci.app.work;

import bci.core.LibraryManager;
import bci.core.exception.NoSuchCreatorExceptionCore;
import bci.app.exception.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;

/**
 * Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {
  DoDisplayWorksByCreator(LibraryManager receiver) {
    super(Label.SHOW_WORKS_BY_CREATOR, receiver);
    addStringField("name", Prompt.creatorId());
  }

  @Override
  protected final void execute() throws NoSuchCreatorException {
    String name = stringField("name");
    try {
      _display.popup(_receiver.listWorksByCreators(name));
    } catch (NoSuchCreatorExceptionCore e) {
      throw new NoSuchCreatorException(name);
    }
    
  }
}
