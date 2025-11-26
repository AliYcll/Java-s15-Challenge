package com.workintech.library.app;

import com.workintech.library.model.Book;
import com.workintech.library.model.BookStatus;
import com.workintech.library.model.Journal;
import com.workintech.library.model.Library;
import com.workintech.library.model.Magazine;
import com.workintech.library.model.Member;
import com.workintech.library.model.MemberType;
import com.workintech.library.model.StudyBook;
import com.workintech.library.service.book.BookService;
import com.workintech.library.service.lending.LendingService;
import com.workintech.library.service.member.MemberService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        BookService bookService = new BookService(library);
        MemberService memberService = new MemberService(library);
        LendingService lendingService = new LendingService(library, bookService, memberService);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz seçim.");
                continue;
            }

            switch (choice) {
                case 1 -> handleAddBook(scanner, bookService);
                case 2 -> handleFindBookById(scanner, bookService);
                case 3 -> handleFindBooksByName(scanner, bookService);
                case 4 -> handleFindBooksByAuthor(scanner, bookService);
                case 5 -> handleFindBooksByCategory(scanner, bookService);
                case 6 -> handleRemoveBook(scanner, bookService);
                case 7 -> handleUpdateBook(scanner, bookService);
                case 8 -> handleAddMember(scanner, memberService);
                case 9 -> handleBorrowBook(scanner, lendingService);
                case 10 -> handleReturnBook(scanner, lendingService);
                case 0 -> {
                    System.out.println("Çıkılıyor...");
                    running = false;
                }
                default -> System.out.println("Geçersiz seçim.");
            }
        }

        scanner.close();
        System.out.println("Program sonlandı.");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== KÜTÜPHANE SİSTEMİ =====");
        System.out.println("1 - Yeni kitap ekle");
        System.out.println("2 - Kitap ara (ID)");
        System.out.println("3 - Kitap ara (isim)");
        System.out.println("4 - Kitap ara (yazar)");
        System.out.println("5 - Kitap ara (kategori)");
        System.out.println("6 - Kitap sil");
        System.out.println("7 - Kitap güncelle");
        System.out.println("8 - Yeni üye ekle");
        System.out.println("9 - Kitap ödünç al");
        System.out.println("10 - Kitap iade et");
        System.out.println("0 - Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private static void handleAddBook(Scanner scanner, BookService bookService) {
        System.out.println("Kitap türü seç:");
        System.out.println("1 - StudyBook");
        System.out.println("2 - Journal");
        System.out.println("3 - Magazine");
        String typeInput = scanner.nextLine();

        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        System.out.print("Yazar: ");
        String author = scanner.nextLine();

        System.out.print("Kitap adı: ");
        String name = scanner.nextLine();

        System.out.print("Kategori: ");
        String category = scanner.nextLine();

        System.out.print("Fiyat: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Baskı (edition): ");
        String edition = scanner.nextLine();

        System.out.print("Satın alma tarihi: ");
        String dateOfPurchase = scanner.nextLine();

        Book book;
        if ("1".equals(typeInput)) {
            book = new StudyBook(
                    bookId,
                    author,
                    name,
                    price,
                    BookStatus.AVAILABLE,
                    edition,
                    dateOfPurchase,
                    category
            );
        } else if ("2".equals(typeInput)) {
            book = new Journal(
                    bookId,
                    author,
                    name,
                    price,
                    BookStatus.AVAILABLE,
                    edition,
                    dateOfPurchase,
                    category
            );
        } else {
            book = new Magazine(
                    bookId,
                    author,
                    name,
                    price,
                    BookStatus.AVAILABLE,
                    edition,
                    dateOfPurchase,
                    category
            );
        }


        bookService.addBook(book);
        System.out.println("Kitap eklendi.");
    }

    private static void handleFindBookById(Scanner scanner, BookService bookService) {
        System.out.print("Kitap ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Book book = bookService.findBookById(id);
        if (book != null) {
            System.out.println(book.getBookId() + " - " + book.getName() + " / " + book.getAuthor());
        } else {
            System.out.println("Kitap bulunamadı.");
        }
    }

    private static void handleFindBooksByName(Scanner scanner, BookService bookService) {
        System.out.print("Kitap adı: ");
        String name = scanner.nextLine();
        List<Book> books = bookService.findBooksByName(name);
        if (books.isEmpty()) {
            System.out.println("Bu isimde kitap bulunamadı.");
        } else {
            for (Book book : books) {
                System.out.println(book.getBookId() + " - " + book.getName() + " / " + book.getAuthor());
            }
        }
    }

    private static void handleFindBooksByAuthor(Scanner scanner, BookService bookService) {
        System.out.print("Yazar adı: ");
        String author = scanner.nextLine();
        List<Book> books = bookService.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("Bu yazara ait kitap bulunamadı.");
        } else {
            for (Book book : books) {
                System.out.println(book.getBookId() + " - " + book.getName());
            }
        }
    }

    private static void handleFindBooksByCategory(Scanner scanner, BookService bookService) {
        System.out.print("Kategori: ");
        String category = scanner.nextLine();
        List<Book> books = bookService.findBooksByCategory(category);
        if (books.isEmpty()) {
            System.out.println("Bu kategoride kitap bulunamadı.");
        } else {
            for (Book book : books) {
                System.out.println(book.getBookId() + " - " + book.getName() + " / " + book.getAuthor());
            }
        }
    }

    private static void handleUpdateBook(Scanner scanner, BookService bookService) {
        System.out.print("Güncellenecek kitap ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Book book = bookService.findBookById(id);
        if (book == null) {
            System.out.println("Güncellenecek kitap bulunamadı.");
            return;
        }

        System.out.println("Mevcut bilgiler: " + book.getName() + " / " + book.getAuthor() + " / " + book.getCategory());

        System.out.print("Yeni kitap adı (değiştirmek istemiyorsanız boş bırakın): ");
        String newName = scanner.nextLine();
        if (newName.isEmpty()) {
            newName = book.getName();
        }

        System.out.print("Yeni yazar adı (değiştirmek istemiyorsanız boş bırakın): ");
        String newAuthor = scanner.nextLine();
        if (newAuthor.isEmpty()) {
            newAuthor = book.getAuthor();
        }

        System.out.print("Yeni kategori (değiştirmek istemiyorsanız boş bırakın): ");
        String newCategory = scanner.nextLine();
        if (newCategory.isEmpty()) {
            newCategory = book.getCategory();
        }

        bookService.updateBook(id, newName, newAuthor, newCategory);
    }

    private static void handleRemoveBook(Scanner scanner, BookService bookService) {
        System.out.print("Silinecek kitap ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean removed = bookService.removeBook(id);
        if (removed) {
            System.out.println("Kitap silindi.");
        } else {
            System.out.println("Silinecek kitap bulunamadı.");
        }
    }

    private static void handleAddMember(Scanner scanner, MemberService memberService) {
        System.out.print("Üye ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print("Üye adı: ");
        String name = scanner.nextLine();

        System.out.println("Üye tipi seç:");
        System.out.println("1 - STUDENT");
        System.out.println("2 - FACULTY");
        System.out.println("3 - REGULAR");
        String typeInput = scanner.nextLine();

        MemberType memberType;
        if ("1".equals(typeInput)) {
            memberType = MemberType.STUDENT;
        } else if ("2".equals(typeInput)) {
            memberType = MemberType.FACULTY;
        } else {
            memberType = MemberType.REGULAR;
        }

        Member member = new Member(memberId, name, memberType);
        memberService.addMember(member);
        System.out.println("Üye eklendi.");
    }

    private static void handleBorrowBook(Scanner scanner, LendingService lendingService) {
        System.out.print("Üye ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        System.out.print("Ödünç alma tarihi: ");
        String borrowDate = scanner.nextLine();

        boolean success = lendingService.borrowBook(memberId, bookId, borrowDate);
        if (success) {
            System.out.println("Kitap başarıyla ödünç verildi.");
        } else {
            System.out.println("Ödünç verme işlemi başarısız.");
        }
    }

    private static void handleReturnBook(Scanner scanner, LendingService lendingService) {
        System.out.print("Üye ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        System.out.print("İade tarihi: ");
        String returnDate = scanner.nextLine();

        boolean success = lendingService.returnBook(memberId, bookId, returnDate);
        if (success) {
            System.out.println("Kitap başarıyla iade edildi.");
        } else {
            System.out.println("İade işlemi başarısız.");
        }
    }
}
