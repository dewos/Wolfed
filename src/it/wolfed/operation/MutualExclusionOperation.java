/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import java.util.List;

public final class MutualExclusionOperation extends IntermediateAlternationOperation
{
    private PlaceVertex intermediatePlaceWithoutToken;
    
    public MutualExclusionOperation(List<PetriNetGraph> inputGraphs, List<IterationOperation> extendedGraphs) throws Exception 
    {
        super("mutex", inputGraphs, extendedGraphs);
        compose();
    }

    void compose() 
    {
        intermediatePlaceWithoutToken = getOperationGraph().insertPlace("Palce_No_Token");
        getOperationGraph().insertArc(null, intermediatePlaceWithoutToken, initialTransitionAsN0);
        getOperationGraph().insertArc(null, finalTransitionAsN1, intermediatePlaceWithoutToken);
    }
    
}
