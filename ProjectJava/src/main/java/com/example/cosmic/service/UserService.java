package com.example.cosmic.service;

import com.example.cosmic.domain.Role;
import com.example.cosmic.domain.Song;
import com.example.cosmic.domain.User;
import com.example.cosmic.domain.UserSong;
import com.example.cosmic.repository.RoleRepository;
import com.example.cosmic.repository.SongRepository;
import com.example.cosmic.repository.UserRepository;
import com.example.cosmic.repository.UserSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
public class UserService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserSongRepository userSongRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public User saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    public User findById(int id){
        return userRepository.findById(id);
    }

    public void AddSongToMyMusic(Integer userId, Integer songId){
        Song song = songRepository.findById(songId);
        User user = userRepository.findById(userId);
        UserSong userSong = new UserSong(song, user);
        userSongRepository.save(userSong);
    }

    @Transactional
    public void sendMail(User user) throws MessagingException {
        mailService.sendMail(user);
    }

}
