namespace A2
{
    partial class DrawLines
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            DataGridViewCellStyle dataGridViewCellStyle1 = new DataGridViewCellStyle();
            colorButton = new Button();
            dataGrid = new DataGridView();
            findX = new DataGridViewTextBoxColumn();
            firstY = new DataGridViewTextBoxColumn();
            secondX = new DataGridViewTextBoxColumn();
            secondY = new DataGridViewTextBoxColumn();
            color = new DataGridViewTextBoxColumn();
            removeButton = new Button();
            findIntersectButton = new Button();
            canvas = new PictureBox();
            ((System.ComponentModel.ISupportInitialize)dataGrid).BeginInit();
            ((System.ComponentModel.ISupportInitialize)canvas).BeginInit();
            SuspendLayout();
            // 
            // colorButton
            // 
            colorButton.Location = new Point(810, 12);
            colorButton.Name = "colorButton";
            colorButton.Size = new Size(268, 40);
            colorButton.TabIndex = 2;
            colorButton.Text = "Color";
            colorButton.UseVisualStyleBackColor = true;
            colorButton.Click += colorButton_Click;
            // 
            // dataGrid
            // 
            dataGrid.AllowUserToDeleteRows = false;
            dataGrid.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGrid.Columns.AddRange(new DataGridViewColumn[] { findX, firstY, secondX, secondY, color });
            dataGrid.Location = new Point(810, 58);
            dataGrid.Name = "dataGrid";
            dataGrid.RowHeadersVisible = false;
            dataGrid.RowHeadersWidth = 72;
            dataGrid.RowTemplate.Height = 50;
            dataGrid.ScrollBars = ScrollBars.Vertical;
            dataGrid.Size = new Size(555, 720);
            dataGrid.TabIndex = 4;
            dataGrid.CellClick += dataGrid_CellClick;
            dataGrid.RowsAdded += dataGrid_RowsAdded;
            dataGrid.RowValidated += dataGrid_CellEndEdit;
            dataGrid.CellValidating += dataGrid_CellValidating;
            // 
            // findX
            // 
            findX.AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader;
            findX.HeaderText = "First X";
            findX.MinimumWidth = 9;
            findX.Name = "findX";
            findX.Width = 110;
            // 
            // firstY
            // 
            firstY.HeaderText = "First Y";
            firstY.MinimumWidth = 9;
            firstY.Name = "firstY";
            firstY.Width = 110;
            // 
            // secondX
            // 
            secondX.HeaderText = "Second X";
            secondX.MinimumWidth = 9;
            secondX.Name = "secondX";
            secondX.Width = 110;
            // 
            // secondY
            // 
            secondY.HeaderText = "Second Y";
            secondY.MinimumWidth = 9;
            secondY.Name = "secondY";
            secondY.Width = 110;
            // 
            // color
            // 
            color.HeaderText = "Color";
            color.MinimumWidth = 9;
            color.Name = "color";
            color.ReadOnly = true;
            color.Width = 110;
            color.Tag = typeof(Color);
            // 
            // removeButton
            // 
            removeButton.Location = new Point(1096, 12);
            removeButton.Name = "removeButton";
            removeButton.Size = new Size(269, 40);
            removeButton.TabIndex = 3;
            removeButton.Text = "Remove";
            removeButton.UseVisualStyleBackColor = true;
            removeButton.Click += removeButton_Click;
            // 
            // findIntersectButton
            // 
            findIntersectButton.Location = new Point(810, 784);
            findIntersectButton.Name = "findIntersectButton";
            findIntersectButton.Size = new Size(555, 40);
            findIntersectButton.TabIndex = 5;
            findIntersectButton.Text = "Find Intersections";
            findIntersectButton.UseVisualStyleBackColor = true;
            findIntersectButton.Click += findIntersectButton_Click;
            // 
            // canvas
            // 
            canvas.BackColor = SystemColors.Window;
            canvas.BorderStyle = BorderStyle.FixedSingle;
            canvas.Location = new Point(12, 12);
            canvas.Name = "canvas";
            canvas.Size = new Size(784, 812);
            canvas.TabIndex = 0;
            canvas.TabStop = false;
            canvas.Paint += canvas_Paint;
            canvas.MouseDown += canvas_MouseDown;
            // 
            // DrawLines
            // 
            AutoScaleDimensions = new SizeF(12F, 30F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1377, 836);
            Controls.Add(canvas);
            Controls.Add(findIntersectButton);
            Controls.Add(removeButton);
            Controls.Add(dataGrid);
            Controls.Add(colorButton);
            Name = "DrawLines";
            Text = "DrawLines";
            Load += DrawLines_Load;
            ((System.ComponentModel.ISupportInitialize)dataGrid).EndInit();
            ((System.ComponentModel.ISupportInitialize)canvas).EndInit();
            ResumeLayout(false);
        }

        #endregion

        private Button colorButton;
        private DataGridView dataGrid;
        private Button removeButton;
        private Button findIntersectButton;
        private DataGridViewTextBoxColumn findX;
        private DataGridViewTextBoxColumn firstY;
        private DataGridViewTextBoxColumn secondX;
        private DataGridViewTextBoxColumn secondY;
        private DataGridViewTextBoxColumn color;
        private PictureBox canvas;
    }
}