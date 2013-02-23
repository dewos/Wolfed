package it.wolfed.operations;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;
import java.util.List;

public class DefferedChoiceOperation extends Operation
{
    public DefferedChoiceOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("deffchoice", inputGraphs, 2, true);
    }
    
    /*
     * Deferred choice
     * (XOR-split + XOR-join)
     * 
     * Either A or B is executed (choice is implicit)
     * 
     *     -> A ->   
     *  i |		  | o
     *     -> B ->
     * 
     * where i and o are places.
     */
    @Override
    void process()
    {
        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);

        Vertex initialPlaceAsN0 = getEquivalentVertex(net0, net0.getInitialPlaces().get(0));
        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));

        Vertex initialPlaceAsN1 = getEquivalentVertex(net1, net1.getInitialPlaces().get(0));
        Vertex finalPlaceAsN1 = getEquivalentVertex(net1, net1.getFinalPlaces().get(0));


        // aggionamento archi iniziali 
        for (Object edgeOjb : getOperationGraph().getOutgoingEdges(initialPlaceAsN1))
        {
            mxCell edge = (mxCell) edgeOjb;
            getOperationGraph().insertArc(edge.getId(), initialPlaceAsN0, (Vertex) edge.getTarget());
        }
        // aggiornamento archi finali 
        for (Object edgeOjb : getOperationGraph().getIncomingEdges(finalPlaceAsN1))
        {
            mxCell edge = (mxCell) edgeOjb;
            getOperationGraph().insertArc(edge.getId(), (Vertex) edge.getSource(), finalPlaceAsN0);
        }
        // Elimina gli archi iniziali e la piazza iniziale della rete 2
        for (Object edgeObj : getOperationGraph().getEdges(initialPlaceAsN1))
        {
            getOperationGraph().getModel().remove(edgeObj);
        }
        getOperationGraph().getModel().remove(initialPlaceAsN1);

        // Elimina gli archi iniziali e la piazza iniziale della rete 2
        for (Object edgeObj : getOperationGraph().getEdges(finalPlaceAsN1))
        {
            getOperationGraph().getModel().remove(edgeObj);
        }
        getOperationGraph().getModel().remove(finalPlaceAsN1);
    }
}
