import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : YangChunLong
 * @date : Created in 2019/9/22 12:17
 * @description: 关于时间操作的工具类
 * @modified By:
 * @version: :0.0.1
 */
public class DateUtil {
    public static final String formatDefaultTimestamp = "yyyy-MM-dd HH:mm:ss";
    public static final String format_yyyy_MM_dd_HHmm = "yyyy-MM-dd HH:mm";
    public static final String format_yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String format_yyyyMMdd = "yyyyMMdd";
    public static final String format_yyyy = "yyyy";
    public static final String format_yyyyMM = "yyyyMM";
    public static final String format_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String format_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String format_yyyyMMddHH = "yyyyMMddHH";

    public static final String[] weekStr = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    public enum WEEK {
        MON("MON", 2),
        TUE("TUE", 3),
        WED("WED", 4),
        THU("THU", 5),
        FRI("FRI", 6),
        SAT("SAT", 7),
        SUN("SUN", 1);

        private String name;
        private int index;

        private WEEK(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static int getIndexByName(String name) {
            for (WEEK w : WEEK.values()) {
                if (w.getName().equals(name)) {
                    return w.getIndex();
                }
            }
            return 0;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将相应格式的时间字符串转成DATE
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date, String formatType) {
        SimpleDateFormat f = new SimpleDateFormat(formatType);
        Date innerDate;
        try {
            innerDate = f.parse(date);
        } catch (ParseException e) {
            innerDate = new Date();
            e.printStackTrace();
        }
        return innerDate;
    }

    /**
     * 获取相应格式的当前时间
     *
     * @param formatType
     * @return
     */
    public static String getCurrentFormatDate(String formatType) {
        Locale locale = Locale.CHINESE;
        SimpleDateFormat dateStyle = new SimpleDateFormat(formatType, locale);
        return dateStyle.format(new Date());
    }

    /**
     * 把日期时间格式化为指定格式，如：yyyy-MM-dd HH:mm
     *
     * @param dt         java.util.Date
     * @param formatType : 指定日期转化格式字符串模板,例如:yyyy-MM-dd
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(Date dt, String formatType) {
        String newDate = "";
        if (dt != null) {
            Locale locale = Locale.CHINESE;
            SimpleDateFormat dateStyle = new SimpleDateFormat(formatType, locale);
            newDate = dateStyle.format(dt);
        }
        return newDate;
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s, String format) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间格式字符串转换为时间对象
     *
     * @param strDate
     * @return
     */
    public static Date format(String strDate, String aFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 获取某一天是星期几
     *
     * @param date
     * @return
     * @author 张广彬
     * @date 2013-5-10
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取某一天的前一天
     *
     * @param date
     * @return
     * @author 张广彬
     * @date 2013-6-7
     */
    public static Date getYesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date getTom(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param time   时间戳(长整形字符串)
     * @param format 格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(long time, String format) {
        Date d = new Date();
        d.setTime(time);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    public static Date getDate(long time) {
        Date d = new Date();
        d.setTime(time);

        return d;
    }

    /**
     * 秒时间戳，转日期时间戳
     *
     * @param timeStamp
     * @param format
     * @return
     */
    public static long getTimeStampDate(String timeStamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp + "000")));
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(sd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long s = date.getTime() / 1000;
        return s;
    }

    /**
     * 时间戳转日期
     *
     * @param timeStamp
     * @return
     */
    public static String getDateTimeStamp(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp + "000")));
        return sd;
    }

