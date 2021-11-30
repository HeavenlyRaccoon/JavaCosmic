package com.example.cosmic.repository;

import com.example.cosmic.domain.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository <Song, Long> {
    List<Song> findByAuthorContaining(String author);

    List<Song> findByIdLessThan(Integer id);

    Song findById(Integer id);

    List<Song> findByIdIn(List<Integer> list);

}
