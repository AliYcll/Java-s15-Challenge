package com.workintech.library.service;

import com.workintech.library.model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class LibraryService {
    private Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    //Kitapları map'e "kitap id-> kitap bilgisi" şeklinde ekliyor
    public void addBook(Book book){
        Map<Integer, Book> books = library.getBooks();
        int id = book.getBookId();
        books.put(id, book);
    }


    //Kitap arama mapteki id'ye göre yapılıyor çünkü en hızlı yöntem bu.
    public Book findBookById(int id) {
        Map<Integer, Book> books = library.getBooks();

        //books.get(id) ya book döner ya null döner.
        Book book = books.get(id);
        if (book != null) {
            return book;
        } else {
            System.out.println("Kitap bulunamadı!");
            return null;
        }



}
