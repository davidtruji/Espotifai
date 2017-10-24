package espotifai.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Musica {

	File archivo;
	private StringProperty artista;
	private StringProperty titulo;
	private StringProperty album;
	private StringProperty ano;
	private StringProperty genero;
	private Image caratula;

	public Musica(File file) {

		AudioFile f = null;
		archivo = file;
		Artwork art=null;
		try {
			f = AudioFileIO.read(file);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}

		Tag tag = f.getTag();

		if (tag != null) {
			// TODO BUGS NULL POINTER
			artista = new SimpleStringProperty(tag.getFirst(FieldKey.ARTIST));
			titulo = new SimpleStringProperty(tag.getFirst(FieldKey.TITLE));
			album = new SimpleStringProperty(tag.getFirst(FieldKey.ALBUM));
			ano = new SimpleStringProperty(tag.getFirst(FieldKey.YEAR));
			genero = new SimpleStringProperty(tag.getFirst(FieldKey.GENRE));
			// Creacion de la imagen
			art = tag.getFirstArtwork();
		}

		if (art != null) {
			final byte[] data = art.getBinaryData();
			ByteArrayInputStream bytes = new ByteArrayInputStream(data);
			caratula = new Image(bytes);
		}
	}

	public Image getCaratula() {
		return caratula;
	}

	public void setCaratula(Image caratula) {
		this.caratula = caratula;
	}

	public void setTagArtista(String Artista) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.setField(FieldKey.ARTIST, Artista);
		f.commit();
	}

	public String getTagArtista() {
		AudioFile f = null;
		try {
			f = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();
		return tag.getFirst(FieldKey.ARTIST);
	}

	public void BorrarTagArtista() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.deleteField(FieldKey.ARTIST);
		f.commit();
	}

	public void setTagTitulo(String Titulo) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.setField(FieldKey.TITLE, Titulo);
		f.commit();
	}

	public String getTagTitulo() {
		AudioFile f = null;
		try {
			f = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();
		return tag.getFirst(FieldKey.TITLE);
	}

	public void BorrarTagTitulo() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.deleteField(FieldKey.TITLE);
		f.commit();
	}

	public void setTagAlbum(String Album) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.setField(FieldKey.ALBUM, Album);
		f.commit();
	}

	public String getTagAlbum() {
		AudioFile f = null;
		try {
			f = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();
		return tag.getFirst(FieldKey.ALBUM);
	}

	public void BorrarTagAlbum() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.deleteField(FieldKey.ALBUM);
		f.commit();
	}

	public void setTagAno(String Ano) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.setField(FieldKey.YEAR, Ano);
		f.commit();
	}

	public String getTagAno() {
		AudioFile f = null;
		try {
			f = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();
		return tag.getFirst(FieldKey.YEAR);
	}

	public void BorrarTagAno() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.deleteField(FieldKey.YEAR);
		f.commit();
	}

	public void setTagGenero(String Genero) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.setField(FieldKey.GENRE, Genero);
		f.commit();
	}

	public String getTagGenero() {
		AudioFile f = null;
		try {
			f = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();
		return tag.getFirst(FieldKey.GENRE);
	}

	public void BorrarTagGenero() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTag();
		tag.deleteField(FieldKey.GENRE);
		f.commit();
	}

	public StringProperty getGenero() {
		return genero;
	}

	public void setGenero(StringProperty genero) {
		this.genero = genero;
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
