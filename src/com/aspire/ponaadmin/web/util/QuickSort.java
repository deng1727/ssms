package com.aspire.ponaadmin.web.util;
 
import java.util.List;
 

public class QuickSort
{
	public QuickSort()
	{
	}

	/**
	 * sort 只是为了便于观察分析才设了这个方法，可有可无。
	 * 
	 * @param number
	 *            int[] 数组
	 */
	public static void sort( List dataList)
	{

		thirdsort(dataList, 0, dataList.size() - 1);
	}

	/**
	 * swap 交换值方法
	 * 
	 * @param number
	 *            int[]
	 * @param i
	 *            int
	 * @param j
	 *            int
	 */
	private static void swap( List dataList , int i , int j)
	{
		Object ti = dataList.get(i);
		Object tj = dataList.get(j);
		if(j > i )
		{
			dataList.remove(j);
			dataList.remove(i);
			dataList.add(i, tj);
			dataList.add(j, ti);

		}
		else if(j < i )
		{
			dataList.remove(i);

			dataList.remove(j);
			dataList.add(j, ti);

			dataList.add(i, tj);

		}
	}

	private static void thirdsort( List dataList , int left , int right)
	{
		if(left < right )
		{
			int q = partition(dataList, left, right);
			thirdsort(dataList, left, q - 1);
			thirdsort(dataList, q + 1, right);
		}
	}

	/**
	 * partition 在轴设置方面有优点
	 * 
	 * @param number
	 *            int[]
	 * @param left
	 *            int
	 * @param right
	 *            int
	 * @return int
	 */
	private static int partition( List dataList , int left , int right) // 以右端元素为轴,划分数组,返回划分点
	{
		Comparable s = (Comparable) dataList.get(right); // 先以右边最后一个为轴
		int i = left - 1;
		for(int j = left; j < right; j++)
		{
			if(s.compareTo(dataList.get(j)) <= 0 )
			{
				i++;
				swap(dataList, i, j);
			}
		}
		swap(dataList, i + 1, right);
		return i + 1;
	}
}
