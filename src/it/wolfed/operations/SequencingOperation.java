package it.wolfed.operations;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;
import java.util.List;

public class SequencingOperation extends Operation
{  
    public SequencingOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super(inputGraphs, "seq");
        
        if(inputGraphs.size() != 2)
        {
            throw new Exception("Two WorkFlow Net required");
        }
    }
    
    @Override
    void process()
    {
        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);

        Vertex finalPlaceAsN0 = getSameVertexById(net0, net0.getFinalPlaces().get(0));
        Vertex initialPlaceAsN1 = getSameVertexById(net1, net1.getInitialPlaces().get(0));

        // Sposta tutti gli archi
        for(Object edgeOjb : getOperationGraph().getIncomingEdges(finalPlaceAsN0))
        {
            mxCell edge = (mxCell) edgeOjb;
            getOperationGraph().insertArc(edge.getId(), (Vertex) edge.getSource(), (Vertex) initialPlaceAsN1);
        }

        // Elimina gli archi e la final place della rete 2
        for(Object edgeObj : getOperationGraph().getEdges(finalPlaceAsN0))
        {
            getOperationGraph().getModel().remove(edgeObj);
        }

        getOperationGraph().getModel().remove(finalPlaceAsN0);
    }
}