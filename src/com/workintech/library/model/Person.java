package com.workintech.library.model;

/*PERSON neden abstract? çünkü:
sistemde “Person” diye biri yok. Person sadece ortak şeyleri taşımak için var. gerçek nesneler:
Member
Author
Librarian
yani new Person() demek mantıksız bu yüzden Person abstract*/

public abstract class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

//   setter yok çünkü bir kişinin: id’si, adı sistemde değiştirilen şeyler değildir.

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /* abstract metot. alt sınıflar (Author, Member, Librarian) bunu override etmek zorunda.
       -gövdesi yok
       -sadece imza
       -alt sınıflar dolduracak */
    public abstract String whoYouAre();


}
