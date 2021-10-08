package rk.examples.app.music.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rk.examples.app.music.entity.Album;
import rk.examples.app.music.exception.AlbumNotFoundException;
import rk.examples.app.music.model.AlbumRequest;
import rk.examples.app.music.repository.AlbumRepository;

@Service
public class AlbumService {

	@Autowired
	private AlbumRepository albumRepo;
	
	public Long createNewAlbum(AlbumRequest request) {
		Album album = Album.builder()
				.artistName(request.getArtistName())
				.songName(request.getSongName())
				.genre(request.getGenre()).build();
		
		album = albumRepo.save(album);
		return album.getId();
	}

	public List<Album> getAlbums() {
		return albumRepo.findAll();
	}

	public Album getAlbumById(Long id) {
		Optional<Album> requestedAlbum = albumRepo.findById(id);

		if (requestedAlbum.isEmpty()) {
			throw new AlbumNotFoundException(String.format("Album with id '%d' is not found", id));
		}
		return requestedAlbum.get();
	}

	@Transactional
	public Album updateAlbum(Long id, AlbumRequest albumRequestToUpdate) {
		Album albumToUpdate = getAlbumById(id);
		
		albumToUpdate.setArtistName(albumRequestToUpdate.getArtistName());
		albumToUpdate.setGenre(albumRequestToUpdate.getGenre());
		albumToUpdate.setSongName(albumRequestToUpdate.getSongName());
		
		return albumToUpdate;
	}

	public void deleteAlbumById(Long id) {
		Album album  = getAlbumById(id);
		albumRepo.deleteById(album.getId());
	}

}
