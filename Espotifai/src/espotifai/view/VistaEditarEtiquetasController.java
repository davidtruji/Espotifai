package espotifai.view;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import espotifai.Main;
import espotifai.model.Musica;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * Espotifai
 * 
 * @Fichero: VistaEditarEtiquetasController.java
 * @Autor: David Trujillo Torres
 * @Fecha: 3 nov. 2017
 */
public class VistaEditarEtiquetasController {

	@FXML
	private TextField Artista;
	@FXML
	private TextField Titulo;
	@FXML
	private TextField Album;
	@FXML
	private TextField Ano;
	@FXML
	private TextField Genero;
	@FXML
	private ImageView caratula;

	private Stage dialogStage;
	private Musica cancion;
	private Main main = new Main();
	@SuppressWarnings("unused")
	private boolean caratulaCambiada = false;
	private File imagenCaratula = null;

	/**
	 * Ajusta el Stage
	 * 
	 * @param dialogStage2
	 *            Tipo Stage que genera este dialogo
	 */
	public void setDialogStage(Stage dialogStage2) {
		dialogStage = dialogStage2;
	}

	/**
	 * Configura la informacion de la cancion de la que se van a editar las
	 * etiquetas
	 * 
	 * @param cancion
	 *            Tipo Musica con la cancion a editar
	 * @throws IOException
	 */
	public void setCancion(Musica cancion) throws IOException {
		this.cancion = cancion;

		try {
			Artista.setText(cancion.getArtista());
		} catch (Exception e) {
			Artista.setText("");
		}

		try {
			Titulo.setText(cancion.getTitulo());
		} catch (Exception e) {
			Titulo.setText("");
		}

		try {
			Album.setText(cancion.getAlbum());
		} catch (Exception e) {
			Album.setText("");
		}

		try {
			Ano.setText(cancion.getAno());
		} catch (Exception e) {
			Ano.setText("");
		}

		try {
			Genero.setText(cancion.getGenero());
		} catch (Exception e) {
			Genero.setText("");
		}

		try {
			if (cancion.getCaratula() != null) {
				caratula.setImage(cancion.getCaratula());

			}
		} catch (Exception e) {
		}

	}

	/**
	 * Accion de elegir cartaula, lanza un dialogo de seleccion de imagen y la pone
	 * en el objeto Musica
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	@FXML
	public void AccionSeleccionarCaratula() throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		ExtensionFilter filter = new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg");
		imagenCaratula = main.LanzarDialogoEleccionArchivo("Espotifai - Seleción de carátula", filter);
		if (imagenCaratula != null) {
			caratula.setImage(new Image("file:" + imagenCaratula.getAbsolutePath()));
			caratulaCambiada = true;
		}
	}

	/**
	 * Accion de editar todas las etiquetas de la cancion que han sido modificadas
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	@FXML
	public void AccionEditarEtiqutas() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		if (!Artista.getText().isEmpty())
			cancion.setArtista(Artista.getText());
		else
			cancion.BorrarTagArtista();

		if (!Titulo.getText().isEmpty())
			cancion.setTitulo(Titulo.getText());
		else
			cancion.BorrarTagTitulo();

		if (!Album.getText().isEmpty())
			cancion.setAlbum(Album.getText());
		else
			cancion.BorrarTagAlbum();

		if (!Ano.getText().isEmpty()) {

			try {
				Integer.parseInt(Ano.getText());
				if (Integer.valueOf(Ano.getText()) > 1000 && Integer.valueOf(Ano.getText()) < 3000) {
					cancion.setAno(Ano.getText());
				} else {

				}
			} catch (NumberFormatException e) {

			}

		} else
			cancion.BorrarTagAno();

		if (!Genero.getText().isEmpty())
			cancion.setGenero(Genero.getText());
		else
			cancion.BorrarTagGenero();

		if (imagenCaratula != null) {
			cancion.setCaratula(imagenCaratula);
		}

		dialogStage.close();
	}

	/**
	 * Limpia todos los textFields de la ventana
	 */
	@FXML
	public void AccionLimpiarCampos() {
		Artista.clear();
		Titulo.clear();
		Album.clear();
		Ano.clear();
		Genero.clear();
	}

	/**
	 * Accion de cancelar, Cierra el dialogo
	 */
	@FXML
	public void AccionCancelar() {
		dialogStage.close();
	}

}
