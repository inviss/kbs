using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.ComponentModel;
using System.Reflection;

namespace Transcoder_KBS
{
    public class DataGridViewProgressColumn : DataGridViewImageColumn
    {
#region Variables
        public static Color _ProgressBarColor;
        private static int _ProgressBarWidth;
        private static int _ProgressBarHeight;
#endregion
#region Consturctor
        public DataGridViewProgressColumn() 
        {
            CellTemplate = new DataGridViewProgressCell(); ; 
        }
#endregion
#region Property
        [Browsable(true)]
        public Color ProgressBarColor
        {
            get
            {
                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                return this.ProgressBarCellTemplate.ProgressBarColor;
            }
            set
            {

                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                this.ProgressBarCellTemplate.ProgressBarColor = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetProgressBarColor(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        [Browsable(true)]
        public Color ProgressBarColorGradiant
        {
            get
            {
                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                return this.ProgressBarCellTemplate.ProgressBarColorGradiant;
            }
            set
            {

                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                this.ProgressBarCellTemplate.ProgressBarColorGradiant = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetProgressBarColorGradiant(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        [Browsable(true)]
        public Color ProgressBarBorderColor
        {
            get
            {
                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                return this.ProgressBarCellTemplate.ProgressBarBorderColor;
            }
            set
            {

                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                this.ProgressBarCellTemplate.ProgressBarBorderColor = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetProgressBarBorderColor(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        [Browsable(true)]
        public Color ProgressBarTextColor
        {
            get
            {
                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                return this.ProgressBarCellTemplate.ProgressBarTextColor;
            }
            set
            {

                if (this.ProgressBarCellTemplate == null)
                {
                    throw new InvalidOperationException("Operation cannot be completed because this DataGridViewColumn does not have a CellTemplate.");
                }
                this.ProgressBarCellTemplate.ProgressBarTextColor = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetProgressBarTextColor(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        [Browsable(true)]
        public bool UseProgressAutoResize
        {
            get { return this.ProgressBarCellTemplate.UseProgressAutoResize; }
            set
            {
                this.ProgressBarCellTemplate.UseProgressAutoResize = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetUseProgressAutoResize(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        [Browsable(true)]
        public int ProgressBarWidth
        {
            get { return this.ProgressBarCellTemplate.ProgressBarWidth; }
            set 
            {
                this.ProgressBarCellTemplate.ProgressBarWidth = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetProgressBarWidth(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        [Browsable(true)]
        public int ProgressBarHeight
        {
            get { return this.ProgressBarCellTemplate.ProgressBarHeight; }
            set 
            {
                this.ProgressBarCellTemplate.ProgressBarHeight = value;
                if (this.DataGridView != null)
                {
                    DataGridViewRowCollection dataGridViewRows = this.DataGridView.Rows;
                    int rowCount = dataGridViewRows.Count;
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
                    {
                        DataGridViewRow dataGridViewRow = dataGridViewRows.SharedRow(rowIndex);
                        DataGridViewProgressCell dataGridViewCell = dataGridViewRow.Cells[this.Index] as DataGridViewProgressCell;
                        if (dataGridViewCell != null)
                        {
                            dataGridViewCell.SetProgressBarHeight(rowIndex, value);
                        }
                    }
                    this.DataGridView.InvalidateColumn(this.Index);
                    // TODO: This column and/or grid rows may need to be autosized depending on their
                    //       autosize settings. Call the autosizing methods to autosize the column, rows, 
                    //       column headers / row headers as needed.
                }
            }
        }
        private DataGridViewProgressCell ProgressBarCellTemplate
        {
            get
            {
                return (DataGridViewProgressCell)this.CellTemplate;
            }
        }
#endregion
#region Override
        public override DataGridViewCell CellTemplate
        {
            get
            {
                return base.CellTemplate;
            }
            set
            {
                if (value != null &&
                    !value.GetType().IsAssignableFrom(typeof(DataGridViewProgressCell)))
                {
                    throw new InvalidCastException("Must be a DataGridViewProgressCell");
                }
                base.CellTemplate = value;
            }
        }
#endregion
    }
}
