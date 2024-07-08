using System.Data;
using System.Windows.Forms;

namespace A2
{
    public partial class DrawLines : Form
    {
        public DrawLines()
        {
            InitializeComponent();
        }

        private void DrawLines_Load(object sender, EventArgs e)
        {

        }
        private List<Point>? intersections;
        private void findIntersectButton_Click(object sender, EventArgs e)
        {
            intersections = new List<Point>();
            if (lines != null)
            {
                for (int i = 0; i < lines.Count - 1; i++)
                {
                    for (int j = i + 1; j < lines.Count; j++)
                    {
                        if (i != j)
                        {
                            if (Intersects(lines[i], lines[j], out Point intersection))
                            {
                                intersections.Add(intersection);
                            }
                        }
                    }
                }
                canvas.Invalidate();
            }
        }

        private static bool Intersects(Line line1, Line line2, out Point intersection)
        {
            intersection = Point.Empty;

            // Get the slopes and y-intercepts of the two lines
            float m1 = (float)(line1.EndPoint.Y - line1.StartPoint.Y) / (line1.EndPoint.X - line1.StartPoint.X);
            float b1 = line1.StartPoint.Y - m1 * line1.StartPoint.X;

            float m2 = (float)(line2.EndPoint.Y - line2.StartPoint.Y) / (line2.EndPoint.X - line2.StartPoint.X);
            float b2 = line2.StartPoint.Y - m2 * line2.StartPoint.X;

            // Check if the lines are parallel
            if (Math.Abs(m1 - m2) < 0.001)
            {
                return false;
            }

            // Calculate the intersection point
            float x = (b2 - b1) / (m1 - m2);
            float y = m1 * x + b1;
            intersection = new Point((int)x, (int)y);

            // Check if the intersection point is within the bounds of the two line segments
            if (!PointOnLine(line1.StartPoint, line1.EndPoint, intersection))
            {
                return false;
            }

            if (!PointOnLine(line2.StartPoint, line2.EndPoint, intersection))
            {
                return false;
            }

            return true;
        }

        private static bool PointOnLine(Point p1, Point p2, Point p)
        {
            int minX = Math.Min(p1.X, p2.X);
            int maxX = Math.Max(p1.X, p2.X);
            int minY = Math.Min(p1.Y, p2.Y);
            int maxY = Math.Max(p1.Y, p2.Y);

            return p.X >= minX && p.X <= maxX && p.Y >= minY && p.Y <= maxY;
        }

