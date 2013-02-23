package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

public abstract class Iteration extends Operation
{
    protected TransitionVertex initialTransition;
    protected TransitionVertex finalTransition;
    protected PlaceVertex initialPlace;
    protected PlaceVertex finalPlace;

    public Iteration(String operationName, List<PetriNetGraph> inputGraphs) throws Exception
    {
        super(operationName, inputGraphs, 1, true);
    }

    @Override
    void process()
    {
        insertInitialPattern();
        insertFinalPattern();
        compose();
    }

    /**
     * Il processo che modifica il grafo dell'operazione.
     *
     * @return void
     */
    abstract void compose();

    private void insertInitialPattern()
    {
        initialPlace = getOperationGraph().insertPlace("initial");
        initialTransition = getOperationGraph().insertTransition(null);

        PetriNetGraph net0 = getInputGraphs().get(0);
        Vertex initialPlaceAsN0 = getEquivalentVertex(net0, net0.getInitialPlaces().get(0));

        getOperationGraph().insertArc(null, initialPlace, initialTransition);
        getOperationGraph().insertArc(null, initialTransition, initialPlaceAsN0);
    }

    private void insertFinalPattern()
    {
        finalPlace = getOperationGraph().insertPlace("final");
        finalTransition = getOperationGraph().insertTransition(null);
        
        PetriNetGraph net0 = getInputGraphs().get(0);
        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));

        getOperationGraph().insertArc(null, finalTransition, finalPlace);
        getOperationGraph().insertArc(null, finalPlaceAsN0, finalTransition);
    }
}