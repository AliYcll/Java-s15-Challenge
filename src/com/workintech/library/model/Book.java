package com.workintech.library.model;

public abstract class Book {
    private int bookId;
    private String author;
    private String name;
    private double price;
    private BookStatus status;
    private String edition;
    private String dateOfPurchase;

    public Book(int bookId, String author, String name, double price, BookStatus status, String edition, String dateOfPurchase) {
        this.bookId = bookId;
        this.author = author;
        this.name = name;
        this.price = price;
        this.status = status;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
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

    //Book’un türünü alt sınıflar belirleyecek diye abstract metot var.
    public abstract String getCategory();

}
