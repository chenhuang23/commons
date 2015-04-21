package com.github.commons.cache;

import java.util.Map;

/**
 * <pre>
 * <p>文件功能: 缓存基础客户端</p>
 * 
 * <p>编程者: xiaofeng.zhou</p>
 * 
 * <p>初作时间: 2014年7月8日 下午2:55:23</p>
 * 
 * <p>版本: version 1.0 </p>
 * 
 * <p>输入说明: </p>
 * 
 * <p>输出说明: </p>
 * 
 * <p>程序流程: </p>
 * 
 * <p>============================================</p>
 * <p>修改序号:</p>
 * <p>时间:	 </p>
 * <p>修改者:  </p>
 * <p>修改内容:  </p>
 * <p>============================================</p>
 * </pre>
 */
public interface CacheClient {

	/**
	 * <pre>
	 * Description： 初始化客户端
	 * @return void
	 * @author name：xiaofeng.zhou
	 * <p>============================================</p>
	 * Modified No： 
	 * Modified By： 
	 * Modified Date： 
	 * Modified Description: 
	 * <p>============================================</p>
	 * </pre>
	 */
	public void init();

	/**
	 * 
	 * <pre>
	 * Description： 销毁客户端
	 * @return void
	 * @author name：xiaofeng.zhou
	 * <p>============================================</p>
	 * Modified No： 
	 * Modified By： 
	 * Modified Date： 
	 * Modified Description: 
	 * <p>============================================</p>
	 * </pre>
	 *
	 */
	public void destroy();

	/**
	 * <pre>
	 * Description：设置配置信息
	 * @param config
	 * @return void
	 * @author name：xiaofeng.zhou
	 * <p>============================================</p>
	 * Modified No： 
	 * Modified By： 
	 * Modified Date： 
	 * Modified Description: 
	 * <p>============================================</p>
	 * </pre>
	 */
	public void setConfigs(Map<String, String> config);

	/**
	 * 
	 * <pre>
	 * Description：设置服务器地址列表
     *
     *  格式为参考http://www.ietf.org/rfc/rfc2396.txt
     *    例如:
     *     schema://username:password@ip:port/ , 多个地址以逗号分割
	 * @param servers
	 * @return void
	 * @author name：xiaofeng.zhou
	 * <p>============================================</p>
	 * Modified No： 
	 * Modified By： 
	 * Modified Date： 
	 * Modified Description: 
	 * <p>============================================</p>
	 * </pre>
	 *
	 */
	public void setServers(String servers);
}
