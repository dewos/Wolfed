
package it.wolfed.manipulation;

import com.mxgraph.model.mxCell;
import it.wolfed.model.PetriNetGraph;
import it.wolfed.model.Vertex;

/**
 * Some handy shortcut methods for all the common editing actions.
 */
public class GraphManipulation
{
    /**
    * Remove, in a graph, a vertex and his edges.
    * 
    * @param vertex 
    */
    public static void removeVertexAndHisEdges(PetriNetGraph graph, Vertex vertex)
    {
        for(Object edgeObj : graph.getEdges(vertex))
        {
            graph.getModel().remove(edgeObj);
        }

        graph.getModel().remove(vertex);
    }
    
    /**
     * Clone, in a graph, all incoming edges from a vertex to another.
     * 
     * @param from
     * @param to 
     */
    public static void cloneIncomingEdges(PetriNetGraph graph, Vertex from, Vertex to)
    {
        for(Object edgeOjb : graph.getIncomingEdges(from))
        {
            mxCell edge = (mxCell) edgeOjb;
            graph.insertArc(edge.getId(), (Vertex) edge.getSource(), to);
        }
    }
    
    /**
     * Clone, in a graph, all outgoing edges from a vertex to another.
     * 
     * @param from
     * @param to 
     */
    public static void cloneOutgoingEdges(PetriNetGraph graph, Vertex from, Vertex to)
    {
        for(Object edgeOjb : graph.getOutgoingEdges(from))
        {
            mxCell edge = (mxCell) edgeOjb;
            graph.insertArc(edge.getId(), to, (Vertex) edge.getTarget());
        }
    }
    
    /**
     * Clone, in a graph, all edges from a vertex to another.
     * 
     * @param from
     * @param to 
     */
    public static void cloneEdges(PetriNetGraph graph, Vertex from, Vertex to)
    {
         cloneIncomingEdges(graph, from, to);
         cloneOutgoingEdges(graph, from, to);
    }
}
