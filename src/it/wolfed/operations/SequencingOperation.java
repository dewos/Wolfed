package it.wolfed.operations;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;
import java.util.List;

public class SequencingOperation extends Operation
{  
    public SequencingOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("seq", inputGraphs, 2, true);
    }
    
    @Override
    void process()
    {
        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);

        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));
        Vertex initialPlaceAsN1 = getEquivalentVertex(net1, net1.getInitialPlaces().get(0));

        // Sposta tutti gli archi
        for(Object edgeOjb : getOperationGraph().getIncomingEdges(finalPlaceAsN0))
        {
            mxCell edge = (mxCell) edgeOjb;
            getOperationGraph().insertArc(edge.getId(), (Vertex) edge.getSource(), (Vertex) initialPlaceAsN1);
        }
        
        removeVertexAndHisEdges(finalPlaceAsN0);
    }
}