    /**
     * 返回一个指定数字后的时间
     *
     * @param x 指定几分钟
     * @return
     */
    public static String getTimeMinuteAdd(Date date, int x) {
        long new_d = date.getTime() + (x * 60 * 1000);
        return getDate(new_d, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间+Interval 分钟 后的时间
     *
     * @param Interval
     * @return
     * @author 王振江
     * @date 2013-6-6
     */
    public static Date addDate(int Interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // 设置当前日期
        c.add(Calendar.MINUTE, Interval); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        Date date = c.getTime();
        return date;

    }

    public static String getFutureDay(String appDate, String format, int days) {
        String future = "";
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {

        }

        return future;
    }

    /**
     * 字符型时间变成时间类型
     *
     * @param date   字符型时间 例如: "2008-08-08"
     * @param format 格式化形式 例如: "yyyy-MM-dd"
     * @return 出现异常时返回null
     */
    public static Date getFormatDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        if (date == null) {
            return d;
        }
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 得到今天的星期
     *
     * @return 今天的星期
     */
    public static String getWeeks(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param date   时间戳(长整形字符串)
     * @param format 格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(Date date, String format) {
        String formatDate = "";
        if (date != null) {
            DateFormat df = new SimpleDateFormat(format);
            formatDate = df.format(date);
        }
        return formatDate;
    }

    /**
     * 日期转换(竞彩专用)
     *
     * @param agalistNo
     */
    public static String getDate(String agalistNo) {
        String resDate = "";// 日期和星期
        int iss = Integer.valueOf(agalistNo.substring(8, 9));
        switch (iss) {
            case 1:
                resDate = "周一" + " " + agalistNo.substring(9, 12);
                break;
            case 2:
                resDate = "周二" + " " + agalistNo.substring(9, 12);
                break;
            case 3:
                resDate = "周三" + " " + agalistNo.substring(9, 12);
                break;
            case 4:
                resDate = "周四" + " " + agalistNo.substring(9, 12);
                break;
            case 5:
                resDate = "周五" + " " + agalistNo.substring(9, 12);
                break;
            case 6:
                resDate = "周六" + " " + agalistNo.substring(9, 12);
                break;
            case 7:
                resDate = "周日" + " " + agalistNo.substring(9, 12);
                break;
            default:
                break;
        }

        return resDate;
    }

    /**
     * 求两个日期差
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期相差天数
     */
    public static long getDateMargin(Date beginDate, Date endDate) {
        long margin = 0;
        margin = endDate.getTime() - beginDate.getTime();
        margin = margin / (1000 * 60 * 60 * 24);
        return margin;
    }

    /**
     * 求两个日期差:天数相关
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期相差天数
     */
    public static long getDateMargin4D(Date beginDate, Date endDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(beginDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(endDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    public static Date getLastDate(int interval, String day, String type) {
        if (StringUtils.isBlank(type)) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (interval != -1) {
            cal.add(Calendar.DAY_OF_YEAR, 0 - interval);
            return cal.getTime();
        }
        if (type.equals("WEEK")) {
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            cal.set(Calendar.DAY_OF_WEEK, WEEK.getIndexByName(day));
            return cal.getTime();
        } else if (type.equals("MONTH")) {
            cal.add(Calendar.MONTH, -1);
            cal.set(Calendar.DAY_OF_MONTH, new Integer(day));
            return cal.getTime();
        }
        return null;
    }

    public static Date getEndDate(int day, String type) {
        return null;
    }


    /**
     * 判断时间是否是昨天的日期
     *
     * @param time
     * @return
     */
    public static int isYesterday(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null || "".equals(time)) {
            return -1;
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        //昨天，返回1
        if (current.before(today) && current.after(yesterday)) {
            return 0;
        }
        if (current.after(today)) {
            //如果是今天，返回false
            return 1;
        }
        return -1;
    }

    /**
     * 格式化时间
     *
     * @param dateStr
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String getFormatString(String dateStr, String fromFormat, String toFormat) {
        if (StringUtils.isEmpty(dateStr) && StringUtils.isEmpty(fromFormat) && StringUtils.isEmpty(toFormat)) {
            return null;
        }
        try {
            Date date = getFormatDate(dateStr, fromFormat);
            SimpleDateFormat format1 = new SimpleDateFormat(toFormat);
            return format1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * start
     * 本周开始时间戳
     */
    public static long getWeekStartTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        // 获取星期日开始时间戳
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String time = simpleDateFormat.format(cal.getTime()) + "000000";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long l = 0l;
        try {
            l = sdf.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * end
     * 本周结束时间戳
     */
    public static long getWeekEndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        // 获取星期六结束时间戳
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String time = simpleDateFormat.format(cal.getTime()) + "235959";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long l = 0l;
        try {
            l = sdf.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }


    /**
     * 获取当前时间到第二天零点的秒数
     *
     * @return
     */
    public synchronized static int getSurplusDaySecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long l = calendar.getTimeInMillis() - System.currentTimeMillis();
        return (int) l / 1000;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy.MM.dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yy.MM.dd").format(c.getTime());
        return dayBefore;
    }


    /**
     * 获取指定日期的前n天
     *
     * @param specifiedDay
     * @param num
     * @return
     */
    public static String getSpecifiedDay(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy.MM.dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - num);

        String dayBefore = new SimpleDateFormat("yy.MM.dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayV2(String specifiedDay, int num, String dateFormat) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(dateFormat).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);

        String dayBefore = new SimpleDateFormat(dateFormat).format(c.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的格式
     *
     * @param specifiedDay
     * @return
     */
    public static String getDateTime(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy.MM.dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 时间比较大小
     *
     * @param beforeTime
     * @param afterTime
     * @return
     */
    public static boolean timeCampare(String beforeTime, String afterTime) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dt1 = null;//将字符串转换为date类型
        try {
            dt1 = df.parse(beforeTime);
            Date dt2 = df.parse(afterTime);
            if (dt1.getTime() > dt2.getTime())//比较时间大小,dt1小于dt2
            {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String nowHourMin() {
        Calendar ncalendar = Calendar.getInstance();
        return ncalendar.get(Calendar.HOUR_OF_DAY) + ":" + ncalendar.get(Calendar.MINUTE) + ":00";
    }


    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getHourOfDay(int hourOfDay) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //获取昨天的开始时间
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    //获取昨天的结束时间
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    //获取明天的开始时间
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    public static Date getHourOfDayOfTomorrow(int hourOfDay) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getHourOfDay(hourOfDay));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    //获取明天的结束时间
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    //获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();

        return days;
    }

    //两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    //获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    //获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    //返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    //返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    //返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    //获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
    public static List<List<Date>> getTimeList(int beginYear, int beginMonth, int endYear,
                                               int endMonth, int k) {
        List<List<Date>> list = new ArrayList<List<Date>>();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    //获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
    public static List<Date> getTimeList(int beginYear, int beginMonth, int k) {
        List<Date> list = new ArrayList<Date>();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

    /**
     * 获取当前日期零点的unix时间戳
     *
     * @return
     */
    public static long getZeroTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * <p>Title: getTimeByHour</p>
     * <p>Description: 获取当前时间的几个小时的时间</p>
     *
     * @param hour
     * @return
     * @author S.Wang  2017年3月28日 下午8:26:06
     */
    public static Calendar getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return calendar;
    }

    public static void main(String[] args) throws ParseException {
//		 System.out.println(System.currentTimeMillis());
//		 System.out.println(System.currentTimeMillis()/1000);
//		 System.out.println((long)1490842170*1000);
//	     System.out.println( getDate(1490842170000l));
//	     System.out.println(getDateMargin4D(getDate(1498730976000L),new Date()));
//	     System.out.println(dateToStamp("2017-10-21",format_yyyy_MM_dd ));
        Calendar cal = Calendar.getInstance();
//	     cal.setTime( getYesterday(new Date()));
        cal.setTime(getTom(new Date()));
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));

    }

    /**
     * Date类型转Long类型
     *
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        long longTime = date.getTime();
        return longTime;
    }

    public static String longToDate(long longDate) {
        Date date = new Date(longDate);
        SimpleDateFormat sd = new SimpleDateFormat(formatDefaultTimestamp);
        return sd.format(date);
    }

    public static String dateToUnixTimeStamp(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatDefaultTimestamp);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return : java.lang.Long
     * @author : YangChunLong
     * @date : Created in 2019/7/5 13:07
     * @description: 获取 当前 日期（精确到小时） 的时间戳
     * @modified By:
     * @Param: dateutils
     */
    public static Long getCurrentTimeStampHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(format_yyyyMMddHH);
        String ss = format.format(date);
        try {
            Date date1 = format.parse(ss);
            return date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * @author     : YangChunLong
     * @date       : Created in 2019/9/29 10:52
     * @description: 获取下 nexthour 个小时的 第 minute 分钟的时间戳
     * @modified By:
     * @Param: nextHour 第 nexthour 个小时
     * @Param: minute 第 minute 分钟
     * @return     : java.lang.Long
     */
    public static Date getMinuteOfNextHour(int nextHour, int minute) {
        Calendar calendar = Calendar.getInstance();
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        int mill = calendar.get(Calendar.MILLISECOND);
        calendar.add(Calendar.HOUR, nextHour);
        calendar.add(Calendar.MINUTE, minute-m);
        calendar.add(Calendar.SECOND,-s);
        calendar.add(Calendar.MILLISECOND,-mill);
        return calendar.getTime();
    }
}
