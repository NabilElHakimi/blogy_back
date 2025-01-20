package me.elhakimi.blogy.repository;

import me.elhakimi.blogy.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository <Image, Long> {

}
