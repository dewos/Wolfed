package it.wolfed.operationsRefactored;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;

/**
 * Sequencing Operation.
 */
public class ParallelismOperation extends Operation
{
    PetriNetGraph firstGraph;
    PetriNetGraph secondGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph
     * @throws Exception  
     */
    public ParallelismOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = getIfIsWorkFlow(firstGraph);
        this.secondGraph = getIfIsWorkFlow(secondGraph);
        this.operationGraph = (new MergeGraphsOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
        execute();
    }
   
    /**
     * Parallelism.
     * 
     * (AND-split + AND-join)
     * A and B are both executed in no particular order
     *       			-> p1 -> A -> p3 ->  
     *  i -> AND-split						AND-join -> o
     *       			-> p2 -> B -> p4 ->
     *       
     *      where AND-split, AND-join are Transitions
     *      and i, p1, p2, p3, p4 and o are places
     */
    @Override
    void process()
    {
        insertInitialPattern();
        insertFinalPattern();
    }
    
    /**
     * Insert initial pattern.
     * 
     *        			-> p1 -> A -> p3 ->  
     *  i -> AND-split						AND-join -> o
     *       			-> p2 -> B -> p4 ->
     */
    private void insertInitialPattern()
    {
        PlaceVertex pi = getOperationGraph().insertPlace("initial");
        TransitionVertex andSplit = getOperationGraph().insertTransition("AND-split");

        PlaceVertex initialPlaceAsFirst =  (PlaceVertex) getEquivalentVertex(1, firstGraph.getInitialPlaces().get(0));
        PlaceVertex initialPlaceAsSecond = (PlaceVertex)getEquivalentVertex(2, secondGraph.getInitialPlaces().get(0));

        getOperationGraph().insertArc(null, pi, andSplit);
        getOperationGraph().insertArc(null, andSplit, initialPlaceAsFirst);
        getOperationGraph().insertArc(null, andSplit, initialPlaceAsSecond);
        
        // Sets tokens
        pi.setTokens(1);
        initialPlaceAsFirst.setTokens(0);
        initialPlaceAsSecond.setTokens(0);
    }
    
    /**
     * Insert final pattern.
     * 
     *        			-> p1 -> A -> p3 ->  
     *  i -> AND-split						AND-join -> o
     *       			-> p2 -> B -> p4 ->
     */
    private void insertFinalPattern()
    {
        PlaceVertex po = getOperationGraph().insertPlace("final");
        TransitionVertex andJoin = getOperationGraph().insertTransition("and-join");

        Vertex finalPlaceAsFirst = getEquivalentVertex(1, firstGraph.getFinalPlaces().get(0));
        Vertex finalPlaceAsSecond = getEquivalentVertex(2, secondGraph.getFinalPlaces().get(0));

        getOperationGraph().insertArc(null, andJoin, po);
        getOperationGraph().insertArc(null, finalPlaceAsFirst, andJoin);
        getOperationGraph().insertArc(null, finalPlaceAsSecond, andJoin);
    }
}