package com.workintech.library.app;

import com.workintech.library.model.*;
import com.workintech.library.service.book.BookService;
import com.workintech.library.service.lending.LendingService;
import com.workintech.library.service.member.MemberService;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        BookService bookService = new BookService(library);
        MemberService memberService = new MemberService(library);
        LendingService lendingService = new LendingService(library, bookService, memberService);
        Scanner scanner = new Scanner(System.in);

        loadInitialData(bookService, memberService, library);

        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> listAllBooks(library);
                    case 2 -> listAllMembers(library);
                    case 3 -> handleBorrowBook(scanner, lendingService, library, memberService, bookService);
                    case 4 -> handleReturnBook(scanner, lendingService, library, memberService, bookService);
                    case 5 -> handleAddBook(scanner, bookService);
                    case 6 -> handleAddMember(scanner, memberService);
                    case 7 -> handleFindBook(scanner, bookService);
                    case 8 -> handleDeleteBook(scanner, bookService, library);
                    case 9 -> handleUpdateBook(scanner, bookService, library);
                    case 0 -> {
                        System.out.println("Sistemden çıkılıyor...");
                        running = false;
                    }
                    default -> System.out.println("Geçersiz seçim. Lütfen menüden bir numara girin.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Hatalı giriş! Lütfen bir sayı giriniz.");
                scanner.nextLine(); // Hatalı girişi temizle
            }
        }

        scanner.close();
        System.out.println("Program sonlandı.");
    }

    private static void printMenu() {
        System.out.println("\n===== KÜTÜPHANE YÖNETİM SİSTEMİ =====");
        System.out.println("1. Tüm Kitapları Listele");
        System.out.println("2. Tüm Üyeleri Listele");
        System.out.println("3. Kitap Ödünç Al");
        System.out.println("4. Kitap İade Et");
        System.out.println("5. Yeni Kitap Ekle (Admin)");
        System.out.println("6. Yeni Üye Ekle (Admin)");
        System.out.println("7. Kitap Ara");
        System.out.println("8. Kitap Sil (Admin)");
        System.out.println("9. Kitap Güncelle (Admin)");
        System.out.println("0. Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private static void loadInitialData(BookService bookService, MemberService memberService, Library library) {
        // Kitaplar (10 Adet)
        bookService.addBook(new StudyBook(1, "Halit Ziya Uşaklıgil", "Aşk-ı Memnu", 150.0, BookStatus.AVAILABLE, "1. Baskı", "2023-01-10", "Roman"));
        bookService.addBook(new Journal(2, "Fyodor Dostoyevski", "Suç ve Ceza", 120.0, BookStatus.AVAILABLE, "2. Baskı", "2022-05-20", "Roman"));
        Book borrowedBook = new Magazine(3, "J.K. Rowling", "Harry Potter ve Felsefe Taşı", 100.0, BookStatus.BORROWED, "1. Baskı", "2021-11-30", "Fantastik");
        bookService.addBook(borrowedBook);
        bookService.addBook(new StudyBook(4, "George Orwell", "1984", 90.0, BookStatus.AVAILABLE, "3. Baskı", "2023-02-15", "Distopya"));
        bookService.addBook(new StudyBook(5, "Antoine de Saint-Exupéry", "Küçük Prens", 80.0, BookStatus.AVAILABLE, "10. Baskı", "2020-01-01", "Çocuk Kitabı"));
        bookService.addBook(new Journal(6, "Victor Hugo", "Sefiller", 200.0, BookStatus.AVAILABLE, "5. Baskı", "2021-03-22", "Klasik"));
        bookService.addBook(new Magazine(7, "Stephen King", "O (It)", 180.0, BookStatus.AVAILABLE, "2. Baskı", "2022-07-04", "Korku"));
        bookService.addBook(new StudyBook(8, "Jane Austen", "Gurur ve Önyargı", 110.0, BookStatus.AVAILABLE, "8. Baskı", "2020-11-11", "Roman"));
        bookService.addBook(new Journal(9, "Gabriel Garcia Marquez", "Yüzyıllık Yalnızlık", 160.0, BookStatus.AVAILABLE, "4. Baskı", "2023-04-01", "Büyülü Gerçekçilik"));
        bookService.addBook(new Magazine(10, "Dan Brown", "Da Vinci Şifresi", 95.0, BookStatus.AVAILABLE, "7. Baskı", "2021-09-15", "Gerilim"));

        // Üyeler (7 Adet)
        Member member1 = new Member(101, "Ali Veli", MemberType.STUDENT);
        memberService.addMember(member1);
        memberService.addMember(new Member(102, "Ayşe Fatma", MemberType.FACULTY));
        memberService.addMember(new Member(103, "Canan Demir", MemberType.STUDENT));
        memberService.addMember(new Member(104, "Deniz Kaya", MemberType.FACULTY));
        memberService.addMember(new Member(105, "Emre Yıldız", MemberType.STUDENT));
        memberService.addMember(new Member(106, "Fulya Akın", MemberType.FACULTY));
        memberService.addMember(new Member(107, "Gökhan Bulut", MemberType.STUDENT));

        // Ödünç alınmış kitap bilgisini senkronize edelim
        member1.addBorrowedBook(borrowedBook);


        System.out.println("Başlangıç verileri yüklendi.");
    }


    private static void listAllBooks(Library library) {
        System.out.println("\n--- Tüm Kitaplar ---");
        Collection<Book> books = library.getBooks().values();
        if (books.isEmpty()) {
            System.out.println("Kütüphanede hiç kitap yok.");
            return;
        }
        books.forEach(book -> System.out.println("ID: " + book.getBookId() + " | Ad: " + book.getName() + " | Yazar: " + book.getAuthor() + " | Durum: " + book.getStatus()));
    }

    private static void listAllMembers(Library library) {
        System.out.println("\n--- Tüm Üyeler ---");
        Collection<Member> members = library.getMembers().values();
        if (members.isEmpty()) {
            System.out.println("Sistemde hiç üye yok.");
            return;
        }
        members.forEach(member -> System.out.println("ID: " + member.getId() + " | Ad: " + member.getName() + " | Tip: " + member.getMemberType() + " | Ödünç Alınan Kitap Sayısı: " + member.getBorrowedBooks().size()));
    }

    private static void handleBorrowBook(Scanner scanner, LendingService lendingService, Library library, MemberService memberService, BookService bookService) {
        System.out.println("\n--- Kitap Ödünç Al ---");
        listAllMembers(library);

        Member member;
        while (true) {
            System.out.print("Lütfen geçerli bir üye ID'si girin: ");
            int memberId = readInt(scanner);
            if (memberId == -1) continue;
            member = memberService.findMemberById(memberId);
            if (member != null) {
                break;
            }
            System.out.println("Hata: Bu ID'ye sahip bir üye bulunamadı.");
        }

        if (!member.canBorrowMore()) {
            System.out.println("Hata: Bu üye maksimum kitap ödünç alma limitine (5) ulaşmıştır.");
            return;
        }

        System.out.println("\n--- Mevcut Kitaplar ---");
        library.getBooks().values().stream()
                .filter(b -> b.getStatus() == BookStatus.AVAILABLE)
                .forEach(b -> System.out.println("ID: " + b.getBookId() + " | Ad: " + b.getName()));

        Book book;
        while (true) {
            System.out.print("Lütfen ödünç almak istediğiniz kitabın ID'sini girin: ");
            int bookId = readInt(scanner);
            if (bookId == -1) continue;
            book = bookService.findBookById(bookId); // Kitap sistemde var mı?
            if (book != null) {
                if (book.getStatus() == BookStatus.AVAILABLE) {
                    break;
                } else {
                    System.out.println("Hata: Bu kitap şu an mevcut değil (ödünç alınmış).");
                }
            } else {
                System.out.println("Hata: Bu ID'ye sahip bir kitap bulunamadı.");
            }
        }

        System.out.print("Ödünç alma tarihi (YYYY-AA-GG): ");
        String borrowDate = scanner.nextLine();

        if (lendingService.borrowBook(member.getId(), book.getBookId(), borrowDate)) {
            System.out.println("'" + book.getName() + "' adlı kitap, '" + member.getName() + "' adlı üyeye başarıyla ödünç verildi.");
        } else {
            // Bu blok artık teorik olarak ulaşılamaz olmalı, ancak bir güvenlik ağı olarak kalabilir.
            System.out.println("Beklenmedik bir hata nedeniyle ödünç verme işlemi başarısız oldu.");
        }
    }


    private static void handleReturnBook(Scanner scanner, LendingService lendingService, Library library, MemberService memberService, BookService bookService) {
        System.out.println("\n--- Kitap İade Et ---");
        listAllMembers(library);

        Member member;
        while (true) {
            System.out.print("Lütfen geçerli bir üye ID'si girin: ");
            int memberId = readInt(scanner);
            if (memberId == -1) continue;
            member = memberService.findMemberById(memberId);
            if (member != null) {
                if (!member.getBorrowedBooks().isEmpty()) {
                    break;
                } else {
                    System.out.println("Bu üyenin iade edilecek kitabı bulunmuyor.");
                    return; // Üyenin kitabı yoksa işlemden çık
                }
            } else {
                System.out.println("Hata: Bu ID'ye sahip bir üye bulunamadı.");
            }
        }

        System.out.println("\n--- " + member.getName() + " Adlı Üyenin Kitapları ---");
        member.getBorrowedBooks().forEach(b -> System.out.println("ID: " + b.getBookId() + " | Ad: " + b.getName()));

        Book book;
        while (true) {
            System.out.print("Lütfen iade etmek istediğiniz kitabın ID'sini girin: ");
            int bookId = readInt(scanner);
            if (bookId == -1) continue;

            book = bookService.findBookById(bookId); // Kitap sistemde var mı?
            if (book != null && member.getBorrowedBooks().contains(book)) {
                break;
            } else {
                System.out.println("Hata: Geçersiz kitap ID'si veya bu kitap bu üye tarafından ödünç alınmamış.");
            }
        }

        System.out.print("İade tarihi (YYYY-AA-GG): ");
        String returnDate = scanner.nextLine();

        if (lendingService.returnBook(member.getId(), book.getBookId(), returnDate)) {
            System.out.println("Kitap başarıyla iade edildi.");
        } else {
            System.out.println("Beklenmedik bir hata nedeniyle iade işlemi başarısız oldu.");
        }
    }


    private static void handleAddBook(Scanner scanner, BookService bookService) {
        System.out.println("\n--- Yeni Kitap Ekle ---");
        int bookId;
        while (true) {
            System.out.print("Kitap ID: ");
            try {
                bookId = Integer.parseInt(scanner.nextLine());
                if (bookService.findBookById(bookId) == null) {
                    break; // Benzersiz ID bulundu, döngüden çık.
                } else {
                    System.out.println("Hata: Bu ID'ye sahip bir kitap zaten mevcut. Lütfen farklı bir ID girin.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hatalı giriş! Lütfen sayısal bir ID değeri girin.");
            }
        }

        try {
            System.out.print("Yazar: ");
            String author = scanner.nextLine();
            System.out.print("Kitap adı: ");
            String name = scanner.nextLine();
            System.out.print("Fiyat: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Kategori: ");
            String category = scanner.nextLine();
            System.out.print("Baskı (edition): ");
            String edition = scanner.nextLine();
            System.out.print("Satın alma tarihi (YYYY-AA-GG): ");
            String dateOfPurchase = scanner.nextLine();

            Book newBook = new StudyBook(bookId, author, name, price, BookStatus.AVAILABLE, edition, dateOfPurchase, category);
            bookService.addBook(newBook);
            System.out.println("'" + name + "' adlı kitap başarıyla eklendi.");
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş! Fiyat sayısal bir değer olmalıdır.");
        }
    }

    private static void handleAddMember(Scanner scanner, MemberService memberService) {
        System.out.println("\n--- Yeni Üye Ekle ---");
        try {
            System.out.print("Üye ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            System.out.print("Üye Adı Soyadı: ");
            String name = scanner.nextLine();
            System.out.println("Üye Tipi (1: STUDENT, 2: FACULTY): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            MemberType type = (typeChoice == 1) ? MemberType.STUDENT : MemberType.FACULTY;

            memberService.addMember(new Member(memberId, name, type));
            System.out.println("'" + name + "' adlı üye başarıyla eklendi.");

        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş! ID ve Üye Tipi sayısal bir değer olmalıdır.");
        }
    }

    private static void handleFindBook(Scanner scanner, BookService bookService) {
        System.out.println("\n--- Kitap Ara ---");
        System.out.println("Arama kriteri seçin: (1: ID, 2: İsim, 3: Yazar, 4: Kategori)");
        int choice = readInt(scanner);
        if (choice == -1) return;

        switch (choice) {
            case 1:
                System.out.print("Aranacak Kitap ID: ");
                int id = readInt(scanner);
                if (id == -1) return;
                Book book = bookService.findBookById(id);
                if (book != null) System.out.println("Bulunan Kitap: " + book);
                else System.out.println("Bu ID ile kitap bulunamadı.");
                break;
            case 2:
                System.out.print("Aranacak Kitap İsmi: ");
                String name = scanner.nextLine();
                List<Book> booksByName = bookService.findBooksByName(name);
                if (booksByName.isEmpty()) System.out.println("Bu isimde kitap bulunamadı.");
                else booksByName.forEach(System.out::println);
                break;
            case 3:
                System.out.print("Aranacak Yazar İsmi: ");
                String author = scanner.nextLine();
                List<Book> booksByAuthor = bookService.findBooksByAuthor(author);
                if (booksByAuthor.isEmpty()) System.out.println("Bu yazara ait kitap bulunamadı.");
                else booksByAuthor.forEach(System.out::println);
                break;
            case 4:
                System.out.print("Aranacak Kategori: ");
                String category = scanner.nextLine();
                List<Book> booksByCategory = bookService.findBooksByCategory(category);
                if (booksByCategory.isEmpty()) System.out.println("Bu kategoride kitap bulunamadı.");
                else booksByCategory.forEach(System.out::println);
                break;
            default:
                System.out.println("Geçersiz arama kriteri.");
        }
    }

    private static void handleDeleteBook(Scanner scanner, BookService bookService, Library library){
        System.out.println("\n--- Kitap Sil ---");
        listAllBooks(library);

        Book book;
        while (true) {
            System.out.print("Silinecek kitabın ID'sini girin: ");
            int id = readInt(scanner);
            if (id == -1) continue;
            book = bookService.findBookById(id);
            if (book != null) {
                if(book.getStatus() == BookStatus.AVAILABLE){
                    break;
                } else {
                    System.out.println("Hata: Ödünç alınmış bir kitap silinemez.");
                }
            } else {
                 System.out.println("Hata: Bu ID ile bir kitap bulunamadı.");
            }
        }

        if (bookService.removeBook(book.getBookId())) {
            System.out.println(book.getBookId() + " ID'li kitap başarıyla silindi.");
        } else {
             System.out.println("Beklenmedik bir hata nedeniyle kitap silinemedi.");
        }
    }

    private static void handleUpdateBook(Scanner scanner, BookService bookService, Library library){
        System.out.println("\n--- Kitap Güncelle ---");
        listAllBooks(library);
        
        Book book;
        while (true) {
            System.out.print("Güncellenecek kitabın ID'sini girin: ");
            int id = readInt(scanner);
            if(id == -1) continue;
            book = bookService.findBookById(id);
            if(book != null){
                break;
            }
            System.out.println("Hata: Bu ID ile bir kitap bulunamadı.");
        }

        System.out.println("Mevcut Bilgiler: " + book);
        System.out.print("Yeni kitap adı (değiştirmek istemiyorsanız boş bırakın): ");
        String newName = scanner.nextLine();
        System.out.print("Yeni yazar (değiştirmek istemiyorsanız boş bırakın): ");
        String newAuthor = scanner.nextLine();

        bookService.updateBook(book.getBookId(), newName.isEmpty() ? book.getName() : newName, 
                                  newAuthor.isEmpty() ? book.getAuthor() : newAuthor, 
                                  book.getCategory());

        System.out.println("Kitap başarıyla güncellendi.");
    }

    private static int readInt(Scanner scanner) {
        try {
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş! Lütfen bir sayı giriniz.");
            scanner.nextLine();
            return -1;
        }
    }
}