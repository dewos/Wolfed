package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

public class IntermediateAlternationOperation extends Operation
{
    protected PlaceVertex intermediatePlace;
    protected final List<IterationOperation> extendedGraphs;
    protected Vertex initialTransitionAsN0;
    protected Vertex finalTransitionAsN0;
    protected Vertex initialTransitionAsN1;
    protected Vertex finalTransitionAsN1;
    
    public IntermediateAlternationOperation(String operationName, List<PetriNetGraph> inputGraphs, List<IterationOperation> extendedGraphs) throws Exception 
    {
        super(operationName, inputGraphs, 2, true);
        this.extendedGraphs = extendedGraphs;
        createIntermediateGraph();
    }

    @Override
    void process() 
    {
        
    }
    
    
    private void createIntermediateGraph() {
        intermediatePlace = getOperationGraph().insertPlace("Palce_Token");
        intermediatePlace.setTokens(1);
        
        IterationOperation net0 = extendedGraphs.get(0);
        IterationOperation net1 = extendedGraphs.get(1);
        
        initialTransitionAsN0 = getEquivalentVertex(net0.getOperationGraph(), net0.initialTransition);
        initialTransitionAsN0 = getOperationGraph().getVertexById("n0_"+net0.initialTransition.getId());
        finalTransitionAsN0 = getOperationGraph().getVertexById("n0_"+net0.finalTransition.getId());
        
        initialTransitionAsN1 = getOperationGraph().getVertexById("n1_"+net1.initialTransition.getId());
        finalTransitionAsN1 = getOperationGraph().getVertexById("n1_"+net1.finalTransition.getId());
        
        getOperationGraph().insertArc(null, finalTransitionAsN0, intermediatePlace);
        getOperationGraph().insertArc(null, intermediatePlace, initialTransitionAsN1);
        
        insertInitialPattern();
        insertFinalPattern();
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
