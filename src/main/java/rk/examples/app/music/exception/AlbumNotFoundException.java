package rk.examples.app.music.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlbumNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlbumNotFoundException(String message) {
		super(message);
	}
}
