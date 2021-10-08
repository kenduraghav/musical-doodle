package rk.examples.app.music;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import rk.examples.app.music.entity.Album;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlbumControllerIntegrationTest {

	@LocalServerPort
	int randomPort;

	private TestRestTemplate testRestTemplate;

	@BeforeEach
	public void setUp() {
		this.testRestTemplate = new TestRestTemplate();
	}

	@Test
	void deleteAlbumWithKnownId_shouldReturn404AfterDelete() {

		int albumId = 3;

		String baseUrl = "http://localhost:" + randomPort;

		ResponseEntity<Album> existingAlbumResponse = this.testRestTemplate
				.getForEntity(baseUrl + "/api/albums/" + albumId, Album.class);
		
		assertThat(existingAlbumResponse.getStatusCode(), is(HttpStatus.OK));
		
		this.testRestTemplate.delete(baseUrl + "/api/albums/" +albumId);
		
		ResponseEntity<Album> album = this.testRestTemplate
				.getForEntity(baseUrl + "/api/albums/" + albumId, Album.class);
		
		assertThat(album.getStatusCode(), is(HttpStatus.NOT_FOUND));
		

	}

}
