using System;
using System.Text;
using System.Reflection;

namespace D2net.Common
{
    /// <summary>
    /// Time Code Format
    /// </summary>
    public enum TCFormat : byte
    {
        /// <summary>
        /// NTSC Drop Frame Time code format
        /// </summary>
        NTSC_Drop = 0,

        /// <summary>
        /// NTSC non Drop Frame Time code format
        /// </summary>
        NTSC_Nondrop,

        /// <summary>
        /// PAL Time code format
        /// </summary>
        PAL,

        /// <summary>
        /// User defined frame rate
        /// </summary>
        Frames,
    }

    /// <summary>
    /// Time code Mode
    /// </summary>
    public enum TCMode : byte
    {
        
        /// <summary>
        /// TC Mode
        /// </summary>
        TimeCode = 0,

        /// <summary>
        /// CTL Mode
        /// </summary>
        CTL,
    }

	/// <summary>
	/// 
	/// </summary>
    public abstract class TimeCode
    {
        // array indexes for a time code array 
		/// <summary>
		/// 시간
		/// </summary>
        protected const Int64 HOURS = 0;
		/// <summary>
		/// 분
		/// </summary>
        protected const Int64 MINUTES = 1;
		/// <summary>
		/// 초
		/// </summary>
        protected const Int64 SECONDS = 2;
		/// <summary>
		/// Frame
		/// </summary>
        protected const Int64 FRAMES = 3;

		/// <summary>
		/// frames per hour in non-drop mode
		/// </summary>
        protected const Int64 FR_HOUR_ND = 108000;    // frames per hour in non-drop mode
		/// <summary>
		/// frames per hour in drop frame mode
		/// </summary>
        protected const Int64 FR_HOUR_DROP = 107892;  // frames per hour in drop frame mode
		/// <summary>
		/// frames per minute in non-drop mode
		/// </summary>
        protected const Int64 FR_MINUTE_ND = 1800;    // frames per minute in non-drop mode
		/// <summary>
		/// frames per ten minutes in drop frame
		/// </summary>
        protected const Int64 FR_TENMIN_DROP = 17982; // frames per ten minutes in drop frame
		/// <summary>
		/// frame per minute in drop frame mode
		/// </summary>
        protected const Int64 FR_MINUTE_DROP = 1798;  // frame per minute in drop frame mode
		/// <summary>
		/// frames per second, both modes NTSC
		/// </summary>
        protected const Int64 FR_SECOND = 30;         // frames per second, both modes NTSC
		/// <summary>
		/// frames per hour in PAL
		/// </summary>
        protected const Int64 FR_HOUR_PAL = 90000;    // frames per hour in PAL
		/// <summary>
		/// frames per minute in PAL
		/// </summary>
        protected const Int64 FR_MINUTE_PAL = 1500;   // frames per minute in PAL
		/// <summary>
		/// frames per second in PAL
		/// </summary>
        protected const Int64 FR_SECOND_PAL = 25;     // frames per second in PAL

		/// <summary>
		/// Non drop Frame의 최대 프레임 수
		/// </summary>
        protected const Int64 MAX_NTSC_ND_FRAMES = 2592000;
		/// <summary>
		/// Drop Frame의 최대 프레임 수
		/// </summary>
        protected const Int64 MAX_NTSC_D_FRAMES = 2589408;
		/// <summary>
		/// PAL의 최대 프레임 수
		/// </summary>
        protected const Int64 MAX_PAL_FRAMES = 2160000;
		/// <summary>
		/// Non drop Frame CTL의 최대 프레임 수
		/// </summary>
        protected const Int64 MAX_NTSC_ND_CTLS = 1079999;
		/// <summary>
		/// Drop Frame CTL의 최대 프레임 수
		/// </summary>
        protected const Int64 MAX_NTSC_D_CTLS = 1078937;
		/// <summary>
		/// PAL CTL의 최대 프레임 수
		/// </summary>
        protected const Int64 MAX_PAL_CTLS = 899975;

        // member variable
		/// <summary>
		/// time code mode
		/// </summary>
        protected TCMode tcmode;
		/// <summary>
		/// time code format
		/// </summary>
        protected TCFormat tcformat;
		/// <summary>
		/// current frame
		/// </summary>
        protected Int64 frame;

