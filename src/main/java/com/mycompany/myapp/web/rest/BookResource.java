package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Book;

import com.mycompany.myapp.repository.BookRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.BookDTO;
import com.mycompany.myapp.service.mapper.BookMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    private static final String ENTITY_NAME = "book";

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookResource(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * POST  /books : Create a new book.
     *
     * @param bookDTO the bookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookDTO, or with status 400 (Bad Request) if the book has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/books")
    @Timed
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) throws URISyntaxException {
        log.debug("REST request to save Book : {}", bookDTO);
        if (bookDTO.getId() != null) {
            throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        BookDTO result = bookMapper.toDto(book);
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /books : Updates an existing book.
     *
     * @param bookDTO the bookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookDTO,
     * or with status 400 (Bad Request) if the bookDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/books")
    @Timed
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) throws URISyntaxException {
        log.debug("REST request to update Book : {}", bookDTO);
        if (bookDTO.getId() == null) {
            return createBook(bookDTO);
        }
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        BookDTO result = bookMapper.toDto(book);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /books : get all the books.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of books in body
     */
    @GetMapping("/books")
    @Timed
    public List<BookDTO> getAllBooks() {
        log.debug("REST request to get all Books");
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDto(books);
        }

    /**
     * GET  /books/:id : get the "id" book.
     *
     * @param id the id of the bookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/books/{id}")
    @Timed
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        log.debug("REST request to get Book : {}", id);
        Book book = bookRepository.findOne(id);
        BookDTO bookDTO = bookMapper.toDto(book);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookDTO));
    }

    /**
     * DELETE  /books/:id : delete the "id" book.
     *
     * @param id the id of the bookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/books/{id}")
    @Timed
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("REST request to delete Book : {}", id);
        bookRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
