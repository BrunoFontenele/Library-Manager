package bci.app.main;

import bci.core.LibraryManager;
import bci.core.exception.MissingFileAssociationException; 
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
// FIXME add more imports if needed

import java.io.IOException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<LibraryManager> {

  DoSaveFile(LibraryManager receiver) {
    super(Label.SAVE_FILE, receiver);
    if(stringField("fileName") == null)
      addStringField("fileName", Prompt.newSaveAs());
  }

  @Override
  protected final void execute() {
    String fileName = stringField("fileName");
    _receiver.saveAs(fileName);
    //exceptions?

    

  }
}
