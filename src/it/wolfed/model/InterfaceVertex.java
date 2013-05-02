
package it.wolfed.model;

import it.wolfed.util.Constants;
import it.wolfed.util.IterableNodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Interface.
 */
public class InterfaceVertex extends Vertex
{
    /**
     * {@link InterfaceVertex} Constructor
     * 
     * @param parent
     * @param id
     * @param value 
     */
    public InterfaceVertex(Object parent, String id, Object value)
    {
        super(
            parent,
            id,
            value,
            0, 0, 40, 40,
            Constants.STYLE_INTERFACE
        );
    }
    
    /**
     * Generate a new {@link InterfaceVertex} from a pnml valid dom node.
     * From toolSpecific
     * 
     * 
     * <interface id="p2" /> 

     * @param parent
     * @param dom
     * @return  InterfaceVertex
     */  
    public static InterfaceVertex factory(Object parent, Node dom)
    {
        String id = dom.getAttributes().getNamedItem(Constants.PNML_ID).getNodeValue();
        return new InterfaceVertex(parent, id, id);
    };

    /**
     * Export PNML interface
     * 
     * @param doc
     * @return 
     */
    public Element exportPNML(Document doc) 
    {
        /**<interface id="i1"> */
        Element interf = doc.createElement(Constants.PNML_INTERFACE);
	interf.setAttribute(Constants.PNML_ID, getId());
        
        return interf;
    }
    
    /**
     * Export DOT interface
     * 
     * @return 
     */
      public String exportDOT() 
      {
        return "\n"+this.getId().replaceAll("-", "")+" [label=\""+getValue().toString()+"\", shape=\"doublecircle\", color=\"orange\" ];";
    }
}
