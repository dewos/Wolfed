/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import java.util.List;

/**
 *
 * @author gbpellizzi
 */
public class OneServePerTimeOperation extends Iteration{

    public OneServePerTimeOperation(List<PetriNetGraph> inputGraphs) throws Exception {
        super("OneServePerTime", inputGraphs);
    }

    @Override
    void compose() {
        PlaceVertex tokenedPlace = getOperationGraph().insertPlace(null);
        
        tokenedPlace.setTokens(1);
        
        getOperationGraph().insertArc(null, finalTransition, tokenedPlace);
        getOperationGraph().insertArc(null, tokenedPlace, initialTransition);
    }
    
}
