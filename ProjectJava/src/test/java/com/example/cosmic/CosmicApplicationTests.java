package com.example.cosmic;

import com.example.cosmic.domain.Song;
import com.example.cosmic.repository.SongRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CosmicApplicationTests {

    @MockBean
    SongRepository songRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddSongs() {
        List<Song> songs = Arrays.asList(
                new Song("1","1","1","1","1"),
                new Song("1","1","1","1","1")
        );

        when(songRepository.findAll()).thenReturn(songs);

        Assert.assertEquals(songRepository.findAll(), songs);
    }

    @Test
    public void testGetSongs() throws Exception {
        setUp();
        List<Song> songs = Arrays.asList(
                new Song("1","1","burger","1","1"),
                new Song("2","2","burger2","2","2")
        );

        when(songRepository.findByIdLessThan(20)).thenReturn(songs);

        Assert.assertEquals(songRepository.findByIdLessThan(20), songs);
    }
}
