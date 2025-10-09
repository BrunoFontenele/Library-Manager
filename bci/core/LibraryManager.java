package bci.core;

import bci.core.exception.*;
import java.io.*;
import java.util.Comparator;

public class LibraryManager {

  private Library _library;
  private String _filename;  
  private boolean _modified;

  public LibraryManager() {
      _library = new Library();
      _filename = null;
      _modified = false;
  }

    // SERIALIZAÇÃO
/**
 * Saves the library to the current file.
 *
 * @throws UnavailableFileException If no file name is set.
 * @throws IOException If an error occurs while writing the file.
 */
public void save() throws UnavailableFileException, IOException {
    if (_filename == null || _filename.isBlank()) {
        throw new UnavailableFileException(_filename);
    }

    if (!_modified) return;

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename))) {
        out.writeObject(_library);
        _modified = false;
    }
}

/**
 * Saves the library to a new file.
 *
 * @param filename The name of the file to save to.
 * @throws IOException If an error occurs while writing the file.
 */
public void saveAs(String filename) throws IOException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
        out.writeObject(_library);
        _filename = filename;
        _modified = false;
    }
}


 /**
 * Loads the library from a file.
 *
 * @param filename The file to load the library from.
 * @throws UnavailableFileException If the file cannot be read or is not a valid library.
 */
  public void load(String filename) throws UnavailableFileException {
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
          Object obj = in.readObject();
          if (!(obj instanceof Library)) 
              throw new UnavailableFileException(filename);
          _library = (Library) obj;
          _filename = filename;
          _modified = false;
      } catch (IOException | ClassNotFoundException e) {
          throw new UnavailableFileException(filename);
      }
  }

  // IMPORTAÇÃO DE FICHEIROS DE TEXTO
  public void importFile(String datafile) throws ImportFileException {
      try {
          if (datafile != null && !datafile.isEmpty()) {
              _library.importFile(datafile);
              _modified = true;
          }
      } catch (IOException | UnrecognizedEntryException e) {
          throw new ImportFileException(datafile, e);
      }
  }

  // MÉTODOS DE GESTÃO

  public int getCurrentDay() { return _library.getCurrentDay(); }

  public void advanceDays(int days) { 
      _library.advanceDays(days); 
      _modified = true;
  }

  public void alterInvWork(int number, int workId) { 
      _library.alterInvWork(number, workId); 
      _modified = true;
  }

  public String listWork(int workId) throws NoSuchWorkExceptionCore { 
      return _library.listWork(workId); 
  }

  public String listWorks() { 
      return _library.listWorks(); 
  }

  public String listWorksByCreators(String name) throws NoSuchCreatorExceptionCore { 
      return _library.listWorksByCreators(name); 
  }

  public int registerUser(String name, String email) { 
      _modified = true;
      return _library.registerUser(name, email); 
  }

  public String listUser(int userId) throws NoSuchUserExceptionCore { 
      return _library.listUser(userId); 
  }

  public String listUsers() { 
      return _library.listUsers(); 
  }

  public void registerDvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type) {
      _library.registerDvd(igac, creator, title, price, numberOfCopies, type);
      _modified = true;
  }

  public void registerBook(String isbn, int price, String title, int numberOfCopies, 
                            java.util.List<Creator> creators, Category type) {
      _library.registerBook(isbn, price, title, numberOfCopies, creators, type);
      _modified = true;
  }

  public Creator registerCreator(String name) {
      _modified = true;
      return _library.registerCreator(name);
  }

  public boolean isModified(){
    return _modified;
  }

}
