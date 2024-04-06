package ua.aleksenko.blogservice.service;

import java.util.List;
import ua.aleksenko.blogservice.model.entity.Comment;

public interface CommentService {

  Comment createComment(Long postId, String content);

  List<Comment> getPostComments(Long postId);

}
