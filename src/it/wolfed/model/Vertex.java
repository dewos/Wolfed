
package it.wolfed.model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

abstract public class Vertex extends mxCell
{
    public Vertex(Object parent, String id, Object value,
			double x, double y, double width, double height, String style)
    {
      setId(id);
      setValue(value);
      
      setGeometry(new mxGeometry(x, y, width, height));
      setStyle(style);

		setVertex(true);
		setConnectable(true);
	}
}
