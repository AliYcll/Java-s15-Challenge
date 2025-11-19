package com.workintech.library.model;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {

    private List<Book> books;

    public Author(int id, String name) {
        super(id, name);
        this.books = new ArrayList<>();
    }


    @Override
    public String whoYouAre() {
        return "Author";
    }

    public void addBook(Book book) {
        books.add(book);
    }

}
