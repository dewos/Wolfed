package it.wolfed.operation;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.Vertex;

/**
 * OneServePerTime Operation
 */
public class OneServePerTimeOperation extends Operation
{
    public OneServePerTimeOperation(PetriNetGraph operationGraph, PetriNetGraph iterationGraph) throws Exception
    {
        super(operationGraph);
        this.operationGraph = (new IterationPatternOperation(operationGraph, iterationGraph)).getOperationGraph();
        execute();
    }

    @Override
    void process() throws Exception
    {
        PlaceVertex placeVertex = this.operationGraph.insertPlace(null);
        Object[] initialTransitions = this.operationGraph.getOutgoingEdges(this.operationGraph.getInitialPlaces().get(0));
        
        for (Object edgeOjb : initialTransitions)
        {
            mxCell edge = (mxCell) edgeOjb;
            this.operationGraph.insertArc(null, placeVertex, (Vertex) edge.getTarget());
        }

        Object[] finalTransitions = this.operationGraph.getIncomingEdges(this.operationGraph.getFinalPlaces().get(0));
        for (Object edgeOjb : finalTransitions)
        {
            mxCell edge = (mxCell) edgeOjb;
            this.operationGraph.insertArc(null, (Vertex) edge.getSource(), placeVertex);
        }

        // set tokens
        placeVertex.setTokens(1);
        this.operationGraph.getInitialPlaces().get(0).setTokens(1);
    }
}
