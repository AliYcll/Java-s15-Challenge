package com.workintech.library.model;

public class Magazine extends Book {

    public Magazine(int bookId, String author, String name, double price, BookStatus status, String edition, String dateOfPurchase) {
        super(bookId, author, name, price, status, edition, dateOfPurchase);
    }

    @Override
    public String getCategory() {
        return "";
    }
}
