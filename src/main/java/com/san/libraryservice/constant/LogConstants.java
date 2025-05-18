package com.san.libraryservice.constant;

public class LogConstants {

    private LogConstants() {
    }

    public static final String ADD_BOOK_CONTROLLER_START = "Controller: Starting addBook with request: {}";
    public static final String ADD_BOOK_SERVICE_START = "Service: Adding book with ISBN: {}";
    public static final String ADD_BOOK_ISBN_CONFLICT = "ISBN conflict for ISBN: {}";
    public static final String ADD_BOOK_SERVICE_SUCCESS = "Book saved successfully with ISBN: {}";

    public static final String GET_ALL_BOOKS_CONTROLLER_START = "Controller: Fetching all books";
    public static final String GET_ALL_BOOKS_SERVICE_START = "Service: Retrieving all books from repository";
    public static final String GET_ALL_BOOKS_SERVICE_SUCCESS = "Successfully retrieved {} books";
    public static final String GET_ALL_BOOKS_EMPTY = "No books found in the repository";

    public static final String REGISTER_BORROWER_CONTROLLER_START = "Controller: Registering borrower: {}";
    public static final String REGISTER_BORROWER_SERVICE_START = "Service: Registering borrower: {}";
    public static final String REGISTER_BORROWER_SUCCESS = "Borrower registered successfully with ID: {}";

    public static final String BORROW_BOOK_CONTROLLER_START = "Controller: Borrower [{}] is attempting to borrow Book [{}]";
    public static final String BORROW_BOOK_SERVICE_START = "Service: Starting borrow process for Borrower [{}], Book [{}]";
    public static final String BORROW_BOOK_VALIDATION = "Validating availability for Book [{}]";
    public static final String BORROW_RECORD_SAVED = "Borrow record saved for Borrower [{}] and Book [{}]";
    public static final String BORROW_BOOK_SUCCESS = "Borrow process completed for Borrower [{}] and Book [{}]";

    public static final String RETURN_BOOK_CONTROLLER_START = "Controller: Borrower [{}] is returning Book [{}]";
    public static final String RETURN_BOOK_SERVICE_START = "Service: Processing return for Borrower [{}], Book [{}]";
    public static final String RETURN_BOOK_RECORD_FOUND = "Active borrow record found for Borrower [{}], Book [{}]";
    public static final String RETURN_BOOK_RECORD_UPDATED = "Borrow record updated with return time for Borrower [{}], Book [{}]";
    public static final String RETURN_BOOK_SUCCESS = "Book [{}] successfully returned by Borrower [{}]";

}
