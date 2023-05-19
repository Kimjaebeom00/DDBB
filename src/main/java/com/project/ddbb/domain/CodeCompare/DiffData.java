package com.project.ddbb.domain.CodeCompare;

public class DiffData
{
    public int Length;
    public int[] data;
    public boolean[] modified;

    public DiffData(int[] initData) {
        data = initData;
        Length = initData.length;
        modified = new boolean[Length + 2];
    }
}