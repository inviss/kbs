using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.ComponentModel;

namespace Transcoder_KBS
{
    class DataGridViewProgressCell : DataGridViewImageCell
    {
        // Used to make custom cell consistent with a DataGridViewImageCell
        static Image emptyImage;
        // Used to remember color of the progress bar
        static Color _ProgressBarColorMain;
        static Color _ProgressBarColorGradiant;
        static Color _ProgressBarBorderColor;
        static Color _ProgressBarTextColor;

        static bool _UseProgressAutoResize;
        static int _ProgressBarWidth;
        static int _ProgressBarHeight;

        public Color ProgressBarColor
        {
            get { return _ProgressBarColorMain; }
            set { _ProgressBarColorMain = value; }
        }
        public Color ProgressBarColorGradiant
        {
            get { return DataGridViewProgressCell._ProgressBarColorGradiant; }
            set { DataGridViewProgressCell._ProgressBarColorGradiant = value; }
        }
        public Color ProgressBarBorderColor
        {
            get { return DataGridViewProgressCell._ProgressBarBorderColor; }
            set { DataGridViewProgressCell._ProgressBarBorderColor = value; }
        }
        public Color ProgressBarTextColor
        {
            get { return DataGridViewProgressCell._ProgressBarTextColor; }
            set { DataGridViewProgressCell._ProgressBarTextColor = value; }
        }
        public bool UseProgressAutoResize
        {
            get { return DataGridViewProgressCell._UseProgressAutoResize; }
            set { DataGridViewProgressCell._UseProgressAutoResize = value; }
        }
        public int ProgressBarWidth
        {
            get { return _ProgressBarWidth; }
            set { _ProgressBarWidth = value; }
        }
        public int ProgressBarHeight
        {
            get { return _ProgressBarHeight; }
            set { _ProgressBarHeight = value; }
        }

