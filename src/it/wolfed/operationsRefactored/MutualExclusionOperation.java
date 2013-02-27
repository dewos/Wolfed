package it.wolfed.operationsRefactored;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.Vertex;

/**
 *  MutualExclusion Operation
 */
public class MutualExclusionOperation extends Operation{
    private PetriNetGraph firstGraph;
    private PetriNetGraph secondGraph;
    private PlaceVertex placeVertex;
    private PetriNetGraph currentGraph;
    public MutualExclusionOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
    {
        super(operationGraph);
        this.firstGraph = (new IterationPatternOperation(new PetriNetGraph("1"), firstGraph)).getOperationGraph();
        this.secondGraph = (new IterationPatternOperation(new PetriNetGraph("2"), secondGraph)).getOperationGraph();
        
//        this.operationGraph = (new MergeGraphsOperation(operationGraph, this.firstGraph, this.secondGraph)).getOperationGraph();
        
        this.operationGraph = (new ParallelismOperation(operationGraph, this.firstGraph, this.secondGraph)).getOperationGraph();
        execute();
    }

    @Override
    void process() throws Exception {
        
        placeVertex = this.operationGraph.insertPlace(null);
        this.currentGraph = this.firstGraph;
        connectPlace(1);
        this.currentGraph = this.secondGraph;
        connectPlace(2);
        
        // set tokens
        placeVertex.setTokens(1);
    }

    private void connectPlace(int index) {
        Vertex initialPlaceAsFirst = getEquivalentVertex(index, this.currentGraph.getInitialPlaces().get(0));
        Vertex finalPlaceAsFirst = getEquivalentVertex(index, this.currentGraph.getFinalPlaces().get(0));
        Object[] initialTransitions = this.operationGraph.getOutgoingEdges(initialPlaceAsFirst);
        for (Object edgeOjb : initialTransitions) {
            mxCell edge = (mxCell) edgeOjb;
            this.operationGraph.insertArc(null, placeVertex, (Vertex) edge.getTarget());
        }
        
        Object[] finalTransitions = this.operationGraph.getIncomingEdges(finalPlaceAsFirst);
        for (Object edgeOjb : finalTransitions) {
            mxCell edge = (mxCell) edgeOjb;
            this.operationGraph.insertArc(null, (Vertex) edge.getSource(), placeVertex);
        }
    }
}
