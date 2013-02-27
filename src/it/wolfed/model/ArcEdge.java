
package it.wolfed.model;

import it.wolfed.util.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Arcs are the edges type in a PetriNets.
 * 
 * Note: mxCell superclass force the setup of
 * the arc only AFTER the cell creation,
 * so sourceId and targetId are here only
 * for using them in the PetriNetGraph factory.
 *  
 * {@link PetriNetGraph#factory(org.w3c.dom.Node, java.lang.String)
 */
public class ArcEdge extends Edge
{
    /**
     * Source mapping of PNML file.
     */
    private String sourceId;
    
    /**
     * Target mapping of PNML file.
     */
    private String targetId;
    
    /**
     * {@link ArcEdge} Constructor. 
     * 
     * @param parent
     * @param id
     * @param value 
     */
    public ArcEdge(Object parent, String id, Object value)
    {
        super(
            parent,
            id,
            value,
            null,
            null
        );  
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
     * @return ArcEdge
     */
    public static ArcEdge factory(Object parent, Node dom)
    {
        NamedNodeMap transitionAttributes = dom.getAttributes();
        String id = transitionAttributes.getNamedItem(Constants.PNML_ID)
                        .getNodeValue();
        
        String sourceId = transitionAttributes.getNamedItem(Constants.PNML_SOURCE)
                             .getNodeValue();

        String targetId = transitionAttributes.getNamedItem(Constants.PNML_TARGET)
                        .getNodeValue();
        
        ArcEdge arc = new ArcEdge(parent, id, null);
        
        arc.setTargetId(targetId);
        arc.setSourceId(sourceId);
        return arc;
    };
    
    /**
     * Gets Pnml source;
     * 
     * @return String
     */
    public String getSourceId()
    {
        return sourceId;
    }
    
    /**
     * Sets Pnml source.
     * 
     * @param sourceId 
     */
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    /**
     * Gets Pnml target.
     * 
     * @return String
     */
    public String getTargetId()
    {
        return targetId;
    }

    /**
     * Sets Pnml target.
     * 
     * @param targetId 
     */
    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }

    public Element exportPNML(Document doc) {
        /*
         * <arc id="a8" source="p4" target="t4">
         */
	Element arcAsXML = doc.createElement(Constants.PNML_ARC);
	arcAsXML.setAttribute(Constants.PNML_ID, getId());
	arcAsXML.setAttribute(Constants.PNML_SOURCE, getSourceId());
	arcAsXML.setAttribute(Constants.PNML_TARGET, getTargetId());
	return arcAsXML;
    }

    public String exportDOT() {
        return "\n" + getSourceId() + " -> " + getTargetId() + ";";
        
    }
}