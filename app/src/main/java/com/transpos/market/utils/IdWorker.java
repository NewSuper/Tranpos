package com.transpos.market.utils;

import java.util.HashSet;
import java.util.Set;

public class IdWorker {

	private final long workerId;
	private final long datacenterId;
	private final long idepoch;

	private final long workerIdBits = 5L;
	private final long datacenterIdBits = 5L;
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

	private final long sequenceBits = 12L;
	private final long workerIdShift = sequenceBits;
	private final long datacenterIdShift = sequenceBits + workerIdBits;
	private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;
	private long sequence;

	public IdWorker(long workerId, long datacenterId, long sequence) {
		this(workerId, datacenterId, sequence, 1344322705519L);
	}

	//
	public IdWorker(long workerId, long datacenterId, long sequence, long idepoch) {
		this.workerId = workerId;
		this.datacenterId = datacenterId;
		this.sequence = sequence;
		this.idepoch = idepoch;
		if (workerId < 0 || workerId > maxWorkerId) {
			throw new IllegalArgumentException("workerId is illegal: " + workerId);
		}
		if (datacenterId < 0 || datacenterId > maxDatacenterId) {
			throw new IllegalArgumentException("datacenterId is illegal: " + workerId);
		}
	}

	public long getDatacenterId() {
		return datacenterId;
	}

	public long getWorkerId() {
		return workerId;
	}

	public long getTime() {
		return System.currentTimeMillis();
	}

	public long getId() {
		long id = nextId();
		return id;
	}

	private synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new IllegalStateException("Clock moved backwards.");
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		long id = ((timestamp - idepoch) << timestampLeftShift)//
				| (datacenterId << datacenterIdShift)//
				| (workerId << workerIdShift)//
				| sequence;
		return id;
	}

	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {

		IdWorker idWorker = new IdWorker(0, 0, 0);

		Set<Long> ids = new HashSet<Long>();
		for (int i = 0; i < 1000000; i++) {
			long id = idWorker.getId();
			if (ids.contains(id)) {
				System.out.println("Duplicate id:" + id);
				System.exit(1);
			}
			ids.add(id);

			System.out.println(id);
		}
	}
}