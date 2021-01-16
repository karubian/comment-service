package com.brk.commentor.controller;

import com.brk.commentor.config.CommentModelAssembler;
import com.brk.commentor.exception.CommentNotFoundException;
import com.brk.commentor.model.Comment;
import com.brk.commentor.repository.CommentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CommentController {


    private final CommentRepository repository;

    private final CommentModelAssembler assembler;

    CommentController(CommentRepository repository,CommentModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/comments")
    public CollectionModel<EntityModel<Comment>> all() {
        List<EntityModel<Comment>> comments = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(comments,linkTo(methodOn(CommentController.class).all()).withSelfRel());
    }

    @PostMapping("/comments")
    public Comment newComment(@RequestBody Comment newComment) {
        return repository.save(newComment);
    }

    @GetMapping("/comments/{id}")
    public EntityModel<Comment> one(@PathVariable Long id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new CommentNotFoundException(id));
        return assembler.toModel(comment);
    }

    @PutMapping("/comments/{id}")
    public Comment replaceComment(@RequestBody Comment newComment, @PathVariable Long id) {
        return repository.findById(id).map(comment -> {
            comment.setContent(newComment.getContent());
            comment.setTitle(newComment.getTitle());
            return repository.save(newComment);
        }).orElseGet(() -> {
            newComment.setComment_id(id);
            return repository.save(newComment);
        });
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
