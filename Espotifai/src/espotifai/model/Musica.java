package espotifai.model;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Musica {

	File archivo;
	private StringProperty artista;
	private StringProperty titulo;
	private StringProperty album;
	private StringProperty ano;

	// public Musica(String n, String t) {
	// artista = new SimpleStringProperty(n);
	// titulo = new SimpleStringProperty(t);
	// }

	public Musica(File file) {

		AudioFile f = null;
		try {
			f = AudioFileIO.read(file);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();

		archivo = file;
		artista = new SimpleStringProperty(tag.getFirst(FieldKey.ARTIST));
		titulo = new SimpleStringProperty(tag.getFirst(FieldKey.TITLE));
		album = new SimpleStringProperty(tag.getFirst(FieldKey.ALBUM));
		ano = new SimpleStringProperty(tag.getFirst(FieldKey.YEAR));

	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public StringProperty getAlbum() {
		return album;
	}

	public void setAlbum(StringProperty album) {
		this.album = album;
	}

	public StringProperty getAno() {
		return ano;
	}

	public void setAno(StringProperty ano) {
		this.ano = ano;
	}

	public StringProperty getArtista() {
		return artista;
	}

	public void setArtista(StringProperty artista) {
		this.artista = artista;
	}

	public StringProperty getTitulo() {
		return titulo;
	}

	public void setTitulo(StringProperty titulo) {
		this.titulo = titulo;
	}

}
