package com.github.commons.datasource.seq;

/**
 * ID生成器
 * 
 * @author xiaofeng.zhouxf
 *
 * @param <T>
 */
public interface SeqGenerator<T> {

	/**
	 * 
	 * <pre>
	 * Description：ID生成器
	 * @return
	 * @return T
	 * @author name：xiaofeng.zhou
	 * @throws SeqGenException
	 * </pre>
	 *
	 */
	public T generate();

}
