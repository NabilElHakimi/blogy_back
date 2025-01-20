package me.elhakimi.blogy.service;


import lombok.AllArgsConstructor;
import me.elhakimi.blogy.domain.Blog;
import me.elhakimi.blogy.repository.BlogRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;


    public Blog save(Blog blog) {
        if(blog.getAuthor() == null) {

            Random random = new Random();
            int randomInt = random.nextInt(999999999);
            blog.setAuthor("Anonymous_" + randomInt);

        }

        blog.setCreatedDate(LocalDateTime.now());

        return blogRepository.save(blog);
    }

    public Page<Blog> findAllByPublishedIsTrue(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return blogRepository.findAllByPublishedIsTrueOrderByCreatedDateDesc(pageable);
    }

    public Blog findById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    public Blog update(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> findByTitle(String title) {
        return blogRepository.findByTitle(title);
    }

    public Blog acceptBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setPublished(true);
            return blogRepository.save(blog);
        }
        return null;
    }


    public Blog updateStatus(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setPublished(!blog.isPublished());
            return blogRepository.save(blog);
        }

        return null;
    }


}
