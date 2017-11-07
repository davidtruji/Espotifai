/**
 * 
 * Espotifai
 * 
 * @Fichero: Musica.java
 * @Autor: David Trujillo Torres
 * @Fecha: 3 nov. 2017
 */

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
	private AudioFile audioFile;

	/**
	 * Constructor parametrizado de objetos Musica a partir de un tipo File que sea
	 * musica
	 * 
	 * @param file
	 *            El tipo File que contiene el archivo de musica
	 */
	public Musica(File file) {

		audioFile = null;
		archivo = file;
		Artwork art = null;
		try {
			audioFile = AudioFileIO.read(file);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = audioFile.getTagOrCreateDefault();

		if (tag != null) {
			artista = tag.getFirst(FieldKey.ARTIST);
			titulo = tag.getFirst(FieldKey.TITLE);
			album = tag.getFirst(FieldKey.ALBUM);
			ano = tag.getFirst(FieldKey.YEAR);
			genero = tag.getFirst(FieldKey.GENRE);
			tasaBits = audioFile.getAudioHeader().getBitRate();

			// Creacion de la imagen
			art = tag.getFirstArtwork();

		}

		if (art != null) {
			final byte[] data = art.getBinaryData();
			ByteArrayInputStream bytes = new ByteArrayInputStream(data);
			caratula = new Image(bytes);
		}
	}

	/*
	 * 
	 * SETTERS----------------------------------------------------------------------
	 * 
	 * 
	 */

	/**
	 * Configura el tipo File contenido en el objeto Musica
	 * 
	 * @param archivo
	 *            Tipo File del archivo de musica
	 */
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	/**
	 * Configura el artista, tanto en el objeto como en el archivo
	 * 
	 * @param artista
	 *            String con el nombre del artista
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void setArtista(String artista) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.setField(FieldKey.ARTIST, artista);
		audioFile.commit();
		this.artista = artista;
	}

	/**
	 * Configura el titulo de la cancion, tanto en el objeto como en el archivo
	 * 
	 * @param titulo
	 *            String con el titulo de la cancion
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void setTitulo(String titulo) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.setField(FieldKey.TITLE, titulo);
		audioFile.commit();
		this.titulo = titulo;
	}

	/**
	 * Configura el album, tanto en el objeto como en el archivo
	 * 
	 * @param album
	 *            String con el album de la cancion
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void setAlbum(String album) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.setField(FieldKey.ALBUM, album);
		audioFile.commit();
		this.album = album;
	}

	/**
	 * Configura el ano, tanto en el objeto como en el archivo
	 * 
	 * @param ano
	 *            String con el ano de la cancion
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void setAno(String ano) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.setField(FieldKey.YEAR, ano);
		audioFile.commit();
		this.ano = ano;
	}

	/**
	 * Configura el genero, tanto en el objeto como en el archivo
	 * 
	 * @param genero
	 *            String con el genero de la cancion
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void setGenero(String genero) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.setField(FieldKey.GENRE, genero);
		audioFile.commit();
		this.genero = genero;
	}

	/**
	 * Configura la tasa de Kbits/s, en el objeto
	 * 
	 * @param tasaBits
	 *            String con la tasa de Kbits/s de la cancion
	 */
	public void setTasaBits(String tasaBits) {
		this.tasaBits = tasaBits;
	}

	/**
	 * Configura la caratula, tanto en el objeto como en el archivo
	 * 
	 * @param cover
	 *            File que contiene la imagen deseada
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void setCaratula(File cover) throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Artwork a = ArtworkFactory.createArtworkFromFile(cover);

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.deleteArtworkField();
		tag.setField(a);
		audioFile.commit();

		final byte[] data = a.getBinaryData();
		ByteArrayInputStream bytes = new ByteArrayInputStream(data);
		caratula = new Image(bytes);

	}

	/*
	 * 
	 * GETTERS----------------------------------------------------------------------
	 * 
	 * 
	 */

	/**
	 * Devuelve el archivo de la cancion
	 * 
	 * @return Tipo File de la cancion
	 */
	public File getArchivo() {
		return archivo;
	}

	/**
	 * Devuelve el artista de la cancion
	 * 
	 * @return String con el artista de la cancion
	 */
	public String getArtista() {
		return artista;
	}

	/**
	 * Devuelve el titulo de la cancion
	 * 
	 * @return String con el titulo de la cancion
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Devuelve el album de la cancion
	 * 
	 * @return String con el album de la cancion
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Devuelve el ano de la cancion
	 * 
	 * @return String con el ano de la cancion
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * Devuelve el genero de la cancion
	 * 
	 * @return String con el genero de la cancion
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Devuelve la tasa de bits de la cancion
	 * 
	 * @return String con la tasa de bits de la cancion
	 */
	public String getTasaBits() {
		return tasaBits;
	}

	/**
	 * Devuelve la caratula de la cancion
	 * 
	 * @return Image con caratula de la cancion
	 */
	public Image getCaratula() {
		return caratula;
	}

	/**
	 * Devuelve el artista de la cancion, pero leido del fichero
	 * 
	 * @return String con el artista de la cancion
	 */
	public String getTagArtista() {

		try {
			audioFile = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = audioFile.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.ARTIST);
	}

	/**
	 * Devuelve el titulo de la cancion, pero leido del fichero
	 * 
	 * @return String con el titulo de la cancion
	 */
	public String getTagTitulo() {

		try {
			audioFile = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = audioFile.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.TITLE);
	}

	/**
	 * Devuelve el album de la cancion, pero leido del fichero
	 * 
	 * @return String con el album de la cancion
	 */
	public String getTagAlbum() {

		try {
			audioFile = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = audioFile.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.ALBUM);
	}

	/**
	 * Devuelve el ano de la cancion, pero leido del fichero
	 * 
	 * @return String con el ano de la cancion
	 */
	public String getTagAno() {

		try {
			audioFile = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = audioFile.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.YEAR);
	}

	/**
	 * Devuelve el genero de la cancion, pero leido del fichero
	 * 
	 * @return String con el genero de la cancion
	 */
	public String getTagGenero() {

		try {
			audioFile = AudioFileIO.read(archivo);

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		Tag tag = audioFile.getTagOrCreateDefault();
		return tag.getFirst(FieldKey.GENRE);
	}

	/*
	 * 
	 * Metodos para borrar campos del
	 * fichero--------------------------------------------------------------------
	 * 
	 */

	/**
	 * Borra el campo artista del archivo de musica
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void BorrarTagArtista() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.deleteField(FieldKey.ARTIST);
		audioFile.commit();
	}

	/**
	 * Borra el campo titulo del archivo de musica
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void BorrarTagTitulo() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.deleteField(FieldKey.TITLE);
		audioFile.commit();
	}

	/**
	 * Borra el campo album del archivo de musica
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void BorrarTagAlbum() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.deleteField(FieldKey.ALBUM);
		audioFile.commit();
	}

	/**
	 * Borra el campo ano del archivo de musica
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void BorrarTagAno() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.deleteField(FieldKey.YEAR);
		audioFile.commit();
	}

	/**
	 * Borra el campo genero del archivo de musica
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public void BorrarTagGenero() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		Tag tag = audioFile.getTagOrCreateDefault();
		tag.deleteField(FieldKey.GENRE);
		audioFile.commit();
	}

}
