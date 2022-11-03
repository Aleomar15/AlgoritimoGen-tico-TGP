
package aggenetico_tgp;

import javax.swing.JOptionPane;

/**
 *
 * @author Alexandre Marinho
 */
public class AgGenetico_Tgp {

   static Tgp tgp;
    public static void main(String[] args) {
        tgp = new Tgp();
        tgp.setnVertice(Integer.parseInt(JOptionPane.showInputDialog("Numero de vertices:")));
        tgp.Ag_Tgp();
    }
    
}
