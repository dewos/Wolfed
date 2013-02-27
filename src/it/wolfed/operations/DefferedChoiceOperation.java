package it.wolfed.operations;

import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;

/**
 * Sequencing Operation.
 */
public class DefferedChoiceOperation extends Operation
{
    PetriNetGraph firstGraph;
    PetriNetGraph secondGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph
     * @throws Exception  
     */
    public DefferedChoiceOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = getIfIsWorkFlow(firstGraph);
        this.secondGraph = getIfIsWorkFlow(secondGraph);
        this.operationGraph = (new MergeGraphsOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
        execute();
    }
   
    /**
     * Deferred choice.
     * 
     * (XOR-split + XOR-join)
     * 
     * Either A or B is executed (choice is implicit)
     * 
     *     -> A ->   
     *  i |		  | o
     *     -> B ->
     * 
     * where i and o are places.
     */
    @Override
    void process()
    {
        Vertex initialPlaceAsFirst = getEquivalentVertex(1, firstGraph.getInitialPlaces().get(0));
        Vertex initialPlaceAsSecond = getEquivalentVertex(2, secondGraph.getInitialPlaces().get(0));
        Vertex finalPlaceAsFirst = getEquivalentVertex(1, firstGraph.getFinalPlaces().get(0));
        Vertex finalPlaceAsSecond = getEquivalentVertex(2, secondGraph.getFinalPlaces().get(0));

        cloneOutgoingEdges(initialPlaceAsSecond, initialPlaceAsFirst);
        cloneIncomingEdges(finalPlaceAsSecond, finalPlaceAsFirst);
        
        removeVertexAndHisEdges(initialPlaceAsSecond);
        removeVertexAndHisEdges(finalPlaceAsSecond);
    }
}