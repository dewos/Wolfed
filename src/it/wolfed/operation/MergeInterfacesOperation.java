package it.wolfed.operation;

import com.mxgraph.model.mxCell;
import it.wolfed.model.InterfaceVertex;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.PlaceVertex;
import it.wolfed.model.TransitionVertex;
import it.wolfed.model.Vertex;
import java.util.List;

public class MergeInterfacesOperation extends Operation
{  
    public MergeInterfacesOperation(List<PetriNetGraph> inputGraphs) throws Exception
    {
        super("system", inputGraphs, 2, true);
    }
    
    @Override
    void process()
    {
        mergeInterfaces();
        
        // @todo praticamente è il parallelOperation, bisogna decidere un modo
        // per poter utilizzare le operazioni dentro le operazioni
        parallel();
    }
    
    private void mergeInterfaces()
    {
        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);

        // Finds all interfaces in n0
        for(Object cellObj : net0.getChildVertices())
        {
            if(cellObj instanceof InterfaceVertex)
            {
                InterfaceVertex interfN0 = (InterfaceVertex) cellObj;
                InterfaceVertex interfN1 = (InterfaceVertex) net1.getVertexByValue(interfN0.getValue());
                
                // Matching exists?
                if(interfN1 != null)
                {
                    // Matching n0 and n1 found! Merge the same interface in op
                    Vertex interfAsN0 = getEquivalentVertex(net0, interfN0);
                    Vertex interfAsN1 = getEquivalentVertex(net1, interfN1);
                    
                    // Mirror a place instead the interface
                    PlaceVertex placeInterf = getOperationGraph().insertPlace((String) interfN0.getValue());
                    
                    // Move all the arcs as N0
                    for(Object edgeOjb : getOperationGraph().getIncomingEdges(interfAsN0))
                    {
                        mxCell edge = (mxCell) edgeOjb;
                        getOperationGraph().insertArc(edge.getId(), (Vertex) edge.getSource(), placeInterf);
                    }
                    
                    for(Object edgeOjb : getOperationGraph().getOutgoingEdges(interfAsN0))
                    {
                        mxCell edge = (mxCell) edgeOjb;
                        getOperationGraph().insertArc(edge.getId(), placeInterf, (Vertex) edge.getTarget());
                    }
                    
                    // Move all the arcs as N1
                    for(Object edgeOjb : getOperationGraph().getIncomingEdges(interfAsN1))
                    {
                        mxCell edge = (mxCell) edgeOjb;
                        getOperationGraph().insertArc(edge.getId(), (Vertex) edge.getSource(), placeInterf);
                    }
                    
                    for(Object edgeOjb : getOperationGraph().getOutgoingEdges(interfAsN1))
                    {
                        mxCell edge = (mxCell) edgeOjb;
                        getOperationGraph().insertArc(edge.getId(), placeInterf, (Vertex) edge.getTarget());
                    }
                    
                    // Remove as N0
                    removeVertexAndHisEdges(interfAsN0);
                    removeVertexAndHisEdges(interfAsN1);
                }
            }
        }
    }
    
    private void parallel()
    {
        PetriNetGraph net0 = getInputGraphs().get(0);
        PetriNetGraph net1 = getInputGraphs().get(1);
       
        // Initial Link pattern
        PlaceVertex pi = getOperationGraph().insertPlace(null);
        TransitionVertex ti = getOperationGraph().insertTransition(null);
        getOperationGraph().insertArc(null, pi, ti);
        
        Vertex initialPlaceAsN0 = getEquivalentVertex(net0, net0.getInitialPlaces().get(0));
        Vertex initialPlaceAsN1 = getEquivalentVertex(net1, net1.getInitialPlaces().get(0));
        
        getOperationGraph().insertArc(null, ti, initialPlaceAsN0);
        getOperationGraph().insertArc(null, ti, initialPlaceAsN1);
        
        // Final Link pattern
        PlaceVertex po = getOperationGraph().insertPlace(null);
        TransitionVertex to = getOperationGraph().insertTransition(null);
        
        Vertex finalPlaceAsN0 = getEquivalentVertex(net0, net0.getFinalPlaces().get(0));
        Vertex finalPlaceAsN1 = getEquivalentVertex(net1, net1.getFinalPlaces().get(0));

        getOperationGraph().insertArc(null, to, po);
        getOperationGraph().insertArc(null, finalPlaceAsN0, to);
        getOperationGraph().insertArc(null, finalPlaceAsN1, to);
    }
}