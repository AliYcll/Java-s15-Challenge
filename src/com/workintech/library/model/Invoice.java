package com.workintech.library.model;

public class Invoice {
    private int invoiceId;
    private Member member;
    private Book book;
    private double amount;
    private String date;
    private boolean refunded;

    public Invoice(int invoiceId, Member member, Book book, double amount, String date) {
        this.invoiceId = invoiceId;
        this.member = member;
        this.book = book;
        this.amount = amount;
        this.date = date;
        this.refunded = false;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void refund() {
        this.refunded = true;
    }

}
