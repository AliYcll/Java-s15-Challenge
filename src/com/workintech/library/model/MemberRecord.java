package com.workintech.library.model;

public class MemberRecord {
    private int recordId;
    private Member member;
    private Book book;
    private String borrowDate;
    private String returnDate;
    private boolean returned;

    public MemberRecord(int recordId, Member member, Book book, String borrowDate) {
        this.recordId = recordId;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = null;
        this.returned = false;
    }

    public int getRecordId() {
        return recordId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markAsReturned(String returnDate) {
        this.returnDate = returnDate;
        this.returned = true;
    }

}
