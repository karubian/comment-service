package com.brk.commentor.controller;

import com.brk.commentor.config.CommentModelAssembler;
import com.brk.commentor.exception.CommentNotFoundException;
import com.brk.commentor.model.Comment;
import com.brk.commentor.repository.CommentRepository;
import org.hibernate.boot.model.source.spi.CascadeStyleSource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<?> newComment(@RequestBody Comment newComment) {
        EntityModel<Comment> entityModel = assembler.toModel(repository.save(newComment));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/comments/{id}")
    public EntityModel<Comment> one(@PathVariable Long id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new CommentNotFoundException(id));
        return assembler.toModel(comment);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> replaceComment(@RequestBody Comment newComment, @PathVariable Long id) {
        Comment updatedComment = repository.findById(id).map(comment -> {
            comment.setTitle(newComment.getTitle());
            comment.setContent(newComment.getContent());
            return repository.save(comment);
        }).orElseGet(() -> {
            newComment.setComment_id(id);
            return repository.save(newComment);
        });
        EntityModel<Comment> entityModel = assembler.toModel(updatedComment);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        Optional<Comment> commentOptional = repository.findById(id);
        if(commentOptional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
