import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.swing.*; 

import com.google.gson.Gson;

public class CalculatorTrack implements ActionListener{
	
	Face parent;
	static Graph.Edge[] nodesFull;
	
	CalculatorTrack(Face parent){    
		this.parent = parent;  
		} 
	
	public class Nodes{
		public String name;
		public Track[] track;
		public String getName() { return name; }
	}
	
	public class Track{
		public String name;
		public int weight;
		public String getName() { return name; }
		public int getWeight() { return weight; }
	}
	
	public static class GraphNotFull {
		public String from, to;
		public int weight;
		public String getFrom() { return from; }
		public String getTo() { return to; }
		public int getWeight() { return weight; }
	}
	
	public static Nodes[] parseJson (String path) {
		try ( FileReader reader = new FileReader(path)) {
	        Nodes[] nodes = new Gson().fromJson(reader, Nodes[].class); // Convert JSON to Java Object  
	    	reader.close();
	    	return nodes;
	   	} catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return null;
	}
	
	public static String calculationTrack(String from, String to) { //расчет веса точек
		Graph g = new Graph(nodesFull);
		g.dijkstra(from);
		String res = g.printPath(to);
		return res;
	}
	
	public void actionPerformed(ActionEvent e){ // слушаем кнопку на клик
		JButton btn = (JButton) e.getSource();       
        String from = parent.from.getSelectedItem().toString();
        String to = parent.to.getSelectedItem().toString();
        String path = CalculatorTrack.calculationTrack(from,to);
        parent.path.setText("");
        parent.path.setText(path);
	}
	
	public static void main(String[] args) {  
		Nodes[] node = CalculatorTrack.parseJson("route_matrix.json");
        int nodeLenghtFull=0;
        
        for (int i=0; i<node.length; i++) //Ќаходим количество элементов вида a-b,weight дл€ масива нод
	        {    		
        		for (int j=0; j<node[i].track.length; j++) {
	        		nodeLenghtFull++;
        		}
	        }
        
        String[] nodeName = new String[node.length];
        for (int i=0; i<node.length; i++) //выдел€ем все вершины (костыльно пока, но работает)
	        {
        		nodeName[i] = node[i].getName();
	        }
        
        GraphNotFull[] nodes = new GraphNotFull[nodeLenghtFull];
        for (int i=0; i<nodes.length; i++) {
        	nodes[i] = new GraphNotFull();
        }
        
        int k=0;
        for (int i=0; i<node.length; i++) //ƒелаем масив с нодами
	        {        		
        		for (int j=0; j<node[i].track.length; j++) {
        			nodes[k].from = new String(node[i].getName());
	        		nodes[k].to = node[i].track[j].getName();
	        		nodes[k].weight = node[i].track[j].getWeight();
	        		k++;
        		}
	        }
        
        nodesFull = new Graph.Edge[nodeLenghtFull*2];
        for (int i=0; i<nodesFull.length; i++) {
        	nodesFull[i] = new Graph.Edge();
        }
        
        int j = 0;
        for (int i=0; i<nodes.length; i++) { //добавл€ем обратные св€зи, т.к. путь может быть как и 'a->f' так и 'f->a'
        	nodesFull[j].from = nodes[i].getFrom();
        	nodesFull[j].to = nodes[i].getTo();
        	nodesFull[j].weight = nodes[i].getWeight();
        	j++;
        	nodesFull[j].from = nodes[i].getTo();
        	nodesFull[j].to = nodes[i].getFrom();
        	nodesFull[j].weight = nodes[i].getWeight();
        	j++;
        }
        
		Face face = new Face(nodeName); //рисуем
		
		} 
}
