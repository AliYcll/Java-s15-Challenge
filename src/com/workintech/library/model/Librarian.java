package com.workintech.library.model;

public class Librarian extends Person {

    private String password;

    public Librarian(int id, String name, String password) {
        super(id, name);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String whoYouAre() {
        return "Librarian";
    }
}
