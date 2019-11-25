package graphicalElements;

import javax.swing.*;

public interface IMenu {

    public JPanel nord();

    public JPanel centre(JButton classique, JButton infini);

    public JPanel centreFin(JButton replay, JButton exit);

    public JPanel sud();

    public void menuPanel();

    public void start(boolean infinity);

    public void replay();

    //public void end();
}
