package drawing;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class PnlDrawing extends JPanel {

	private DrawingApp frame;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Point startPoint;
	private Shape selected;

	public PnlDrawing(DrawingApp frame) {
		this.frame = frame;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pritisakMisa(arg0);
			}
		});

	}

	protected void pritisakMisa(MouseEvent e) {
		Shape newShape = null;
		int x1 = 0;
		int y1 = 0;
		if (frame.getTglbtnSelektuj().isSelected()) {
			selected = null;
			Iterator<Shape> it = shapes.iterator();
			while (it.hasNext()) {
				Shape shape = it.next();
				shape.setSelected(false);
				if (shape.contains(e.getX(), e.getY())) {
					selected = shape;
				}
			}
			if (selected != null) {
				selected.setSelected(true);
			}
		} else if (frame.getTglbtnTacka().isSelected()) {
			DlgUnosTackaLinija unos = new DlgUnosTackaLinija();
			unos.setTitle("Tacka");
			unos.pack();
			unos.setVisible(true);
			if (unos.isOK()) {
				Point p = new Point(e.getX(), e.getY(), false);
				p.setColor(unos.getC());
				newShape = p;
			}
		} else if (frame.getTglbtnLinija().isSelected()) {
			DlgUnosTackaLinija unos = new DlgUnosTackaLinija();
			if (startPoint == null) {
				startPoint = new Point(e.getX(), e.getY());
			} else {
				Point endPoint = new Point(e.getX(), e.getY());
				unos.setTitle("Linija");
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					Line l = new Line(startPoint, endPoint, false);
					l.setColor(unos.getC());
					newShape = l;
				}
				startPoint = null;
			}
		} else if (frame.getTglbtnPravougaonik().isSelected()) {
			DlgUnos unos = new DlgUnos();
			unos.pack();
			unos.setTitle("Pravougaonik");
			unos.setVisible(true);
			if (unos.isOK()) {
				x1 = Integer.parseInt(unos.getTxtUnos().getText());
				y1 = Integer.parseInt(unos.getTxtUnos1().getText());
			}
			Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), y1, x1, false);
			r.setColor(unos.getC());
			r.setColorUnutrasnjost(unos.getColorUnutrasnjost());
			try {
				newShape = r;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Pogresan unos!", "Greska", JOptionPane.ERROR_MESSAGE);
			}
		} else if (frame.getTglbtnKrug().isSelected()) {
			DlgUnosKrug unos = new DlgUnosKrug();
			unos.pack();
			unos.setVisible(true);
			if (unos.isOK()) {
				x1 = Integer.parseInt(unos.getTxtPoluprecnik().getText());
			}
			Circle c = new Circle(new Point(e.getX(), e.getY()), x1, false);
			c.setColor(unos.getC());
			c.setColorUnutrasnjost(unos.getColorUnutrasnjost());
			try {
				newShape = c;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Pogresan unos!", "Greska", JOptionPane.ERROR_MESSAGE);
			}
		} else if (frame.getTglbtnKrugSaRupom().isSelected()) {
			DlgUnos unos = new DlgUnos();
			unos.getLblUnesite().setText("Unesite poluprecnik spoljasnjeg kruga:");
			unos.getLblUnesite1().setText("Unesite poluprecnik unutrasnjeg kruga:");
			unos.setTitle("Krug sa rupom");
			unos.getLblIzaberiteBoju().setText("Izaberite boju ivice spoljasnjeg kruga:");
			unos.getLblIzaberiteBojuUnutrasnjosti().setText("Izabetite boju sposljasnjeg kruga:");
			unos.getLblIzaberiteBojuUnutrasnjeg().setVisible(true);
			unos.getLblIzaberiteBojuIvice().setVisible(true);
			unos.getBtnBojaIviceUnutrasnji().setVisible(true);
			unos.getBtnBojaUnutrasnjiKrug().setVisible(true);
			unos.pack();
			unos.setVisible(true);
			if (unos.isOK()) {
				x1 = Integer.parseInt(unos.getTxtUnos().getText());
				y1 = Integer.parseInt(unos.getTxtUnos1().getText());
			}
			Donut d = new Donut(new Point(e.getX(), e.getY()), x1, y1, false);
			d.setColor(unos.getC());
			d.setColorIviceMali(unos.getColorIviceUnutrasnjiKrug());
			d.setColorUnutrasnostMali(unos.getColorUnutrasnjiKrugBoja());
			d.setColorUnutrasnjostVeliki(unos.getColorUnutrasnjost());
			try {
				newShape = d;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Pogresan unos!", "Greska", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (newShape != null) {
			shapes.add(newShape);
		}
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}

	public Shape getSelected() {
		return selected;
	}

	public void setSelected(Shape selected) {
		this.selected = selected;
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}
}
