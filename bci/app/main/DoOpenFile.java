package bci.app.main;

import bci.core.LibraryManager;

import java.io.IOException;

import bci.app.exception.FileOpenFailedException;
import bci.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoOpenFile extends Command<LibraryManager> {

  DoOpenFile(LibraryManager receiver) {
    super(Label.OPEN_FILE, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      if (_receiver.isModified()) {
        if (Form.confirm(Prompt.saveBeforeExit())) {
          try {
            _receiver.save();
          } catch (UnavailableFileException | IOException ioe){
            throw new FileOpenFailedException(ioe);
          }
        }
      }
      String fileName = Form.requestString(Prompt.openFile());
      _receiver.load(fileName);
    } catch (UnavailableFileException efe) {
      throw new FileOpenFailedException(efe);
    }
  }
}
