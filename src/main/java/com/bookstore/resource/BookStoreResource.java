package com.bookstore.resource;

import com.bookstore.dto.AuthorDto;
import com.bookstore.dto.AuthorView;
import com.bookstore.entity.Author;
import com.bookstore.service.BookstoreService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book-store")
public class BookStoreResource {

    private final BookstoreService bookstoreService;
    private final ModelMapper modelMapper;

    public BookStoreResource(BookstoreService bookstoreService,
                             ModelMapper modelMapper) {
        this.bookstoreService = bookstoreService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/fetch-by-id/{id}")
    public byte[] test(@PathVariable Long id) {
        return bookstoreService.fetchAuthorAvatarViaId(id);
    }


    @GetMapping("/fetch-entity-by-age/{age}")
    public List<AuthorView> fetchAuthorsByAgeGreaterThanEqual(@PathVariable int age) {
        List<Author> authors = bookstoreService.fetchAuthorsByAgeGreaterThanEqual(age);
        modelMapper.typeMap(Author.class, AuthorView.class)
                .addMappings(mapper -> mapper.skip(AuthorView::setAvatar));
        return authors.stream()
                .map(author -> modelMapper.map(author, AuthorView.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/fetch-entity-by-age-v2/{age}")
    public List<Author> fetchAuthorsDetailsByAgeGreaterThanEqual(@PathVariable int age) {
        return bookstoreService.fetchAuthorsDetailsByAgeGreaterThanEqual(age);
    }


    @GetMapping("/fetch-dto-by-age/{age}")
    public List<AuthorDto> fetchAuthorsWithAvatarsByAgeGreaterThanEqual(@PathVariable int age) {
        return bookstoreService.fetchAuthorsWithAvatarsByAgeGreaterThanEqual(age);
    }


}
