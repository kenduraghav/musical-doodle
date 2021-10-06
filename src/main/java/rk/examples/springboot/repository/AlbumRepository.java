package rk.examples.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rk.examples.springboot.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
