
package it.wolfed.model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

abstract public class Edge extends mxCell
{
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