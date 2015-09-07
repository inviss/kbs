using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

using System.Drawing.Drawing2D;
using System.Drawing.Design;
using System.Drawing.Imaging;

namespace D2net.Common.UI
{
	/// <summary>
	/// Summary description for TransButton.
	/// </summary>
	public class TransButton : System.Windows.Forms.Button
	{
		public Bitmap ButtonImages;
		protected int imageWidth = 0;
		protected int imageHeight = 0;
		protected int xOffset = 0;
		protected int yOffset = 0;
        private bool m_bActiveState = false;

		public void SetImage(Bitmap bm)
		{
			ButtonImages = bm;
			imageWidth = bm.Width;
			imageHeight = bm.Height/4;
            this.FlatStyle = FlatStyle.Flat;
		}

		public void SetRegion(GraphicsPath gp)
		{
			this.Region = new Region(gp);
		}
		
		public enum ControlState
		{
			/// <summary>Button is in the normal state.</summary>
			Normal,
			/// <summary>Button is in the hover state.</summary>
			Hover,
			/// <summary>Button is in the pressed state.</summary>
			Pressed,
			/// <summary>Button is in the default state.</summary>
			Default,
			/// <summary>Button is in the disabled state.</summary>
			Disabled		
		}		
		
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public TransButton()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

		}
		#region Instance fields

		private ControlState enmState = ControlState.Normal;
		private bool bCanClick = false;
		private Point locPoint;

		#endregion

		#region Constructors
		/// <summary>
		/// TransButton default constructor.
		/// </summary>
		static TransButton()
		{
		}

		#endregion

		#region Properties

		public Point AdjustImageLocation
		{
			get {return locPoint;}
			set 
			{ 
				locPoint = value;
				this.Invalidate();
			}
		}
        public bool ActiveState
        {
            get { return m_bActiveState; }
            set 
            { 
                m_bActiveState = value;
                Invalidate();
            }
        }
		#endregion

        #region Methods

        // Overridden Event Handlers
		protected override void OnClick(EventArgs ea)
		{
			this.Capture = false;
			bCanClick = false;

			if (this.ClientRectangle.Contains(this.PointToClient(Control.MousePosition)))
				enmState = ControlState.Hover;
			else
				enmState = ControlState.Normal;

			this.Invalidate();

			base.OnClick(ea);
		}

		protected override void OnMouseEnter(EventArgs ea)
		{
			base.OnMouseEnter(ea);

			enmState = ControlState.Hover;
			this.Invalidate();
		}

		protected override void OnMouseDown(MouseEventArgs mea)
		{
			base.OnMouseDown(mea);

			if (mea.Button == MouseButtons.Left)
			{
				bCanClick = true;
				enmState = ControlState.Pressed;
				this.Invalidate();
			}
		}

        protected override void OnMouseMove(MouseEventArgs mea)
        {
            base.OnMouseMove(mea);

            if (ClientRectangle.Contains(mea.X, mea.Y))
            {
                if (enmState == ControlState.Hover && this.Capture && !bCanClick)
                {
                    bCanClick = true;
                    enmState = ControlState.Pressed;
                    this.Invalidate();
                }
            }
            else
            {
                if (enmState == ControlState.Pressed)
                {
                    bCanClick = false;
                    enmState = ControlState.Hover;
                    this.Invalidate();
                }
            }
        }

		protected override void OnMouseLeave(EventArgs ea)
		{
			base.OnMouseLeave(ea);

			enmState = ControlState.Normal;
			this.Invalidate();
		}

		[System.Runtime.InteropServices.DllImportAttribute("gdi32.dll")]
		private static extern int GetPixel(
			IntPtr hdc,
			int nX,
			int nY);

		protected override void OnPaint(PaintEventArgs pea)
		{
			this.OnPaintBackground(pea);

            if (m_bActiveState)
            {
                OnDrawPressed(pea.Graphics);
            }
            else
            {
                switch (enmState)
                {
                    case ControlState.Normal:
                        if (this.Enabled)
                        {
                            if (this.Focused || this.IsDefault)
                            {
                                OnDrawDefault(pea.Graphics);
                                break;
                            }
                            else
                            {
                                OnDrawNormal(pea.Graphics);
                                break;
                            }
                        }
                        else
                        {
                            OnDrawDisabled(pea.Graphics);
                        }

                        break;

                    case ControlState.Hover:
                        OnDrawHover(pea.Graphics);
                        break;

                    case ControlState.Pressed:
                        OnDrawPressed(pea.Graphics);
                        break;
                }
            }

			if (ButtonImages != null)
			{
				Rectangle destRect = new Rectangle( 0, 0, 
					pea.ClipRectangle.Width, pea.ClipRectangle.Height);
				Rectangle srcRect = new Rectangle( xOffset, yOffset, 
					pea.ClipRectangle.Width, pea.ClipRectangle.Height);
				GraphicsUnit units = GraphicsUnit.Pixel;
				pea.Graphics.DrawImage(ButtonImages, destRect, srcRect, units);
				//pea.Graphics.DrawImage(ButtonImages, 0, 0);
			}

		}

		protected override void OnEnabledChanged(EventArgs ea)
		{
			base.OnEnabledChanged(ea);
			enmState = ControlState.Normal;
			this.Invalidate();
		}


		private void DrawNormalButton(Graphics g)
		{
			// Normal state is first image 
			// in 4-state bitmap at offset 0,0
			xOffset = 0;
			yOffset = 0;
		}

		private void OnDrawDefault(Graphics g)
		{
			DrawNormalButton(g);
		}

		private void OnDrawNormal(Graphics g)
		{
			DrawNormalButton(g);
		}

		private void OnDrawHover(Graphics g)
		{
			// Hover state is third image 
			// in 4-state bitmap
            //xOffset = imageWidth*2;
            //yOffset = 0;
            xOffset = 0;
            yOffset = imageHeight * 2;
		}

		private void OnDrawPressed(Graphics g)
		{
			// Selected state is second image
			// in 4-state bitmap
            //xOffset = imageWidth;
            //yOffset = 0;
            xOffset = 0;
            yOffset = imageHeight;
		}

		private void OnDrawDisabled(Graphics g)
		{
			// Disabled state is fourth image
			// in 4-state bitmap
			xOffset = 0;
            yOffset = imageHeight * 3;
		}

		#endregion

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if( components != null )
					components.Dispose();
			}
			base.Dispose( disposing );
		}

		#region Component Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
        private void InitializeComponent()
        {
            components = new System.ComponentModel.Container();
            this.FlatStyle = FlatStyle.System;
            this.BackColor = Color.Transparent;
        }
		#endregion
	}
}
