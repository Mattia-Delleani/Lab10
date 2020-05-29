/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.bar;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.bar.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tolleranza"
    private Slider tolleranza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleSimula(ActionEvent event) {
    	txtResult.clear();
    	float tolleranz = (float)tolleranza.getValue()/100;
    	this.model.simula(tolleranz);
    	txtResult.appendText("Simulazione svolta con tolleranza="+tolleranz+" e "+this.model.getNumeroEventi()+" eventi. Risultati:\n");
    	txtResult.appendText(this.model.getResults());
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tolleranza != null : "fx:id=\"tolleranza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	
    }
}
