package bci.core;

import java.io.*;

import bci.core.exception.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;


public class Library implements Serializable {
  private List<Work> _listOfWorks;
  private Set<Creator> _setOfCreators;
  private List<User> _listOfUsers;
  private int _currentDay;
  private int _nextWorkId;
  private int _nextUserId;

  /** Serial number for serialization. */
  @Serial
  private static final long serialVersionUID = 202501101348L;

  /**
   * Read text input file at the beginning of the program and populates the
   * the state of this library with the domain entities represented in the text file.
   * 
   * @param filename name of the text input file to process
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    MyParser parser = new MyParser(this);
    parser.parseFile(filename);
  }

  /**
 * Constructs a new Library instance.
 * Initializes the lists of creators, works, and users, and sets the initial values for user ID, work ID, and current day.
 */
  Library(){
    _setOfCreators = new LinkedHashSet<>();
    _listOfWorks = new ArrayList<>();
    _listOfUsers = new ArrayList<>();
    _nextUserId = _nextWorkId = _currentDay = 1;
  }

  //getters


  /**
   * Returns the list of works in the library.
   *
   * @return a {@link List} of {@link Work} objects representing all works in the library
   */

  List<Work> getListOfWorks() {return Collections.unmodifiableList(_listOfWorks);}

  /**
   * Returns the list of creators registered in the library.
   *
   * @return a {@link List} of {@link Creator} objects representing all creators in the library
   */

  List<Creator> getListOfCreators() {
    return Collections.unmodifiableList(new ArrayList<>(_setOfCreators));
  }

  /**
   * Returns the list of users registered in the library.
   *
   * @return a {@link List} of {@link User} objects representing all users in the library
   */

  List<User> getListOfUsers(){return Collections.unmodifiableList(_listOfUsers);}

  //work
/**
 * Returns a string representation of the work with the given ID.
 *
 * @param workId the unique identifier of the work
 * @return a string containing the work's information
 * @throws NoSuchWorkExceptionCore if no work with the given ID exists
 */
  String listWork(int workId) throws NoSuchWorkExceptionCore {
    for(Work w : _listOfWorks)
        if(w.getWorkId() == workId)
            return w.toString();
    throw new NoSuchWorkExceptionCore(workId);
  }


  /**
  * Returns a formatted list of all works in the library,
  * sorted by their ID.
  *
  * @return a string containing the formatted list of works
  */
  String listWorks(){
    _listOfWorks.sort(Comparator.comparingInt(Work::getWorkId));
    
    StringBuilder sb = new StringBuilder();
    for (Work w : _listOfWorks)
        sb.append(w.toString()).append("\n");
    
    return sb.toString();
  }


  /**
 * Returns a formatted list of all works created by the given creator,
 * sorted alphabetically by title (case-insensitive).
 *
 * @param name the creator's name
 * @return a string containing the list of works by the specified creator
 * @throws NoSuchCreatorExceptionCore if no creator with the given name exists
 */
  String listWorksByCreators(String name) throws NoSuchCreatorExceptionCore {
    List<Work> works = new ArrayList<>();

    for (Creator c : _setOfCreators)
      if (c.getName().equals(name))
        works.addAll(c.getWorkList());

    if (works.isEmpty())
      throw new NoSuchCreatorExceptionCore(name);

    works.sort(Comparator.comparing(w -> w.getTitle().toLowerCase()));

    StringBuilder sb = new StringBuilder();
    for (Work w : works)
      sb.append(w).append("\n");

    return sb.toString();
  }
  
  String performSearch(String search){
    _listOfWorks.sort(Comparator.comparingInt(Work::getWorkId));
    String searchLower = search.trim().toLowerCase();
    StringBuilder sb = new StringBuilder();
    boolean found = false;

    for (Work w : _listOfWorks){
        String workStr = w.toString();
        if (workStr.toLowerCase().contains(searchLower)){
            sb.append(workStr).append("\n");
            found = true;
        }
    }
    if(found){return sb.toString();}
    return null;
  }


  /**
   * Class that represents the library as a whole.
   */
  /**
   * Verifies the state of a given work in the library.
   * <p>
   * If the work has zero copies, it is removed from the library's list of works.
   * Additionally, if the work is a {@link Book}, any associated creators with no works are removed from the list of creators.
   * If the work is a {@link Dvd}, its creator is removed from the list of creators if they have no works left.
   *
   * @param work the {@link Work} to verify and potentially remove from the library
   */

