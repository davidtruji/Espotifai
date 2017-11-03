package espotifai.view;

import java.io.IOException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import espotifai.Main;
import espotifai.model.Musica;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
	@FXML
	private Label ClickCaratula;

	private Stage dialogStage;
	private Musica cancion;
	private Main main=new Main();

	@FXML
	private void initialize() {

	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

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
			// System.out.println("NO COVER FIND");
		}

	}

	public Musica getCancion() {
		return cancion;
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public TextField getArtista() {
		return Artista;
	}

	public void setArtista(TextField artista) {
		Artista = artista;
	}

	public TextField getTitulo() {
		return Titulo;
	}

	public void setTitulo(TextField titulo) {
		Titulo = titulo;
	}

	public TextField getAlbum() {
		return Album;
	}

	public void setAlbum(TextField album) {
		Album = album;
	}

	public TextField getAno() {
		return Ano;
	}

	public void setAno(TextField ano) {
		Ano = ano;
	}

	public TextField getGenero() {
		return Genero;
	}

	public void setGenero(TextField genero) {
		Genero = genero;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void AccionSeleccionarCaratula() {
		//TODO Acabar etiqutar caratula
		System.out.println("VistaEditarEtiquetasController.AccionSeleccionarCaratula()");
		main.CambiarCaratula();
	}

	@FXML
	public void AccionEditarEtiqutas() throws CannotReadException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, CannotWriteException {

		if (!Artista.getText().isEmpty())
			cancion.setTagArtista(Artista.getText());
		else
			cancion.BorrarTagArtista();

		if (!Titulo.getText().isEmpty())
			cancion.setTagTitulo(Titulo.getText());
		else
			cancion.BorrarTagTitulo();

		if (!Album.getText().isEmpty())
			cancion.setTagAlbum(Album.getText());
		else
			cancion.BorrarTagAlbum();

		if (!Ano.getText().isEmpty()) {

			try {
				Integer.parseInt(Ano.getText());
				if (Integer.valueOf(Ano.getText()) > 1000 && Integer.valueOf(Ano.getText()) < 3000) {
					cancion.setTagAno(Ano.getText());
				} else {
					// Main.LanzarDialogoError("El año introducido es incorrecto", "No se etiquetará
					// el año");

				}
			} catch (NumberFormatException e) {

				// Main.LanzarDialogoError("El año introducido es incorrecto", "No se etiquetará
				// el año");

			}

		} else
			cancion.BorrarTagAno();

		if (!Genero.getText().isEmpty())
			cancion.setTagGenero(Genero.getText());
		else
			cancion.BorrarTagGenero();

		cancion.setArtista(cancion.getTagArtista());
		cancion.setTitulo(cancion.getTagTitulo());
		cancion.setAlbum(cancion.getTagAlbum());
		cancion.setAno(cancion.getTagAno());
		cancion.setGenero(cancion.getTagGenero());
		dialogStage.close();
	}

	@FXML
	public void AccionLimpiarCampos() {
		Artista.clear();
		Titulo.clear();
		Album.clear();
		Ano.clear();
		Genero.clear();
	}

	@FXML
	public void AccionCancelar() {
		dialogStage.close();
	}

}