		/// <summary>
		/// 생성자
		/// </summary>
		/// <param name="tcformat">time code format</param>
		/// <param name="tcmode">time code mode</param>
        protected TimeCode(TCFormat tcformat, TCMode tcmode)
        {
            this.tcmode = tcmode;
            this.tcformat = tcformat;
            this.frame = 0;
        }

		/// <summary>
		/// 생성자
		/// </summary>
		/// <param name="tcformat">time code format</param>
		/// <param name="tcmode">time code mode</param>
		/// <param name="frame">current frame</param>
        protected TimeCode(TCFormat tcformat, TCMode tcmode, Int64 frame)
        {
            this.tcmode = tcmode;
            this.tcformat = tcformat;
            this.frame = frame;
        }

		/// <summary>
		/// 생성자
		/// </summary>
		/// <param name="tcformat">time code format</param>
		/// <param name="tcmode">time code mode</param>
		/// <param name="timecode">time code 문자열</param>
        public TimeCode(TCFormat tcformat, TCMode tcmode, string timecode)
        {
            this.tcmode = tcmode;
            this.tcformat = tcformat;
            TC = timecode;
        }

		/// <summary>
		/// time code 문자열을 반환한다
		/// </summary>
		/// <returns>time code 문자열</returns>
        public override string ToString()
        {
            return TC;
        }

        public static Type GetTCType(TCFormat tcformat, TCMode tcmode)
        {
            Type t = null;

            switch (tcformat)
            {
                case TCFormat.NTSC_Drop:
                    t = tcmode == TCMode.TimeCode ? typeof(NTSCDropTC) : typeof(NTSCDropCTL);
                    break;
                case TCFormat.NTSC_Nondrop:
                    t = tcmode == TCMode.TimeCode ? typeof(NTSCNondropTC) : typeof(NTSCNondropCTL);
                    break;
                case TCFormat.PAL:
                    t = tcmode == TCMode.TimeCode ? typeof(PALTC) : typeof(PALCTL);
                    break;
            }

            return t;
        }

        public static TimeCode CreateTimeCode(TCFormat tcformat, TCMode tcmode, Int64 frame)
        {
            Type t = GetTCType(tcformat, tcmode);
            Type[] types = new Type[0];
            object[] param = new object[0];
            ConstructorInfo ci = t.GetConstructor(types);
            TimeCode tc = (TimeCode)ci.Invoke(param);
            tc.Frame = frame;
            return tc;
        }

        public static TimeCode CreateTimeCode(TCFormat tcformat, TCMode tcmode, string timecode)
        {
            Type t = GetTCType(tcformat, tcmode);
            Type[] types = new Type[0];
            object[] param = new object[0];
            ConstructorInfo ci = t.GetConstructor(types);
            TimeCode tc = (TimeCode)ci.Invoke(param);
            tc.TC = timecode;
            return tc;
        }

		/// <summary>
		/// time code를 복제한다
		/// </summary>
		/// <returns>time code</returns>
        public TimeCode Clone()
        {
            return CreateTimeCode(tcformat, tcmode, frame);
        }

		/// <summary>
		/// time code mode
		/// </summary>
        public TCMode TimeCodeMode
        {
            get { return tcmode; }
            set { tcmode = value; }
        }

		/// <summary>
		/// time code format
		/// </summary>
        public TCFormat TimeCodeFormat
        {
            get { return tcformat; }
            set { tcformat = value; }
        }

		/// <summary>
		/// current frame
		/// </summary>
        public Int64 Frame
        {
            get {
                /*if (frame < 0)
                {
                    switch (tcformat)
                    {
                        case TCFormat.NTSC_Drop:
                            switch (tcmode)
                            {
                                case TCMode.CTL:
                                    return MAX_NTSC_D_CTLS + frame;
                                case TCMode.TimeCode:
                                    return MAX_NTSC_D_FRAMES + frame;
                                default:
                                    return frame;
                            }
                            break;
                        case TCFormat.NTSC_Nondrop:
                            switch (tcmode)
                            {
                                case TCMode.CTL:
                                    return MAX_NTSC_ND_CTLS + frame;
                                case TCMode.TimeCode:
                                    return MAX_NTSC_ND_FRAMES + frame;
                                default:
                                    return frame;
                            }
                            break;
                        case TCFormat.PAL:
                            switch (tcmode)
                            {
                                case TCMode.CTL:
                                    return MAX_PAL_CTLS + frame;
                                case TCMode.TimeCode:
                                    return MAX_PAL_FRAMES + frame;
                                default:
                                    return frame;
                            }
                            break;
                        default:
                            return frame;
                    }
                }
                else*/
                {
                    return frame;
                }
            }
            set { frame = value; }
        }

