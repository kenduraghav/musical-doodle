package rk.examples.app.music.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumRequest {

	private Long id;
	
	@NotEmpty
	@Size(min = 2,max = 20)
	private String songName;
	
	@NotEmpty
	@Size(min = 2,max = 10)
	private String genre;
	
	@NotEmpty
	@Size(min = 2,max = 20)
	private String artistName;
}
