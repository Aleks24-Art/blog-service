package ua.aleksenko.blogservice.service;

import java.util.List;
import ua.aleksenko.blogservice.model.entity.Post;

public interface PostService {

  Post savePost(Post post);

  List<Post> getAllPost();

  Post getPostByIdViewCount(Long id);

  Post getPostById(Long id);

  void likePost(Long id);
}
