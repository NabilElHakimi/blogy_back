package me.elhakimi.blogy.repository;

import me.elhakimi.blogy.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog , Long> {
    List<Blog> findByTitle(String title);
    Page<Blog> findAllByPublishedIsTrueOrderByCreatedDateDesc(PageRequest page);

}
