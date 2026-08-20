package com.xsdq.polaris.infrastructure.identity;

/**
 * A simple snowflake distributed id generator algorithm from <a
 * href="https://pdai.tech/md/algorithm/alg-domain-id-snowflake.html">https://pdai.tech/md/algorithm/alg-domain-id-snowflake.html</a>
 *
 * @author XiaoYu
 * @since 2026/8/19 17:41
 */
public class SnowflakeIdentityGenerator implements IdentityGenerator<Long> {

  private static final long TWEPOCH = 1420041600000L; // 开始时间截 (2015-01-01)
  private static final long workerIdBits = 5L; // 机器id所占的位数
  private static final long datacenterIdBits = 5L; // 数据标识id所占的位数
  private static final long maxWorkerId =
      -1L ^ (-1L << workerIdBits); // 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
  private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 支持的最大数据标识id，结果是31
  private static final long sequenceBits = 12L; // 序列在id中占的位数
  private static final long workerIdShift = sequenceBits; // 机器ID向左移12位
  private static final long datacenterIdShift = sequenceBits + workerIdBits; // 数据标识id向左移17位(12+5)
  private static final long timestampLeftShift =
      sequenceBits + workerIdBits + datacenterIdBits; // 时间截向左移22位(5+5+12)
  private static final long sequenceMask =
      -1L ^ (-1L << sequenceBits); // 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)

  private final long workerId; // 工作机器ID(0~31)
  private final long datacenterId; // 数据中心ID(0~31)
  private long sequence = 0L; // 毫秒内序列(0~4095)
  private long lastTimestamp = -1L; // 上次生成ID的时间截

  public SnowflakeIdentityGenerator(long workerId, long datacenterId) {
    if (workerId > maxWorkerId || workerId < 0) {
      throw new IllegalArgumentException(
          String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    }
    if (datacenterId > maxDatacenterId || datacenterId < 0) {
      throw new IllegalArgumentException(
          String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
  }

  protected synchronized long nextId() {
    long timestamp = timeGen();

    if (timestamp < lastTimestamp) {
      throw new RuntimeException(
          String.format(
              "Clock moved backwards.  Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }

    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      // 毫秒内序列溢出
      if (sequence == 0) {
        // 阻塞到下一个毫秒,获得新的时间戳
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;
    }

    lastTimestamp = timestamp;

    return ((timestamp - TWEPOCH) << timestampLeftShift) //
        | (datacenterId << datacenterIdShift) //
        | (workerId << workerIdShift) //
        | sequence;
  }

  protected long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  protected long timeGen() {
    return System.currentTimeMillis();
  }

  @Override
  public Identity<Long> generate() {
    return Identity.create(nextId());
  }
}
