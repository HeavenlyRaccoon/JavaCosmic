package com.example.cosmic.controller;

import com.example.cosmic.DTO.MusicDTO;
import com.example.cosmic.DTO.SongDTO;
import com.example.cosmic.DTO.UserDTO;
import com.example.cosmic.domain.Song;
import com.example.cosmic.domain.User;
import com.example.cosmic.domain.UserSong;
import com.example.cosmic.repository.SongRepository;
import com.example.cosmic.repository.UserSongRepository;
import com.example.cosmic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private UserSongRepository userSongRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(Map<String, Object> model){
        Iterable<Song> songs = songRepository.findByIdLessThan(50);
        model.put("songs", songs);
        return "main";
    }

    @GetMapping("/register")
    public String registerUser() {
        return "registration";
    }

    @GetMapping("/admin")
    public String admin(Map<String, Object> model) {
        Iterable<Song> songs = songRepository.findByIdLessThan(200);
        model.put("songs", songs);
        return "admin";
    }

    @PostMapping("/admin/addsong")
    public ResponseEntity addSong(@RequestBody MusicDTO u) {
        Song song = u.toSong();
        songRepository.save(song);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/admin/removesong")
    public ResponseEntity removeSong(@RequestBody SongDTO s) {
        System.out.println(s.getSongId());
        Song song = songRepository.findById(s.getSongId());
        songRepository.delete(song);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/mymusic")
    public String mymusic(@RequestParam String accountId, Map<String, Object> model) {
        System.out.println(accountId);
        User user = userService.findById(Integer.parseInt(accountId));
        List<UserSong> userSongs = userSongRepository.getAllByUserId(user);

        List<Song> songIds = userSongs.stream().map(UserSong::getSongId).collect(Collectors.toCollection(ArrayList::new));
        List<Integer> Ids = songIds.stream().map(Song::getId).collect(Collectors.toCollection(ArrayList::new));
        Iterable<Song> songs = songRepository.findByIdIn(Ids);
        model.put("songs", songs);
        return "userMusic";
    }

    @PostMapping("/addmymusic")
    public ResponseEntity addmymusic(@RequestBody SongDTO song){
        userService.AddSongToMyMusic(song.getUserId(), song.getSongId());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/removeusersong")
    public ResponseEntity removeUserSong(@RequestBody SongDTO s){
        Song song = songRepository.findById(s.getSongId());
        User user = userService.findById(s.getUserId());
        UserSong userSong = userSongRepository.findBySongIdAndUserId(song, user).get(0);
        userSongRepository.delete(userSong);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/main")
    public String addSong(@RequestParam String name, @RequestParam String author, @RequestParam String image,
                          @RequestParam String size, @RequestParam String link, Map<String ,Object> model){
        Song song = new Song(name, author, image, size, link);
        songRepository.save(song);

        Iterable<Song> songs = songRepository.findAll();
        model.put("songs", songs);

        return "main";
    }

    @PostMapping("filter")
    public String filter (@RequestParam String filter, Map<String, Object> model){
        Iterable<Song> songs;
        if(filter != null && !filter.isEmpty()){
            songs = songRepository.findByAuthorContaining(filter);
            model.put("songs", songs);
        }else {
            songs = songRepository.findAll();
        }

        model.put("songs", songs);
        return "main";
    }

}