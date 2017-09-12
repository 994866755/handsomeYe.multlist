package com.example.kylinarm.multilisttest.Entity;

import com.example.kylinarm.multilisttest.MultiListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class TestOneEntity implements MultiListEntity<TestOneEntity.One> {

    public String q1;
    public String q2;
    public String q3;
    public List<One> ones;

    @Override
    public List<One> getMultiListChildData() {
        return ones;
    }

    @Override
    public String getData() {
        return null;
    }

    public static class One implements MultiListEntity<One.Two>{

        public String w1;
        public String w2;
        public String t2;
        public List<Two> twos;

        @Override
        public List<One.Two> getMultiListChildData() {
            return twos;
        }

        @Override
        public String getData() {
            return t2;
        }

        public static class Two implements MultiListEntity<Two.Three>{
            public int e1;
            public String e2;
            public List<Three> threes;

            @Override
            public List<Two.Three> getMultiListChildData() {
                return threes;
            }

            @Override
            public String getData() {
                return e2;
            }

            public static class Three implements MultiListEntity<String>{

                public String r3;
                public List<String> strings = new ArrayList<>();

                @Override
                public List<String> getMultiListChildData() {
                    return strings;
                }

                @Override
                public String getData() {
                    return r3;
                }
            }

        }

    }


}
