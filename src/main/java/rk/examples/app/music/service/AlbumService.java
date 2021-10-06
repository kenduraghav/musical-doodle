package rk.examples.app.music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rk.examples.app.music.entity.Album;
import rk.examples.app.music.model.AlbumRequest;
import rk.examples.app.music.repository.AlbumRepository;

@Service
public class AlbumService {

	@Autowired
	private AlbumRepository repository;
	
	public Long createNewAlbum(AlbumRequest request) {
		// TODO Auto-generated method stub
		
		Album album = Album.builder()
				.artistName(request.getArtistName())
				.songName(request.getSongName())
				.genre(request.getGenre()).build();
		
		album = repository.save(album);
		return album.getId();
	}

}
