using System;
using System.IO;
using System.Drawing;
using System.Drawing.Imaging;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace D2net.Common
{
    public enum TGAColorMapType : byte
    {
        NoColorMap = 0,
        ColorMap = 1,
    }

    public enum TGAFileFormat : byte
    {
        NoImageData = 0,                        // No Image Data Included
        UncompressedColorMapImage = 1,          // Uncompressed, Color-mapped Image
        UncompressedTrueColorImage = 2,         // Uncompressed, True-color Image
        UncompressedBorWImage = 3,              // Uncompressed, Black-and-white Image
        RunLengthEncodedColorMapImage = 9,      // Run-length encoded, Color-mapped Image
        RunLengthEncodedTrueColorImage = 10,    // Run-length encoded, True-color Image
        RunLengthEncodedBorWImage = 11,         // Run-length encoded, Black-and-white Image
    }

    [StructLayout(LayoutKind.Sequential), ComVisible(false)]
    public struct TGAColorMapSpec
    {
        public ushort FirstEntryIndex;
        public ushort ColorMapLength;
        public byte   ColorMapEntrySize;
    }

    [StructLayout(LayoutKind.Sequential), ComVisible(false)]
    public struct TGAImageSpec
    {
        public ushort X;
        public ushort Y;
        public ushort Width;
        public ushort Height;
        public byte   Depth;
        public byte   Descriptor;

        //  bit spec
        //    7 ~ 6       5 ~ 4             3 ~ 0
        //  | unused | Image Origin | Alpha Channel Bits |
    }

    [StructLayout(LayoutKind.Sequential), ComVisible(false)]
    public struct TGAHeader
    {
        public byte IDLength;
        public TGAColorMapType MapType;
        public TGAFileFormat FileFormat;
        public TGAColorMapSpec ColorMapSpec;
        public TGAImageSpec ImageSpec;
    }

	/*
    [StructLayout(LayoutKind.Sequential), ComVisible(false)]
    public sealed class TGA
    {
        internal TGAHeader _Header;
        internal byte[] _IDs = null;
        internal ImageList _Images = new ImageList();
    }
	*/

    /// <summary>
    /// ImageEx에 대한 요약 설명입니다.
    /// </summary>
    public sealed class TGALoader
    {
        public TGALoader()
        {
        }

        public static unsafe Bitmap FromFile(string path)
        {
            FileStream fs = null;
            // TGA tga = null;
            TGAHeader Header;
            Bitmap img = null;
            int datalen = 0;
            byte* pdata = null;
            // ColorPalette pal = null;

            try
            {
                fs = new FileStream(path, FileMode.Open, FileAccess.Read);
                Read(fs.Handle, &(Header), 3);
                Read(fs.Handle, &(Header.ColorMapSpec), 5);
                Read(fs.Handle, &(Header.ImageSpec), 10);

                // tga = new TGA();
                // tga._Header = Header;
                if (Header.IDLength > 0)
                {
					/*
                    tga._IDs = new byte[Header.IDLength];
                    fixed (byte* p = tga._IDs)
                    {
                        Read(fs.Handle, p, Header.IDLength);
                    }
					*/
                }

                if (Header.MapType == TGAColorMapType.ColorMap &&
                    Header.ColorMapSpec.ColorMapLength > 0)
                {
                    int ColorMapLen = Header.ColorMapSpec.ColorMapLength * 
                        Header.ColorMapSpec.ColorMapEntrySize / 8;

                    // reject anything > 256 palette entries
                    if (Header.ColorMapSpec.ColorMapLength > 256) 
                        throw new Exception("Color Map의 길이가 256보다 큽니다.");

                    // setup temp palette buffer
                    pdata = (byte*)Marshal.AllocCoTaskMem(ColorMapLen);
                    if (pdata == null)
                        throw new Exception("Color Map을 저장할 메모리를 저장할 수 없습니다.");

                    // read into temp buffer
                    Read(fs.Handle, pdata, ColorMapLen);

                    /*
                    RASTER raster;
                    RASTER::CONVERTER *converter=raster.RequestConverter(palette_format,ARGB8888);
                    if (!converter || !converter->Convert(temp,palette,palette_length,NULL))
                    {
                        // failed convert
                        delete converter;
                        delete[] temp;
                        return 0;
                    }

                    // cleanup
                    delete converter;
                    delete[] temp;
                    */
                }

                if (pdata != null)
                    Marshal.FreeCoTaskMem(new IntPtr(pdata));
                pdata = null;

                switch (Header.FileFormat)
                {
//                    case TGAFileFormat.UncompressedColorMapImage:
//                        break;
                    case TGAFileFormat.UncompressedTrueColorImage:
                        datalen = Header.ImageSpec.Width * Header.ImageSpec.Height * 4;
                        pdata = (byte*)Marshal.AllocCoTaskMem(datalen);
                        Read(fs.Handle, pdata, datalen);
                        img = CreateUncompressedTrueColorImage(ref Header, pdata);
                        break;
//                    case TGAFileFormat.UncompressedBorWImage:
//                        break;
//                    case TGAFileFormat.RunLengthEncodedColorMapImage:
//                        break;
//                    case TGAFileFormat.RunLengthEncodedTrueColorImage:
//                        break;
//                    case TGAFileFormat.RunLengthEncodedBorWImage:
//                        break;
                    default:
                        throw new Exception("지원하지 않는 TGA 파일 포멧");
                }

                return img;
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                if (pdata != null)
                    Marshal.FreeCoTaskMem(new IntPtr(pdata));

                if (fs != null)
                    fs.Close();
            }
        }

        private static unsafe Bitmap CreateUncompressedTrueColorImage(ref TGAHeader Header, byte* data)
        {
            Bitmap bmp = null;
            int datalen = 0;
            BitmapData bits = null;
			uint* pdest = null;
            
            try
            {
                datalen = 4 * Header.ImageSpec.Width * Header.ImageSpec.Height;
                bmp = new Bitmap(Header.ImageSpec.Width, Header.ImageSpec.Height, PixelFormat.Format32bppArgb);
                bits = bmp.LockBits(new Rectangle(0, 0, Header.ImageSpec.Width, Header.ImageSpec.Height), 
                    ImageLockMode.WriteOnly, 
                    PixelFormat.Format32bppArgb);
				pdest = (uint*)bits.Scan0.ToPointer();
				for (int i = 0; i < Header.ImageSpec.Height; i++)
				{
					CopyMemory(&(pdest[(Header.ImageSpec.Height - i - 1) * Header.ImageSpec.Width]), 
						&(((uint*)(void*)data)[i * Header.ImageSpec.Width]), 
						(uint)(4 * Header.ImageSpec.Width));
				}
                // CopyMemory(bits.Scan0.ToPointer(), data, (uint)datalen);
				bmp.UnlockBits(bits);
				bits = null;
                return bmp;
            }
            catch (Exception ex)
            {
				System.Diagnostics.Debug.WriteLine(ex.Message + "\r\n" + ex.StackTrace);
                throw ex;
            }
            finally
            {
                if (bmp != null &&
                    bits != null)
                    bmp.UnlockBits(bits);
            }
        }

        [System.Runtime.InteropServices.DllImport("kernel32", SetLastError = true)]
        private static extern unsafe bool ReadFile
            (
            System.IntPtr hFile,      // handle to file
            void* pBuffer,            // data buffer
            int NumberOfBytesToRead,  // number of bytes to read
            int* pNumberOfBytesRead,  // number of bytes read
            int Overlapped            // overlapped buffer
            );

        [System.Runtime.InteropServices.DllImport("kernel32", SetLastError = true)]
        private static extern unsafe void CopyMemory(void* pDest, void* pSrc, uint size);

        private static unsafe int Read(IntPtr handle, void* buffer, int count)
        {
            int n = 0;            
            return ((ReadFile(handle, buffer, count, &n, 0) == false) ? -1 : n);
        }
    }
}
