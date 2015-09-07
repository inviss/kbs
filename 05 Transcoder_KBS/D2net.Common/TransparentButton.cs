using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace D2net.Common.UI
{
    public class TransparentImageButton : Button
    {
        private Bitmap _ButtonImage = new Bitmap(400, 100);

        public TransparentImageButton()
        {
            BackColor = System.Drawing.Color.Transparent;
            ButtonTransparentColor = System.Drawing.Color.Empty;
            FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            FlatAppearance.MouseOverBackColor = System.Drawing.Color.Transparent;
            ImageIndex = 0;
            UseVisualStyleBackColor = true;
        }

        ~TransparentImageButton()
        {

        }

        protected override void OnMouseEnter(EventArgs e)
        {
            base.OnMouseEnter(e);
            if(this.ImageList != null)
            {
                this.ImageIndex = 2;
            }
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            base.OnMouseLeave(e);
            if (this.ImageList != null)
            {
                this.ImageIndex = 0;
            }
        }

        protected override void OnMouseDown(MouseEventArgs mevent)
        {
            base.OnMouseDown(mevent);
            if (this.ImageList != null)
            {
                this.ImageIndex = 1;
            }
        }

        protected override void OnMouseUp(MouseEventArgs mevent)
        {
            base.OnMouseUp(mevent);
            if (this.ImageList != null)
            {
                this.ImageIndex = 0;
            }
        }
        protected override void OnMouseClick(MouseEventArgs e)
        {
            base.OnMouseClick(e);
            if (this.ImageList != null)
            {
                this.ImageIndex = 0;
            }
        }

        protected override void OnVisibleChanged(EventArgs e)
        {
            base.OnVisibleChanged(e);
        }

        public Image ButtonImage
        {
            set
            {
                try
                {
                    if (value != null) _ButtonImage = (Bitmap)value; else return;

                    this.BackColor = Color.Transparent;
                    this.FlatAppearance.MouseOverBackColor = Color.Transparent;
                    this.FlatAppearance.MouseDownBackColor = Color.Transparent;
                    this.FlatAppearance.BorderSize = 0;
                    this.FlatStyle = FlatStyle.Flat;
                    this.ImageIndex = 0;
                    this.Text = "";
                    Bitmap tmp = new Bitmap(this.Size.Width * 4, this.Size.Height, System.Drawing.Imaging.PixelFormat.Format32bppArgb);
                    Graphics g = Graphics.FromImage(tmp);
                    g.DrawImage(_ButtonImage, 0, 0, tmp.Width + 1, tmp.Height + 1);
                    ImageList TheImageList = new ImageList();
                    TheImageList.ColorDepth = ColorDepth.Depth32Bit;
                    TheImageList.ImageSize = new Size(this.Size.Width, this.Size.Height);
                    TheImageList.Images.AddStrip(tmp);
                    TheImageList.TransparentColor = _TransparentColor;
                    this.ImageList = TheImageList;
                    this.ImageAlign = ContentAlignment.MiddleCenter;
                }
                catch(Exception ex)
                {

                }
            }
        }

        public Image ButtonImagePortrate
        {
            set
            {
                try
                {
                    if (value != null) _ButtonImage = (Bitmap)value; else return;

                    this.BackColor = Color.Transparent;
                    this.FlatAppearance.MouseOverBackColor = Color.Transparent;
                    this.FlatAppearance.MouseDownBackColor = Color.Transparent;
                    this.FlatAppearance.BorderSize = 0;
                    this.FlatStyle = FlatStyle.Flat;
                    this.ImageIndex = 0;
                    this.Text = "";
                    Bitmap tmp = new Bitmap(this.Size.Width * 4, this.Size.Height, System.Drawing.Imaging.PixelFormat.Format32bppArgb);
                    Graphics g = Graphics.FromImage(tmp);
                    g.FillRectangle(new SolidBrush(_TransparentColor), new Rectangle(0, 0, tmp.Width, tmp.Height));
                    for (int i = 0; i < 4; i++)
                    {
                        g.DrawImage(_ButtonImage, new Rectangle(i * this.Size.Width, 0, this.Size.Width-1, this.Size.Height-1),
                            new Rectangle(0, (_ButtonImage.Height / 4) * i, _ButtonImage.Width, _ButtonImage.Height / 4), GraphicsUnit.Pixel);
                    }
                    tmp.Save("Test.png");
                    ImageList TheImageList = new ImageList();
                    TheImageList.ColorDepth = ColorDepth.Depth32Bit;
                    TheImageList.ImageSize = new Size(this.Size.Width, this.Size.Height);
                    TheImageList.Images.AddStrip(tmp);
                    TheImageList.TransparentColor = _TransparentColor;
                    this.ImageList = TheImageList;

                }
                catch(Exception ex)
                {
                    MessageBox.Show(ex.StackTrace, ex.Message);
                }
            }
        }

        private Color _TransparentColor = Color.Transparent;

        public Color ButtonTransparentColor
        {
            get { return _TransparentColor; }
            set {
                try
                {
                    _TransparentColor = value;
                    if (ImageList != null)
                    {
                        this.ImageList.TransparentColor = _TransparentColor;
                    }
                }
                catch(Exception ex)
                {

                }
            }
        }

        protected override void OnEnabledChanged(EventArgs e)
        {
            base.OnEnabledChanged(e);
            if(this.Enabled)
            {
                if (this.ImageList != null)
                {
                    this.ImageIndex = 0;
                }
            }
            else
            {
                if (this.ImageList != null)
                {
                    this.ImageIndex = 3;
                }
            }
        }

    }
}
