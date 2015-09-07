using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
	/// <summary>
	/// ImageRadioButton에 대한 요약 설명입니다.
	/// </summary>
	public class ImageRadioButton : System.Windows.Forms.RadioButton
	{
        private Image _CheckBoxImage = null;
        private Image[] _CheckBoxImages = null;
        private Point _CheckBoxLocation = new Point(0, 0);
        private RectangleF _TextBounds = new RectangleF(0, 0, 0, 0);
        private StringFormat _TextAlignment = new StringFormat();
        private Image _CurCheckImage = null;
        private Color _ForeColor;
        private Brush _ForeBrush = null;
        private bool _RollOver = false;
        private ContentAlignment _CheckBoxAlign = ContentAlignment.MiddleLeft;
        private ContentAlignment _TextAlign = ContentAlignment.MiddleLeft;

		public ImageRadioButton()
		{
            _ForeColor = base.ForeColor;
            ResetForeColor();
            RecalcBounds();
            RecalcStringFormat();
		}

        public new ContentAlignment CheckAlign
        {
            get { return _CheckBoxAlign; }
            set
            {
                _CheckBoxAlign = value;
                RecalcBounds();
                Invalidate();
            }
        }

        public new ContentAlignment TextAlign
        {
            get { return _TextAlign; }
            set
            {
                _TextAlign = value;
                RecalcStringFormat();
                Invalidate();
            }
        }

        public new Color ForeColor
        {
            get { return _ForeColor; }
            set
            {
                _ForeColor = value;
                ResetForeColor();
                Invalidate();
            }
        }

        public Image CheckBoxImage
        {
            get { return _CheckBoxImage; }
            set
            {
                _CheckBoxImage = value;
                if (_CheckBoxImage != null)
                {
                    _CheckBoxImages = D2net.Common.UI.ImageSplitter.SplitImage(_CheckBoxImage, 6, 1);
                    _CurCheckImage = _CheckBoxImages[0];
                }
                else
                    _CheckBoxImages = null;

                if (_CheckBoxImages != null)
                {
                    RecalcBounds();
                    RecalcStringFormat();
                }
                Invalidate();
            }
        }

        private void RecalcBounds()
        {
            if (_CheckBoxImage == null)
                return;

            if (this.Appearance == Appearance.Button)
            {
                _CheckBoxLocation.X = 0;
                _CheckBoxLocation.Y = 0;

                _TextBounds.X = 0;
                _TextBounds.Y = 0;
                _TextBounds.Width = this.Bounds.Width;
                _TextBounds.Height = this.Bounds.Height;
                return;
            }

            switch (_CheckBoxAlign)
            {
                case ContentAlignment.TopLeft:
                    _CheckBoxLocation.X = 0;
                    _CheckBoxLocation.Y = 0;
                    _TextBounds.X = _CurCheckImage.Width + 2;
                    _TextBounds.Y = 0;
                    _TextBounds.Width = this.Bounds.Width - _CurCheckImage.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
                case ContentAlignment.TopCenter:
                    _CheckBoxLocation.X = (this.Bounds.Width - _CurCheckImage.Width) / 2;
                    _CheckBoxLocation.Y = 0;
                    _TextBounds.X = 0;
                    _TextBounds.Y = _CurCheckImage.Height;
                    _TextBounds.Width = this.Bounds.Width;
                    _TextBounds.Height = this.Bounds.Height - _CurCheckImage.Height;
                    break;
                case ContentAlignment.TopRight:
                    _CheckBoxLocation.X = this.Bounds.Width - _CurCheckImage.Width;
                    _CheckBoxLocation.Y = 0;
                    _TextBounds.X = 0;
                    _TextBounds.Y = 0;
                    _TextBounds.Width = this.Bounds.Width - _CurCheckImage.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
                case ContentAlignment.MiddleLeft:
                    _CheckBoxLocation.X = 0;
                    _CheckBoxLocation.Y = (this.Bounds.Height - _CurCheckImage.Height) / 2;
                    _TextBounds.X = _CurCheckImage.Width + 2;
                    _TextBounds.Y = 2;
                    _TextBounds.Width = this.Bounds.Width - _CurCheckImage.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
                case ContentAlignment.MiddleCenter:
                    _CheckBoxLocation.X = (this.Bounds.Width - _CurCheckImage.Width) / 2;
                    _CheckBoxLocation.Y = (this.Bounds.Height - _CurCheckImage.Height) / 2;
                    _TextBounds.X = 0;
                    _TextBounds.Y = 2;
                    _TextBounds.Width = this.Bounds.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
                case ContentAlignment.MiddleRight:
                    _CheckBoxLocation.X = this.Bounds.Width - _CurCheckImage.Width;
                    _CheckBoxLocation.Y = (this.Bounds.Height - _CurCheckImage.Height) / 2;
                    _TextBounds.X = 0;
                    _TextBounds.Y = 2;
                    _TextBounds.Width = this.Bounds.Width - _CurCheckImage.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
                case ContentAlignment.BottomLeft:
                    _CheckBoxLocation.X = 0;
                    _CheckBoxLocation.Y = this.Bounds.Height - _CurCheckImage.Height;
                    _TextBounds.X = _CurCheckImage.Width + 2;
                    _TextBounds.Y = 0;
                    _TextBounds.Width = this.Bounds.Width - _CurCheckImage.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
                case ContentAlignment.BottomCenter:
                    _CheckBoxLocation.X = (this.Bounds.Width - _CurCheckImage.Width) / 2;
                    _CheckBoxLocation.Y = this.Bounds.Height - _CurCheckImage.Height;
                    _TextBounds.X = 0;
                    _TextBounds.Y = 0;
                    _TextBounds.Width = this.Bounds.Width;
                    _TextBounds.Height = this.Bounds.Height - _CurCheckImage.Height;
                    break;
                case ContentAlignment.BottomRight:
                    _CheckBoxLocation.X = this.Bounds.Width - _CurCheckImage.Width;
                    _CheckBoxLocation.Y = this.Bounds.Height - _CurCheckImage.Height;
                    _TextBounds.X = 0;
                    _TextBounds.Y = 0;
                    _TextBounds.Width = this.Bounds.Width - _CurCheckImage.Width;
                    _TextBounds.Height = this.Bounds.Height;
                    break;
            }
        }

        private void RecalcStringFormat()
        {
            switch (_TextAlign)
            {
                case ContentAlignment.TopLeft:
                    _TextAlignment.Alignment = StringAlignment.Near;
                    _TextAlignment.LineAlignment = StringAlignment.Near;
                    break;
                case ContentAlignment.TopCenter:
                    _TextAlignment.Alignment = StringAlignment.Center;
                    _TextAlignment.LineAlignment = StringAlignment.Near;
                    break;
                case ContentAlignment.TopRight:
                    _TextAlignment.Alignment = StringAlignment.Far;
                    _TextAlignment.LineAlignment = StringAlignment.Near;
                    break;
                case ContentAlignment.MiddleLeft:
                    _TextAlignment.Alignment = StringAlignment.Near;
                    _TextAlignment.LineAlignment = StringAlignment.Center;
                    break;
                case ContentAlignment.MiddleCenter:
                    _TextAlignment.Alignment = StringAlignment.Center;
                    _TextAlignment.LineAlignment = StringAlignment.Center;
                    break;
                case ContentAlignment.MiddleRight:
                    _TextAlignment.Alignment = StringAlignment.Far;
                    _TextAlignment.LineAlignment = StringAlignment.Center;
                    break;
                case ContentAlignment.BottomLeft:
                    _TextAlignment.Alignment = StringAlignment.Near;
                    _TextAlignment.LineAlignment = StringAlignment.Far;
                    break;
                case ContentAlignment.BottomCenter:
                    _TextAlignment.Alignment = StringAlignment.Center;
                    _TextAlignment.LineAlignment = StringAlignment.Far;
                    break;
                case ContentAlignment.BottomRight:
                    _TextAlignment.Alignment = StringAlignment.Far;
                    _TextAlignment.LineAlignment = StringAlignment.Far;
                    break;
            }
        }

        private new void ResetForeColor()
        {
            _ForeBrush = new SolidBrush(_ForeColor);
        }

        private void DrawCheckBox(Graphics g)
        {
            g.Clear(BackColor);

            #region 이미지 결정
            if (this.Checked)
            {
                if (Enabled)
                {
                    if (_RollOver)
                        _CurCheckImage = _CheckBoxImages[4];
                    else
                        _CurCheckImage = _CheckBoxImages[3];
                }
                else
                    _CurCheckImage = _CheckBoxImages[5];
            }
            else
            {
                if (Enabled)
                {
                    if (_RollOver)
                        _CurCheckImage = _CheckBoxImages[1];
                    else
                        _CurCheckImage = _CheckBoxImages[0];
                }
                else
                    _CurCheckImage = _CheckBoxImages[2];
            }
            #endregion

            if (this.Appearance == Appearance.Button)
                g.DrawImage(_CurCheckImage, 
                    new Rectangle(0, 
                    0,
                    Bounds.Width,
                    Bounds.Height),
                    0, 0, 
                    _CurCheckImage.Width, 
                    _CurCheckImage.Height,
                    GraphicsUnit.Pixel);
            else
                g.DrawImage(_CurCheckImage, _CheckBoxLocation.X, _CheckBoxLocation.Y);

            if (_ForeBrush != null)
            {
                g.DrawString(Text,
                    Font,
                    _ForeBrush,
                    _TextBounds,
                    _TextAlignment);
            }
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            Graphics g = e.Graphics;
            
            if (_CheckBoxImage == null)
            {
                base.OnPaint(e);
                return;
            }

            DrawCheckBox(g);
        }

        protected override void OnSizeChanged(EventArgs e)
        {
            RecalcBounds();
            base.OnSizeChanged(e);
        }

        protected override void OnMouseEnter(EventArgs eventargs)
        {
            _RollOver = true;
            base.OnMouseEnter(eventargs);
        }

        protected override void OnMouseLeave(EventArgs eventargs)
        {
            _RollOver = false;
            base.OnMouseLeave(eventargs);
        }
	}
}
