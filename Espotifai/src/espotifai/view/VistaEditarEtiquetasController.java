package espotifai.view;

import java.io.IOException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import espotifai.model.Musica;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
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

	private Stage dialogStage;
	private Musica cancion;

	@FXML
	private void initialize() {

	}

	public void setCancion(Musica cancion) throws IOException {
		this.cancion = cancion;

		try {
			Artista.setText(cancion.getArtista().get());
		} catch (Exception e) {
			Artista.setText("");
		}

		try {
			Titulo.setText(cancion.getTitulo().get());
		} catch (Exception e) {
			Titulo.setText("");
		}

		try {
			Album.setText(cancion.getAlbum().get());
		} catch (Exception e) {
			Album.setText("");
		}

		try {
			Ano.setText(cancion.getAno().get());
		} catch (Exception e) {
			Ano.setText("");
		}

		try {
			Genero.setText(cancion.getGenero().get());
		} catch (Exception e) {
			Genero.setText("");
		}

		try {
			//caratula.setImage(cancion.getCaratula().);
		} catch (Exception e) {
			System.out.println("NO COVER FIND");
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

		cancion.setArtista(new SimpleStringProperty(cancion.getTagArtista()));
		cancion.setTitulo(new SimpleStringProperty(cancion.getTagTitulo()));
		cancion.setAlbum(new SimpleStringProperty(cancion.getTagAlbum()));
		cancion.setAno(new SimpleStringProperty(cancion.getTagAno()));
		cancion.setGenero(new SimpleStringProperty(cancion.getTagGenero()));
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
