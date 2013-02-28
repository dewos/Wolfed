package it.wolfed.swing;


import it.wolfed.model.PetriNetGraph;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JButton;

public class GraphViewContainer extends Container
{
    private PetriNetGraph graph;
    
    public GraphViewContainer(PetriNetGraph graph)
    {
        this.graph = graph;

        setLayout(new BorderLayout(2, 2));
        add(new GraphComponent(graph), BorderLayout.CENTER);
        add(new AnalysisComponent(graph), BorderLayout.SOUTH);
    }

    public PetriNetGraph getGraph()
    {
        return graph;
    }
}
