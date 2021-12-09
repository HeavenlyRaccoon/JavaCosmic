package com.example.cosmic.repository;

import com.example.cosmic.domain.Song;
import com.example.cosmic.domain.User;
import com.example.cosmic.domain.UserSong;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSongRepository extends CrudRepository <UserSong, Long> {
    List<UserSong> getAllByUserId(User id);

    List<UserSong> findBySongIdAndUserId(Song song, User user);
}
