package com.aladdin.render;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.util.StringUtils;

import com.aladdin.render.bean.Cpu;
import com.aladdin.render.bean.Memory;

/**
 * 获取机器信息方法集合
 */
public class Top {

	public static Cpu cpu() {
		final Sigar sigar = new Sigar();
		Cpu cpu = new Cpu();
		try {
			CpuPerc[] cpus = sigar.getCpuPercList();
			if (cpus != null) {
				cpu.setCpuNum(cpus.length);
				for (int i = 0; i < cpus.length; i++) {
					cpu.setCpuWait(cpu.getCpuWait()+cpus[i].getWait());
					cpu.setCpuIdle(cpu.getCpuIdle()+cpus[i].getIdle());
					cpu.setCpuSys(cpu.getCpuSys()+cpus[i].getSys());
					cpu.setCpuCombined(cpu.getCpuCombined()+cpus[i].getCombined());
					cpu.setCpuUser(cpu.getCpuUser()+cpus[i].getUser());
				}
			}
		} catch (SigarException e) {
			e.printStackTrace();
		} finally {
			sigar.close();
		}
		return cpu;
	}

	public static Memory memory() {
		Sigar sigar = new Sigar();
		Memory memory = new Memory();
		try {
			Mem mem = sigar.getMem();
			memory.setMemoryFreeRate(mem.getFreePercent());
			memory.setMemoryUsedRate(mem.getUsedPercent());
			memory.setMomoryTotal(mem.getTotal());
		} catch (SigarException e) {
			e.printStackTrace();
		} finally {
			sigar.close();
		}
		return memory;
	}

	public static int count() {
		return Counter.getInstance().getNum();
	}

	public static String ip(String netcard) {

		if (StringUtils.isEmpty(netcard)) {
			return "";
		}
		Enumeration<NetworkInterface> ns = null;
		try {
			ns = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// ignored...
		}
		while (ns != null && ns.hasMoreElements()) {
			NetworkInterface n = ns.nextElement();
			if (netcard.equalsIgnoreCase(n.getName())) {
				Enumeration<InetAddress> is = n.getInetAddresses();
				while (is.hasMoreElements()) {
					InetAddress i = is.nextElement();
					if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
							&& !isSpecialIp(i.getHostAddress())) {
						String finalIp = i.getHostAddress();
						if (!StringUtils.isEmpty(finalIp) && finalIp.startsWith("/")) {
							return finalIp.substring(0);
						} else {
							return finalIp;
						}
					}
				}
			}
		}
		return "";
	}

	private static boolean isSpecialIp(String ip) {
		if (ip.contains(":"))
			return true;
		if (ip.startsWith("127."))
			return true;
		if (ip.startsWith("169.254."))
			return true;
		if (ip.equals("255.255.255.255"))
			return true;
		return false;
	}
/*	
	public static void main(String[] args) {
		while(true){
			System.out.println(ip("WLAN2"));
			System.out.println(cpu());
			System.out.println(memory());
			System.out.println(count());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
}
