package ua.aleksenko.blogservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(length = 5000)
  private String content;

  private String postedBy;

  private String img;

  private Date date;

  private int likeCount;

  private int viewCount;

  private List<String> tags;
}
