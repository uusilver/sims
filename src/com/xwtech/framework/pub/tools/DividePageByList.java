package com.xwtech.framework.pub.tools;
import java.util.*;
public class DividePageByList
{
	int per_page_num=10;
	public DividePageByList()
	{
		// TODO Auto-generated constructor stub
	}
	
	public Map deal(int currPage,List list)
	{
		int recordTotalNum=list.size();
		int totalPage=getTotalpage(recordTotalNum);
		
		Map map=new HashMap();
		if(currPage<totalPage)
		{
			map.put("list", list.subList((currPage-1)*per_page_num, currPage*per_page_num));
		}
		if(currPage==totalPage)
		{
			map.put("list", list.subList((currPage-1)*per_page_num, recordTotalNum));
		}
		if(currPage>totalPage)
		{
			currPage=totalPage;
			map.put("list", list.subList((currPage-1)*per_page_num, recordTotalNum));
		}
		map.put("currPage", ""+currPage);//currPage;当前第几页
		map.put("recordTotalNum", ""+recordTotalNum);//共多少条记录
		map.put("totalPage", ""+totalPage);//共多少页
		
		return map;
	}
	/*
	 * have how many page
	 */
	public int getTotalpage(int recordTotalNum)
	{
		
		int totalPage = 1;//共多少页
		try 
		{

			if (recordTotalNum % per_page_num == 0) 
			{
				totalPage = recordTotalNum / per_page_num;
			} 
			else if (recordTotalNum % per_page_num != 0)
			{
				totalPage = (int) (recordTotalNum / per_page_num) + 1;
			}
			

			// ***************//
			if (totalPage == 0) //当recordTotalNum为0时,即一条记录都没有时
			{
				totalPage = 1;
			}
			// ***************//

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}

		return totalPage;
	}

}
