package com.workintech.library.service.book;

import com.workintech.library.model.Book;
import com.workintech.library.model.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookService {

    private final Library library;

    public BookService(Library library) {
        this.library = library;
    }

    //Kitapları map'e "kitap id-> kitap bilgisi" şeklinde ekliyor
    public void addBook(Book book){
        Map<Integer, Book> books = library.getBooks();
        int id = book.getBookId();
        books.put(id, book);
        library.getCategories().add(book.getCategory());

        //kitap map’e eklendi
        //kategori de categories set’ine eklendi
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

    public List<Book> findBooksByName(String name) {

        List<Book> result = new ArrayList<>();

        //library.getBooks().values() mapin sadece sağındaki döner (bizim senaryoda book oluyor)
        for (Book book : library.getBooks().values()) {
            if (book.getName().equalsIgnoreCase(name)) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findBooksByAuthor(String name) {

        List<Book> result = new ArrayList<>();

        for (Book book : library.getBooks().values()) {
            if (book.getAuthor().equalsIgnoreCase(name)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findBooksByCategory(String category) {
        List<Book> result = new ArrayList<>();

        for (Book book : library.getBooks().values()) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                result.add(book);
            }
        }

        return result;
    }


    public boolean updateBook(int id, String name, String author, String category) {
        Book book = findBookById(id);
        if (book != null) {
            book.setName(name);
            book.setAuthor(author);
            book.setCategory(category);
            System.out.println("Kitap bilgileri güncellendi: " + book);
            return true;
        } else {
            System.out.println("Güncellenecek kitap bulunamadı!");
            return false;
        }
    }

    public boolean removeBook(int id) {
        Map<Integer, Book> books = library.getBooks();
        Book removed = books.remove(id);
        if (removed != null) {
            return true;
        } else {
            System.out.println("Silinecek kitap bulunamadı!");
            return false;
        }
    }
}
