package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

public class ZeroOrMoreIterationOperation extends Iteration
{
    public ZeroOrMoreIterationOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("ZeroOrMore", inputGraphs);
    }

    @Override
    void compose()
    {
        TransitionVertex intermediateTransition = getOperationGraph().insertTransition(null);

        PetriNetGraph net0 = getInputGraphs().get(0);
        Vertex initialPlaceAsN0 = getEquivalentVertex(net0, net0.getInitialPlaces().get(0));
        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));

        getOperationGraph().insertArc(null, finalPlaceAsN0, intermediateTransition);
        getOperationGraph().insertArc(null, intermediateTransition, initialPlaceAsN0);

        TransitionVertex zeroTransition = getOperationGraph().insertTransition(null);

        getOperationGraph().insertArc(null, initialPlace, zeroTransition);
        getOperationGraph().insertArc(null, zeroTransition, finalPlace);
    }
}
