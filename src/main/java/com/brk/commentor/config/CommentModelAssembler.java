package com.brk.commentor.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.brk.commentor.controller.CommentController;
import com.brk.commentor.model.Comment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment,EntityModel<Comment>> {

    @Override
    public EntityModel<Comment> toModel(Comment entity) {
        return EntityModel.of(entity,linkTo(methodOn(CommentController.class).one(entity.getComment_id())).withSelfRel(),
                linkTo(methodOn(CommentController.class).all()).withRel("comments"));
    }
}
