/**
 * 
 * Espotifai
 * 
 * @Fichero: VistaPrincipalController.java
 * @Autor: David Trujillo Torres
 * @Fecha: 3 nov. 2017
 */

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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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

	/**
	 * Ajusta la informacion del main principal en el del controlador
	 * 
	 * @param main
	 *            Instancia Main
	 */
	public void setMain(Main main) {
		this.main = main;

		// AÃ±ade las canciones a la tabla playlist
		musicaTableDir.setItems(main.getMusicaDirectorio());

		// AÃ±ade las canciones a la tabla playlist
		musicaTable.setItems(main.getPlaylist());

	}

	/**
	 * Accion que elimina todas las canciones de una playlist
	 */
	@FXML
	public void AccionLimpiarPlaylist() {
		main.getPlaylist().clear();
		actualizarNumeroCancionesPlayslist();
		musicaTable.setDisable(true);
	}

	/**
	 * Accion para elegir un directorio en el que buscar musica
	 */
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

	/**
	 * Accion de añadir toda la musica de un directorio en una playlist
	 */
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
					" Estas canciones no se añadirán...");

		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() > 0)
			musicaTable.setDisable(false);

	}

	/**
	 * Accion de borrar una cancion de la playlist
	 */
	@FXML
	public void AcccionQuitar() {

		musicaTable.getItems().removeAll(musicaTable.getSelectionModel().getSelectedItems());
		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() == 0)
			musicaTable.setDisable(true);
	}

	/**
	 * Accion que aÃ±ade la musica seleccionada de un directorio a una playlist
	 */
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
					" Estas canciones no se añadirán...");

		actualizarNumeroCancionesPlayslist();

		if (main.getPlaylist().size() > 0)
			musicaTable.setDisable(false);
	}

	/**
	 * Accion de generar la playlist creada
	 * 
	 * @throws IOException
	 */
	@FXML
	void AccionGenerarPlaylist() throws IOException {
		if (!main.getPlaylist().isEmpty()) {
			File f = main.LanzarDialogoGenerar();
			if (f != null) {
				String sSistemaOperativo = System.getProperty("os.name");

				if (sSistemaOperativo.contains("Linux")) {
					main.GenerarPlaylist(f, "\n");
				} else if (sSistemaOperativo.contains("Windows")) {
					main.GenerarPlaylist(f, "\r\n");
				}
				main.LanzarDialogoInformacion("Su playlist se ha generado correctamente:", f.getPath());

			}
		} else
			main.LanzarDialogoError("No ha añadido ninguna canción a su playlist",
					"Para añadir música pulse el boton añadir");
	}

	/**
	 * Accion de generar indice
	 * 
	 * @throws IOException
	 */
	@FXML
	private void AccionGenerarIndice() throws IOException {
		File f = main.LanzarDialogoEleccionDirectorio("Seleccione el directorio que contiene la música");
		if (f != null) {
			String sSistemaOperativo = System.getProperty("os.name");

			if (sSistemaOperativo.contains("Linux")) {
				main.GenerarFicheroIndice(f, "//", "\n");
			} else if (sSistemaOperativo.contains("Windows")) {
				main.GenerarFicheroIndice(f, "\\", "\r\n");
			}

			main.LanzarDialogoInformacion("Índice generado correctamente:", f.getAbsolutePath());
		}
	}

	/**
	 * Accion de editar etiquetas de una cancion seleccionada
	 * 
	 * @throws IOException
	 */
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

	/**
	 * Intenta reproducir la cancion seleccionada
	 * 
	 * @throws IOException
	 */
	private void AbrirEnReproducctor(TableView<Musica> t) throws IOException {
		Musica cancion = t.getSelectionModel().getSelectedItem();
		String sSistemaOperativo = System.getProperty("os.name");

		if (sSistemaOperativo.contains("Linux")) {
			// Array de strings que contiene el comando que abre el reproductor
			String[] comandoLinux = { "vlc", cancion.getArchivo().getAbsolutePath() };

			try {
				@SuppressWarnings("unused")
				Process process = Runtime.getRuntime().exec(comandoLinux);

			} catch (Exception e) {
				main.LanzarDialogoError("Error al intentar abrir el fichero",
						"Intentelo de nuevo, o instale VLC si no lo tiene");
			}

		} else if (sSistemaOperativo.contains("Windows")) {
			System.out.println("Windows");
			String ProgramFiles = System.getenv("ProgramFiles");
			// Array de strings que contiene el comando que abre el reproductor
			String[] comandoWindows = { ProgramFiles + "\\VideoLAN\\VLC\\vlc.exe",
					cancion.getArchivo().getAbsolutePath() };

			try {
				@SuppressWarnings("unused")
				Process process = Runtime.getRuntime().exec(comandoWindows);

			} catch (Exception e) {
				main.LanzarDialogoError("Error al intentar abrir el fichero",
						"Intentelo de nuevo, o instale VLC si no lo tiene");
			}
		}
	}

	/**
	 * Accion de salir del programa y cerrarlo por completo
	 */
	@FXML
	private void AccionSalir() {
		Platform.exit();

	}

	/**
	 * Metodo que actualiza el contador de canciones de playlist
	 */
	private void actualizarNumeroCancionesPlayslist() {
		numeroCanciones.setText(Integer.toString(main.getPlaylist().size()) + " Canciones");
	}

	/**
	 * Acciones que se ejecutan automaticamente al generar el controlador de la
	 * vista principal
	 */
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

		musicaTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						try {
							AbrirEnReproducctor(musicaTable);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
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

		musicaTableDir.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						try {
							AbrirEnReproducctor(musicaTableDir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
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

}
