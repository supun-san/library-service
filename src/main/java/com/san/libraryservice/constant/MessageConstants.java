package com.san.libraryservice.constant;

public class MessageConstants {

    private MessageConstants() {
    }

    public static final String ISBN_CONFLICT_MESSAGE = "ISBN already exists with different title or author.";
    public static final String BOOK_NOT_FOUND_MESSAGE = "Not found any books.";
    public static final String BOOK_BORROWED_SUCCESS = "Book borrowed successfully";
    public static final String BOOK_RETUNED_SUCCESS = "Book returned successfully";
    public static final String BORROW_RECORD_NOT_FOUND = "No active borrow record found for borrower ID %d and book ID %d";
}
