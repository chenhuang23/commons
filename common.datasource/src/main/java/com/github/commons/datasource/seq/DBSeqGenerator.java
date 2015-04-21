package com.github.commons.datasource.seq;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <pre>
 *  文件功能: 基于数据库的ID生成器
 *  需要建表（默认为:seq_gen，可以设置 tableName 属性修改）
 *  
 *     column       type             memo
 * 1.    name        varchar(16)    序号关键字
 * 2.    seq        bigtable(20)    seq值
 * 3.    min        bigtable(20)    初始值，一开始从该值开始
 * 4.    max        bigtable(20)    最大值，达到最大值后，数据将会从初始值开始
 *  
 * @author xiaofeng.zhouxf
 * </pre>
 */
public class DBSeqGenerator implements SeqGenerator<Long> {

	private static final Logger logger = LoggerFactory
			.getLogger(DBSeqGenerator.class);

	private static final int DEFAULT_STEP = 500;
	private static final String SEQ_GEN = "seq_gen";

	private static final String SELECT_SEQ = "select name,seq,min,max from {0} where name = ?";
	private static final String UPDATE_SEQ = "update {0} set seq =? where name = ? and seq = ?";

	// 表名
	private String tableName = SEQ_GEN;
	// 步长，每次缓存ID的数量
	private int step = DEFAULT_STEP;
	// 指针
	private AtomicInteger index = new AtomicInteger(0);
	// 当前基值
	private long seqBase = -1L;
	// 失败重试次数
	private int retryTime;

	// 针对某个key的id生成
	private String key;

	private DataSource dataSource;

	public Long generate() {

		// 获取id
		long gen = getGen();

		if (gen != -1) {
			return gen;
		}

		// 获取下一批数据
		synchronized (key.intern()) {
			try {
				long seq = 0;
				long min = 0;
				long max = 0;
				long newSeq = 0L;
				long newBase = 0L;
				int retryLoop = retryTime;
				while (retryLoop-- > 0) {
					// 获取id
					gen = getGen();

					if (gen != -1) {
						return gen;
					}

					ResultSet queryResult = query(key);

					if (queryResult.next()) {
						// key,seq,min,max
						seq = queryResult.getLong(2);
						min = queryResult.getLong(3);
						max = queryResult.getLong(4);

					} else {
						throw new SeqGenException(
								MessageFormat.format(
										"seq gen exception. {1} size is {2}",
										new Object[] { key,
												queryResult.getFetchSize() }));
					}

					// 当当前seq小于最小值或者加上步频大于最大值时，初始化为最小值加上步频
					if (seq < min || seq + step > max) {
						newSeq = min + step;
						newBase = min;
					} else {
						newSeq = seq + step;
						newBase = seq;
					}

					if (update(newSeq, key, seq) == 1) {
						// 成功才更新所有值
						seqBase = newBase;
						index.set(0);
						return newSeq;
					}
				}

			} catch (Throwable e) {
				logger.error("seq gen exception.", e);
				throw new SeqGenException("seq gen exception.", e);
			}

			// 生成失败就抛异常
			throw new SeqGenException("seq gen failed.");
		}
	}

	private long getGen() {
		int currentIndex = index.incrementAndGet();
		if (seqBase != -1 && currentIndex < step) {
			return seqBase + currentIndex;
		}
		return -1;
	}

	private ResultSet query(final String key) throws SQLException {

		String querySelect = MessageFormat.format(SELECT_SEQ, tableName);

		checkDataSource();

		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(
				querySelect);
		pstmt.setString(1, key);

		return pstmt.executeQuery();
	}

	private int update(final long newSeq, final String key,
			final long currentSeq) throws SQLException {
		checkDataSource();

		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(
				MessageFormat.format(UPDATE_SEQ, tableName));
		pstmt.setFetchSize(1);
		pstmt.setLong(1, newSeq); // new
		pstmt.setString(2, key); // key
		pstmt.setLong(3, currentSeq);// current
		return pstmt.executeUpdate();
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	private void checkDataSource() throws SQLException {
		if (dataSource == null || dataSource.getConnection() == null) {
			// 生成失败就抛异常
			throw new SeqGenException("seq gen failed.DataSource error.");
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}

	public long getIndex() {
		return index.get();
	}

	public void setKey(String key) {
		this.key = key;
	}

}
