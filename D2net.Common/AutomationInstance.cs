using System;
using System.Reflection;

namespace D2net.Common
{
	/// <summary>
	/// AutomationInstance에 대한 요약 설명입니다.
	/// </summary>
	public class AutomationInstance
	{
        private object _Instance = null;
        private Type _ClsType = null;

		private AutomationInstance()
		{
		}

        public static AutomationInstance CreateInstance(string ClsPath, string ClsName, params object[] Params)
        {
            Type[] paramTypes = null;
            int len, i = 0;
            AutomationInstance AutoInstance = null;
            ConstructorInfo ci = null;

            try
            {
                AutoInstance = new AutomationInstance();
                Assembly assem = Assembly.LoadFrom(ClsPath);
                AutoInstance._ClsType = assem.GetType("D2net.CMExpress.Extension.SBS.CMDBCopyer");

                if (AutoInstance._ClsType == null)
                    throw new Exception("\"" + ClsPath + "\" 바이너리에 \""
                        + ClsName + "\" 클래스가 존재하지 않습니다.");

                if (Params != null)
                {
                    len = Params.Length;
                    paramTypes = new Type[len];

                    for (i = 0; i < len; i++)
                        paramTypes[i] = Params[i].GetType();
                }

                ci = AutoInstance._ClsType.GetConstructor(paramTypes);
                AutoInstance._Instance = ci.Invoke(Params);
                return AutoInstance;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public object Instance
        {
            get { return _Instance; }
        }

        public object Call(string MethodName, params object[] Params)
        {
            Type[] paramTypes = null;
            int len, i = 0;
            MethodInfo mi = null;

            try
            {
                if (Params != null)
                {
                    len = Params.Length;
                    paramTypes = new Type[len];

                    for (i = 0; i < len; i++)
                        paramTypes[i] = Params[i].GetType();
                }

                mi = _ClsType.GetMethod(MethodName, paramTypes);
                return mi.Invoke(_Instance, Params);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public object GetProperty(string PropertyName)
        {
            PropertyInfo pi = null;

            try
            {
                pi = _ClsType.GetProperty(PropertyName);
                return pi.GetValue(_Instance, null);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void SetProperty(string PropertyName, object val)
        {
            PropertyInfo pi = null;

            try
            {
                pi = _ClsType.GetProperty(PropertyName);
                pi.SetValue(PropertyName, val, null);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
	}
}
