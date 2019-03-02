package com.aspire.ponaadmin.web.util;
 
import java.util.List;
 

public class QuickSort
{
	public QuickSort()
	{
	}

	/**
	 * sort ֻ��Ϊ�˱��ڹ۲����������������������п��ޡ�
	 * 
	 * @param number
	 *            int[] ����
	 */
	public static void sort( List dataList)
	{

		thirdsort(dataList, 0, dataList.size() - 1);
	}

	/**
	 * swap ����ֵ����
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
	 * partition �������÷������ŵ�
	 * 
	 * @param number
	 *            int[]
	 * @param left
	 *            int
	 * @param right
	 *            int
	 * @return int
	 */
	private static int partition( List dataList , int left , int right) // ���Ҷ�Ԫ��Ϊ��,��������,���ػ��ֵ�
	{
		Comparable s = (Comparable) dataList.get(right); // �����ұ����һ��Ϊ��
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
