package espotifai;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import espotifai.model.Musica;
import espotifai.view.VistaEditarEtiquetasController;
import espotifai.view.VistaIndiceController;
import espotifai.view.VistaPrincipalController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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
import javafx.stage.StageStyle;

/**
 * 
 * Espotifai
 * 
 * @Author: David Trujillo Torres
 * @Date: 02-02-2018
 *
 */
public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private int ContadorIndice = 0;
	private float tamBiblioteca = 0;
	private ObservableList<Musica> playlist = FXCollections.observableArrayList();
	private ObservableList<Musica> musicaDirectorio = FXCollections.observableArrayList();
	private Task<?> indiceTask;
	private Task<?> metadatosTask;

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

	public Task<?> getMetadatosTask() {
		return metadatosTask;
	}

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

	public Task<?> getIndiceTask() {
		return indiceTask;
	}

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
	 * Lanza el dialogo de progreso al generar un indice
	 * 
	 * @throws IOException
	 */
	public void LanzarDialogoIndice() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/VistaIndice.fxml"));
		AnchorPane page;
		page = (AnchorPane) loader.load();
		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.initStyle(StageStyle.UNDECORATED);
		dialogStage.setTitle("Espotifai - Generando Índice");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		VistaIndiceController controller = loader.getController();
		controller.setMain(this);
		controller.inicializarProgreso();

		indiceTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				dialogStage.close();
			}
		});
		dialogStage.showAndWait();
	}

	/**
	 * Lanza el dialogo de espera para obtener datos de las canciones en un
	 * directorio
	 * 
	 * @throws IOException
	 */
	public void LanzarDialogoMetadatos() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/VistaCargaMetadatos.fxml"));
		AnchorPane page;
		page = (AnchorPane) loader.load();
		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.initStyle(StageStyle.UNDECORATED);
		dialogStage.setTitle("Espotifai - Obteniendo Etiquetas de música");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		metadatosTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				dialogStage.close();
			}
		});
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
		alert.setTitle("Espotifai - Información");
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
	public void GenerarPlaylist(File f, String salto) throws IOException {
		FileWriter writer = new FileWriter(f.getAbsolutePath() + ".m3u");
		writer.write("#PLAYLIST GENERADA POR ESPOTIFAI" + salto);
		for (int i = 0; i < playlist.size(); i++)
			writer.write(playlist.get(i).getArchivo().getAbsolutePath() + salto);
		writer.close();
	}

	/**
	 * AÃ±ade las canciones encontradas en el directorio pasado por parametro a
	 * musicaDirectorio
	 * 
	 * @param Dir
	 *            Directorio que contiene la musica
	 * @throws IOException
	 */
	public void AnadirMusicaDirectoio(File Dir) throws IOException {
		// TODO Hacer dialogo de carga y en un nuevo thread

		metadatosTask = new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				File[] musicaEncontrada = Dir.listFiles();
				for (int i = 0; i < musicaEncontrada.length; i++) {
					if (esMusica(musicaEncontrada[i])) {
						Musica m = new Musica(musicaEncontrada[i]);
						musicaDirectorio.add(m);
					}
					updateProgress(i, musicaEncontrada.length);
				}
				return null;
			}

		};

		new Thread(metadatosTask).start();
		LanzarDialogoMetadatos();

	}

	/**
	 * Metodo que se emplea para genera el indice
	 * 
	 * @param f
	 *            Directorio a indexar
	 * @throws IOException
	 */
	public void GenerarFicheroIndice(File f, String sep, String salto) throws IOException {

		int numCanciones = ContarCancionesDirectorioR(f);

		indiceTask = new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				FileWriter writer = new FileWriter(f.getAbsolutePath() + sep + "Indice_" + fechaActual() + ".txt");
				writer.write("ÍNDICE DE BIBLIOTECA: " + f.getAbsolutePath() + " GENERADO POR ESPOTIFAI" + salto);
				String esp = "  ";
				ContadorIndice = 0;
				tamBiblioteca = 0;
				GenerarFicheroIndiceR(f, writer, esp, salto, numCanciones);

				writer.write(salto + salto + salto);
				writer.write(ContadorIndice + " CANCIONES ENCONTRADAS EN: " + f.getAbsolutePath() + salto);
				if (tamBiblioteca > 1000000000)
					writer.write(Math.round((tamBiblioteca / 1000000000.0) * 100.0) / 100.0 + " GB DE MÚSICA" + salto);
				else
					writer.write(Math.round((tamBiblioteca / 1000000.0) * 100.0) / 100.0 + " MB DE MÚSICA" + salto);
				writer.close();
				return null;
			}

			private void GenerarFicheroIndiceR(File f, FileWriter fw, String sep, String salto, int numCanciones)
					throws IOException {
				File[] ficheros = f.listFiles();
				for (int i = 0; i < ficheros.length; i++) {
					if (esMusica(ficheros[i])) {
						Musica m = new Musica(ficheros[i]);
						fw.write(sep + "[" + m.getTasaBits() + " kbps] " + ficheros[i].getName() + salto);
						ContadorIndice++;
						updateProgress(ContadorIndice, numCanciones);
						tamBiblioteca = tamBiblioteca + ficheros[i].length();
					} else if (ficheros[i].isDirectory()) {
						updateMessage("/" + ficheros[i].getName());
						fw.write(salto);
						fw.write(salto);
						fw.write(sep + "[" + ficheros[i].getName() + "...]" + salto);
						String nuevo_separador;
						nuevo_separador = sep + "  ";
						GenerarFicheroIndiceR(ficheros[i], fw, nuevo_separador, salto, numCanciones);
					}
				}
			}

		};

		new Thread(indiceTask).start();

	}

	/*
	 * 
	 * METODOS
	 * UTILES-----------------------------------------------------------------------
	 * 
	 */

	/**
	 * Metodo que busca recursivamente y cuenta las canciones en un directorio
	 * 
	 * @param f
	 *            Ruta que contenga la musica a buscar
	 * @return el numero de canciones encontradas
	 */
	public static int ContarCancionesDirectorioR(File f) {
		File[] ficheros = f.listFiles();
		int contador = 0;
		for (int i = 0; i < ficheros.length; i++) {

			if (esMusica(ficheros[i])) {
				contador++;
			} else if (ficheros[i].isDirectory()) {
				contador = contador + ContarCancionesDirectorioR(ficheros[i]);
			}

		}

		return contador;
	}

	/**
	 * Metodo que dado un archivo por parametro devuelve true si es un archivo de
	 * sonido y false en caso contrario
	 * 
	 * @param f
	 *            El archivo en cuestion
	 * @return
	 */
	private static boolean esMusica(File f) {
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
		// Iconos de la aplicacion
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/recursos/imagenes/icon-16.png")));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/recursos/imagenes/icon-32.png")));

		primaryStage.setTitle("Espotifai - Gestor de Música");
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
