using System;
using System.Drawing;

namespace D2net.Common.UI
{
	/// <summary>
	/// 
	/// </summary>
    public class ColorEx
    {
        private Color _col;
        private double _H = 0.0;
        private double _S = 0.0;
        private double _V = 0.0;

        public ColorEx()
        {
        }

        public ColorEx(Color Col)
        {
            _col = Col;
            ConvertToHSV(ref _H, ref _S, ref _V);
        }

        public ColorEx(int _R, int _G, int _B)
        {
            _col = Color.FromArgb(_R, _G, _B);
            ConvertToHSV(ref _H, ref _S, ref _V);
        }

        public ColorEx(double _H, double _S, double _V)
        {
            byte r = 0, g = 0, b = 0;

            this._H = _H;
            this._S = _S;
            this._V = _V;
            ConvertToRGB(ref r, ref g, ref b);
            _col = Color.FromArgb(r, g, b);
        }

        public byte R
        {
            get { return _col.R; }
        }

        public byte G
        {
            get { return _col.G; }
        }

        public byte B
        {
            get { return _col.B; }
        }

        public double dR
        {
            get { return _col.R / (double)255; } 
        }

        public double dG
        {
            get { return _col.G / (double)255; }
        }

        public double dB
        {
            get { return _col.B / (double)255; } 
        }

        public double H
        {
            get { return _H; }
        }

        public double S
        {
            get { return _S; }
        }

        public double V
        {
            get { return _V; }
        }

        private void ConvertToHSV(ref double H, ref double S, ref double V)
        {
            double cmax, cmin, delta;

            cmin = (double)Math.Min(Math.Min(R, G), B);
            cmax = (double)Math.Max(Math.Max(R, B), B);

            V = cmax / 255.0;
            S = (cmax - cmin) / cmax;
            if (S == 0) 
            {
                H = 0.0;
            }
            else 
            {
                delta = cmax - cmin;
                if (R == cmax)
                    H = (G - B) / delta;
                if (B == cmax)
                    H = 2.0 + (B - R) / delta;
                if (G == cmax)
                    H = 4.0 + (R - G) / delta;
                H *= 60;
                if (H < 0.0)
                    H += 360;
            }
        }

        private void ConvertToRGB(ref byte R, ref byte G, ref byte B)
        {
            if (S == 0.0) 
            {
                R = G = B = (byte)(int)(_V * 255);
            } 
            else 
            {
                if (_H == 360.0)
                    _H = 0;
                _H /= 60.0;
                int i = (int)Math.Floor(_H);
                byte f = (byte)(int)(_H - (double)i);
                byte p = (byte)(int)(_V * (1.0 - _S));
                byte q = (byte)(int)(_V * (1.0 - (_S * f)));
                byte t = (byte)(int)(_V * (1.0 - (_S * (1.0 - f))));
                switch (i) 
                {
                    case 0:
                        R = (byte)(int)(_V * 255); G = t; B = p;
                        break;
                    case 1:
                        R = q; G = (byte)(int)(_V * 255); B = p;
                        break;
                    case 2:
                        R = p; G = (byte)(int)(_V * 255); B = t;
                        break;
                    case 3:
                        R = p; G = q; B = (byte)(int)(_V * 255);
                        break;
                    case 4:
                        R = t; G = p; B = (byte)(int)(_V * 255);
                        break;
                    case 5:
                        R = (byte)(int)(_V * 255); G = p; B = q;
                        break;
                }
            }
        }

        public static implicit operator Color (ColorEx c)
        {
            return c._col;
        }
	}
}
