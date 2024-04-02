package ua.aleksenko.blogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.aleksenko.blogservice.model.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