        /// <summary>
        /// 시간
        /// </summary>
        public int Hour
        {
            get
            {
                string tc = TC.Substring(0, 2);
                int sign = 1;

                if (tc.StartsWith("-"))
                {
                    sign = -1;
                    tc = tc.Substring(1, 1);
                }
                else if (tc.StartsWith("+"))
                {
                    tc = tc.Substring(1, 1);
                }

                return sign * Convert.ToInt32(tc);
            }
        }

        /// <summary>
        /// 분
        /// </summary>
        public int Minute
        {
            get { return Convert.ToInt32(TC.Substring(3, 2)); }
        }

        /// <summary>
        /// 초
        /// </summary>
        public int Second
        {
            get { return Convert.ToInt32(TC.Substring(6, 2)); }
        }

        /// <summary>
        /// 프레임
        /// </summary>
        public int FrameOfSecond
        {
            get { return Convert.ToInt32(TC.Substring(9, 2)); }
        }

		/// <summary>
		///  time code 문자열을 반환한다
		/// </summary>
        public string TC
        {
            get
            { 
                bool sign = false;
                char[] timecode = new char[11];

                if (frame < 0)
                    sign = true;

                if (this.tcmode == TCMode.CTL)
                {
                    FramesToTCstring(sign ? -1 * frame : frame, ref timecode, tcformat);

                    if (sign)
                        timecode[0] = '-';
                    else
                        timecode[0] = '+';
                }
                else
                {
                    if (this.frame < 0)
                    {
                        switch (tcformat)
                        {
                            case TCFormat.NTSC_Nondrop:
                                frame = this.frame + MAX_NTSC_ND_FRAMES;
                                break;
                            case TCFormat.NTSC_Drop:
                                frame = this.frame + MAX_NTSC_D_FRAMES;
                                break;
                            case TCFormat.PAL:
                                frame = this.frame + MAX_PAL_FRAMES;
                                break;
                        }
                    }

                    FramesToTCstring(frame, ref timecode, tcformat);
                }
                return new string(timecode);
            }

            set
            {
                int sign = 1;
                bool bigCtl = false;
                Int64 TotFrame = 0;
                char[] chValue = value.ToCharArray();
                
                try
                {
                    // 길이 체크
                    if (chValue.Length != 11)
                        throw new Exception("타임코드 형식의 문자열이 아닙니다.");

                    // CTL값이 10 이상이면 '-'임
                    // [2006-05-21] -> CTL 값이 10이상이면 음으로 설정(위치 이동)
                    if ( // tcmode == TCMode.CTL &&
                        chValue[0] > '0')
                    {
                        bigCtl = true;
                    }

                    // CTL 체크
					if (chValue[0] == '-')
					{    
						if (tcmode != TCMode.CTL)
							throw new Exception("CTL 모드의 타임코드 형식이 아닌 타임코드 인스턴스에 CTL 모드의 값을 대입하였습니다.");
            
						sign = -1;
						chValue[0] = '0';
					}
					else
					{
						if (tcmode == TCMode.CTL)
							chValue[0] = '0';
					}

                    // Frame Setting
                    frame = sign * TCstringToFrames(chValue, tcformat);

                    switch (tcformat)
                    {
                        case TCFormat.NTSC_Nondrop:
                            TotFrame = MAX_NTSC_ND_FRAMES;
                            break;
                        case TCFormat.NTSC_Drop:
                            TotFrame = MAX_NTSC_D_FRAMES;
                            break;
                        case TCFormat.PAL:
                            TotFrame = MAX_PAL_FRAMES;
                            break;
                    }

                    if (bigCtl)
                        frame -= TotFrame;

                    frame %= TotFrame;
                }
                catch (Exception ex)
                {
                    throw ex;
                }
            }
        }

