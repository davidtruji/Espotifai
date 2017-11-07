/**
 * 
 * Espotifai
 * 
 * @Fichero: Main.java
 * @Autor: David Trujillo Torres
 * @Fecha: 3 nov. 2017
 */

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
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private int ContadorIndice = 0;
	private float tamBiblioteca = 0;
	private ObservableList<Musica> playlist = FXCollections.observableArrayList();
	private ObservableList<Musica> musicaDirectorio = FXCollections.observableArrayList();

	/*
	 * 
	 * SETTERS----------------------------------------------------------------------
	 * 
	 */

	/**
	 * Set del array de canciones playlist
	 * 
	 * @param playlist
	 *            Array de Tipos Musica
	 */
	public void setPlaylist(ObservableList<Musica> playlist) {
		this.playlist = playlist;
	}

	/**
	 * Set del array de canciones musicaDirectorio
	 * 
	 * @param musicaDirectorio
	 *            Array de Tipos Musica
	 */
	public void setMusicaDirectorio(ObservableList<Musica> musicaDirectorio) {
		this.musicaDirectorio = musicaDirectorio;
	}

	/*
	 * 
	 * GETTERS----------------------------------------------------------------------
	 * 
	 */

	/**
	 * Devuelve el Array de playlist
	 * 
	 * @return Array de Tipos Musica
	 */
	public ObservableList<Musica> getPlaylist() {
		return playlist;
	}

	/**
	 * Devuelve el Array de musicaDirectorio
	 * 
	 * @return Array de Tipos Musica
	 */
	public ObservableList<Musica> getMusicaDirectorio() {
		return musicaDirectorio;
	}

	/*
	 * 
	 * DIALOGOS---------------------------------------------------------------------
	 * 
	 */

	/**
	 * Lanza un dialogo para elegir un directorio
	 * 
	 * @param titulo
	 *            El titulo de la ventana
	 * @return
	 */
	public File LanzarDialogoEleccionDirectorio(String titulo) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(titulo);
		directoryChooser.setInitialDirectory(new File("/"));
		File selectedDir = directoryChooser.showDialog(primaryStage);
		return selectedDir;
	}

	/**
	 * Lanza un dialogo para elegir un archivo
	 * 
	 * @param titulo
	 *            Titulo de la ventana
	 * @param filter
	 *            Filtro que contendra las extensiones de fichero permitidas
	 * @return
	 */
	public File LanzarDialogoEleccionArchivo(String titulo, ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(titulo);
		fileChooser.setInitialDirectory(new File("/"));
		fileChooser.getExtensionFilters().add(filter);
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		return selectedFile;
	}

	/**
	 * Lanza el dialogo para editar etiquetas de una cancion
	 * 
	 * @param cancion
	 *            Cancion a editar
	 * @throws IOException
	 */
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

	/**
	 * Lanza el dialogo para que elija el directorio de su playlist
	 * 
	 * @return Retorna el directorio seleccionado
	 */
	public File LanzarDialogoGenerar() {
		FileChooser f = new FileChooser();
		f.setTitle("Guarde su playlist");
		f.setInitialDirectory(new File("/"));
		File savedFile = f.showSaveDialog(primaryStage);
		return savedFile;
	}

	/**
	 * Lanza un dialogo de error con informacion de este
	 * 
	 * @param header
	 *            Texto cabecera del error
	 * @param text
	 *            Texto al detalle del error
	 */
	public void LanzarDialogoError(String header, String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Espotifai - Error");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	/**
	 * Lanza un dialogo de informacion
	 * 
	 * @param header
	 *            Texto de cabecera
	 * @param text
	 *            Texto de detalles
	 */
	public void LanzarDialogoInformacion(String header, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Espotifai - InformaciÃ³n");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	/**
	 * Lanza un dialogo de advertencia
	 * 
	 * @param header
	 *            Texto de cabecera
	 * @param text
	 *            Texto de detalles
	 */
	public void LanzarDialogoAdvertencia(String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Espotifai - Advertencia");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	/*
	 * 
	 * METODOS DEL
	 * PROGRAMA---------------------------------------------------------------------
	 * 
	 */

	/**
	 * Genera la playlist .m3u en el directorio indicado por el parametro
	 * 
	 * @param f
	 *            Directorio donde se generar el archivo .m3u
	 * @throws IOException
	 */
	public void GenerarPlaylist(File f) throws IOException {
		FileWriter writer = new FileWriter(f.getAbsolutePath() + ".m3u");
		writer.write("#PLAYLIST GENERADA POR ESPOTIFAI" + "\n");
		for (int i = 0; i < playlist.size(); i++)
			writer.write(playlist.get(i).getArchivo().getAbsolutePath() + "\n");
		writer.close();
	}

	/**
	 * AÃ±ade las canciones encontradas en el directorio pasado por parametro a
	 * musicaDirectorio
	 * 
	 * @param Dir
	 *            Directorio que contiene la musica
	 */
	public void AnadirMusicaDirectoio(File Dir) {
		// TODO Hacer dialogo de carga y en un nuevo thread

		File[] musicaEncontrada = Dir.listFiles();
		for (int i = 0; i < musicaEncontrada.length; i++) {
			if (esMusica(musicaEncontrada[i])) {
				Musica m = new Musica(musicaEncontrada[i]);
				musicaDirectorio.add(m);
				// System.out.println(m.getTitulo().toString() + " - " + m.getTasaBits());
			}
		}
	}

	/**
	 * Metodo que se emplea para genera el indice
	 * 
	 * @param f
	 *            Directorio a indexar
	 * @throws IOException
	 */
	public void GenerarFicheroIndice(File f) throws IOException {
		FileWriter writer = new FileWriter(f.getAbsolutePath() + "\\Indice_" + fechaActual() + ".txt");
		writer.write("Ã�NDICE DE BIBLIOTECA: " + f.getAbsolutePath() + " GENERADO POR ESPOTIFAI" + "\n");
		String sep = "  ";
		ContadorIndice = 0;
		tamBiblioteca = 0;
		GenerarFicheroIndiceR(f, writer, sep);
		writer.write("\n\n\n");
		writer.write(ContadorIndice + " CANCIONES ENCONTRADAS EN: " + f.getAbsolutePath() + "\n");
		if (tamBiblioteca > 1000000000)
			writer.write(Math.round((tamBiblioteca / 1000000000.0) * 100.0) / 100.0 + " GB DE MÃšSICA" + "\n");
		else
			writer.write(Math.round((tamBiblioteca / 1000000.0) * 100.0) / 100.0 + " MB DE MÃšSICA" + "\n");
		writer.close();
	}

	/*
	 * 
	 * METODOS
	 * UTILES-----------------------------------------------------------------------
	 * 
	 */

	/**
	 * Metodo que explora recursivamente directorios encontrando la musica que
	 * contienen y volcandola en un indice
	 * 
	 * @param f
	 *            Ruta a explorar
	 * @param fw
	 *            Flujo de escritura
	 * @param sep
	 *            Separador de directorios
	 * @throws IOException
	 */
	private void GenerarFicheroIndiceR(File f, FileWriter fw, String sep) throws IOException {
		File[] ficheros = f.listFiles();
		for (int i = 0; i < ficheros.length; i++) {
			if (esMusica(ficheros[i])) {
				Musica m = new Musica(ficheros[i]);
				fw.write(sep + "[" + m.getTasaBits() + " kbps] " + ficheros[i].getName() + "\r\n");
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
	 

	/**
	 * Metodo que dado un archivo por parametro devuelve true si es un archivo de
	 * sonido y false en caso contrario
	 * 
	 * @param f
	 *            El archivo en cuestion
	 * @return
	 */
	private boolean esMusica(File f) {
		String nombre = f.getName();
		if (nombre.endsWith("mp3") || nombre.endsWith("MP3") || nombre.endsWith("flac") || nombre.endsWith("FLAC")
				|| nombre.endsWith("wav") || nombre.endsWith("WAV") || nombre.endsWith("wma") || nombre.endsWith("WMA")
				|| nombre.endsWith("ogg") || nombre.endsWith("OGG"))
			return true;
		else
			return false;
	}

	/**
	 * Metodo que genera un String con informacion sobre la fecha actual
	 * 
	 * @return String con la fecha
	 */
	private String fechaActual() {
		Calendar c = Calendar.getInstance();
		String h = Integer.toString(c.get(Calendar.HOUR));
		String m = Integer.toString(c.get(Calendar.MINUTE));
		String s = Integer.toString(c.get(Calendar.SECOND));
		String hora = (h + m + s);
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH));
		String annio = Integer.toString(c.get(Calendar.YEAR));
		return (dia + "-" + mes + "-" + annio + "_" + hora);
	}

	/*
	 * 
	 * METODOS PARA INICIAR STAGE Y
	 * MAIN------------------------------------------------------------------
	 * 
	 */

	/**
	 * Metodo que incia el FXML del programa principal
	 */
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

	/**
	 * Metodo que inicia el Stage
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		//Iconos de la aplicacion
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/recursos/imagenes/icon-16.png")));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/recursos/imagenes/icon-32.png")));
		primaryStage.setTitle("Espotifai - Gestor de MÃºsica");
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(900);
		iniciarRootLayout();
	}

	/**
	 * Main de Espotifai desde donde se lanza todo el programa
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
