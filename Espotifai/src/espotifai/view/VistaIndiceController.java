package espotifai.view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import espotifai.Main;
import espotifai.model.Musica;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

/**
 * 
 * Espotifai
 * 
 * @Author: David Trujillo Torres
 * @Date: 02-02-2018
 *
 */
public class VistaIndiceController {

	@FXML
	private Label cabecera;
	@FXML
	private Label informacion;
	@FXML
	private ProgressBar progreso;

	Main main;	
	
	

	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Establece el mensaje de la cabecera del dialogo
	 * 
	 * @param _cabecera
	 *            mensaje a establecer
	 */
	public void setCabecera(String _cabecera) {

		cabecera.setText(_cabecera);

	}

	/**
	 * Establece el mensaje informacion bajo la barra de progreso del dialogo
	 * 
	 * @param _informacion
	 *            mensaje a establecer
	 */
	public void setInformacion(String _informacion) {
		informacion.setText(_informacion);
	}

	/**
	 * Establece el % progreso de la barra
	 * 
	 * @param _progreso
	 *            numero entre 0 y 1 que representa el progreso
	 */
	public void setProgreso(double _progreso) {
		progreso.setProgress(_progreso);
	}

}