		/// <summary>
		/// VCR에서 사용하는 time code 문자열을 반환한다
		/// </summary>
        public string VCRTC
        {
            get
            {
                Int64 frame = 0;
                char[] timecode = new char[11];

                if (this.frame < 0)
                {
                    switch (tcformat)
                    {
                        case TCFormat.NTSC_Nondrop:
                            frame = this.frame + MAX_NTSC_ND_FRAMES;
                            break;
                        case TCFormat.NTSC_Drop:
                            frame = this.frame + MAX_NTSC_D_FRAMES;
                            break;
                        case TCFormat.PAL:
                            frame = this.frame + MAX_PAL_FRAMES;
                            break;
                    }
                }

                FramesToTCstring(frame, ref timecode, tcformat);
                return new string(timecode);
            }
        }

        // method
        private static Int64 TCstringToFrames(char[] TC, TCFormat format)
        {
            Int64 dwFrames = 0;
            Int64 dwtmp;
            byte[] bTc = new byte[4];
            int   i;

            // Convert the string elements to numbers
            for (i = 0 ; i < 4 ; i++)
                bTc[i] = (byte)(((TC[(i<<1) + i] - '0') * 10) + 
                                 (TC[(i<<1) + i + 1] - '0'));
            
            switch (format)
            {
                    // Mode NTSC NON-DROP FRAMES
                case TCFormat.NTSC_Nondrop:
                    dwFrames = (int)bTc[HOURS]   * FR_HOUR_ND   +
                               (int)bTc[MINUTES] * FR_MINUTE_ND +
                               (int)bTc[SECONDS] * FR_SECOND    +
                               (int)bTc[FRAMES];
                    break;

                    // Mode NTSC DROP FRAMES
                case TCFormat.NTSC_Drop:
                    dwFrames =(int) bTc[HOURS]          * FR_HOUR_DROP   +
                              ((int)bTc[MINUTES] / 10)  * FR_TENMIN_DROP;

                    dwtmp = (int)bTc[MINUTES] % 10;
                    if (dwtmp >= 1)
                    {
                        dwFrames += FR_MINUTE_ND + (dwtmp-1) * FR_MINUTE_DROP;
                        if (bTc[SECONDS] == 0)
                        {
                            if (bTc[FRAMES] >= 2)
                                dwFrames -= 2;
                            else
                                dwFrames -= bTc[FRAMES];
                        }
                        else
                            dwFrames -= 2;
                    }
                    dwFrames += (int)bTc[SECONDS] * FR_SECOND +
                        (int)bTc[FRAMES];
                    break;

                    // Mode PAL
                case TCFormat.PAL:
                    dwFrames = (int)bTc[HOURS]   * FR_HOUR_PAL   +
                               (int)bTc[MINUTES] * FR_MINUTE_PAL +
                               (int)bTc[SECONDS] * FR_SECOND_PAL +
                               (int)bTc[FRAMES];
                    break;
            }

            return dwFrames;
        }

