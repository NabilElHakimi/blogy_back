package me.elhakimi.blogy.repository;

import me.elhakimi.blogy.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository <Images, Long> {

}
