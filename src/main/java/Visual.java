import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

class Visual extends JFrame {
    private List<DefaultGraphCell> c = new ArrayList<>();
    private List<Treap<Integer>> el = new ArrayList<>();
    private JFrame frame = new JFrame("Treap");
    private JScrollPane scrollPane = new JScrollPane();


    Visual(Treap<Integer> test) {
        main(test);
    }

    private void main(Treap<Integer> treap) {
        GraphModel model = new DefaultGraphModel();
        JGraph graph = new JGraph(model);

        graph.setCloneable(true);

        graph.setInvokesStopCellEditing(true);

        graph.setJumpToDefaultPort(true);

        int height = treap.height();
        double x = Math.pow(2, height) * 60; // 60 - w - ширина одного ущла дерева
        // y = 0
        adderTreap(treap, x, 10, height);
        List<DefaultGraphCell> cells = new ArrayList<>(c);


        for (int i = 0; i < el.size(); i++) {               //индекс родителя
            Treap<Integer> left = el.get(i).getLeftTreap();

            if (left != null && left.getValue() != null) {
                Treap<Integer> child = treap.find(left.getValue());
                int indCh = el.indexOf(child);              //индекс ребёнка
                DefaultEdge edge = new DefaultEdge();
                edge.setSource(cells.get(i).getChildAt(0));
                edge.setTarget(cells.get(indCh).getChildAt(0));
                int arrow = GraphConstants.ARROW_CLASSIC;
                GraphConstants.setLineEnd(edge.getAttributes(), arrow);
                GraphConstants.setEndFill(edge.getAttributes(), true);
                cells.add(edge);

            }
            Treap<Integer> right = el.get(i).getRightTreap();
            if (right != null && right.getValue() != null) {
                Treap<Integer> child = treap.find(right.getValue());
                int indCh = el.indexOf(child);              //индекс ребёнка
                DefaultEdge edge = new DefaultEdge();
                edge.setSource(cells.get(i).getChildAt(0));
                edge.setTarget(cells.get(indCh).getChildAt(0));
                int arrow = GraphConstants.ARROW_CLASSIC;
                GraphConstants.setLineEnd(edge.getAttributes(), arrow);
                GraphConstants.setEndFill(edge.getAttributes(), true);
                cells.add(edge);
            }
        }

        for (DefaultGraphCell i : cells) {
            graph.getGraphLayoutCache().insert(i);
        }


        scrollPane = new JScrollPane(graph);
        frame.getContentPane().add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    private static DefaultGraphCell createVertex(String name, double x,
                                                 double y) {

        DefaultGraphCell cell = new DefaultGraphCell(name);
        GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, (double) 60, (double) 20));
        GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
        cell.addPort();
        return cell;
    }

    private void adderTreap(Treap<Integer> treap, double x, double y, int level) {
        c.add(createVertex(treap.getValue() + "; " + treap.getPrior(), x, y));
        el.add(treap);
        if (treap.getLeftTreap() != null && treap.getLeftTreap().getValue() != null)
            adderLeft(treap.getLeftTreap(), x, y, level - 1);
        if (treap.getRightTreap() != null && treap.getRightTreap().getValue() != null)
            adderRight(treap.getRightTreap(), x, y, level - 1);
    }

    private void adderLeft(Treap<Integer> treap, double xPar, double yPar, int level) {
        if (treap != null && treap.getValue() != null) {
            double displace = Math.pow(2, level - 1) *  60;       // смещение
            adderTreap(treap, xPar - displace, yPar + 5 *  20, level);
        }
    }

    private void adderRight(Treap<Integer> treap, double xPar, double yPar, int level) {
        if (treap != null && treap.getValue() != null) {
            double displace = Math.pow(2, level - 1) * 60;       // смещение
            adderTreap(treap, xPar + displace, yPar + 5 * 20, level);
        }
    }

    void update(Treap<Integer> treap) {
        scrollPane.setVisible(false);
        frame.repaint();
        c.clear();
        el.clear();
        main(treap);
        frame.repaint();
    }
}