        private static bool FramesToTCstring(Int64 frames, ref char[] TC, TCFormat format)
        {
            Int64 dwReste;
            byte  bMinutes;
            byte[] bTc = new byte[4];
            int   i;

            // Writes the ":" separation fields in the string
            TC[0] = TC[1] = TC[3] = TC[4] = TC[6] = TC[7] = TC[9] = TC[10] = '0';
            TC[2] = TC[5] = TC[8] = ':';
    
            switch (format)
            {
                    // Mode PAL
                case TCFormat.PAL:
                    bTc[HOURS]     = (byte)(frames / FR_HOUR_PAL);
                    dwReste       = frames % FR_HOUR_PAL;
                    bTc[MINUTES]   = (byte)(dwReste / FR_MINUTE_PAL);
                    dwReste       = dwReste % FR_MINUTE_PAL;
                    bTc[SECONDS]   = (byte)(dwReste / FR_SECOND_PAL);
                    bTc[FRAMES]    = (byte)(dwReste % FR_SECOND_PAL);
                    break;

                    // Mode NTSC NON-DROP FRAMES
                case TCFormat.NTSC_Nondrop:
                    bTc[HOURS]     = (byte)(frames / FR_HOUR_ND);
                    dwReste       = frames % FR_HOUR_ND;
                    bTc[MINUTES]   = (byte)(dwReste / FR_MINUTE_ND);
                    dwReste       = dwReste % FR_MINUTE_ND;
                    bTc[SECONDS]   = (byte)(dwReste / FR_SECOND);
                    bTc[FRAMES]    = (byte)(dwReste % FR_SECOND);
                    break;

                    // Mode NTSC DROP FRAMES
                case TCFormat.NTSC_Drop:
                    // one hour
                    bTc[HOURS]    = (byte)(frames / FR_HOUR_DROP);
                    dwReste       = frames % FR_HOUR_DROP;
                    // 10 minutes
                    bMinutes  = (byte)(10 * (dwReste / FR_TENMIN_DROP));
                    dwReste  %= FR_TENMIN_DROP;

                    if (dwReste >= FR_MINUTE_ND)
                    {
                        dwReste  -= FR_MINUTE_ND;
                        // The first minute of a decade contains 1800 frames
                        // and the 9 others contain 1798 frames 
                        bMinutes += (byte)(1 + dwReste / FR_MINUTE_DROP);
                        dwReste  %= FR_MINUTE_DROP;
                        // two frames advance for the current minute
                        dwReste  += 2;
                    }

                    // minutes 
                    bTc[MINUTES] = bMinutes;
                    bTc[SECONDS]   = (byte)(dwReste / FR_SECOND);
                    bTc[FRAMES]   = (byte)(dwReste % FR_SECOND);
                    TC[8] = ':';
                    break;
            }

            // Writes the numbers in the string
            for (i = 0 ; i < 4 ; i++)
            {
                TC[(i << 1) + i] = (char)Math.Min('9', ((bTc[i] / 10) + '0'));
                TC[(i << 1) + i + 1] = (char)Math.Min('9', ((bTc[i] % 10) + '0'));
            }

            return true;
        }

        private void CheckTimecodeState(TimeCode timecode)
        {
            if (tcmode != timecode.TimeCodeMode)
                throw new Exception("두 타임코드 인스턴스간의 모드가 동일하지 않습니다.");

            if (tcformat != timecode.TimeCodeFormat)
                throw new Exception("두 타임코드 인스턴스간의 포멧이 동일하지 않습니다.");
        }

        private void CheckTimecode(Int64 nFrame)
        {
            Int64 totFrame = 0;

            switch (tcformat)
            {
                case TCFormat.NTSC_Nondrop:
                    totFrame = tcmode == TCMode.CTL ? MAX_NTSC_ND_CTLS : MAX_NTSC_ND_FRAMES;
                    break;
                case TCFormat.NTSC_Drop:
                    totFrame = tcmode == TCMode.CTL ? MAX_NTSC_D_CTLS : MAX_NTSC_D_FRAMES;
                    break;
                case TCFormat.PAL:
                    totFrame = tcmode == TCMode.CTL ? MAX_PAL_CTLS : MAX_PAL_FRAMES;
                    break;
            }

            if (nFrame > totFrame ||
                nFrame < -1 * totFrame)
                throw new Exception("유효한 타임코드의 범위를 벗어났습니다.");
        }

