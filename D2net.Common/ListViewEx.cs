using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace D2net.Common.UI
{
    public class ListViewEx : System.Windows.Forms.ListView
    {
        #region ListViewColumnSorter_Class
        /// <summary>
        /// This class is an implementation of the 'IComparer' interface.
        /// </summary>
        public class ListViewColumnSorter : IComparer
        {
            /// <summary>
            /// Specifies the column to be sorted
            /// </summary>
            private int ColumnToSort;
            /// <summary>
            /// Specifies the order in which to sort (i.e. 'Ascending').
            /// </summary>
            private SortOrder OrderOfSort;
            /// <summary>
            /// Case insensitive comparer object
            /// </summary>
            //private CaseInsensitiveComparer ObjectCompare;
            private NumberCaseInsensitiveComparer ObjectCompare;
            private ImageTextComparer FirstObjectCompare;

            /// <summary>
            /// Class constructor.  Initializes various elements
            /// </summary>
            public ListViewColumnSorter()
            {
                // Initialize the column to '0'
                ColumnToSort = 0;

                // Initialize the sort order to 'none'
                //OrderOfSort = SortOrder.None;
                OrderOfSort = SortOrder.Ascending;

                // Initialize the CaseInsensitiveComparer object
                ObjectCompare = new NumberCaseInsensitiveComparer();//CaseInsensitiveComparer();
                FirstObjectCompare = new ImageTextComparer();
            }

            /// <summary>
            /// This method is inherited from the IComparer interface.  It compares the two objects passed using a case insensitive comparison.
            /// </summary>
            /// <param name="x">First object to be compared</param>
            /// <param name="y">Second object to be compared</param>
            /// <returns>The result of the comparison. "0" if equal, negative if 'x' is less than 'y' and positive if 'x' is greater than 'y'</returns>
            public int Compare(object x, object y)
            {
                int compareResult;
                ListViewItem listviewX, listviewY;

                // Cast the objects to be compared to ListViewItem objects
                listviewX = (ListViewItem)x;
                listviewY = (ListViewItem)y;

                if (ColumnToSort == 0)
                {
                    compareResult = FirstObjectCompare.Compare(x, y);
                }
                else
                {
                    // Compare the two items
                    compareResult = ObjectCompare.Compare(listviewX.SubItems[ColumnToSort].Text, listviewY.SubItems[ColumnToSort].Text);
                }

                // Calculate correct return value based on object comparison
                if (OrderOfSort == SortOrder.Ascending)
                {
                    // Ascending sort is selected, return normal result of compare operation
                    return compareResult;
                }
                else if (OrderOfSort == SortOrder.Descending)
                {
                    // Descending sort is selected, return negative result of compare operation
                    return (-compareResult);
                }
                else
                {
                    // Return '0' to indicate they are equal
                    return 0;
                }
            }

            /// <summary>
            /// Gets or sets the number of the column to which to apply the sorting operation (Defaults to '0').
            /// </summary>
            public int SortColumn
            {
                set
                {
                    ColumnToSort = value;
                }
                get
                {
                    return ColumnToSort;
                }
            }

            /// <summary>
            /// Gets or sets the order of sorting to apply (for example, 'Ascending' or 'Descending').
            /// </summary>
            public SortOrder Order
            {
                set
                {
                    OrderOfSort = value;
                }
                get
                {
                    return OrderOfSort;
                }
            }

        }

        public class ImageTextComparer : IComparer
        {
            //private CaseInsensitiveComparer ObjectCompare;
            private NumberCaseInsensitiveComparer ObjectCompare;

            public ImageTextComparer()
            {
                // Initialize the CaseInsensitiveComparer object
                ObjectCompare = new NumberCaseInsensitiveComparer();//CaseInsensitiveComparer();
            }

            public int Compare(object x, object y)
            {
                //int compareResult;
                int image1, image2;
                ListViewItem listviewX, listviewY;

                // Cast the objects to be compared to ListViewItem objects
                listviewX = (ListViewItem)x;
                image1 = listviewX.ImageIndex;
                listviewY = (ListViewItem)y;
                image2 = listviewY.ImageIndex;

                if (image1 < image2)
                {
                    return -1;
                }
                else if (image1 == image2)
                {
                    return ObjectCompare.Compare(listviewX.Text, listviewY.Text);
                }
                else
                {
                    return 1;
                }
            }
        }

        public class NumberCaseInsensitiveComparer : CaseInsensitiveComparer
        {
            public NumberCaseInsensitiveComparer()
            {

            }

            public new int Compare(object x, object y)
            {
                if ((x is System.String) && IsWholeNumber((string)x) && (y is System.String) && IsWholeNumber((string)y))
                {
                    return base.Compare(System.Convert.ToInt32(x), System.Convert.ToInt32(y));
                }
                else
                {
                    return base.Compare(x, y);
                }
            }

            private bool IsWholeNumber(string strNumber)
            {
                Regex objNotWholePattern = new Regex("[^0-9]");
                return !objNotWholePattern.IsMatch(strNumber);
            }
        }
        #endregion

        #region Interop-Defines
        [DllImport("user32.dll")]
        private static extern IntPtr SendMessage(IntPtr hWnd, int msg, IntPtr wPar, IntPtr lPar);

        // ListView messages
        private const int LVM_FIRST = 0x1000;
        private const int LVM_GETCOLUMNORDERARRAY = (LVM_FIRST + 59);

        //mouse messages
        private const int WM_HSCROLL = 0x114;
        private const int WM_VSCROLL = 0x115;
        private const int WM_MOUSEWHEEL = 0x020A;

        // Windows Messages
        private const int WM_PAINT = 0x000F;
        #endregion

        #region Embedded control func and data
        private struct EmbeddedControl
        {
            public Control Control;
            public int Column;
            public int Row;
            public DockStyle Dock;
            public ListViewItem Item;
        }

        private ArrayList _embeddedControls = new ArrayList();

        public void AddEmbeddedControl(Control c, int col, int row)
        {
            AddEmbeddedControl(c, col, row, DockStyle.Fill);
        }

        public void AddEmbeddedControl(Control c, int col, int row, DockStyle dock)
        {

            if (c == null)
            {
                MessageBox.Show("controll error");
                throw new ArgumentNullException();
            }

            //Console.WriteLine("_____col:{0}, row{1}", col, row);
            if (col >= Columns.Count || row >= Items.Count)
            {
                MessageBox.Show("item error");
                throw new ArgumentOutOfRangeException();
            }

            EmbeddedControl ec;
            ec.Control = c;
            ec.Column = col;
            ec.Row = row;
            ec.Dock = dock;
            ec.Item = Items[row];

            _embeddedControls.Add(ec);

            // Add a Click event handler to select the ListView row when an embedded control is clicked
            c.Click += new EventHandler(_embeddedControl_Click);

            this.Controls.Add(c);
        }

        protected int[] GetColumnOrder()
        {
            IntPtr lPar = Marshal.AllocHGlobal(Marshal.SizeOf(typeof(int)) * Columns.Count);

            IntPtr res = SendMessage(Handle, LVM_GETCOLUMNORDERARRAY, new IntPtr(Columns.Count), lPar);
            if (res.ToInt32() == 0)	// Something went wrong
            {
                Marshal.FreeHGlobal(lPar);
                return null;
            }

            int[] order = new int[Columns.Count];
            Marshal.Copy(lPar, order, 0, Columns.Count);

            Marshal.FreeHGlobal(lPar);

            return order;
        }

        /// <summary>
        /// Retrieve the bounds of a ListViewSubItem
        /// </summary>
        /// <param name="Item">The Item containing the SubItem</param>
        /// <param name="SubItem">Index of the SubItem</param>
        /// <returns>Subitem's bounds</returns>
        protected Rectangle GetSubItemBounds(ListViewItem Item, int SubItem)
        {
            Rectangle subItemRect = Rectangle.Empty;

            if (Item == null)
                throw new ArgumentNullException("Item");

            int[] order = GetColumnOrder();
            if (order == null) // No Columns
                return subItemRect;

            if (SubItem >= order.Length)
                throw new IndexOutOfRangeException("SubItem " + SubItem + " out of range");

            // Retrieve the bounds of the entire ListViewItem (all subitems)
            Rectangle lviBounds = Item.GetBounds(ItemBoundsPortion.Entire);
            int subItemX = lviBounds.Left;

            // Calculate the X position of the SubItem.
            // Because the columns can be reordered we have to use Columns[order[i]] instead of Columns[i] !
            ColumnHeader col;
            int i;
            for (i = 0; i < order.Length; i++)
            {
                col = this.Columns[order[i]];
                if (col.Index == SubItem)
                    break;
                subItemX += col.Width;
            }

            subItemRect = new Rectangle(subItemX, lviBounds.Top, this.Columns[order[i]].Width, lviBounds.Height + 10);

            return subItemRect;
        }

        public Control GetEmbeddedControl(int col, int row)
        {
            foreach (EmbeddedControl ec in _embeddedControls)
                if (ec.Row == row && ec.Column == col)
                    return ec.Control;

            return null;
        }

        protected override void WndProc(ref Message m)
        {
            if (m.Msg == WM_PAINT)
            {
                if (View != View.Details)
                    return;

                // Calculate the position of all embedded controls

                foreach (EmbeddedControl ec in _embeddedControls)
                {
                    Rectangle rc = this.GetSubItemBounds(ec.Item, ec.Column);

                    if ((this.HeaderStyle != ColumnHeaderStyle.None) &&
                        (rc.Top < this.Font.Height)) // Control overlaps ColumnHeader
                    {
                        ec.Control.Visible = false;
                        continue;
                    }
                    else
                    {
                        ec.Control.Visible = true;
                    }

                    if (rc.Y > 0 && rc.Y < this.ClientRectangle.Height)
                    {
                        ec.Control.Visible = true;
                        ec.Control.Bounds = new Rectangle(rc.X, rc.Y, rc.Width - 1, rc.Height - 11);
                    }
                    else
                    {
                        ec.Control.Visible = false;
                    }
                }

                switch (m.Msg)
                {
                    case WM_HSCROLL:
                    case WM_VSCROLL:
                    case WM_MOUSEWHEEL:
                        this.Focus();
                        break;
                }
            }

            base.WndProc(ref m);
        }

        /// <summary>
        /// Remove a control from the ListView
        /// </summary>
        /// <param name="c">Control to be removed</param>
        public void RemoveEmbeddedControl(Control c)
        {
            if (c == null)
            {
                Console.WriteLine("control is null in RemoveEmbeddedControl");
                throw new ArgumentNullException();
            }

            for (int i = 0; i < _embeddedControls.Count; i++)
            {
                EmbeddedControl ec = (EmbeddedControl)_embeddedControls[i];
                if (ec.Control == c)
                {
                    Console.WriteLine("control is found");
                    c.Click -= new EventHandler(_embeddedControl_Click);
                    this.Controls.Remove(c);
                    _embeddedControls.RemoveAt(i);
                    return;
                }
            }
            throw new Exception("Control not found!");
        }

        private void _embeddedControl_Click(object sender, EventArgs e)
        {
            // When a control is clicked the ListViewItem holding it is selected
            foreach (EmbeddedControl ec in _embeddedControls)
            {
                if (ec.Control == (Control)sender)
                {
                    this.SelectedItems.Clear();
                    ec.Item.Selected = true;
                }
            }
        }
        #endregion

        public ListViewColumnSorter lvwColumnSorter = null;
        private ImageList imgList = new ImageList();
        public ListViewEx()
        {
            lvwColumnSorter = new ListViewColumnSorter();

            this.ListViewItemSorter = lvwColumnSorter;
            this.Sorting = SortOrder.Ascending;
            this.AutoArrange = true;

#if false
            //행의 높이를 조절하기 위해 이미지리스트를 삽입
            imgList.ImageSize = new Size(16, 20);
            this.SmallImageList = imgList;
#endif
            this.View = View.Details;
            this.FullRowSelect = true;
        }
    }
}
