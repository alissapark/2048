import javax.swing.JFrame;

public class Window extends JFrame{
    Window(){
        board b = new board();

        this.add(b);

        this.setVisible(true);
        this.setTitle("2048");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
