import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;

@SuppressWarnings("serial")
public class GraphNetwork extends JFrame{
	
	private JFrame graphFrame;
	private JPanel graphPanel;
	private JPanel panelView;
	private Graph<String, String> graph;
	private Registry regRecord;
	private ArrayList<Suspect> listOfSuspects = new ArrayList<Suspect>();
	private JButton searchPanelButton;
		
	GraphNetwork(Registry record){
		
		regRecord = record;
		listOfSuspects.addAll( regRecord.listOfSuspects() );
		
		graphFrame = new JFrame();
		graphPanel = new JPanel();
		panelView = new JPanel();
		searchPanelButton = new JButton("Back to Search");
		
		graph = createGraph();

        Dimension size = new Dimension(400, 500);
        VisualizationViewer<String, String> visualViewer = new VisualizationViewer<String, String>(new FRLayout<String, String>(graph, size));
		
        visualViewer.getRenderContext().setVertexLabelTransformer(new Transformer<String, String>() {public String transform(String arg0) {return arg0;}});
        
		graphFrame.getContentPane().add(visualViewer);
		graphPanel.add(visualViewer);
		
		GridLayout panelViewGrid = new GridLayout( 2 , 0 );
		panelView.setLayout(panelViewGrid);
		panelView.add(visualViewer);
		panelView.add(searchPanelButton);
				
		BorderLayout panelBox = new BorderLayout();
		panelView.setLayout(panelBox);
		panelView.add(visualViewer, BorderLayout.CENTER);
		panelView.add(searchPanelButton, BorderLayout.SOUTH);
		
		this.setContentPane(panelView);
		
		ButtonListener listener = new ButtonListener();
		searchPanelButton.addActionListener(listener);
		
		this.setVisible(true);
		this.setSize(500, 600);
		this.setTitle("Suspects Network");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Create graph with vertices and edges
	public Graph<String, String> createGraph() {
	    	    
	    Graph<String, String> g = new UndirectedSparseGraph<String, String>();
	    ArrayList<Suspect> partners = new ArrayList<Suspect>();
	    	    
	    for (Suspect suspect : listOfSuspects) {
	        g.addVertex(suspect.getCodeName()); 
	    }
	    
	    for (Suspect suspect : listOfSuspects) {
	    	partners.addAll(suspect.getPartnersOFsuspect());
	    	for (Suspect partner : partners) {
	    		g.addEdge(suspect.getName() + partner.getName(), suspect.getCodeName(), partner.getCodeName(), EdgeType.UNDIRECTED);
	    	}
	    	partners.removeAll(listOfSuspects);
	    }	    
	    return g;
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == searchPanelButton) {
				
				new GUIFindSuspect(regRecord);
				dispose();
			}
		}
	}
}
