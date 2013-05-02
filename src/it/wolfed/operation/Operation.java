
package it.wolfed.operation;

import it.wolfed.manipulation.GraphManipulation;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;
import it.wolfed.util.Constants;

/**
 * Basic Operation Class.
 * 
 * A base class that trigger and execute all operation with some handy
 * shortcut methods for all the common editing actions.
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
     * @throws Exception 
     */
    protected void execute() throws Exception
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
    abstract void process() throws Exception;
    
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
        return Constants.OPERATION_PREFIX + id + "_";
    }
    
    /**
     * Search, in the {@link Operation#operationGraph}, the equivalent vertex from another graph.
     * 
     * @param id            input graph prefix (first = 1, second = 2 etc...)
     * @param sameVertex    vertex to search
     * @return 
     */
    public Vertex getEquivalentVertex(int id, Vertex sameVertex)
    {
        return operationGraph.getVertexById(getPrefix(id) + sameVertex.getId());
    }
    
     /**
     * Remove, in {@link Operation#operationGraph}, a vertex and his edges.
     * 
     * @param vertex 
     */
    protected void removeVertexAndHisEdges(Vertex vertex)
    {
        GraphManipulation.removeVertexAndHisEdges(operationGraph, vertex);
    }
    
    /**
     * Clone, in {@link Operation#operationGraph}, all incoming edges from a vertex to another.
     * 
     * @param from
     * @param to 
     */
    protected void cloneIncomingEdges(Vertex from, Vertex to)
    {
         GraphManipulation.cloneIncomingEdges(operationGraph, from, to);
    }
    
    /**
     * Clone, in {@link Operation#operationGraph}, all outgoing edges from a vertex to another.
     * 
     * @param from
     * @param to 
     */
    protected void cloneOutgoingEdges(Vertex from, Vertex to)
    {
        GraphManipulation.cloneOutgoingEdges(operationGraph, from, to);
    }
    
    /**
     * Clone, in {@link Operation#operationGraph}, all edges from a vertex to another.
     * 
     * @param from
     * @param to 
     */
    protected void cloneEdges(Vertex from, Vertex to)
    {
        GraphManipulation.cloneEdges(operationGraph, from, to);
    }
}