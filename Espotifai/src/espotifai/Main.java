package espotifai;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import espotifai.model.Musica;
import espotifai.view.VistaPrincipalController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private ObservableList<Musica> musicaDirectorio = FXCollections.observableArrayList();

	public ObservableList<Musica> getMusicaDirectorio() {
		return musicaDirectorio;
	}

	public void setMusicaDirectorio(ObservableList<Musica> musicaDirectorio) {
		this.musicaDirectorio = musicaDirectorio;
	}

	private ObservableList<Musica> playlist = FXCollections.observableArrayList();

	public Main() {
		// playlist.add(new Musica("DJ Rajobos", "Cancion 1"));

	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Espotifai - Gestor de Música");
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(900);
		iniciarRootLayout();

	}

	private void iniciarRootLayout() {
		try {
			// Carga el Layout a partir del FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/VistaPrincipal.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Muestra la escena que contine el layout
			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.show();

			VistaPrincipalController controller = loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public File LanzarDialogoEleccionDirectorio() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Selección de directorio");
		directoryChooser.setInitialDirectory(new File("/"));
		File selectedDir = directoryChooser.showDialog(primaryStage);

		return selectedDir;

	}

	public File LanzarDialogoGenerar() {
		FileChooser f = new FileChooser();
		f.setTitle("Guarde su playlist");
		f.setInitialDirectory(new File("/"));
		// show save dialog:
		File savedFile = f.showSaveDialog(primaryStage);
		return savedFile;
	}

	public void LanzarDialogoPlaylistVacia() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Espotifai - Error");
		alert.setHeaderText("Su playlist está vacia");
		alert.setContentText("Puede añadir canciones pulsando el botón [añadir]");

		alert.showAndWait();
	}

	public void GenerarPlaylist(File f) throws IOException {
		if (f != null) {
			FileWriter writer = new FileWriter(f.getAbsolutePath() + ".m3u");

			// System.out.println("Generando fichero..." + playlist.size() + " Archivos");
			for (int i = 0; i < playlist.size(); i++)
				writer.write(playlist.get(i).getArchivo().getAbsolutePath() + "\n");

			writer.close();

			LanzarDialogoPlaylistGenerada(f);
		}

	}

	public void LanzarDialogoPlaylistGenerada(File f) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Espotifai - Información");
		alert.setHeaderText("Su playlist se ha generado correctamente:");
		alert.setContentText(f.getAbsolutePath());

		alert.showAndWait();
	}

	public void LanzarDialogoCancionRepetida() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Espotifai - Advertencia");
		alert.setHeaderText("Una o mas canciones seleccionadas ya se encuentran en su playlist");
		alert.setContentText("Las canciones repetidas no se añadirán");

		alert.showAndWait();

	}

	public void AnadirMusicaDirectoio(File Dir) {
		String nombre;
		if (Dir != null) {
			File[] musicaEncontrada = Dir.listFiles();

			for (int i = 0; i < musicaEncontrada.length; i++) {
				nombre = musicaEncontrada[i].getName();
				if (nombre.endsWith("mp3") || nombre.endsWith("MP3") || nombre.endsWith("flac")
						|| nombre.endsWith("FLAC") || nombre.endsWith("wav") || nombre.endsWith("WAV")
						|| nombre.endsWith("wma") || nombre.endsWith("WMA") || nombre.endsWith("ogg")
						|| nombre.endsWith("OGG")) {
					musicaDirectorio.add(new Musica(musicaEncontrada[i]));
				}
			}

		}

	}

	public ObservableList<Musica> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(ObservableList<Musica> playlist) {
		this.playlist = playlist;
	}

	public static void main(String[] args) {
		launch(args);

	}
}
