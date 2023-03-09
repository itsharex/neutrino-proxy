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

import fun.asgc.neutrino.core.db.annotation.Delete;

import java.util.Date;


public interface DataCleanMapper {

    @Delete("delete from `job_log` where create_time < ?")
    void cleanJobLog(Date date);

    @Delete("delete from `user_login_record` where create_time < ?")
    void cleanUserLoginRecord(Date date);

    @Delete("delete from `client_connect_record` where create_time < ?")
    void cleanClientConnectRecord(Date date);

    @Delete("delete from `flow_report_minute` where create_time < ?")
    void cleanFlowMinuteReport(Date date);

    @Delete("delete from `flow_report_hour` where create_time < ?")
    void cleanFlowHourReport(Date date);

    @Delete("delete from `flow_report_day` where create_time < ?")
    void cleanFlowDayReport(Date date);

}
