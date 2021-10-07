package rk.examples.app.music;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import rk.examples.app.music.controller.AlbumController;
import rk.examples.app.music.entity.Album;
import rk.examples.app.music.exception.AlbumNotFoundException;
import rk.examples.app.music.model.AlbumRequest;
import rk.examples.app.music.service.AlbumService;

@WebMvcTest(AlbumController.class)
class AlbumControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private AlbumService albumService;

	@Captor
	ArgumentCaptor<AlbumRequest> argumentCaptor;

	@Test
	public void postNewAlbum_shouldCreateAlbumInDB() throws Exception {

		AlbumRequest albumRequest = AlbumRequest.builder()
				.artistName("raghav")
				.songName("Thottal Poo Malarum")
				.genre("Love").build();

		when(albumService.createNewAlbum(argumentCaptor.capture())).thenReturn(1L);

		this.mockMvc
				.perform(post("/api/albums").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(albumRequest)))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"))
				.andExpect(header().string("Location", "http://localhost/api/albums/1"));

		assertThat(argumentCaptor.getValue().getArtistName()).isEqualTo("raghav");
	}

	@Test
	public void getAllAlbumsEndpoint_shouldReturnAlbumList() throws Exception {

		when(albumService.getAlbums()).thenReturn(List.of(createNewAlbum(1L, "raghav", "Thottal Poo Malarum", "Love"),
				createNewAlbum(2L, "Hari", "Malare", "Romantic")));

		this.mockMvc.perform(get("/api/albums")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].songName", is("Thottal Poo Malarum")))
				.andExpect(jsonPath("$[0].artistName", is("raghav")))
				.andExpect(jsonPath("$[0].genre", is("Love")))
				.andExpect(jsonPath("$[0].id", is(1)));

	}

	@Test
	public void getAlbumById_shouldReturnAnAlbum() throws Exception {
		
		when(albumService.getAlbumById(1L)).thenReturn(createNewAlbum(1L, "raghav", "Thottal Poo Malarum", "Love"));

		this.mockMvc.perform(get("/api/albums/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.songName", is("Thottal Poo Malarum")))
				.andExpect(jsonPath("$.artistName", is("raghav")))
				.andExpect(jsonPath("$.genre", is("Love")))
				.andExpect(jsonPath("$.id", is(1)));
	}
	
	@Test
	void getAlbumWithUnknownId_shouldReturn404() throws Exception {
		when(albumService.getAlbumById(33L)).thenThrow(new AlbumNotFoundException("Requested Album not found in the albums"));

		this.mockMvc.perform(get("/api/albums/33")).andExpect(status().isNotFound());
	}

	private Album createNewAlbum(long id, String artist, String song, String genre) {
		return Album.builder().artistName(artist).id(id).songName(song).genre(genre).build();
	}
}
