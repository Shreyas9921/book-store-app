package com.shreyas.springbootstrap.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shreyas.springbootstrap.datamodel.*;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);
}