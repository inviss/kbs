using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using System.Diagnostics;

namespace D2net.Common.UI
{
	/// <summary>
	/// Summary description for TrackBarEx.
	/// </summary>
	public class TrackBarEx : System.Windows.Forms.UserControl
	{
		private int	_minValue = 0;
		private int	_maxValue = 100;
		private int _value    = 0;
        private int _dispvalue = 0;
		private int	_tickFrequency = 10;
		private int	_smallChange = 1;
		private bool _dragModeEnabled = false;
		private Point _startDragPoint;		

        private System.Drawing.Color _tickColor = Color.Black;
		public System.Windows.Forms.PictureBox picSlider;

        private System.Drawing.Image _ActiveImage=null;
        private System.Drawing.Image _NonActiveImage=null;

        
		#region Initialise
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public TrackBarEx()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

			// Position the slider on the channel
            this.picSlider.Top = (this.Height / 2) - (this.picSlider.Height / 2);
			this.MoveSlider();
		}

		/// <summary> 
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}
		#endregion

		#region Component Designer generated code
		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TrackBarEx));
            this.picSlider = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.picSlider)).BeginInit();
            this.SuspendLayout();
            // 
            // picSlider
            // 
            this.picSlider.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.picSlider.Image = ((System.Drawing.Image)(resources.GetObject("picSlider.Image")));
            this.picSlider.Location = new System.Drawing.Point(10, 72);
            this.picSlider.Name = "picSlider";
            this.picSlider.Size = new System.Drawing.Size(30, 20);
            this.picSlider.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picSlider.TabIndex = 0;
            this.picSlider.TabStop = false;
            this.picSlider.MouseDown += new System.Windows.Forms.MouseEventHandler(this.picSlider_MouseDown);
            this.picSlider.MouseMove += new System.Windows.Forms.MouseEventHandler(this.picSlider_MouseMove);
            this.picSlider.MouseUp += new System.Windows.Forms.MouseEventHandler(this.picSlider_MouseUp);
           
            // 
            // TrackBarEx
            // 
            this.Controls.Add(this.picSlider);
            this.Name = "TrackBarEx";
            this.Size = new System.Drawing.Size(40, 120);
            ((System.ComponentModel.ISupportInitialize)(this.picSlider)).EndInit();
            this.ResumeLayout(false);
		}

       
		#endregion

		#region Public Properties
		[Bindable(true), Category("Behavior"),
		DefaultValue(0), Description("최소값")]
		public int Minimum
		{
			get { return this._minValue; }
			set
			{
				this._minValue = value;

				if (this._value < this._minValue)
					this.Value = this._minValue;
				else
				{
					this.MoveSlider();
					this.Invalidate();
				}
			}
		}

		[Bindable(true), Category("Behavior"),
		DefaultValue(0), Description("선택된 값")]
		public int Value
		{
            get { return this._dispvalue; }
			set
			{
				if (value < this._minValue || value > this._maxValue)
				{
					MessageBox.Show("Value out of bounds");
					return;
				}

                if (value == this._value)
                {
                    return;
                }

				int tmp = this._value;
                this._dispvalue = value;
                this._value = this._maxValue - this._dispvalue;

                this.MoveSlider();
			}
		}

		[Bindable(true), Category("Behavior"),
		DefaultValue(100), Description("최대값")]
		public int Maximum
		{
			get { return this._maxValue; }
			set
			{
				this._maxValue = value;

				if (this._value > this._maxValue)
					this.Value = this._maxValue;
				else
				{
					this.MoveSlider();
					this.Invalidate();
				}
			}
		}

		[Bindable(true), Category("Appearance"),
		DefaultValue(5), Description("눈금 간격 크기")]
		public int TickFrequency
		{
			get { return this._tickFrequency; }
			set
			{
				this._tickFrequency = value;
				this.Invalidate();
			}
		}

		[Bindable(true), Category("Appearance"),
		DefaultValue(1), Description("키보드 입력에 대한 작은 변화 크기")]
		public int SmallChange
		{
			get { return this._smallChange; }
			set
			{
				this._smallChange = value;
				this.Invalidate();
			}
		}

		[Bindable(true), Category("Appearance"),
		DefaultValue(typeof(Color), "System.Drawing.Color.Black"), Description("눈금 색상")]
		public System.Drawing.Color TickColor
		{
			get { return this._tickColor; }
			set
			{ 
				this._tickColor = value;
				this.Invalidate();
			}
		}

        [Bindable(true), Category("Appearance"), Description("그립의 이미지")]
        public System.Drawing.Image GripImage
        {
            get { return this.picSlider.Image; }
            set
            {
                picSlider.Size = value.Size;
                picSlider.Image = value;

				this.picSlider.Top = (this.Height / 2) - (this.picSlider.Height / 2);
                this.MoveSlider();
            }
        }
        [Bindable(true), Category("Appearance"), Description("Active 그립의 이미지")]
        public System.Drawing.Image AtiveImage{
            get{ return _ActiveImage;}
            set{ _ActiveImage = value;}
        }
        [Bindable(true), Category("Appearance"), Description("Non Active 그립의 이미지")]
         public System.Drawing.Image NonAtiveImage{
            get{ return _NonActiveImage;}
            set{ _NonActiveImage = value;}
        }


		#endregion

		#region Public Events
		[Category("Action"), Description("Occurs when the slider is moved")]
		public event EventHandler ValueChanged;
		#endregion

		#region Overridden events
		protected override void OnResize(EventArgs e)
		{
			base.OnResize (e);

			// Position the slider on the channel
			this.picSlider.Left = this.Location.X;
			this.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			//e.Graphics.Clear(this.BackColor);

			/*
			// Init the drawing tools
			Pen penSmallTick = new Pen(this._tickColor, 1);
			Pen penLargeTick = new Pen(this._tickColor, 2);
						
			// Draw the slider channel up the middle
			e.Graphics.FillRectangle(
				penSmallTick.Brush,
				this.picSlider.Width / 2,
                (this.Height / 2) - 1,
                this.Width - this.picSlider.Width,
                2);
						
			// Draw the ticks
            double tickSpacing = ((((double)this.Width) - this.picSlider.Width) / 
                ((Math.Abs(this._minValue) + Math.Abs(this._maxValue)) / 
                this._tickFrequency));
            double startPosition = (double)(this.picSlider.Width) / 2;
            double endPosition = (double)(this.Width - startPosition);

			int i = 0;
			
            for (double x = startPosition; x <= endPosition; x += tickSpacing)
			{
				if (i % 2 == 0)
				{
                    e.Graphics.DrawLine(penLargeTick, (int)x, 5, (int)x, (this.Height / 2) - 5);
                    e.Graphics.DrawLine(penLargeTick, (int)x, (this.Height / 2) + 5, (int)x, this.Height - 5);
				}
				else
				{
                    e.Graphics.DrawLine(penSmallTick, (int)x, 9, (int)x, (this.Height / 2) - 5);
                    e.Graphics.DrawLine(penSmallTick, (int)x, (this.Height / 2) + 5, (int)x, this.Height - 9);
				}

				i++;
			}
			*/
			//if(_InnerImage != null)
			//	e.Graphics.DrawImage(this.BackgroundImage, new Rectangle(0, 0, this.Width, this.Height));
		}



		private void picSlider_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			// Set the drag flag for the mousemove event
            Cursor = Cursors.Hand;
            //GripImage = new Bitmap(Application.StartupPath + @"\Skin\PreviewControl\process_ball_ovr.png");
            if (_ActiveImage == null)
            {
            }else{
                GripImage = _ActiveImage;
            }
			this._dragModeEnabled = true;

			// Record the start point for the slider movement
			this._startDragPoint = new Point(e.X, e.Y);
		}

		private void picSlider_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			// User isn't dragging the slider, so dont bother moving it
			if (this._dragModeEnabled == false)
				return;

			// Calculate the distance the mouse moved
			int delta = e.X - this._startDragPoint.X;

			if (delta == 0)
				return;

			this.MoveSlider(delta);
		}

		private void picSlider_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			// Reset the drag flag
			this._dragModeEnabled = false;

            if (_NonActiveImage == null)
            {
            }
            else
            {
                GripImage = _NonActiveImage;
            }
           
            Cursor = Cursors.Arrow;
		}
		#endregion

		private void MoveSlider(int delta)
		{
			// Move the slider and make sure it stays in the bounds of the control
			if (delta < 0 && (this.picSlider.Left + delta) <= 0)
				this.picSlider.Left = 0 + 10;
			else if (delta > 0 && (this.picSlider.Left + this.picSlider.Width + delta) >= (this.Width))
				this.picSlider.Left = this.Width - this.picSlider.Width -10;
			else
				this.picSlider.Left += delta;

             this.CalculateSliderValue();
		}

		private void MoveSlider()
        {
            if (_value == 0)
            {
                this.picSlider.Left = this.Location.X;
            }
            else
            {
                // distance between tics used in ratio calc
                int distance = Math.Abs(this._maxValue) + Math.Abs(this._minValue);

                // percentage of control travelled
                float percent = (float)this._value / (float)distance;

                // New slider location
                this.picSlider.Left = this.Width - picSlider.Width - Convert.ToInt32(percent * (float)(this.Width - this.picSlider.Width));
            }          
        }
        private void CalculateSliderValue()
		{
			// distance between tics used in ratio calc
            int distance = this.Width - this.picSlider.Width;

			// percentage of control travelled
			float percent = (float)this.picSlider.Left / (float)distance;
			
			// Slider movement in points
			int movement  = Convert.ToInt32(percent * (float)(Math.Abs(this._maxValue) + Math.Abs(this._minValue)));
			
			// New value
            this._value = (this._maxValue >= 0) ? this._maxValue - movement : this._maxValue + movement;
            this._dispvalue = this._maxValue - _value;

			// Raise the ValueChanged event
            
		    ValueChanged(this, new EventArgs());
		}
	}
}