  void verify(Work work) {
    if (work.getNumberOfCopies() == 0) {
      _listOfWorks.remove(work);
    }

    if (work instanceof Book) {
      Book workb = (Book) work;
      for (Creator c : workb.getCreators()) {
        if (c.getWorkList().isEmpty()) {
          _setOfCreators.remove(c);  // <-- Alterado
        }
      }
    } else {
      Dvd workd = (Dvd) work;
      if (workd.getCreator().getWorkList().isEmpty()) {
        _setOfCreators.remove(workd.getCreator()); // <-- Alterado
      }
    }
  }
  

  /**
 * Alters the inventory of a work by its ID.
 * <p>
 * If the number is positive, increases the number of copies of the work.
 * If the number is negative or zero, decreases the number of copies.
 * If the resulting number of copies is zero or less, removes the work from the library and updates creators accordingly.
 *
 * @param number the amount to change the number of copies (positive to add, negative to remove)
 * @param workId the unique identifier of the work to alter
 */
  void alterInvWork(int number, int workId) throws NoSuchWorkExceptionCore, NotEnoughInventoryExceptionCore {
    for (Work w : _listOfWorks) {
      if (w.getWorkId() == workId) {
        int current = w.getNumberOfCopies();

        if (number < 0) {
          int remove = -number;
          if (current < remove)
            throw new NotEnoughInventoryExceptionCore(w.getTitle());
          w.setNumberOfCopies(current - remove);
          if (w.getNumberOfCopies() == 0) {
            verify(w);
          }
        } else { 
          w.setNumberOfCopies(current + number);
        }
        return;
      }
    }

    throw new NoSuchWorkExceptionCore(workId);
  }


/**
 * Registers a new DVD in the system.
 *
 * @param igac IGAC code of the DVD.
 * @param creator The creator of the DVD.
 * @param title The title of the DVD.
 * @param price The price of the DVD.
 * @param numberOfCopies Number of available copies.
 * @param type The category of the DVD.
 */
void registerDvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type) {
    Dvd newDvd = new Dvd(igac, creator, title, price, numberOfCopies, type, _nextWorkId);
    _listOfWorks.add(newDvd);
    creator.addWork(newDvd);
    _nextWorkId++;
}

/**
 * Registers a new book in the system.
 *
 * @param isbn ISBN code of the book.
 * @param price The price of the book.
 * @param title The title of the book.
 * @param numberOfCopies Number of available copies.
 * @param creators List of creators of the book.
 * @param type The category of the book.
 */
void registerBook(String isbn, int price, String title, int numberOfCopies, List<Creator> creators, Category type) {
    Book newBook = new Book(isbn, price, title, numberOfCopies, creators, type, _nextWorkId);
    _listOfWorks.add(newBook);
    for (Creator c : creators) {
        c.addWork(newBook);
    }
    _nextWorkId++;
}

/**
 * Registers a new creator in the system.
 *
 * @param name The name of the creator.
 * @return The created {@link Creator} object.
 */
Creator registerCreator(String name) {
    for (Creator c : _setOfCreators) {
      if (c.getName().equals(name))
        return c;
    }

    Creator newCreator = new Creator(name);
    _setOfCreators.add(newCreator);
    return newCreator;
  }


  //Time

  /**
  * Returns the day of the system.
  * 
  * @return returns an int represeting the day.
  */
  int getCurrentDay(){ return _currentDay;}

  /**
  * Advances the current system day by the given number of days.
  *
  * @param days number of days to advance. Must be positive.
  */
  void advanceDays(int days){
    _currentDay += days;
  }

  //User

  /**
  * Registers a new user in the library system.
  *
  * @param name  the user's name
  * @param email the user's email address
  * @return the unique ID assigned to the newly registered user
  */
  int registerUser(String name, String email){
    _listOfUsers.add(new User(name, email, _nextUserId));
    int userId = _nextUserId;
    _nextUserId++;
    return userId;
  }

  /**
  * Returns a string representation of the user with the given ID.
  *
  * @param userId the unique identifier of the user
  * @return a string containing the user's information
  * @throws NoSuchUserExceptionCore if no user with the given ID exists
  */
  String listUser(int userId) throws NoSuchUserExceptionCore{
    for(User u : _listOfUsers){
      if(u.getUserId() == userId){
        return u.toString();
      }
    }
    throw new NoSuchUserExceptionCore(userId);
  }

  /**
  * Returns a formatted list of all registered users, sorted alphabetically by name
  * (case-insensitive) and by user ID when names are equal.
  *
  * @return a string containing the formatted list of users
  */
  String listUsers() {
    _listOfUsers.sort(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER).thenComparingInt(User::getUserId));

    StringBuilder sb = new StringBuilder();
    for (User u : _listOfUsers)
        sb.append(u.toString()).append("\n");
    return sb.toString();
  }
}

  
