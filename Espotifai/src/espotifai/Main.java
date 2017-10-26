package espotifai;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import espotifai.model.Musica;
import espotifai.view.VistaEditarEtiquetasController;
import espotifai.view.VistaPrincipalController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private int ContadorIndice = 0;
	private float tamBiblioteca = 0;
	private ObservableList<Musica> playlist = FXCollections.observableArrayList();
	private ObservableList<Musica> musicaDirectorio = FXCollections.observableArrayList();
	
	public ObservableList<Musica> getMusicaDirectorio() {
		return musicaDirectorio;
	}

	public void setMusicaDirectorio(ObservableList<Musica> musicaDirectorio) {
		this.musicaDirectorio = musicaDirectorio;
	}

	public Main() {
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

	public File LanzarDialogoEleccionDirectorio(String titulo) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(titulo);
		directoryChooser.setInitialDirectory(new File("/"));
		File selectedDir = directoryChooser.showDialog(primaryStage);
		return selectedDir;
	}

	public void LanzarDialogoEditar(Musica cancion) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/VistaEditarEtiquetas.fxml"));
		AnchorPane page;
		page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.setTitle("Espotifai - Editar etiquetas");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		VistaEditarEtiquetasController controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setCancion(cancion);
		dialogStage.showAndWait();
	}

	public File LanzarDialogoGenerar() {
		FileChooser f = new FileChooser();
		f.setTitle("Guarde su playlist");
		f.setInitialDirectory(new File("/"));
		File savedFile = f.showSaveDialog(primaryStage);
		return savedFile;
	}

	public void LanzarDialogoError(String header, String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Espotifai - Error");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void GenerarPlaylist(File f) throws IOException {
		FileWriter writer = new FileWriter(f.getAbsolutePath() + ".m3u");
		writer.write("#PLAYLIST GENERADA POR ESPOTIFAI" + "\n");
		for (int i = 0; i < playlist.size(); i++)
			writer.write(playlist.get(i).getArchivo().getAbsolutePath() + "\n");
		writer.close();
	}

	public void LanzarDialogoInformacion(String header, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Espotifai - Información");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void LanzarDialogoAdvertencia(String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Espotifai - Advertencia");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	private boolean esMusica(File f) {
		String nombre = f.getName();
		if (nombre.endsWith("mp3") || nombre.endsWith("MP3") || nombre.endsWith("flac") || nombre.endsWith("FLAC")
				|| nombre.endsWith("wav") || nombre.endsWith("WAV") || nombre.endsWith("wma") || nombre.endsWith("WMA")
				|| nombre.endsWith("ogg") || nombre.endsWith("OGG"))
			return true;
		else
			return false;
	}

	// TODO Hacer dialogo de carga y en un nuevo thread
	public void AnadirMusicaDirectoio(File Dir) {
		File[] musicaEncontrada = Dir.listFiles();
		for (int i = 0; i < musicaEncontrada.length; i++) {
			if (esMusica(musicaEncontrada[i]))
				musicaDirectorio.add(new Musica(musicaEncontrada[i]));
		}
	}

	private String fechaActual() {
		Calendar c = Calendar.getInstance();
		String h = Integer.toString(c.get(Calendar.HOUR));
		String m = Integer.toString(c.get(Calendar.MINUTE));
		String s = Integer.toString(c.get(Calendar.SECOND));
		String hora = (h + ":" + m + ":" + s);
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH));
		String annio = Integer.toString(c.get(Calendar.YEAR));
		return (dia + "-" + mes + "-" + annio + "_" + hora);
	}

	public void GenerarFicheroIndiceR(File f, FileWriter fw, String sep) throws IOException {
		File[] ficheros = f.listFiles();
		for (int i = 0; i < ficheros.length; i++) {
			if (esMusica(ficheros[i])) {
				fw.write(sep + ficheros[i].getName() + "\n");
				ContadorIndice++;
				tamBiblioteca = tamBiblioteca + ficheros[i].length();
			} else if (ficheros[i].isDirectory()) {
				fw.write("\n");
				fw.write("\n");
				fw.write(sep + "[" + ficheros[i].getName() + "...]" + "\n");
				String nuevo_separador;
				nuevo_separador = sep + "  ";
				GenerarFicheroIndiceR(ficheros[i], fw, nuevo_separador);
			}
		}
	}

	public void GenerarFicheroIndice(File f) throws IOException {
		FileWriter writer = new FileWriter(f.getAbsolutePath() + "//Indice_" + fechaActual() + ".txt");
		writer.write("ÍNDICE DE BIBLIOTECA: " + f.getAbsolutePath() + " GENERADO POR ESPOTIFAI" + "\n");
		String sep = "  ";
		ContadorIndice = 0;
		tamBiblioteca = 0;
		GenerarFicheroIndiceR(f, writer, sep);
		writer.write("\n\n\n");
		writer.write(ContadorIndice + " CANCIONES ENCONTRADAS EN: " + f.getAbsolutePath() + "\n");
		if (tamBiblioteca > 1000000000)
			writer.write(Math.round((tamBiblioteca / 1000000000.0) * 100.0) / 100.0 + " GB DE MÚSICA" + "\n");
		else
			writer.write(Math.round((tamBiblioteca / 1000000.0) * 100.0) / 100.0 + " MB DE MÚSICA" + "\n");
		writer.close();
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
