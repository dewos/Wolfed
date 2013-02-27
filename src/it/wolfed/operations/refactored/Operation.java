
package it.wolfed.operations.refactored;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;

/**
 * Basic Operation Class.
 */
abstract public class Operation
{
    /**
     * Holds the graph to edit.
     */
    protected PetriNetGraph operationGraph;
    
    /**
     * {@link Operation} Constructor.
     * 
     * @param operationGraph
     */
    public Operation(PetriNetGraph operationGraph)
    {
        this.operationGraph = operationGraph;
    }
    
    /**
     * Execute the specific operation process.
     */
    protected void execute()
    {
        operationGraph.getModel().beginUpdate();

        try
        {
            process();
        }     
        finally
        {
            operationGraph.getModel().endUpdate();
        }
    }
    
    /**
     * Abstract definition for process in subclass.
     */
    abstract void process();
    
    /**
     * Return the graph if is a Graph is a valid workflow.
     * 
     * @param graph
     * @return PetriNetGraph
     * @throws Exception 
     */
    protected PetriNetGraph getIfIsWorkFlow(PetriNetGraph graph) throws Exception
    {
        if (graph.isWorkFlow() == false)
        {
            throw new Exception("WorkFlow required! " + graph.getId() + " failed!");
        }
        
        return graph;
    }
    
    /**
     * Returns current operationGraph.
     * 
     * @return PetriNetGraph
     */
    public PetriNetGraph getOperationGraph()
    {
        return operationGraph;
    }
    
    /**
     * Returns a prefixed id per input net.
     * 
     * @param id
     * @return prefixed id 
     */
    protected String getPrefix(int id)
    {
        return "n" + id + "_";
    }
    
    /**
     * Cerca, nella rete operazionale, un vertice con lo stesso id 
     * di quello di una rete diversa (valutando il prefisso).
     * 
     * @param id 
     * @param sameVertex    il vertice da cercare
     * @return 
     */
    public Vertex getEquivalentVertex(int id, Vertex sameVertex)
    {
        return operationGraph.getVertexById(getPrefix(id) + sameVertex.getId());
    }
    
     /**
     * 
     * @param vertex 
     */
    protected void removeVertexAndHisEdges(Vertex vertex)
    {
        for(Object edgeObj : getOperationGraph().getEdges(vertex))
        {
            operationGraph.getModel().remove(edgeObj);
        }

        operationGraph.getModel().remove(vertex);
    }
    
    protected void cloneIncomingEdges(Vertex from, Vertex to)
    {
        for(Object edgeOjb : getOperationGraph().getIncomingEdges(from))
        {
            mxCell edge = (mxCell) edgeOjb;
            getOperationGraph().insertArc(edge.getId(), (Vertex) edge.getSource(), (Vertex) to);
        }
    }
    
    protected void cloneOutgoingEdges(Vertex from, Vertex to)
    {
        for(Object edgeOjb : getOperationGraph().getOutgoingEdges(from))
        {
            mxCell edge = (mxCell) edgeOjb;
            getOperationGraph().insertArc(edge.getId(), (Vertex) to, (Vertex) edge.getTarget());
        }
    }
    
    protected void cloneEdges(Vertex from, Vertex to)
    {
        cloneIncomingEdges(from, to);
        cloneOutgoingEdges(from, to);
    }
}