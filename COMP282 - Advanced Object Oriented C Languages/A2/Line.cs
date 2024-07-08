using System;

public class Line
{
    private Point startPoint;
    private Point endPoint;
    private Color color;

    public Line(int firstX, int firstY, int secondX, int secondY, Color color)
    {
        this.StartPoint = new Point(firstX, firstY);
        this.EndPoint = new Point(secondX, secondY);
        this.Color = color;
    }

    public Point StartPoint
    {
        get { return startPoint; }
        set { startPoint = value; }
    }

    public Point EndPoint
    {
        get { return endPoint; }
        set { endPoint = value; }
    }

    public Color Color
    {
        get { return color; }
        set { color = value; }
    }

    public override string ToString()
    {
        return $"Start point: ({StartPoint.X}, {StartPoint.Y}), End point: ({EndPoint.X}, {EndPoint.Y})";
    }
}