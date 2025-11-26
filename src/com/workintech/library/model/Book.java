package com.workintech.library.model;

public abstract class Book {
    private int bookId;
    private String author;
    private String name;
    private double price;
    private BookStatus status;
    private String edition;
    private String dateOfPurchase;
    private String category;


    public Book(int bookId, String author, String name, double price, BookStatus status, String edition, String dateOfPurchase, String category) {
        this.bookId = bookId;
        this.author = author;
        this.name = name;
        this.price = price;
        this.status = status;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
        this.category = category;
    }

    public int getBookId() {
        return bookId;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getEdition() {
        return edition;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public String getCategory() {
        return category;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

}
