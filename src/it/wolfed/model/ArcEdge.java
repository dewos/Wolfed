
package it.wolfed.model;

import com.mxgraph.model.mxCell;
import it.wolfed.util.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Arcs are the edges type in a PetriNets.
 */
public class ArcEdge extends Edge
{
    /**
     * {@link ArcEdge} Constructor. 
     * 
     * @param parent
     * @param id
     * @param value
     * @param source
     * @param target  
     */
    public ArcEdge(Object parent, String id, Object value, Vertex source, Vertex target)
    {
        super(
            parent,
            id,
            value,
            null,
            null
        );  
        
        setSource(source);
        setTarget(target);
    }
    
    /**
    * Generate a new {@link ArcEdge} from a pnml valid dom node.
    * 
    *  <arc id="a31" source="t7" target="p7">
    *    <inscription>
    *       <text>1</text>
    *     </inscription>
    *     <graphics/>
    *     <toolspecific tool="WoPeD" version="1.0">
    *        <probability>1.0</probability>
    *        <displayProbabilityOn>false</displayProbabilityOn>
    *        <displayProbabilityPosition x="500.0" y="0.0"/>
    *     </toolspecific>
    *  </arc>
     * 
     * @param parent
     * @param dom
     * @param graph 
     * @return ArcEdge
     */
    public static ArcEdge factory(Object parent, Node dom, PetriNetGraph graph)
    {
        String id = dom.getAttributes().getNamedItem(Constants.PNML_ID).getNodeValue();
        String sourceId = dom.getAttributes().getNamedItem(Constants.PNML_SOURCE).getNodeValue();
        String targetId = dom.getAttributes().getNamedItem(Constants.PNML_TARGET).getNodeValue();
        
        return new ArcEdge(parent, id, null, graph.getVertexById(sourceId), graph.getVertexById(targetId));
    };
    
    /**
     * Export PNML.
     * Static for the edge creation bug.
     * 
     * @param doc
     * @param edge
     * @return 
     */
    public static Element exportPNML(Document doc, mxCell edge)
    {
        /** <arc id="a8" source="p4" target="t4"> */         
        Element arc = doc.createElement(Constants.PNML_ARC);
        arc.setAttribute(Constants.PNML_ID, edge.getId());
        arc.setAttribute(Constants.PNML_SOURCE, edge.getSource().getId());
        arc.setAttribute(Constants.PNML_TARGET, edge.getTarget().getId());

        return arc;
    }
    
    /**
     * Export DOT edge.
     * Static for the edge creation bug.
     *
     * @return
     */
    public static String exportDOT(mxCell edge)
    {
        return "\n" + edge.getSource().getId().replaceAll("-", "") + " -> " + edge.getTarget().getId().replaceAll("-", "") + ";";
    }
}
