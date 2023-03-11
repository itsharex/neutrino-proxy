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
package fun.asgc.neutrino.proxy.server.dal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.asgc.neutrino.core.annotation.Component;
import fun.asgc.neutrino.core.annotation.Param;
import fun.asgc.neutrino.core.aop.Intercept;
import fun.asgc.neutrino.core.db.annotation.Delete;
import fun.asgc.neutrino.core.db.annotation.ResultType;
import fun.asgc.neutrino.core.db.annotation.Select;
import fun.asgc.neutrino.core.db.annotation.Update;
import fun.asgc.neutrino.core.db.page.PageInfo;
import fun.asgc.neutrino.proxy.server.controller.req.LicenseListReq;
import fun.asgc.neutrino.proxy.server.controller.res.LicenseListRes;
import fun.asgc.neutrino.proxy.server.dal.entity.LicenseDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author: aoshiguchen
 * @date: 2022/8/6
 */
@Intercept(ignoreGlobal = true)
@Component
@Mapper
public interface LicenseMapper extends BaseMapper<LicenseDO> {

	/**
	 * 查询license分页
	 * @param pageInfo
	 * @param req
	 */
	void page(PageInfo pageInfo, LicenseListReq req);

	@ResultType(LicenseListRes.class)
	@Select("select * from license where enable = 1")
	List<LicenseListRes> list();

	@ResultType(LicenseDO.class)
	@Select("select * from license")
	List<LicenseDO> listAll();

	@ResultType(LicenseDO.class)
	@Select("select * from `license` where user_id = :userId")
	List<LicenseDO> listByUserId(@Param("userId") Integer userId);

	/**
	 * 新增license
	 * @param license
	 */
	int add(LicenseDO license);

	@Update("update `license` set enable = :enable, update_time = :updateTime where id = :id")
	void updateEnableStatus(@Param("id") Integer id, @Param("enable") Integer enable, @Param("updateTime") Date updateTime);

	@Update("update `license` set is_online = :isOnline, update_time = :updateTime where id = :id")
	default void updateOnlineStatus(@Param("id") Integer id, @Param("isOnline") Integer isOnline, @Param("updateTime") Date updateTime) {
		this.update(null, new LambdaUpdateWrapper<LicenseDO>()
				.eq(LicenseDO::getId, id)
				.set(LicenseDO::getIsOnline, isOnline)
				.set(LicenseDO::getUpdateTime, updateTime)
		);
	}

	@Update("update `license` set is_online = :isOnline, update_time = :updateTime")
	void updateOnlineStatus(@Param("isOnline") Integer isOnline, @Param("updateTime") Date updateTime);

	@Update("update `license` set `key` = :key,update_time = :updateTime where id = :id")
	void reset(@Param("id") Integer id, @Param("key") String key, @Param("updateTime") Date updateTime);

	@Delete("delete from `license` where id = ?")
	void delete(Integer id);

	@Select("select * from `license` where id = ?")
	default LicenseDO findById(Integer id) {
		return this.selectById(id);
	}

	@Update("update `license` set name = :name, update_time = :updateTime where id = :id")
	void update(@Param("id") Integer id, @Param("name") String name, @Param("updateTime") Date updateTime);

	@ResultType(LicenseDO.class)
	@Select("select * from `license` where id in (:ids)")
	default List<LicenseDO> findByIds(@Param("ids")Set<Integer> ids) {
		return selectBatchIds(ids);
	}

	@ResultType(LicenseDO.class)
	@Select("select * from `license` where user_id = :userId and name =:name limit 0,1")
	default LicenseDO checkRepeat(@Param("userId") Integer userId, @Param("name") String name) {
		return this.selectOne(new LambdaQueryWrapper<LicenseDO>()
				.eq(LicenseDO::getUserId, userId)
				.eq(LicenseDO::getName, name)
				.last("limit 1")
		);
	}

	@ResultType(LicenseDO.class)
	@Select("select * from `license` where user_id = :userId and name =:name and id not in (:excludeIds) limit 0,1")
	LicenseDO checkRepeat(@Param("userId") Integer userId, @Param("name") String name, @Param("excludeIds") Set<Integer> excludeIds);

	@Select("select * from `license` where `key` = ?")
	default LicenseDO findByKey(String licenseKey) {
		return selectOne(new LambdaQueryWrapper<LicenseDO>()
				.eq(LicenseDO::getKey, licenseKey)
		);
	}
}
