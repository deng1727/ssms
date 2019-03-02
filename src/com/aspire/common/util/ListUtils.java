package com.aspire.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
	 
	 
    /**
     * ��һ��List���ֳ�n��list,��Ҫͨ��ƫ������ʵ�ֵ�
     *
     * @param source Դ����
     * @param limit ���ֵ
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int limit) {
        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int listCount = (source.size() - 1) / limit + 1;
        int remaider = source.size() % listCount; // (�ȼ��������)
        int number = source.size() / listCount; // Ȼ������
        int offset = 0;// ƫ����
        for (int i = 0; i < listCount; i++) {
            List<T> value;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
