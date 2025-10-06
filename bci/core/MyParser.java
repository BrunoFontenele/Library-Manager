package bci.core;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.List;
import java.util.ArrayList;

import bci.core.exception.UnrecognizedEntryException;

// MAYBE more import

public class MyParser {
  private Library _library;

  MyParser(Library lib) {
    _library = lib;
  }

  void parseFile(String filename) throws IOException {
    String line;

    try (BufferedReader in = new BufferedReader(new FileReader(filename));) {
      while ((line = in.readLine()) != null){
        parseLine(line);
      }
    }catch(UnrecognizedEntryException e){
      throw new IOException();
    }
  }

  private void parseLine(String line) throws UnrecognizedEntryException {
    String[] components = line.split(":");

    switch (components[0]) {
      case "USER":
        parseUser(components, line);
        break;

      case "DVD":
        parseDvd(components, line);
        break;

      case "BOOK":
        parseBook(components, line);
        break;

      default:
        throw new UnrecognizedEntryException("Tipo inválido " + components[0] + " na linha " + line);
    }
  }

  private void parseUser(String[] components, String line) throws UnrecognizedEntryException {
      if (components.length != 3)
        throw new UnrecognizedEntryException ("Número inválido de campos (3) na descrição de um utente: " + line);

      _library.registerUser(components[1], components[2]);
  }

  private void parseDvd(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 7)
      throw new UnrecognizedEntryException ("Número inválido de campos (7) na descrição de um DVD: " + line);

    int price = Integer.parseInt(components[3]);
    int nCopies = Integer.parseInt(components[6]);
    Category category = Category.valueOf(components[4]);
    Creator creator = _library.registerCreator(components[2].trim());

    _library.registerDvd(components[5], creator, components[1], price, nCopies, category);
  }
  
  private void parseBook(String[] components, String line) throws UnrecognizedEntryException {
    if (components.length != 7)
      throw new UnrecognizedEntryException ("Número inválido de campos (7) na descrição de um Book: " + line);
    
    int price = Integer.parseInt(components[3]);
    int nCopies = Integer.parseInt(components[6]);
    Category category = Category.valueOf(components[4]);
    List<Creator> creators = new ArrayList<>();
    for (String name : components[2].split(","))
      creators.add(_library.registerCreator(name.trim()));

    _library.registerBook(components[5], price, components[1], nCopies, creators, category);
  }
}