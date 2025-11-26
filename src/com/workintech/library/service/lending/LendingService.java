package com.workintech.library.service.lending;

import com.workintech.library.model.*;
import com.workintech.library.service.book.BookService;
import com.workintech.library.service.member.MemberService;

public class LendingService {

    private final Library library;
    private final BookService bookService;
    private final MemberService memberService;

    private int nextRecordId = 1;
    private int nextInvoiceId = 1;

    public LendingService(Library library, BookService bookService, MemberService memberService) {
        this.library = library;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    //*****************************************************************
    //                          ÖDÜNÇ AL
    //*****************************************************************

    public boolean borrowBook(int memberId, int bookId, String borrowDate) {
        //kontroller yapılıyor.
        Member member = memberService.findMemberById(memberId);
        if (member == null) {
            System.out.println("Üye bulunamadı.");
            return false;
        }
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return false;
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Kitap şu anda müsait değil.");
            return false;
        }
        if (!member.canBorrowMore()) {
            System.out.println("Üyenin kitap limiti dolu.");
            return false;
        }

        book.setStatus(BookStatus.BORROWED);
        member.addBorrowedBook(book);

        MemberRecord record = new MemberRecord(nextRecordId, member, book, borrowDate);
        library.getRecords().put(record.getRecordId(), record);
        nextRecordId++;

        double amount = book.getPrice();
        Invoice invoice = new Invoice(nextInvoiceId, member, book, amount, borrowDate);
        library.getInvoices().put(invoice.getInvoiceId(), invoice);
        nextInvoiceId++;

        return true;
    }

    //*****************************************************************
    //                          İADE ET
    //*****************************************************************
    public boolean returnBook(int memberId, int bookId, String returnDate) {

        Member member = memberService.findMemberById(memberId);
        if (member == null) {
            System.out.println("Üye bulunamadı.");
            return false;
        }

        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return false;
        }

        // Kitap gerçekten bu üyede mi?
        if (!member.getBorrowedBooks().contains(book)) {
            System.out.println("Bu kitap bu üyede görünmüyor.");
            return false;
        }

        // Kitabı geri ver
        book.setStatus(BookStatus.AVAILABLE);
        member.returnBook(book);

        // Record bul
        MemberRecord recordToUpdate = null;
        for (MemberRecord record : library.getRecords().values()) {
            if (record.getBook().equals(book) &&
                    record.getMember().equals(member) &&
                    !record.isReturned()) {
                recordToUpdate = record;
                break;
            }
        }

        if (recordToUpdate != null) {
            recordToUpdate.markAsReturned(returnDate);
        }

        // Fatura bul ve refund et
        Invoice invoiceToRefund = null;
        for (Invoice invoice : library.getInvoices().values()) {
            if (invoice.getBook().equals(book) &&
                    invoice.getMember().equals(member) &&
                    !invoice.isRefunded()) {
                invoiceToRefund = invoice;
                break;
            }
        }

        if (invoiceToRefund != null) {
            invoiceToRefund.refund();
        }

        return true;
    }

}
