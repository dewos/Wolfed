/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wolfed.operationsRefactored;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.Vertex;

/**
 * Alternation Operation
 */
public class AlternationOperation extends Operation{
    private PetriNetGraph firstGraph;
    private PetriNetGraph secondGraph;
    
    private PlaceVertex tokenedPlaceVertex;
    private PlaceVertex unTokenedPlaceVertex;
    private PlaceVertex currentPlace;
    private Vertex initialPlace;
    private Vertex finalPlace;
    
    public AlternationOperation(PetriNetGraph operationGraph, PetriNetGraph firstGraph, PetriNetGraph secondGraph) throws Exception
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
        
        this.tokenedPlaceVertex = this.operationGraph.insertPlace(null);
        this.currentPlace = this.tokenedPlaceVertex;
        initialPlace = getEquivalentVertex(1, this.firstGraph.getInitialPlaces().get(0));
        finalPlace = getEquivalentVertex(2, this.secondGraph.getFinalPlaces().get(0));
        
        connectPlace();
        
        this.unTokenedPlaceVertex = this.operationGraph.insertPlace(null);
        this.currentPlace = this.unTokenedPlaceVertex;
        initialPlace = getEquivalentVertex(2, this.secondGraph.getInitialPlaces().get(0));
        finalPlace = getEquivalentVertex(1, this.firstGraph.getFinalPlaces().get(0));
        connectPlace();
        
        // set tokens
        tokenedPlaceVertex.setTokens(1);
    }
    
     private void connectPlace() {
        Object[] initialTransitions = this.operationGraph.getOutgoingEdges(this.initialPlace);
        for (Object edgeOjb : initialTransitions) {
            mxCell edge = (mxCell) edgeOjb;
            this.operationGraph.insertArc(null, this.currentPlace, (Vertex) edge.getTarget());
        }
        
        Object[] finalTransitions = this.operationGraph.getIncomingEdges(this.finalPlace);
        for (Object edgeOjb : finalTransitions) {
            mxCell edge = (mxCell) edgeOjb;
            this.operationGraph.insertArc(null, (Vertex) edge.getSource(), this.currentPlace);
        }
    }
}
