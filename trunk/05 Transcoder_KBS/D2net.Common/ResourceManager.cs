using System;
using System.IO;
using System.Drawing;
using System.Collections;
//using java.io;
//using java.util.zip;

namespace D2net.Common.UI
{
	/// <summary>
	/// CMBankResMgr에 대한 요약 설명입니다.
	/// </summary>
	public class ResourceManager
	{
        //static Hashtable _Tables = new Hashtable();

        //public ResourceManager(string path)
        //{
        //    try
        //    {
        //        FileInputStream fis = new FileInputStream(path);
        //        MemoryStream ms = null;
        //        ZipInputStream zis = new ZipInputStream(fis);
        //        ZipEntry ze = null;
        //        string name, ext;
        //        int find;
        //        Image img = null;
        //        sbyte[] sdata = new sbyte[8192];
        //        byte[] udata = new byte[8192];
        //        long len = 0;
        //        int readlen = 0;

        //        // [2006-08-31] -> 초기화시 Resource Table을 초기화함
        //        _Tables.Clear();
        //        while ((ze = zis.getNextEntry()) != null)
        //        {
        //            if (!ze.isDirectory())
        //            {
        //                name = ze.getName().ToLower();
        //                len = ze.getSize();
        //                find = name.LastIndexOf(".");

        //                ext = name.Substring(find + 1, name.Length - find - 1);
        //                name = name.Substring(0, find);

        //                if (ext != "jpg" &&
        //                    ext != "bmp")
        //                    continue;

        //                ms = new MemoryStream();
        //                while (len != 0)
        //                {
        //                    readlen = zis.read(sdata, 0, ((len < 8192) ? (int)len : 8192));
        //                    System.Buffer.BlockCopy(sdata, 0, udata, 0, readlen);
        //                    ms.Write(udata, 0, readlen);
        //                    len -= readlen;
        //                }

        //                img = Image.FromStream(ms);
        //                ms.Close();
        //                _Tables.Add(name, img);
        //            }
        //        }

        //        zis.close();
        //        fis.close();
        //    }
        //    catch (Exception ex)
        //    {
        //        throw ex;
        //    }
        //}

        //public Image this [string index]
        //{
        //    get
        //    {
        //       return (Image)_Tables[index.ToLower()];
        //    }
        //}
	}
}
