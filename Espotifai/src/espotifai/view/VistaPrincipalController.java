package espotifai.view;

import java.io.File;
import java.io.IOException;
import espotifai.Main;
import espotifai.model.Musica;
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
	private TableColumn<Musica, String> ArtistaDir;
	@FXML
	private TableColumn<Musica, String> TituloDir;
	@FXML
	private TableColumn<Musica, String> AlbumDir;
	@FXML
	private TableColumn<Musica, String> AnoDir;

	@FXML
	private TableView<Musica> musicaTable;
	@FXML
	private TableColumn<Musica, String> Artista;
	@FXML
	private TableColumn<Musica, String> Titulo;
	@FXML
	private TableColumn<Musica, String> Album;
	@FXML
	private TableColumn<Musica, String> Ano;

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
				}
			}
		});

		// Inicializa la tabla de directorio
		musicaTableDir.setDisable(true);
		musicaTableDir.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ArtistaDir.setCellValueFactory(cellData -> cellData.getValue().getArtista());
		TituloDir.setCellValueFactory(cellData -> cellData.getValue().getTitulo());
		AlbumDir.setCellValueFactory(cellData -> cellData.getValue().getAlbum());
		AnoDir.setCellValueFactory(cellData -> cellData.getValue().getAno());

		// Inicializa la tabla de playlist
		musicaTable.setDisable(true);
		musicaTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		Artista.setCellValueFactory(cellData -> cellData.getValue().getArtista());
		Titulo.setCellValueFactory(cellData -> cellData.getValue().getTitulo());
		Album.setCellValueFactory(cellData -> cellData.getValue().getAlbum());
		Ano.setCellValueFactory(cellData -> cellData.getValue().getAno());

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

		main.getMusicaDirectorio().clear();

		File dir = main.LanzarDialogoEleccionDirectorio();

		if (dir != null) {
			nombreCarpeta.setVisible(true);
			nombreCarpeta.setText(dir.getName());
			rutaSeleccionada.setText(dir.getAbsolutePath());
			main.AnadirMusicaDirectoio(dir);
			numeroCancionesDir.setVisible(true);
			numeroCancionesDir.setText(Integer.toString(main.getMusicaDirectorio().size()) + " Canciones encontradas");
			musicaTableDir.setDisable(false);
			// musicaTable.setDisable(false);

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
			main.LanzarDialogoCancionRepetida();

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
			main.LanzarDialogoCancionRepetida();

		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() > 0)
			musicaTable.setDisable(false);
	}

	@FXML
	void AccionGenerarPlaylist() throws IOException {
		if (!main.getPlaylist().isEmpty())
			main.GenerarPlaylist(main.LanzarDialogoGenerar());
		else
			main.LanzarDialogoPlaylistVacia();
	}

	//TODO
	@FXML
	private void AccionGenerarIndice() {

		
		main.GenerarFicheroIndice(main.LanzarDialogoEleccionDirectorio()," ");
		
		
		
	}

	private void actualizarNumeroCancionesPlayslist() {

		numeroCanciones.setText(Integer.toString(main.getPlaylist().size()) + " Canciones");

	}

}
