package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

/**
 *
 * 
 */
public class IntermediateAlternationOperation extends Operation{
    private PlaceVertex intermediatePlace;
    private final List<ExtendedGraph> extendedGraphs;
    private Vertex initialTransitionAsN0;
    private Vertex finalTransitionAsN0;
    private Vertex initialTransitionAsN1;
    private Vertex finalTransitionAsN1;
    private PlaceVertex intermediatePlaceWithoutToken;
    
    public IntermediateAlternationOperation(String operationName, List<PetriNetGraph> inputGraphs, List<ExtendedGraph> extendedGraphs) throws Exception {
        super(operationName, inputGraphs, 2, true);
        this.extendedGraphs = extendedGraphs;
        compose();
    }

    @Override
    void process() {
        
    }

    private void compose() {
        intermediatePlace = getOperationGraph().insertPlace("Palce_Token");
        intermediatePlace.setTokens(1);
        
        ExtendedGraph net0 = extendedGraphs.get(0);
        ExtendedGraph net1 = extendedGraphs.get(1);
        
        initialTransitionAsN0 = getOperationGraph().getVertexById("n0_"+net0.initialTransition.getId());
        finalTransitionAsN0 = getOperationGraph().getVertexById("n0_"+net0.finalTransition.getId());
        
        initialTransitionAsN1 = getOperationGraph().getVertexById("n1_"+net1.initialTransition.getId());
        finalTransitionAsN1 = getOperationGraph().getVertexById("n1_"+net1.finalTransition.getId());
        
        
        getOperationGraph().insertArc(null, finalTransitionAsN0, intermediatePlace);
        getOperationGraph().insertArc(null, intermediatePlace, initialTransitionAsN1);
        
        insertInitialPattern();
        insertFinalPattern();
    }

        PetriNetGraph alternation() {

        getOperationGraph().insertArc(null, intermediatePlace, initialTransitionAsN0);
        getOperationGraph().insertArc(null, finalTransitionAsN1, intermediatePlace);
        return getOperationGraph();
    }

    PetriNetGraph mutualExclusion() {
        intermediatePlaceWithoutToken = getOperationGraph().insertPlace("Palce_No_Token");
        getOperationGraph().insertArc(null, intermediatePlaceWithoutToken, initialTransitionAsN0);
        getOperationGraph().insertArc(null, finalTransitionAsN1, intermediatePlaceWithoutToken);
        return getOperationGraph();
    }
    
    
    private void insertInitialPattern()
    {
        TransitionVertex andSplit = getOperationGraph().insertTransition("AND-split");
        for (PlaceVertex initialPlace : getOperationGraph().getInitialPlaces()) {
            getOperationGraph().insertArc(null, andSplit,initialPlace);
        }
        
        PlaceVertex pi = getOperationGraph().insertPlace("start");
        getOperationGraph().insertArc(null, pi, andSplit);
    }
    private void insertFinalPattern()
    {
     TransitionVertex andJoin = getOperationGraph().insertTransition("AND-join");

        for (PlaceVertex finalPlace : getOperationGraph().getFinalPlaces()) {
            getOperationGraph().insertArc(null, finalPlace, andJoin);
        }
        PlaceVertex po = getOperationGraph().insertPlace("finish");
        getOperationGraph().insertArc(null, andJoin, po);

    }
}
