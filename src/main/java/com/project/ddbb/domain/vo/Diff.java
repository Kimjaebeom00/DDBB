package com.project.ddbb.domain.vo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class Diff
{
    public static class Item
    {
        public int StartA;
        public int StartB;
        public int deletedA;
        public int insertedB;
    }

    private static class SMSRD
    {
        public int x, y;
    }

    public static Item[] DiffText(String TextA, String TextB)
    {
        return DiffText(TextA, TextB, false, false, false);
    }

    public static Item[] DiffText(String TextA, String TextB, boolean trimSpace, boolean ignoreSpace, boolean ignoreCase)
    {
        Hashtable<String, Integer> h = new Hashtable<>(TextA.length() + TextB.length());

        DiffData DataA = new DiffData(DiffCodes(TextA, h, trimSpace, ignoreSpace, ignoreCase));

        DiffData DataB = new DiffData(DiffCodes(TextB, h, trimSpace, ignoreSpace, ignoreCase));

        int MAX = DataA.Length + DataB.Length + 1;
        int[] DownVector = new int[2 * MAX + 2];
        int[] UpVector = new int[2 * MAX + 2];

        LCS(DataA, 0, DataA.Length, DataB, 0, DataB.Length, DownVector, UpVector);

        Optimize(DataA);
        Optimize(DataB);
        return CreateDiffs(DataA, DataB);
    }

    private static void Optimize(DiffData Data)
    {
        int StartPos, EndPos;

        StartPos = 0;
        while (StartPos < Data.Length)
        {
            while ((StartPos < Data.Length) && (Data.modified[StartPos] == false))
                StartPos++;
            EndPos = StartPos;
            while ((EndPos < Data.Length) && (Data.modified[EndPos] == true))
                EndPos++;
            if ((EndPos < Data.Length) && (Data.data[StartPos] == Data.data[EndPos]))
            {
                Data.modified[StartPos] = false;
                Data.modified[EndPos] = true;
            }
            else
            {
                StartPos = EndPos;
            }
        }
    }

    public static Item[] DiffInt(int[] ArrayA, int[] ArrayB)
    {
        DiffData DataA = new DiffData(ArrayA);
        DiffData DataB = new DiffData(ArrayB);
        int MAX = DataA.Length + DataB.Length + 1;
        int[] DownVector = new int[2 * MAX + 2];
        int[] UpVector = new int[2 * MAX + 2];

        LCS(DataA, 0, DataA.Length, DataB, 0, DataB.Length, DownVector, UpVector);

        return CreateDiffs(DataA, DataB);
    }

    private static int[] DiffCodes(String aText, Hashtable<String, Integer> h, boolean trimSpace, boolean ignoreSpace, boolean ignoreCase)
    {
        String[] Lines;
        int[] Codes;
        int lastUsedCode = h.size();
        Integer aCode;
        String s;

        aText = aText.replace("\r", "");
        Lines = aText.split("\n");

        Codes = new int[Lines.length];

        for (int i = 0; i < Lines.length; ++i)
        {
            s = Lines[i];
            if (trimSpace)
                s = s.trim();

            if (ignoreSpace)
            {
                s = Pattern.compile("\\s+").matcher(s).replaceAll(" ");
            }

            if (ignoreCase)
                s = s.toLowerCase();

            aCode = h.get(s);
            if (aCode == null)
            {
                lastUsedCode++;
                h.put(s, lastUsedCode);
                Codes[i] = lastUsedCode;
            }
            else
            {
                Codes[i] = aCode;
            }
        }
        return Codes;
    }

    private static SMSRD SMS(DiffData DataA, int LowerA, int UpperA, DiffData DataB, int LowerB, int UpperB, int[] DownVector, int[] UpVector)
    {
        SMSRD ret = new SMSRD();
        int MAX = DataA.Length + DataB.Length + 1;

        int DownK = LowerA - LowerB;
        int UpK = UpperA - UpperB;

        int Delta = (UpperA - LowerA) - (UpperB - LowerB);
        boolean oddDelta = (Delta & 1) != 0;

        int DownOffset = MAX - DownK;
        int UpOffset = MAX - UpK;

        int MaxD = ((UpperA - LowerA + UpperB - LowerB) / 2) + 1;

        DownVector[DownOffset + DownK + 1] = LowerA;
        UpVector[UpOffset + UpK - 1] = UpperA;

        for (int D = 0; D <= MaxD; D++)
        {
            for (int k = DownK - D; k <= DownK + D; k += 2)
            {
                int x, y;
                if (k == DownK - D)
                {
                    x = DownVector[DownOffset + k + 1];
                }
                else
                {
                    x = DownVector[DownOffset + k - 1] + 1;
                    if (k < DownK + D && DownVector[DownOffset + k + 1] >= x)
                        x = DownVector[DownOffset + k + 1];
                }
                y = x - k;

                while (x < UpperA && y < UpperB && DataA.data[x] == DataB.data[y])
                {
                    x++;
                    y++;
                }
                DownVector[DownOffset + k] = x;

                if (oddDelta && UpK - D < k && k < UpK + D)
                {
                    if (UpVector[UpOffset + k] <= DownVector[DownOffset + k])
                    {
                        ret.x = DownVector[DownOffset + k];
                        ret.y = DownVector[DownOffset + k] - k;

                        return ret;
                    }
                }
            }

            for (int k = UpK - D; k <= UpK + D; k += 2)
            {
                int x, y;
                if (k == UpK + D)
                {
                    x = UpVector[UpOffset + k - 1];
                }
                else
                {
                    x = UpVector[UpOffset + k + 1] - 1;
                    if (k > UpK - D && UpVector[UpOffset + k - 1] < x)
                        x = UpVector[UpOffset + k - 1];
                }
                y = x - k;
                while (x > LowerA && y > LowerB && DataA.data[x - 1] == DataB.data[y - 1])
                {
                    x--;
                    y--;
                }
                UpVector[UpOffset + k] = x;

                if (!oddDelta && (DownK - D <= k) && (k <= DownK + D))
                {
                    if (UpVector[UpOffset + k] <= DownVector[DownOffset + k])
                    {
                        ret.x = DownVector[DownOffset + k];
                        ret.y = DownVector[DownOffset + k] - k;
                        return ret;
                    }
                }
            }
        }
        throw new RuntimeException("The algorithm should never come here.");
    }

    private static void LCS(DiffData DataA, int LowerA, int UpperA, DiffData DataB, int LowerB, int UpperB, int[] DownVector, int[] UpVector)
    {
        while (LowerA < UpperA && LowerB < UpperB && DataA.data[LowerA] == DataB.data[LowerB])
        {
            LowerA++;
            LowerB++;
        }

        while (LowerA < UpperA && LowerB < UpperB && DataA.data[UpperA - 1] == DataB.data[UpperB - 1])
        {
            --UpperA;
            --UpperB;
        }

        if (LowerA == UpperA)
        {
            while (LowerB < UpperB)
            {
                DataB.modified[LowerB++] = true;
            }
        }
        else if (LowerB == UpperB)
        {
            while (LowerA < UpperA)
            {
                DataA.modified[LowerA++] = true;
            }
        }
        else
        {
            SMSRD smsrd = SMS(DataA, LowerA, UpperA, DataB, LowerB, UpperB, DownVector, UpVector);
            LCS(DataA, LowerA, smsrd.x, DataB, LowerB, smsrd.y, DownVector, UpVector);
            LCS(DataA, smsrd.x, UpperA, DataB, smsrd.y, UpperB, DownVector, UpVector);
        }
    }

    private static Item[] CreateDiffs(DiffData DataA, DiffData DataB)
    {
        ArrayList<Item> a = new ArrayList<>();
        Item aItem;
        Item[] result;

        int StartA, StartB;
        int LineA, LineB;

        LineA = 0;
        LineB = 0;
        while (LineA < DataA.Length || LineB < DataB.Length)
        {
            if (LineA < DataA.Length && !DataA.modified[LineA] && LineB < DataB.Length && !DataB.modified[LineB])
            {
                LineA++;
                LineB++;

            }
            else
            {
                StartA = LineA;
                StartB = LineB;

                while (LineA < DataA.Length && (LineB >= DataB.Length || DataA.modified[LineA]))
                    LineA++;

                while (LineB < DataB.Length && (LineA >= DataA.Length || DataB.modified[LineB]))
                    LineB++;

                if (StartA < LineA || StartB < LineB)
                {
                    aItem = new Item();
                    aItem.StartA = StartA;
                    aItem.StartB = StartB;
                    aItem.deletedA = LineA - StartA;
                    aItem.insertedB = LineB - StartB;
                    a.add(aItem);
                }
            }
        }
        result = new Item[a.size()];
        a.toArray(result);
        return result;
    }
}