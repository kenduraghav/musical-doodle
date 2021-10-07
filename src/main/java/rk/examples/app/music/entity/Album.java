package rk.examples.app.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="album")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name="song_name")
	private String songName;

	@Column(name="genre")
	private String genre;

	@Column(name="artist_name")
	private String artistName;

}
