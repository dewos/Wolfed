package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import java.util.List;

public final class OneServePerTimeOperation extends IterationOperation
{
    public OneServePerTimeOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("one_x_time", inputGraphs);
        compose();
    }

    void compose()
    {
        PlaceVertex tokenedPlace = getOperationGraph().insertPlace(null);
        tokenedPlace.setTokens(1);

        getOperationGraph().insertArc(null, finalTransition, tokenedPlace);
        getOperationGraph().insertArc(null, tokenedPlace, initialTransition);
    }
}