        public void CheckTimecode()
        {
            try
            {
                CheckTimecode(frame);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 입력된 프레임과 현재 프레임과의 차이를 계산(프레임 수)
		/// </summary>
		/// <param name="frame">차이를 계산 하고자 하는 프레임</param>
		/// <returns>차이(프레임 수)</returns>
        public Int64 Compare(Int64 frame)
        {
            TimeCode tc = CreateTimeCode(this.tcformat, this.tcmode, frame);
            Int64 result = 0;

            try
            {
                result = Compare(tc);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return result;
        }

		/// <summary>
		/// 입력된 time code 문자열과 현재 프레임과의 차이를 계산(프레임 수)
		/// </summary>
		/// <param name="timecode">time code 문자열</param>
		/// <returns>차이(프레임 수)</returns>
        public Int64 Compare(string timecode)
        {
            TimeCode tc = CreateTimeCode(this.tcformat, this.tcmode, timecode);
            Int64 result = 0;

            try
            {
                result = Compare(tc);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return result;
        }

		/// <summary>
		/// 입력된 time code 객체와 현재 프레임과의 차이를 계산(프레임 수)
		/// </summary>
		/// <param name="timecode">time code 객체</param>
		/// <returns>차이(프레임 수)</returns>
        public Int64 Compare(TimeCode timecode)
        {
            Int64 result = 0;

            try
            {
                Int64 totFrame = 0;

                switch (tcformat)
                {
                    case TCFormat.NTSC_Nondrop:
                        totFrame = tcmode == TCMode.CTL ? MAX_NTSC_ND_CTLS : MAX_NTSC_ND_FRAMES;
                        break;
                    case TCFormat.NTSC_Drop:
                        totFrame = tcmode == TCMode.CTL ? MAX_NTSC_D_CTLS : MAX_NTSC_D_FRAMES;
                        break;
                    case TCFormat.PAL:
                        totFrame = tcmode == TCMode.CTL ? MAX_PAL_CTLS : MAX_PAL_FRAMES;
                        break;
                }

                CheckTimecodeState(timecode);
                result = (frame - timecode.Frame) % totFrame;
                // result = (frame - timecode.Frame);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return result;
        }

		/// <summary>
		/// 입력된 frame과 현재 프레임을 더한다
		/// </summary>
		/// <param name="frame">프레임 수</param>
        public void Sum(int frame)
        {
            TimeCode tc = CreateTimeCode(this.tcformat, this.tcmode, frame);
            
            try
            {
                Sum(tc);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 입력된 time code 문자열과 현재 프레임을 더한다
		/// </summary>
		/// <param name="timecode">time code 문자열</param>
        public void Sum(string timecode)
        {
            TimeCode tc = CreateTimeCode(this.tcformat, this.tcmode, timecode);
            
            try
            {
                Sum(tc);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 입력된 time code 객체와 현재 프레임을 더한다
		/// </summary>
		/// <param name="timecode">time code 객체</param>
        public void Sum(TimeCode timecode)
        {
            Int64 frame;

            try
            {
                CheckTimecodeState(timecode);
                frame = timecode.Frame;
                frame += this.frame;
                CheckTimecode(frame);
                this.frame = frame;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 입력된 frame과 현재 프레임을 뺀다
		/// </summary>
		/// <param name="frame">프레임 수</param>
        public void Sub(int frame)
        {
            TimeCode tc = CreateTimeCode(this.tcformat, this.tcmode, frame);
            
            try
            {
                Sub(tc);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 입력된 time code 문자열과 현재 프레임을 뺀다
		/// </summary>
		/// <param name="timecode">time code 문자열</param>
        public void Sub(string timecode)
        {
            TimeCode tc = CreateTimeCode(this.tcformat, this.tcmode, timecode);
            
            try
            {
                Sub(tc);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 입력된 time code 객체와 현재 프레임을 뺀다
		/// </summary>
		/// <param name="timecode">time code 객체</param>
        public void Sub(TimeCode timecode)
        {
            Int64 frame;

            try
            {
                CheckTimecodeState(timecode);
                frame = this.frame;
                frame -= timecode.Frame;
                CheckTimecode(frame);
                this.frame = frame;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 현재 프레임을 1 증가
		/// </summary>
        public void Increase()
        {
            try
            {
                CheckTimecode(frame + 1);
                frame++;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

		/// <summary>
		/// 현재 프레임을 1 감소
		/// </summary>
        public void Decrease()
        {
            try
            {
                CheckTimecode(frame - 1);
                frame--;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
	}

    public class NTSCDropTC : TimeCode
    {
        public NTSCDropTC()
            : base(TCFormat.NTSC_Drop, TCMode.TimeCode)
        {
        }
    }

    public class NTSCDropCTL : TimeCode
    {
        public NTSCDropCTL()
            : base(TCFormat.NTSC_Drop, TCMode.CTL)
        {
        }
    }

    public class NTSCNondropTC : TimeCode
    {
        public NTSCNondropTC()
            : base(TCFormat.NTSC_Nondrop, TCMode.TimeCode)
        {
        }
    }

    public class NTSCNondropCTL : TimeCode
    {
        public NTSCNondropCTL()
            : base(TCFormat.NTSC_Nondrop, TCMode.CTL)
        {
        }
    }

    public class PALTC : TimeCode
    {
        public PALTC()
            : base(TCFormat.PAL, TCMode.TimeCode)
        {
        }
    }

    public class PALCTL : TimeCode
    {
        public PALCTL()
            : base(TCFormat.PAL, TCMode.CTL)
        {
        }
    }
}
