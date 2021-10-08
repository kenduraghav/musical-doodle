package rk.examples.app.music.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import rk.examples.app.music.entity.Album;
import rk.examples.app.music.model.AlbumRequest;
import rk.examples.app.music.service.AlbumService;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@PostMapping
	public ResponseEntity<Void> createNewAlbum(@Valid @RequestBody AlbumRequest albumRequest,
			UriComponentsBuilder uriComponentsBuilder) {

		Long id = albumService.createNewAlbum(albumRequest);

		UriComponents uriComponents = uriComponentsBuilder.path("/api/albums/{id}").buildAndExpand(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Album>> getAllAlbums() {
		return ResponseEntity.ok(albumService.getAlbums());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
		return ResponseEntity.ok(albumService.getAlbumById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Album> updateAlbumById(@PathVariable Long id, @Valid @RequestBody AlbumRequest albumRequest) {
		return ResponseEntity.ok(albumService.updateAlbum(id, albumRequest));
	}
}
