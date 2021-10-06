package rk.examples.app.music;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		
		//ArgumentCaptor<AlbumRequest> argumentCaptor = ArgumentCaptor.forClass(AlbumRequest.class);
		
		when(albumService.createNewAlbum(argumentCaptor.capture())).thenReturn(1L);
		
		this.mockMvc.perform(post("/api/albums")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(albumRequest)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(header().string("Location","http://localhost/api/albums/1"));
		
		assertThat(argumentCaptor.getValue().getArtistName()).isEqualTo("raghav");
	}
}