        private void removeButton_Click(object sender, EventArgs e)
        {
            if (dataGrid.SelectedCells.Count > 0) // Check if a cell is selected
            {
                int rowIndex = dataGrid.SelectedCells[0].RowIndex; // Get the index of the row that contains the selected cell
                if(rowIndex > -1 && rowIndex < dataGrid.Rows.Count - 1)
                {
                    dataGrid.Rows.RemoveAt(rowIndex); // Remove the row at the specified index
                    redraw();
                }
                else
                {
                    MessageBox.Show("Cannot delete, as selection is invalid", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            else
            {
                MessageBox.Show("Select a row to delete", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private Color currColor = Color.Black;
        private void colorButton_Click(object sender, EventArgs e)
        {
            ColorDialog colorDialog = new ColorDialog();

            // Display the color picker dialog and get the selected color
            if (colorDialog.ShowDialog() == DialogResult.OK)
            {
                currColor = colorDialog.Color;
            }
        }
        private void dataGrid_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            if (e.RowIndex -1 >= 0 && dataGrid.Rows[e.RowIndex - 1].Cells["Color"].Tag == null)
            {
                SetRowColor(e.RowIndex - 1, currColor);
            }
        }

        private void dataGrid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == dataGrid.Columns["Color"].Index && e.RowIndex >= 0)
            {
                ColorDialog colorDialog = new ColorDialog();

                if (colorDialog.ShowDialog() == DialogResult.OK)
                {
                    SetRowColor(e.RowIndex, colorDialog.Color);
                    redraw();
                }
            }
        }

        private void SetRowColor(int rowIndex, Color color)
        {
            if (rowIndex >= 0)
            {
                dataGrid.Rows[rowIndex].Cells["Color"].Tag = color;
                dataGrid.Rows[rowIndex].Cells["Color"].Style.BackColor = color;
            }
        }

        private List<Line>? lines;
        private void dataGrid_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.RowIndex < dataGrid.Rows.Count)
            {
                redraw();
            }
        }

        private void redraw()
        {
            lines = new List<Line>();
            for (int j = 0; j < dataGrid.Rows.Count - 1; j++)
            {
                bool validRow = true;
                int value;
                for (int i = 0; i < 4; i++)
                {
                    if (dataGrid.Rows[j].Cells[i].Value == null)
                    {
                        validRow = false;
                        dataGrid.Rows[j].Cells[i].ErrorText = "Cell cannot be empty";
                    }
                    else if (!int.TryParse(dataGrid.Rows[j].Cells[i].Value.ToString(), out value))
                    {
                        validRow = false;
                    }
                }
                if (validRow)
                {
                    Color color = (Color)dataGrid.Rows[j].Cells[4].Tag;
                    Line line = new Line(Convert.ToInt32(dataGrid.Rows[j].Cells[0].Value), Convert.ToInt32(dataGrid.Rows[j].Cells[1].Value), 
                        Convert.ToInt32(dataGrid.Rows[j].Cells[2].Value), Convert.ToInt32(dataGrid.Rows[j].Cells[3].Value), color);
                    lines.Add(line);
                }
            }
            canvas.Invalidate();
        }

        private void canvas_Paint(object sender, PaintEventArgs e)
        {
            e.Graphics.Clear(Color.White); // clear the PictureBox

            Pen pen = new Pen(Color.Black, 2); // create a pen for drawing the lines
            if (lines != null)
            {
                foreach (Line line in lines)
                {
                    pen.Color = line.Color; // set the pen color
                    e.Graphics.DrawLine(pen, line.StartPoint, line.EndPoint); // draw the line
                }
                pen.Color = currColor;
                if(intersections != null)
                {
                    foreach (Point intersection in intersections)
                    {
                        e.Graphics.DrawEllipse(pen, intersection.X - 10, intersection.Y - 10, 20, 20);
                    }
                }
            }
        }

        private Point firstClick;
        private Point secondClick;

        private void canvas_MouseDown(object sender, MouseEventArgs e)
        {
            if (firstClick.IsEmpty)
            {
                firstClick = e.Location;
            }
            else
            {
            secondClick = e.Location;
                // Create a new row in the DataGridView
                int rowIndex = dataGrid.Rows.Add(firstClick.X,firstClick.Y,secondClick.X,secondClick.Y);
                // Set the value of each cell in the new row
                /*dataGrid.Rows[rowIndex].Cells[0].Value = firstClick.X;
                dataGrid.Rows[rowIndex].Cells[1].Value = firstClick.Y;
                dataGrid.Rows[rowIndex].Cells[2].Value = secondClick.X;
                dataGrid.Rows[rowIndex].Cells[3].Value = secondClick.Y;*/
                SetRowColor(rowIndex, currColor);
                // Reset the firstClick variable
                firstClick = Point.Empty;
                redraw();
            }
        }

        private void dataGrid_CellValidating(object sender, DataGridViewCellValidatingEventArgs e)
        {
            if(e.ColumnIndex != 4)
            {
                int value;
                dataGrid.Rows[e.RowIndex].Cells[e.ColumnIndex].ErrorText = "";
                if (e.FormattedValue.ToString() != "")
                {
                    if (!int.TryParse(e.FormattedValue.ToString(), out value))
                    {
                        dataGrid.Rows[e.RowIndex].Cells[e.ColumnIndex].ErrorText = "Cell must be a valid numeric value.";
                    }
                }
            }
        }
    }
}