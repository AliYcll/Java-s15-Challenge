package com.workintech.library.model;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person {

    private List<Book> borrowedBooks;
    private MemberType memberType;
    private static final int MAX_BOOK_LIMIT = 5;

    //borrowedBooks parametresi almaz, çünkü Member oluşturulurken kitap listesi her zaman boş başlar.
    public Member(int id, String name, MemberType memberType) {
        super(id, name);
        this.borrowedBooks = new ArrayList<>();
        this.memberType = memberType;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    //kitap alma limit kontrolü
    public boolean canBorrowMore() {
        return borrowedBooks.size() < MAX_BOOK_LIMIT;
    }

    //kitabı iade et
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }


    @Override
    public String whoYouAre() {
        return "Member";
    }

}
