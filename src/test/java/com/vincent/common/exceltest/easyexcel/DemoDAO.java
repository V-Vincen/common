package com.vincent.common.exceltest.easyexcel;

import java.util.List;

/**
 * @author vincent
 * 假设这个是你的 DAO 存储。当然还要这个类让 spring 管理，当然你不用需要存储，也不需要这个类。
 **/
public class DemoDAO {
    public void save(List<DemoReadDto> list) {
        // 如果是 mybatis，尽量别直接调用多次 insert，自己写一个mapper里面新增一个方法 batchInsert，所有数据一次性插入
    }
}