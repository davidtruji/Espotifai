package espotifai.view;

import java.io.File;
import java.io.IOException;
import espotifai.Main;
import espotifai.model.Musica;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class VistaPrincipalController {

	@FXML
	private TextField rutaSeleccionada;
	@FXML
	private Label nombreCarpeta;
	@FXML
	private Label numeroCancionesDir;
	@FXML
	private Label numeroCanciones;
	@FXML
	private TableView<Musica> musicaTableDir;

	@FXML
	private TableColumn<Musica, String> FicheroDir;
	@FXML
	private TableColumn<Musica, String> ArtistaDir;
	@FXML
	private TableColumn<Musica, String> TituloDir;
	@FXML
	private TableColumn<Musica, String> AlbumDir;
	@FXML
	private TableColumn<Musica, String> AnoDir;
	@FXML
	private TableColumn<Musica, String> GeneroDir;

	@FXML
	private TableView<Musica> musicaTable;
	@FXML
	private TableColumn<Musica, String> Fichero;

	private Main main;

	@FXML
	private void initialize() {
		rutaSeleccionada.setDisable(true);
		nombreCarpeta.setVisible(false);
		numeroCancionesDir.setVisible(false);

		musicaTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case DELETE:
					AcccionQuitar();
					break;
				default:
					break;
				}
			}
		});

		musicaTableDir.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case ENTER:
					AccionAnadirMusicaAPlaylist();
					break;
				default:
					break;
				}
			}
		});

		// Inicializa la tabla de directorio
		musicaTableDir.setDisable(true);
		musicaTableDir.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		FicheroDir
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArchivo().getName()));
		ArtistaDir.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtista()));
		TituloDir.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
		AlbumDir.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlbum()));
		AnoDir.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAno()));
		GeneroDir.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));

		// Inicializa la tabla de playlist
		musicaTable.setDisable(true);
		musicaTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		Fichero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArchivo().getName()));

	}

	public void setMain(Main main) {
		this.main = main;

		// Añade las canciones a la tabla playlist
		musicaTableDir.setItems(main.getMusicaDirectorio());

		// Añade las canciones a la tabla playlist
		musicaTable.setItems(main.getPlaylist());

	}

	@FXML
	public void AccionLimpiarPlaylist() {
		main.getPlaylist().clear();
		actualizarNumeroCancionesPlayslist();
		musicaTable.setDisable(true);
	}

	@FXML
	public void AccionSeleccionarDirectorio() {

		File dir = main.LanzarDialogoEleccionDirectorio("Por favor seleccione un directorio");

		if (dir != null) {
			nombreCarpeta.setVisible(true);
			nombreCarpeta.setText(dir.getName());
			rutaSeleccionada.setText(dir.getAbsolutePath());
			main.getMusicaDirectorio().clear();
			main.AnadirMusicaDirectoio(dir);
			numeroCancionesDir.setVisible(true);
			numeroCancionesDir.setText(Integer.toString(main.getMusicaDirectorio().size()) + " Canciones encontradas");
			musicaTableDir.setDisable(false);
		}
	}

	@FXML
	public void AcccionAnadirTodo() {
		boolean rep = false;
		ObservableList<Musica> listaSeleccionados = musicaTableDir.getItems();

		for (int i = 0; i < listaSeleccionados.size(); i++) {
			if (!musicaTable.getItems().contains(listaSeleccionados.get(i))) {
				musicaTable.getItems().add(listaSeleccionados.get(i));
			} else {
				rep = true;
			}

		}

		if (rep)
			main.LanzarDialogoAdvertencia("Una o mas caciones, ya se encuentran en la playlist",
					" Estas canciones no se añarirán...");

		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() > 0)
			musicaTable.setDisable(false);

	}

	@FXML
	public void AcccionQuitar() {

		musicaTable.getItems().removeAll(musicaTable.getSelectionModel().getSelectedItems());
		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() == 0)
			musicaTable.setDisable(true);
	}

	@FXML
	public void AccionAnadirMusicaAPlaylist() {
		boolean rep = false;
		ObservableList<Musica> listaSeleccionados = musicaTableDir.getSelectionModel().getSelectedItems();

		for (int i = 0; i < listaSeleccionados.size(); i++) {
			if (!musicaTable.getItems().contains(listaSeleccionados.get(i))) {
				musicaTable.getItems().add(listaSeleccionados.get(i));
			} else {
				rep = true;
			}

		}

		if (rep)
			main.LanzarDialogoAdvertencia("Una o mas caciones, ya se encuentran en la playlist",
					" Estas canciones no se añarirán...");

		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() > 0)
			musicaTable.setDisable(false);
	}

	@FXML
	void AccionGenerarPlaylist() throws IOException {
		if (!main.getPlaylist().isEmpty()) {
			File f = main.LanzarDialogoGenerar();
			if (f != null) {
				main.GenerarPlaylist(f);
				main.LanzarDialogoInformacion("Su playlist se ha generado correctamente:", f.getPath());
			}
		} else
			main.LanzarDialogoError("No ha añadido ninguna canción a su playlist",
					"Para añadir música pulse el boton añadir");
	}

	@FXML
	private void AccionGenerarIndice() throws IOException {
		File f = main.LanzarDialogoEleccionDirectorio("Seleccione el directorio que contiene la música");
		if (f != null) {
			main.GenerarFicheroIndice(f);
			main.LanzarDialogoInformacion("Índice generado correctamente:", f.getAbsolutePath());
		}
	}

	@FXML
	private void AccionEditarEtiquetas() throws IOException {
		ObservableList<Musica> listaSeleccionados = musicaTableDir.getSelectionModel().getSelectedItems();
		if (listaSeleccionados.size() == 1) {
			main.LanzarDialogoEditar(listaSeleccionados.get(0));
			musicaTableDir.refresh();
		} else {
			main.LanzarDialogoAdvertencia("Ninguna canción del directorio seleccionada",
					"Debe seleccionar la canción que desee editar");
		}

	}

	@FXML
	private void AccionSalir() {
		Platform.exit();

	}

	private void actualizarNumeroCancionesPlayslist() {
		numeroCanciones.setText(Integer.toString(main.getPlaylist().size()) + " Canciones");
	}

}
