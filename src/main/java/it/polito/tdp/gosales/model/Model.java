package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	private GOsalesDAO dao;
	private Graph<Products, DefaultWeightedEdge> grafo;
	private Map<Integer, Products> idMap;
	private List<Arco> archi;
	private List<Arco> percorso;
	private int numArchi;
	
	public Model() {
		this.dao = new GOsalesDAO();
		this.idMap = new HashMap<>();
		this.archi = new ArrayList<>();
	}
	
	public List<Integer> getAnni(){
		return this.dao.getYear();
	}
	
	public List<String> getColori(){
		return this.dao.getColors();
	}
	
	public void creaGrafo(String color, int anno) {
		this.grafo = new SimpleWeightedGraph<Products,DefaultWeightedEdge >(DefaultWeightedEdge.class);
		
		List<Products> vertici = this.dao.getProductsByColor(color);
		Graphs.addAllVertices(this.grafo, vertici);
		
		this.idMap.clear();
		for(Products p: vertici) {
			this.idMap.put(p.getNumber(), p);
		}
		
		 archi = this.dao.getArchi(color, anno, idMap);
		for(Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
		}
	}
	
	public Set<Products> getVertici(){
		return this.grafo.vertexSet();
	}
	
	public Set<DefaultWeightedEdge> getArchi(){
		return this.grafo.edgeSet();
	}
	
	public List<Arco> pesiMax(){
		List<Arco> edges = new ArrayList<>(this.archi);
		Collections.sort(edges);
		List<Arco> max = new ArrayList<>();
		if(edges.size()>0) {
			for(int i =0; i<=2; i++) {
				max.add(edges.get(i));
			}
		}
		
		return max;
	}
	
	public List<Products> getVerticiMax(List<Arco> max){
		List<Products> presenti = new ArrayList<>();
		
		for(Arco a1 : max) {
			presenti.add(a1.getP1());
			presenti.add(a1.getP2());
		}
		List<Products> ripetuti = new ArrayList<>();
		//Collections.sort(presenti);
		for(int i =0; i<=presenti.size()-2; i++) {
			for(int j= i+1; j<=presenti.size()-1; j++) {
				if(presenti.get(i).equals(presenti.get(j))) {
					ripetuti.add(presenti.get(i));
				}
			}
		}
		return ripetuti;
		
	}
	
	public List<Arco> getPercorso(Products p){
		this.percorso = new ArrayList<>();
		this.numArchi = 0;
		List<Arco> parziale = new ArrayList<>();
		List<Arco> archi = new ArrayList<>(this.archi);
		cerca(parziale, archi, p);
		return this.percorso;
	}
	
	public void cerca(List<Arco> parziale,List<Arco> tutti, Products p) {
		//condizione terminale
		
		if(parziale.size()>this.numArchi) {
			this.numArchi = parziale.size();
			if(tutti.isEmpty()) {
				this.percorso = new ArrayList<>(parziale);
				return;
			}
		}
		
		List<Arco> iniziali = new ArrayList<>();
		for(Arco a : tutti) {
			if(a.getP1().equals(p)) {
				iniziali.add(a);
				if(parziale.isEmpty()) {
					parziale.add(a);
				
				}else {
					if(a.getPeso()>=parziale.get(parziale.size()-1).getPeso()) {
						parziale.add(a);
						tutti.removeAll(iniziali);
						List<Arco> nuovi = new ArrayList<>(tutti);
						Products next = a.getP2();
						cerca(parziale, nuovi, next);
						
						parziale.remove(parziale.size()-1);
					}
				}
			}
		}
		
	}
	
	
}
