
package it.wolfed.model;

import it.wolfed.util.Constants;

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
}