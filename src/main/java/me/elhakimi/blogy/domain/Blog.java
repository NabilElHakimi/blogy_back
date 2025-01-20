package me.elhakimi.blogy.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    private String content;

    @OneToMany(mappedBy = "blog" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Images> images = new ArrayList<>();
    private LocalDateTime createdDate;
    private boolean published = false ;

}
