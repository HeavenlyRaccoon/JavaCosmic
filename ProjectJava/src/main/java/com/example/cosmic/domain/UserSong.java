package com.example.cosmic.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class UserSong {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private Song songId;

    @OneToOne(fetch = FetchType.EAGER)
    private User userId;

    public UserSong(Song songId, User userId) {
        this.songId = songId;
        this.userId = userId;
    }

    public UserSong() {
    }
}

