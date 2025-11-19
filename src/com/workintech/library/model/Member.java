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

    //limit kontrolü için
    public boolean canBorrowMore() {
        return borrowedBooks.size() < MAX_BOOK_LIMIT;
    }

    @Override
    public String whoYouAre() {
        return "Member";
    }

}
