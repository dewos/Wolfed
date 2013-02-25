
package it.wolfed.model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

/**
 * Edges are the link elements of the graph model. 
 */
abstract public class Edge extends mxCell
{
    /**
     * {@link Edge} Constructor.
     * 
     * @param parent
     * @param id
     * @param value
     * @param source
     * @param target 
     */
    public Edge(Object parent, String id, Object value, 
        Object source, Object target)
    {
        setId(id);
        setValue(value);
        setEdge(true);
        setGeometry(new mxGeometry());
        getGeometry().setRelative(true);
    }
}