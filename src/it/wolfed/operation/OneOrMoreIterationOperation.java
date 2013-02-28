package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;

/**
 * OneOrMoreIteration Operation
 */
public class OneOrMoreIterationOperation extends Operation
{
    private final PetriNetGraph iterationGraph;

    public OneOrMoreIterationOperation(PetriNetGraph operationGraph, PetriNetGraph iterationGraph) throws Exception
    {
        super(operationGraph);
        this.iterationGraph = iterationGraph;
        this.operationGraph = (new IterationPatternOperation(operationGraph, this.iterationGraph)).getOperationGraph();
        execute();
    }

    @Override
    void process() throws Exception
    {
        TransitionVertex intermediateTransition = getOperationGraph().insertTransition(null);
        Vertex initialPlaceAsN0 = getEquivalentVertex(1, this.iterationGraph.getInitialPlaces().get(0));
        Vertex finalPlaceAsN0 = getEquivalentVertex(1, this.iterationGraph.getFinalPlaces().get(0));
        getOperationGraph().insertArc(null, finalPlaceAsN0, intermediateTransition);
        getOperationGraph().insertArc(null, intermediateTransition, initialPlaceAsN0);
        
        // set token to initial place
        this.operationGraph.getInitialPlaces().get(0).setTokens(1);
    }
}