        static DataGridViewProgressCell()
        {
            emptyImage = new Bitmap(1, 1, System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        }
        public DataGridViewProgressCell()
        {
            this.ValueType = typeof(int);
        }
        // Method required to make the Progress Cell consistent with the default Image Cell.
        // The default Image Cell assumes an Image as a value, although the value of the Progress Cell is an int.
        protected override object GetFormattedValue(object value,
            int rowIndex, ref DataGridViewCellStyle cellStyle,
            TypeConverter valueTypeConverter,
            TypeConverter formattedValueTypeConverter,
            DataGridViewDataErrorContexts context)
        {
            return emptyImage;
        }

        protected override void Paint(System.Drawing.Graphics g, 
            System.Drawing.Rectangle clipBounds, 
            System.Drawing.Rectangle cellBounds, 
            int rowIndex, 
            DataGridViewElementStates cellState, 
            object value, object formattedValue, 
            string errorText, 
            DataGridViewCellStyle cellStyle, 
            DataGridViewAdvancedBorderStyle advancedBorderStyle, 
            DataGridViewPaintParts paintParts)
        {
            if (Convert.ToInt16(value) == 0 || value == null)
            {
                value = 0;
            }

            int progressVal = Convert.ToInt32(value);

            float percentage = ((float)progressVal / 100.0f); // Need to convert to float before division; otherwise C# returns int which is 0 for anything but 100%.
            Brush backColorBrush = new SolidBrush(cellStyle.BackColor);
            Brush foreColorBrush = new SolidBrush(cellStyle.ForeColor);

            // Draws the cell grid
            base.Paint(g, clipBounds, cellBounds, rowIndex, cellState, value, formattedValue, errorText, cellStyle, advancedBorderStyle, (paintParts & ~DataGridViewPaintParts.ContentForeground));

            float posX = cellBounds.X;
            float posY = cellBounds.Y;

            Font TextFont = new Font("맑은 고딕", 10, FontStyle.Bold);
            float textWidth = TextRenderer.MeasureText(progressVal.ToString() + "%", TextFont).Width;
            float textHeight = TextRenderer.MeasureText(progressVal.ToString() + "%", TextFont).Height;

            if (percentage >= 0.0)
            { 
                /************************************************************************/
                int ProWdith = (int)(cellBounds.Width - (textWidth+10));
                if (_UseProgressAutoResize)
                {
                    _ProgressBarWidth = ProWdith - 10;
                }
                float PrgPosX = cellBounds.X + (ProWdith / 2) - _ProgressBarWidth / 2;
                float PrgPosY = cellBounds.Y + (cellBounds.Height / 2) - _ProgressBarHeight / 2;

                if(_ProgressBarBorderColor ==null)
                    g.FillRectangle(Brushes.Black, PrgPosX, PrgPosY, ProWdith, _ProgressBarHeight);
                else
                    g.FillRectangle(new SolidBrush(_ProgressBarBorderColor), PrgPosX, PrgPosY, ProWdith, _ProgressBarHeight);

                g.FillRectangle(new SolidBrush(_ProgressBarColorMain), PrgPosX, PrgPosY, Convert.ToInt32((percentage * ProWdith)), ProgressBarHeight / 2);
                g.FillRectangle(new SolidBrush(_ProgressBarColorGradiant), PrgPosX, PrgPosY + (ProgressBarHeight / 2), Convert.ToInt32((percentage * ProWdith)), ProgressBarHeight / 2);
                //Draw text
                /************************************************************************/
                posX = cellBounds.X + cellBounds.Width - textWidth;
                posY = cellBounds.Y + (cellBounds.Height / 2) - textHeight / 2;
                g.DrawString(progressVal.ToString() + "%", TextFont, new SolidBrush(_ProgressBarTextColor), posX, posY);
            }
            else
            {
                //if percentage is negative, we don't want to draw progress bar
                //wa want only text
                //if (this.DataGridView.CurrentRow.Index == rowIndex)
                //{
                //    g.DrawString(progressVal.ToString() + "%", cellStyle.Font, new SolidBrush(cellStyle.SelectionForeColor), posX, posX);
                //}
                //else
                //{
                //    g.DrawString(progressVal.ToString() + "%", cellStyle.Font, foreColorBrush, posX, posY);
                //}
            }
        }

        public override object Clone()
        {
            DataGridViewProgressCell dataGridViewCell = base.Clone() as DataGridViewProgressCell;
            if (dataGridViewCell != null)
            {
                dataGridViewCell.ProgressBarColor = this.ProgressBarColor;
                dataGridViewCell.ProgressBarWidth = this.ProgressBarWidth;
                dataGridViewCell.ProgressBarHeight = this.ProgressBarHeight;
                dataGridViewCell.ProgressBarBorderColor = this.ProgressBarBorderColor;
                dataGridViewCell.ProgressBarColorGradiant = this.ProgressBarColorGradiant;
                dataGridViewCell.ProgressBarTextColor = this.ProgressBarTextColor;
            }
            return dataGridViewCell;
        }

        internal void SetProgressBarColor(int rowIndex, Color value)
        {
            this.ProgressBarColor = value;
        }
        internal void SetProgressBarColorGradiant(int rowIndex, Color value)
        {
            this.ProgressBarColorGradiant = value;
        }
        internal void SetProgressBarBorderColor(int rowIndex, Color value)
        {
            this.ProgressBarBorderColor = value;
        }
        internal void SetProgressBarTextColor(int rowIndex, Color value)
        {
            this.ProgressBarTextColor = value;
        }
        internal void SetUseProgressAutoResize(int rowIndex, bool value)
        {
            this.UseProgressAutoResize = value;
        }
   
        internal void SetProgressBarWidth(int rowIndex, int value)
        {
            this.ProgressBarWidth = value;
        }
        internal void SetProgressBarHeight(int rowIndex, int value)
        {
            this.ProgressBarHeight = value;
        }

    }
}
