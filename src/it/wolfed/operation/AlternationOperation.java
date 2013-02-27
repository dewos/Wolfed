
package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import java.util.List;

public final class AlternationOperation extends IntermediateAlternationOperation
{
    public AlternationOperation(List<PetriNetGraph> inputGraphs, List<IterationOperation> extendedGraphs) throws Exception 
    {
        super("alter", inputGraphs, extendedGraphs);
        compose();
    }
    
    void compose() 
    {
        getOperationGraph().insertArc(null, intermediatePlace, initialTransitionAsN0);
        getOperationGraph().insertArc(null, finalTransitionAsN1, intermediatePlace);
    }
    
}
