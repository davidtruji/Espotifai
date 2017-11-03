package espotifai.model;

import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import org.jaudiotagger.tag.images.ArtworkFactory;

import javafx.scene.image.Image;

public class Musica {

	File archivo;
	private String artista;
	private String titulo;
	private String album;
	private String ano;
	private String genero;
	private String tasaBits;
	private Image caratula;

	public Musica(File file) {

		AudioFile f = null;
		archivo = file;
		Artwork art = null;
		try {
			f = AudioFileIO.read(file);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = f.getTagOrCreateDefault();

		if (tag != null) {
			artista = tag.getFirst(FieldKey.ARTIST);
			titulo = tag.getFirst(FieldKey.TITLE);
			album = tag.getFirst(FieldKey.ALBUM);
			ano = tag.getFirst(FieldKey.YEAR);
			genero = tag.getFirst(FieldKey.GENRE);
			tasaBits = f.getAudioHeader().getBitRate();

			// Creacion de la imagen
			art = tag.getFirstArtwork();

		}

		if (art != null) {
			final byte[] data = art.getBinaryData();
			ByteArrayInputStream bytes = new ByteArrayInputStream(data);
			caratula = new Image(bytes);
		}
	}

	public String getTasaBits() {
		return tasaBits;
	}

	public void setTasaBits(String tasaBits) {
		this.tasaBits = tasaBits;
	}

	public Image getCaratula() {
		return caratula;
	}

	public void setCaratula(File cover)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

		
		Artwork a = ArtworkFactory.createArtworkFromFile(cover);
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
		tag.deleteArtworkField();
		tag.setField(a);
		f.commit();
		
		final byte[] data = a.getBinaryData();
		ByteArrayInputStream bytes = new ByteArrayInputStream(data);
		caratula = new Image(bytes);
		

	}

	public void setTagArtista(String Artista) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
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
		Tag tag = f.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.ARTIST);
	}

	public void BorrarTagArtista() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
		tag.deleteField(FieldKey.ARTIST);
		f.commit();
	}

	public void setTagTitulo(String Titulo) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
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
		Tag tag = f.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.TITLE);
	}

	public void BorrarTagTitulo() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
		tag.deleteField(FieldKey.TITLE);
		f.commit();
	}

	public void setTagAlbum(String Album) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
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
		Tag tag = f.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.ALBUM);
	}

	public void BorrarTagAlbum() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
		tag.deleteField(FieldKey.ALBUM);
		f.commit();
	}

	public void setTagAno(String Ano) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
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
		Tag tag = f.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.YEAR);
	}

	public void BorrarTagAno() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
		tag.deleteField(FieldKey.YEAR);
		f.commit();
	}

	public void setTagGenero(String Genero) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
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
		Tag tag = f.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.GENRE);
	}

	public void BorrarTagGenero() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(archivo);
		Tag tag = f.getTagOrCreateDefault();
		tag.deleteField(FieldKey.GENRE);
		f.commit();
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
