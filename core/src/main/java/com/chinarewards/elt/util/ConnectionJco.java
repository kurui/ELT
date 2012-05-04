package com.chinarewards.elt.util;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import java.util.Properties;

public class ConnectionJco {
  public static void main(String[] args) {
	  JCO.Client myConnection;
	    myConnection = JCO.createClient("000","SAP*","PASS", "EN","10.3.3.220","00");
		 /*获得一个到SAP系统的连接  END    */
		 myConnection.connect();//进行实际连接
		 //如果连接不为null并且处于活动状态
		 if (myConnection != null && myConnection.isAlive()) {
		 //从连接获得一个逻辑意义上的“仓库”对象（Repository）
		 JCO.Repository myRepository = new JCO.Repository("Repository", myConnection); //活动的连接
		 //要调用的SAP函数名称
		 String strFunc = "BAPI_FLIGHT_GETLIST";
		 //从“仓库”中获得一个指定函数名的函数模板
		 IFunctionTemplate ft = myRepository.getFunctionTemplate(strFunc.toUpperCase());
		 //从这个函数模板获得该SAP函数的对象
		 JCO.Function function = ft.getFunction();
		//获得函数的import参数列表
		 JCO.ParameterList input = function.getImportParameterList();
		
		 //设定一个import参数的值。参数名为“MAX_ROWS”，设定值为10
		 input.setValue(10, "MAX_ROWS");

		 /*设定结构参数  START  */
		 //如果参数是一个结构，用参数名获得一个对应类型的结构对象
		 JCO.Structure sFrom = input.getStructure("DESTINATION_FROM");
		 //设定结构中的变量。变量名为“CITY”，设定值为“NEW YORK”
		 sFrom.setValue("NEW YORK", "CITY");
		 //将处理好的结构赋给该参数
		 input.setValue(sFrom, "DESTINATION_FROM");
		 /*设定结构参数  END    */
		 /*设定table参数  START  */
		 //用参数名获得一个对应类型的内部表对象
		 JCO.Table tDateRange = function.getTableParameterList().getTable("DATE_RANGE");
		
		 //接下来基本属于体力活了......
		 //新增一条空行
		 tDateRange.appendRow();
		 //定位到第0行
		 tDateRange.setRow(0);
		 //设定该行对应变量
		 tDateRange.setValue("I", "SIGN");
		 tDateRange.setValue("EQ", "OPTION");
		 tDateRange.setValue("20040330", "LOW");
		 //新增一条空行
		 tDateRange.appendRow();
		 //定位到第1行
		 tDateRange.setRow(1);
		 //设定该行对应变量
		 tDateRange.setValue("I", "SIGN");
		 tDateRange.setValue("EQ", "OPTION");
		 tDateRange.setValue("20040427", "LOW");
		 //......
		  /*设定table参数  END    */
		 //执行函数
		 myConnection.execute(function);
		 //在执行函数后可用相同方式获得输出结果
		 JCO.Table flights = function.getTableParameterList().getTable("FLIGHT_LIST");
		 //JCO.Table对象可以直接输出到html文件
		 flights.writeHTML("C:/function.html");
		 //也可以如下单独获得表中个别变量
		 System.out.println("Airline" + "\t\t"
		 + "from city" + "\t"
		 + "to city" + "\t\t"
		 + "departure time" + "\t\t"
		 + "price" + "\t"
		 + "CURR");
		 for (int i = 0; i < flights.getNumRows(); i++) {
		   flights.setRow(i);
		   System.out.println(flights.getString("AIRLINE") + "\t"
		  + flights.getString("CITYFROM") + "\t"
		  + flights.getString("CITYTO") + "\t"
		  + flights.getDate("FLIGHTDATE") + "\t"
		  + flights.getDouble("PRICE") + "\t"
		  + flights.getString("CURR"));
		}
		  //断开连接
          myConnection.disconnect();
       } else{
    	   System.out.println(false);
    	   
       }
		 
    }
}
