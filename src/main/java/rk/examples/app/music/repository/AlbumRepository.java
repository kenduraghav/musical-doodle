package rk.examples.app.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rk.examples.app.music.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
