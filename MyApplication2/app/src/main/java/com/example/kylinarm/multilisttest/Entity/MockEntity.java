package com.example.kylinarm.multilisttest.Entity;

import com.example.kylinarm.multilisttest.MultiListEntity;

import java.util.List;

/**
 * Created by kylinARM on 2017/9/10.
 */

public class MockEntity extends BaseMultiEntity<MockEntity.Onedata>{

    public String title;
    public List<Onedata> onedata;

    @Override
    public List<Onedata> getMultiListChildData() {
        return onedata;
    }

    @Override
    public String getData() {
        return null;
    }

    public static class Onedata extends BaseMultiEntity<Onedata.Twodata>{

        public String name;
        public List<Twodata> twodata;

        @Override
        public List<Twodata> getMultiListChildData() {
            return twodata;
        }

        @Override
        public String getData() {
            return name;
        }

        public static class Twodata extends BaseMultiEntity<Twodata.Threedata>{

            public String name;
            public List<Threedata> threedata;

            @Override
            public List<Threedata> getMultiListChildData() {
                return threedata;
            }

            @Override
            public String getData() {
                return name;
            }

            public static class Threedata extends BaseMultiEntity<MockDetailsEntity>{
                public String name;
                public List<MockDetailsEntity> four;

                @Override
                public List<MockDetailsEntity> getMultiListChildData() {
                    return four;
                }

                @Override
                public String getData() {
                    return name;
                }
            }

        }

    }

}
