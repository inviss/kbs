using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace D2net.Common.UI
{
    public partial class AdvPanel : Panel
    {
        private Panel _InnerPanel = new Panel();
        private int _OutterLineSize = 1;

        public AdvPanel()
        {
            InitializeComponent();
            this.BackColor = Color.Black;
            _InnerPanel.Size = new System.Drawing.Size(this.Width - (_OutterLineSize * 2), this.Height - (_OutterLineSize * 2));
            _InnerPanel.Location = new System.Drawing.Point(_OutterLineSize, _OutterLineSize);
            _InnerPanel.BorderStyle = BorderStyle.FixedSingle;
            this.Controls.Add(_InnerPanel);
            _InnerPanel.Show();
            _InnerPanel.SendToBack();
            this.ControlAdded += new ControlEventHandler(AdvPanel_ControlAdded);
        }

        void AdvPanel_ControlAdded(object sender, ControlEventArgs e)
        {
            //throw new Exception("The method or operation is not implemented.");
            _InnerPanel.SendToBack();
        }

        /// <summary>
        /// Whether or not to enable the segment feature of the time slider. This feature shows a timespan
        /// within the min and max to be highlighted within the slider.
        /// </summary>
        public BorderStyle InnerBorderStyle
        {
            get { return _InnerPanel.BorderStyle; }
            set
            {
                _InnerPanel.BorderStyle = value;
            }
        }

        public Color InnerPanelBackColor
        {
            get { return _InnerPanel.BackColor; }
            set {_InnerPanel.BackColor = value;}
        }

        public int OutterLineSize
        {
            get { return _OutterLineSize; }
            set {
                _OutterLineSize = value;
                AdjustThis();
            }
        }

        public Color InnerPanelForeColor
        {
            get { return _InnerPanel.ForeColor; }
            set { _InnerPanel.ForeColor = value; }
        }

        protected override void OnSizeChanged(EventArgs e)
        {
            AdjustThis();
            base.OnSizeChanged(e);
        }

        private void AdjustThis()
        {
            _InnerPanel.Size = new System.Drawing.Size(this.Width - (_OutterLineSize * 2), this.Height - (_OutterLineSize * 2));
            _InnerPanel.Location = new System.Drawing.Point(_OutterLineSize, _OutterLineSize);
        }
    }
}
