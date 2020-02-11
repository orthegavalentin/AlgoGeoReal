package ui;

import java.awt.Color;


public class Style {
	public static final int defaultHalfWidth = 0;
	static float hue = .0f;
	private boolean drawLabel;
	private boolean drawLine;
	private Color color;
	public pointStyle drawPoint;
	private int pointSize = 5;
	
	public static Style createShadeStyle() {
		Style grayStyle = new Style();
		grayStyle.color = new Color(128,128,128);
		grayStyle.drawPoint = pointStyle.square;
		grayStyle.pointSize = 4;
		return grayStyle;
	};
	
	public Style() {
		super();
		this.drawLabel = false;
		this.drawPoint = pointStyle.disc;
		this.drawLine = true;
		this.color = Color.getHSBColor(Style.hue, 0.7f, 0.7f);
		Style.hue = Style.hue < 1 ? Style.hue + 0.05f : .0f;
	}

	public boolean drawLabel() {
		return drawLabel;
	}
	
	public boolean drawLine() {
		return drawLine;
	}

	public Color color() {
		return color;
	}

	public pointStyle drawPoint() {
		return drawPoint;
	}

	public boolean drawPoints() {
		return drawPoint != pointStyle.empty;
	}

	public int getWidth() {
		return pointSize;
	}
}
