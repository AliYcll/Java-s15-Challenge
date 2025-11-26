package com.workintech.library.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* iş mantığı (addBook, borrow vs.) buraya koymuyoruz, onları service paketindeki LibraryService’e yazacağız.
Library şu anda sadece state tutan bir model.*/ 
public class Library {
    private Map<Integer, Book> books;
    private Map<Integer, Member> members;
    private Map<Integer, Librarian> librarians;
    private Map<Integer, MemberRecord> records;
    private Map<Integer, Invoice> invoices;
    private Set<String> categories; //(tekrarsız diye "set")

    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.librarians = new HashMap<>();
        this.records = new HashMap<>();
        this.invoices = new HashMap<>();
        this.categories = new HashSet<>();
    }

    public Map<Integer, Book> getBooks() {
        return books;
    }

    public Map<Integer, Member> getMembers() {
        return members;
    }

    public Map<Integer, Librarian> getLibrarians() {
        return librarians;
    }

    public Map<Integer, MemberRecord> getRecords() {
        return records;
    }

    public Map<Integer, Invoice> getInvoices() {
        return invoices;
    }

    public Set<String> getCategories() {
        return categories;
    }


}
