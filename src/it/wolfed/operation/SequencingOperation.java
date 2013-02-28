package it.wolfed.operation;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;

/**
 * Sequencing Operation.
 */
public class SequencingOperation extends Operation
{
    PetriNetGraph firstGraph;
    PetriNetGraph secondGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph
     * @throws Exception  
     */
    public SequencingOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = getIfIsWorkFlow(firstGraph);
        this.secondGraph = getIfIsWorkFlow(secondGraph);
        this.operationGraph = (new MergeGraphsOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
        execute();
    }
   
    /**
     * Process Sequencing.
     * 
     * firstGraph = P1 -> T1 -> P2
     * secondGraph = P3 -> T2 -> P4
     * 
     * result = P1 -> T1 -> (P2 + P3) -> T2 -> P4
     */
    @Override
    void process()
    {
        Vertex finalPlaceAsFirst = getEquivalentVertex(1, firstGraph.getFinalPlaces().get(0));
        Vertex initialPlaceAsSecond = getEquivalentVertex(2, secondGraph.getInitialPlaces().get(0));

        cloneIncomingEdges(finalPlaceAsFirst, initialPlaceAsSecond);
        removeVertexAndHisEdges(finalPlaceAsFirst);
        
        // set token to initial place
        this.operationGraph.getInitialPlaces().get(0).setTokens(1);
    }
}