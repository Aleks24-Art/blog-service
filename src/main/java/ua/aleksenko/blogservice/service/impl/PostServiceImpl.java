package ua.aleksenko.blogservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.aleksenko.blogservice.model.entity.Post;
import ua.aleksenko.blogservice.repository.PostRepository;
import ua.aleksenko.blogservice.service.PostService;
import ua.aleksenko.blogservice.util.UserUtils;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  @Transactional
  public Post savePost(Post post) {
    post.setDate(new Date());
    post.setPostedBy(UserUtils.getUserName());

    return postRepository.save(post);
  }

  @Transactional
  public List<Post> getAllPost() {
    return postRepository.findAll();
  }

  public Post getPostByIdViewCount(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Post with id: " + id + " not found"));

    post.setViewCount(post.getViewCount() + 1);

    return postRepository.save(post);
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Post with id: " + id + " not found"));
  }

  public void likePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Post with id: " + id + " not found"));

    post.setLikeCount(post.getLikeCount() + 1);
    postRepository.save(post);
  }

}
