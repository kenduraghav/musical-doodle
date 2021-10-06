package rk.examples.springboot;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;
import rk.examples.springboot.entity.Album;
import rk.examples.springboot.repository.AlbumRepository;

@Slf4j
@Component
public class AlbumInitializer implements CommandLineRunner {

	@Autowired
	AlbumRepository albumRepository;

	@Override
	public void run(String... args) throws Exception {
		
		log.info("Initializing Album data");
		Faker faker = new Faker();

		IntStream.rangeClosed(1, 10).forEach(i -> {

			Album album = Album.builder()
					.artistName(faker.artist().name())
					.genre(faker.music().genre())
					.songName(faker.music().chord()).build();
			
			albumRepository.save(album);
		});
		log.info("Album data initialized successfully");
	}

}
