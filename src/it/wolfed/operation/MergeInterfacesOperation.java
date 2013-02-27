package it.wolfed.operation;

import com.mxgraph.model.mxCell;
import it.wolfed.model.InterfaceVertex;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.Vertex;

/**
 * MergeInterfaces Operation.
 */
public class MergeInterfacesOperation extends Operation
{
    PetriNetGraph firstGraph;
    PetriNetGraph secondGraph;
    
    /**
     * @param operationGraph
     * @param firstGraph
     * @param secondGraph
     * @throws Exception  
     */
    public MergeInterfacesOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = getIfIsWorkFlow(firstGraph);
        this.secondGraph = getIfIsWorkFlow(secondGraph);
        this.operationGraph = (new MergeGraphsOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
        execute();
    }
   
    /**
     * Process Merge Interfaces.
     */
    @Override
    void process() throws Exception
    {
        // Finds all interfaces in n0
        for(Object cellObj : firstGraph.getChildVertices())
        {
            if(cellObj instanceof InterfaceVertex)
            {
                InterfaceVertex interfFirst = (InterfaceVertex) cellObj;
                InterfaceVertex interfSecond = (InterfaceVertex) secondGraph.getVertexByValue(interfFirst.getValue());
                
                // Matching exists?
                if(interfSecond != null)
                {
                    // Matching first and second found! Merge the same interface in op
                    Vertex interfAsN0 = getEquivalentVertex(1, interfFirst);
                    Vertex interfAsN1 = getEquivalentVertex(2, interfSecond);
                    
                    // Mirror a place instead the interface
                    PlaceVertex placeInterf = getOperationGraph().insertPlace((String) interfFirst.getValue());
                    cloneEdges(interfAsN0, placeInterf);
                    cloneEdges(interfAsN1, interfAsN1);

                    // Remove
                    getOperationGraph().getModel().remove(interfAsN0);
                    getOperationGraph().getModel().remove(interfAsN1);
                }
                
                operationGraph = (new ParallelismOperation(operationGraph, firstGraph, secondGraph)).getOperationGraph();
            }
        }
    }
}