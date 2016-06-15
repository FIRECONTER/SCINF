package cn.scinf.utils;

public class SCHOOLUtils {

	public SCHOOLUtils() {
		// TODO Auto-generated constructor stub
	}
public static String parseSchool(String myschool)
{
	if(myschool.equals("qinghua")) return "清华";
	else if(myschool.equals("beida")) return "北大";
	else if(myschool.equals("fudan")) return "复旦";
	else if(myschool.equals("shangjiao")) return "上交";
	else if(myschool.equals("renda")) return "人大";
	else if(myschool.equals("keda")) return "中科大";
	else if(myschool.equals("beiwai")) return "北外";
	else if(myschool.equals("beiyou")) return "北邮";
	else return "北交";
}
}
