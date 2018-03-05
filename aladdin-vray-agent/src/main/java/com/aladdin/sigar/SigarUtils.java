package com.aladdin.sigar;

import java.util.Map;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Who;

import com.google.common.collect.Maps;

public class SigarUtils {
	
	private static final Sigar sigar = new Sigar();
 	
	public static Map<String,Object> cpu(){
		Map map = Maps.newHashMap();
		CpuPerc cpus[] = null;
		Who who[] = null;
		long[] pids = null;
		try {
			cpus = sigar.getCpuPercList();
			who = sigar.getWhoList();
			pids = sigar.getProcList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		 for (int i = 0; i < cpus.length; i++) {
//              printCpuPerc(cpuList[i]);
			 System.out.println(cpus[i].getCombined());
         }
		 for (int i = 0; i < who.length; i++) {
//              printCpuPerc(cpuList[i]);
			 System.out.println(who[i].getDevice());
		 }
		 for(int i=0;i<pids.length;i++){
			 try {
				ProcState prs = sigar.getProcState(pids[i]);
				System.out.println(prs);
			} catch (SigarException e) {
				e.printStackTrace();
			}
		 }
		return null;
	}
	/*public static void main(String[] args) {
		SigarUtils.cpu();
	}*/
}
