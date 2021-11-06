package com.example.cosmic.DTO;

import com.example.cosmic.domain.Song;
import lombok.Getter;

@Getter
public class MusicDTO {

    private String name;
    private String author;
    private String image;
    private String size;
    private String link;

    public Song toSong(){
        return new Song(this.getName(), this.getAuthor(), this.getImage(), this.getSize(), this.getLink());
    }
}