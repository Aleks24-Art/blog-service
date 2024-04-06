package ua.aleksenko.blogservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.aleksenko.blogservice.model.entity.Comment;
import ua.aleksenko.blogservice.model.entity.Post;
import ua.aleksenko.blogservice.repository.CommentRepository;
import ua.aleksenko.blogservice.repository.PostRepository;
import ua.aleksenko.blogservice.service.CommentService;
import ua.aleksenko.blogservice.util.UserUtils;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserUtils userUtils;

  public Comment createComment(Long postId, String content) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("Post with id: " + postId + " not found"));

    Comment comment = new Comment();
    comment.setPost(post);
    comment.setContent(content);
    comment.setPostedBy(userUtils.getUserName());

    return commentRepository.save(comment);
  }

  public List<Comment> getPostComments(Long postId) {
   return commentRepository.findByPostId(postId);
  }

}
