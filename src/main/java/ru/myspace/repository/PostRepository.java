package ru.myspace.repository;

import org.springframework.data.repository.CrudRepository;
import ru.myspace.models.Post;

public interface PostRepository extends CrudRepository<Post,Long> {

}
