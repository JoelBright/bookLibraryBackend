package mts.demo.booksSpringBoot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mts.demo.booksSpringBoot.exception.ResourceNotFoundException;
import mts.demo.booksSpringBoot.model.Author;
import mts.demo.booksSpringBoot.repository.AuthorRepository;

@RestController
@RequestMapping("/api")
public class AuthorController {

	@Autowired
	private AuthorRepository authorRepository;
	
	// Get all Authors
    @GetMapping("/authors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }
	
	// Create new Author
    @PostMapping("/authors")
    public Author createAuthor(@Valid @RequestBody Author author) {
        return authorRepository.save(author);
    }
	
	// Get a single Author
    @GetMapping("/authors_id/{id}")
    public Author getAuthorById(@PathVariable(value = "id") Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
    }

	// Get Authors by Name
    @GetMapping("/authors/{authorName}")
    public Author findAuthorByName(@PathVariable(value = "authorName") String authorName) {
        return authorRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "authorName", authorName));
    }
	
	// Update an Author
    @PutMapping("/authors/{id}")
    public Author updateAuthor(@PathVariable(value = "id") Long authorId,
                                            @Valid @RequestBody Author authorInfo) {

    	Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

    	author.setAuthorName(authorInfo.getAuthorName());
    	author.setAuthorDescription(authorInfo.getAuthorDescription());

        Author updatedAuthor = authorRepository.save(author);
        return updatedAuthor;
    }
	
	// Delete an Author
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable(value = "id") Long authorId) {
    	Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

        authorRepository.delete(author);

        return ResponseEntity.ok().build();
    }
	
}
