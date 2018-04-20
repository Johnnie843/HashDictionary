import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Graph extends JPanel {

	private int padding = 40;
	private int labelPadding = 40;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 4;
	private List<Integer> accesses;
	private List<Float> loadFactors;

	public Graph(List<Integer> accesses, List<Float> loadFactors) {
		this.accesses = accesses;
		this.loadFactors = loadFactors;
	}

	@Override
	protected void paintComponent(Graphics g) {

		/// design layout
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// my table space
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, 500, 500);
		g2.setColor(Color.BLACK);

		// y axis
		g2.drawLine(padding + labelPadding, padding, padding + labelPadding, padding + 500);
		// x axis
		g2.drawLine(padding + labelPadding, padding + 500, padding + labelPadding + 500, padding + 500);

		// draw axis label
		g2.setColor(lineColor);
		g2.setStroke(GRAPH_STROKE);
		g2.setColor(Color.BLACK);
		String yAxisLabel = "Accesses";
		g2.drawString(yAxisLabel, 5, getHeight() / 2);
		g2.setColor(Color.BLACK);
		String xAxisLabel = "Load Factor";
		g2.drawString(xAxisLabel, getWidth() / 2, getHeight() - 20);

		// loop through and make grid for y axis
		for (int i = 0; i <= 10; i++) {
			// draw grid
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = padding + (i * 50);
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);
			g2.setColor(gridColor);
			g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, padding + labelPadding + 500, y1);

			// draw labels
			g2.setColor(Color.BLACK);
			String yLabel = Integer.toString(10 - i);
			FontMetrics metrics = g2.getFontMetrics();
			int labelWidth = metrics.stringWidth(yLabel);
			g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			g2.drawLine(x0, y0, x1, y1);
		}
		// loop through and make grid for x axis
		for (int i = 0; i <= 10; i++) {

			// draw grid
			int x0 = padding + labelPadding + 500 - (50 * i);
			int x1 = x0;
			int y0 = getHeight() - padding - labelPadding;
			int y1 = y0 - pointWidth;
			g2.setColor(gridColor);
			g2.drawLine(x0, padding + 500, x1, padding);
			// draw label
			g2.setColor(Color.BLACK);
			String xLabel = "." + Integer.toString(10 - i);
			FontMetrics metrics = g2.getFontMetrics();
			int labelWidth = metrics.stringWidth(xLabel);
			g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 20);
			g2.drawLine(x0, y0 + 20, x1, y1 + 19);
		}

		// create points from accessCount array and loadFactor array
		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < accesses.size(); i++) {
			int x1 = (int) (padding + labelPadding + (loadFactors.get(i) * 10 * 50));
			int y1 = (int) (padding + 500 - (accesses.get(i) * 50));
			graphPoints.add(new Point(x1, y1));
		}

		// plot points
		g2.setColor(lineColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);
		}
		g2.setColor(pointColor);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
	}

	public static void drawGraph() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				List<Integer> accesses = HashTableAnalysis.getAccesses();
				List<Float> loadFactors = HashTableAnalysis.getLoadFactors();
				Graph mainPanel = new Graph(accesses, loadFactors);
				mainPanel.setPreferredSize(new Dimension(600, 600));
				JFrame frame = new JFrame("Hash Table Analysis");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(mainPanel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}