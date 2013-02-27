
package it.wolfed.swing;

import it.wolfed.util.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarController_1 extends JMenuBar
{   
    public MenuBarController_1(final WolfedEditorCopyOne editor)
    {
        // File
        {
            JMenu fileMenu = new JMenu("File");
            fileMenu.setMnemonic('F');
            add(fileMenu);

            // New
            JMenuItem newItem = new JMenuItem("New");
            newItem.setMnemonic('N');
            newItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.newFile();
                }
            });
            
            fileMenu.add(newItem);

            // Open
            JMenuItem openItem = new JMenuItem("Open");
            openItem.setMnemonic('O');
            openItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.openFile();
                }
            });
            
            fileMenu.add(openItem);

            // Save
            JMenuItem saveItem = new JMenuItem("Save");
            saveItem.setMnemonic('v');
            saveItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.saveFile(Constants.WOLFED_EXPORT_DOT);
                    editor.saveFile(Constants.WOLFED_EXPORT_PNML);
                }
            });
            fileMenu.add(saveItem);	

            // Exit
            JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.setMnemonic('x');
            exitItem.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    editor.dispose();
                }
            });
            fileMenu.add(exitItem);
        }
            
        // Operations
        {
            JMenu operationMenu = new JMenu("Operations");
            operationMenu.setMnemonic('p');
            add(operationMenu);
            
            for(String operation : editor.getOperations())
            {
                JMenuItem operationItem = new JMenuItem(operation);
                operationItem.addActionListener(new ActionListener() 
                {  
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        editor.executeOperation(e.getActionCommand());
                    }
                });
                
                operationMenu.add(operationItem);
            }
        }
            
        // Layout
        {
            JMenu layoutMenu = new JMenu("Layouts");
            layoutMenu.setMnemonic('l');
            add(layoutMenu);

            for(String layout : editor.getLayouts())
            {
                JMenuItem layoutItem = new JMenuItem(layout);
                layoutItem.addActionListener(new ActionListener() 
                {  
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        editor.executeLayout(null, e.getActionCommand());
                    }
                });
                
                layoutMenu.add(layoutItem);
            }
        }
        
        // About
        {
            JMenu aboutMenu = new JMenu("About");
            aboutMenu.setMnemonic('a');
            add(aboutMenu); 
        }
    }
}
