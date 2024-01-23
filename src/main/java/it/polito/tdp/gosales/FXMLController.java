package it.polito.tdp.gosales;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCercaPercorso;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbColore;

    @FXML
    private ComboBox<Products> cmbProdotto;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextArea txtResGrafo;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCercaPercorso(ActionEvent event) {
    	Products p = this.cmbProdotto.getSelectionModel().getSelectedItem();
    	
    	if(this.cmbProdotto.getSelectionModel().isEmpty()) {
    		this.txtResult.setText("Selezionare un prodotto di partenza dal menu");
    		return;
    	}
    	
    	List<Arco> percorso = this.model.getPercorso(p);
    	if(percorso.size()==0) {
    		this.txtResult.setText("Percorso non trovato");
    		return;
    	}else {
    		this.txtResult.setText("Percorso trovato\n");
    		for(Arco a : percorso) {
    			this.txtResult.appendText(""+a+"\n");
    		}
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String color = this.cmbColore.getSelectionModel().getSelectedItem();
    	Integer anno = this.cmbAnno.getSelectionModel().getSelectedItem();
    	
    	if(this.cmbColore.getSelectionModel().isEmpty()) {
    		this.txtResGrafo.setText("Selezionare un colore dal menu");
    		return;
    	}
    	if (this.cmbAnno.getSelectionModel().isEmpty()) {
    		this.txtResGrafo.setText("Selezionare un anno dal menu");
    		return;
    	}
    	
    	try {
    		if(anno<=0) {
    			this.txtResult.setText("Selezionare un numero dal menu");
    			return;
    		}
    		
    		this.model.creaGrafo(color, anno);
    		Set<Products> vertici = this.model.getVertici();
    		if(vertici.size() ==0) {
    			this.txtResGrafo.setText("Grafo non creato");
    			return;
    		}else {
    			this.txtResGrafo.setText("Grafo creato\n");
    			this.txtResGrafo.appendText("Ci sono "+vertici.size()+" vertici.\n");
    			this.txtResGrafo.appendText("Ci sono "+this.model.getArchi().size()+" archi.\n");
    			
    			this.cmbProdotto.getItems().addAll(vertici);
    			
    			List<Arco> archi = this.model.pesiMax();
    			for(Arco a : archi) {
    				this.txtArchi.appendText(""+a+"\n");
    			}
    			
    			List<Products> max = this.model.getVerticiMax(archi);
    			if(max.size()>0) {
    				this.txtArchi.appendText("Vertici ripetuti:\n");
	    			for(Products p: max) {
	    				this.txtArchi.appendText(""+p+"\n");
	    			}
    			}else {
    				this.txtArchi.appendText("Non sono presenti archi ripetuti");
    			}
    	
    		}
    		
    	}catch(NumberFormatException e) {
    		this.txtResGrafo.setText("Selezionare un numero dal menu");
    	}

    }

    @FXML
    void initialize() {
        assert btnCercaPercorso != null : "fx:id=\"btnCercaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbColore != null : "fx:id=\"cmbColore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResGrafo != null : "fx:id=\"txtResGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbAnno.getItems().addAll(this.model.getAnni());
    	this.cmbColore.getItems().addAll(this.model.getColori());
    }

}
