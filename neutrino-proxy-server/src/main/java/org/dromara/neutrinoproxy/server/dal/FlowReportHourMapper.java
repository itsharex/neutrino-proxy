/**
 * Copyright (c) 2022 aoshiguchen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.dromara.neutrinoproxy.server.dal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dromara.neutrinoproxy.server.dal.entity.FlowReportHourDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface FlowReportHourMapper extends BaseMapper<FlowReportHourDO> {
    default void clean(Date date) {
        this.delete(new LambdaQueryWrapper<FlowReportHourDO>()
                .lt(FlowReportHourDO::getDate, date)
        );
    }

    default void deleteByDateStr(String dateStr) {
        this.delete(new LambdaQueryWrapper<FlowReportHourDO>()
                .eq(FlowReportHourDO::getDateStr, dateStr)
        );
    }

    default List<FlowReportHourDO> findListByDateRange(Date startDate, Date endDate) {
        return this.selectList(new LambdaQueryWrapper<FlowReportHourDO>()
                .ge(FlowReportHourDO::getDate, startDate)
                .le(FlowReportHourDO::getDate, endDate)
        );
    }

}
